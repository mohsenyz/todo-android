package com.mphj.todo.repositories.rest.models.responses;

import java.util.List;

public class PostTodoResponse {

    public int status;

    public long lastUpdateTime;

    public List<SavedObjectResponse> todoList;

    public List<SavedObjectResponse> userLists;

}
