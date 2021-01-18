package com.sqlitedemo.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("getallthemes")
    @FormUrlEncoded
    Call<JsonObject> GetAllTheme(@Field("token") String token, @Field("application_id") String applicationId);
}
