package com.sqlitedemo.retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit retrofit;
        public static Retrofit getRetrofit() {
        Retrofit build = new Retrofit.Builder().baseUrl("http://admin.trendinganimations.com/public/api/").addConverterFactory(GsonConverterFactory.create()).build();
        retrofit = build;
        return build;
    }
}
