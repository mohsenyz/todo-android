package com.mphj.todo.repositories.rest.models.requests;

import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.repositories.db.entities.UserList;

import java.util.List;

public class PostTodoRequest {

    public long lastUpdateTime;

    public List<UserList> userLists;

    public List<Todo> todoList;

}
