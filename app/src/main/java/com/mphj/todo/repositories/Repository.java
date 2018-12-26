package com.mphj.todo.repositories;

import android.content.Context;

import com.mphj.todo.repositories.db.Db;
import com.mphj.todo.repositories.rest.RetrofitClientInstance;
import com.mphj.todo.repositories.rest.TodoService;

import androidx.room.Room;


public class Repository {

    private static final String DATABASE_NAME = "todo_db";

    private static volatile Db db;

    public static TodoService todoService() {
        return RetrofitClientInstance.getRetrofitInstance().create(TodoService.class);
    }

    public static Db db(Context context) {
        if (db == null) {

            synchronized (Db.class) {
                if (db == null) db = Room.databaseBuilder(context, Db.class, DATABASE_NAME).build();
            }
        }

        return db;
    }

}
