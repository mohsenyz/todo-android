package com.mphj.todo.repositories.rest;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.mphj.todo.repositories.rest.models.requests.LoginRequest;
import com.mphj.todo.repositories.rest.models.requests.PostTodoRequest;
import com.mphj.todo.repositories.rest.models.requests.SignupRequest;
import com.mphj.todo.repositories.rest.models.responses.LoginResponse;
import com.mphj.todo.repositories.rest.models.responses.PostTodoResponse;
import com.mphj.todo.repositories.rest.models.responses.SignupResponse;
import com.mphj.todo.repositories.rest.models.responses.UserSyncResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TodoService {

    @POST("user/register")
    LiveData<Resource<SignupResponse>> createUser(@Body SignupRequest signupRequest);

    @POST("/user/todo")
    LiveData<Resource<PostTodoResponse>> postTodo(@Body PostTodoRequest postTodoRequest, @Header("Token") String token);

    @POST("/user/login")
    LiveData<Resource<LoginResponse>> login(@Body LoginRequest loginRequest);

    @GET("/user/sync")
    LiveData<Resource<UserSyncResponse>> sync(@Query("lastUpdateTime") long lastUpdateTime, @Header("Token") String token);

}
