package com.rathana.asynctaskdemo.data.service;

import com.rathana.asynctaskdemo.model.AuthorResponse;
import com.rathana.asynctaskdemo.model.CategoryResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CategoryService {

    @GET("/v1/api/categories")
    Call<CategoryResponse> getCategories();

//    @GET("/v1/api/users")
//    Call<AuthorResponse> getAuthors(
//            @Query("page") long page
//            ,@Query("limit") long limit);

    @GET("/v1/api/users")
    Call<AuthorResponse> getAuthors(
            @QueryMap Map<String ,Long> params);
}
