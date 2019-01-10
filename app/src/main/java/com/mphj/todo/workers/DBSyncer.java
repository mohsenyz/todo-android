package com.mphj.todo.workers;

import android.content.Context;
import android.util.Log;

import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.repositories.rest.models.requests.PostTodoRequest;
import com.mphj.todo.repositories.rest.models.responses.PostTodoResponse;
import com.mphj.todo.repositories.rest.models.responses.SavedObjectResponse;
import com.mphj.todo.repositories.rest.models.responses.UserSyncResponse;
import com.mphj.todo.utils.Auth;
import com.mphj.todo.utils.Prefs;
import com.mphj.todo.utils.RealTimeDatabase;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBSyncer extends Worker {

    public static String NAME = "db_sync";

    long lastUpdateTime;

    public DBSyncer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        lastUpdateTime = RealTimeDatabase.getLastUpdate(getApplicationContext());
        Log.d(getClass().getSimpleName(), "work started");
        try {
            Response<UserSyncResponse> sRep = Repository.todoService()
                    .sync(lastUpdateTime, Auth.token(getApplicationContext()))
                    .execute();
            if (sRep.isSuccessful()) {
                if (sRep.body().status == 200) {
                    UserSyncResponse sBody = sRep.body();
                    RealTimeDatabase.setUpdatedAt(sBody.updateTime, getApplicationContext());
                    List<Todo> todoList = sBody.todoList;
                    for (Todo obj : todoList) {
                        try {
                            obj.localId = Repository.db(getApplicationContext()).todoDao().byLocalId(obj.id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (obj.localId == 0) {
                            Repository.db(getApplicationContext()).todoDao().insert(obj);
                        } else {
                            Repository.db(getApplicationContext()).todoDao().update(obj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }

        lastUpdateTime = RealTimeDatabase.getLastUpdate(getApplicationContext());


        // List of changes
        List<Todo> todoList = Repository.db(getApplicationContext()).todoDao().allSince(lastUpdateTime);

        // Making request
        PostTodoRequest ptReq = new PostTodoRequest();
        ptReq.todoList = todoList;
        ptReq.lastUpdateTime = lastUpdateTime;

        // Send request
        try {
            Response<PostTodoResponse> response = Repository.todoService()
                    .postTodo(ptReq, Auth.token(getApplicationContext()))
                    .execute();
            if (response.isSuccessful()) {
                PostTodoResponse ptResp = response.body();

                if (ptResp.status == 200) {
                    RealTimeDatabase.setUpdatedAt(ptResp.lastUpdateTime, getApplicationContext());
                    List<SavedObjectResponse> list = ptResp.todoList;
                    for (SavedObjectResponse obj : list) {
                        Repository.db(getApplicationContext()).todoDao().setServerId(obj.localId, obj.serverId);
                    }
                } else {
                    return Result.retry();
                }
            } else {
                return Result.retry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }

        return Result.success();
    }
}
