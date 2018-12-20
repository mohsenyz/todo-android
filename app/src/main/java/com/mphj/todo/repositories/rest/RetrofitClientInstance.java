package com.mphj.todo.repositories.rest;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {


    private static final String BASE_URL = "http://0.0.0.0:8080/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                    .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
