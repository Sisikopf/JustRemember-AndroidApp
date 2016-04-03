package com.justremember.justremember;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dimko_000 on 03.04.2016.
 */
public class ApiProvider {
    private static volatile ApiProvider provider;
    private Retrofit retrofit;
    private API api;
    private ApiProvider() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }
    public static ApiProvider getInstance() {
        ApiProvider localInstance = provider;
        if(localInstance == null) {
            synchronized (ApiProvider.class) {
                localInstance = provider;
                if(localInstance == null) {
                    provider = localInstance = new ApiProvider();
                }
            }
        }
        return localInstance;
    }

    public API getApi() {
        return api;
    }
}
