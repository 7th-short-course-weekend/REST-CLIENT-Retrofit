package com.rathana.asynctaskdemo.data.service;

import com.rathana.asynctaskdemo.model.CategoryResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/v1/api/categories")
    Call<CategoryResponse> getCategories();

}
