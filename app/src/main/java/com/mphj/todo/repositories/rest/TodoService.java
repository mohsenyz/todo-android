package com.mphj.todo.repositories.rest;

import com.mphj.todo.repositories.rest.models.requests.LoginRequest;
import com.mphj.todo.repositories.rest.models.requests.PostTodoRequest;
import com.mphj.todo.repositories.rest.models.requests.SignupRequest;
import com.mphj.todo.repositories.rest.models.responses.FcmUploadTokenResponse;
import com.mphj.todo.repositories.rest.models.responses.LoginResponse;
import com.mphj.todo.repositories.rest.models.responses.PostTodoResponse;
import com.mphj.todo.repositories.rest.models.responses.SignupResponse;
import com.mphj.todo.repositories.rest.models.responses.UserSyncResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TodoService {

    @POST("/user/register")
    Call<SignupResponse> signUp(@Body SignupRequest signupRequest);

    @GET("/user/fcm/token")
    Call<FcmUploadTokenResponse> renewFcmToken(@Query("fcmToken") String newToken, @Header("Token") String token);

    @POST("/user/todo")
    Call<PostTodoResponse> postTodo(@Body PostTodoRequest postTodoRequest, @Header("Token") String token);

    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/user/sync")
    Call<UserSyncResponse> sync(@Query("lastUpdateTime") long lastUpdateTime, @Header("Token") String token);

}
