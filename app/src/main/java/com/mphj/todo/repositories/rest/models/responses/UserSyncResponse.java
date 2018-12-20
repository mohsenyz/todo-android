package com.mphj.todo.repositories.rest.models.responses;

import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.repositories.db.entities.UserList;

import java.util.List;

public class UserSyncResponse {

    public int status;

    public long updateTime;

    public List<UserList> userLists;

    public List<Todo> todoList;

}
