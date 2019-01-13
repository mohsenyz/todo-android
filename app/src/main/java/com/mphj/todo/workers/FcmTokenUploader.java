package com.mphj.todo.workers;

import android.content.Context;

import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.rest.models.responses.FcmUploadTokenResponse;
import com.mphj.todo.utils.Auth;
import com.mphj.todo.utils.Prefs;

import androidx.annotation.NonNull;
import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Response;

import static com.mphj.todo.services.AppFirebaseMessagingService.PREFS_KEY_FCM_TOKEN;

public class FcmTokenUploader extends Worker {

    public static String NAME = "fcm_token_uploader";
    public static String ARG_TOKEN = "token";

    public FcmTokenUploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<FcmUploadTokenResponse> response = Repository.todoService()
                    .renewFcmToken(getInputData().getString(ARG_TOKEN), Auth.token(getApplicationContext()))
                    .execute();
            if (response.isSuccessful() && response.body().status == 200) {
                Prefs.setNull(PREFS_KEY_FCM_TOKEN, getApplicationContext());
                return Result.success();
            } else {
                return Result.retry();
            }
        } catch (Exception e) {
            return Result.retry();
        }
    }
}
