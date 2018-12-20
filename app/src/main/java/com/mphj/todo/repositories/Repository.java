package com.mphj.todo.repositories;

import com.mphj.todo.repositories.rest.RetrofitClientInstance;
import com.mphj.todo.repositories.rest.TodoService;

public class Repository {

    public static TodoService todoService() {
        return RetrofitClientInstance.getRetrofitInstance().create(TodoService.class);
    }

}
