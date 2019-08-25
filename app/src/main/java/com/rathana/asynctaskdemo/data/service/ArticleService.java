package com.rathana.asynctaskdemo.data.service;

import com.rathana.asynctaskdemo.model.Article;
import com.rathana.asynctaskdemo.model.ArticleForm;
import com.rathana.asynctaskdemo.model.ArticleFormResponse;
import com.rathana.asynctaskdemo.model.ArticleResponse;
import com.rathana.asynctaskdemo.model.DeleteArticleResponse;
import com.rathana.asynctaskdemo.model.UploadImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("/v1/api/articles")
    Call<ArticleResponse> getArticles(
            @Query("page") long page,
            @Query("limit") long limit);

    @DELETE("/v1/api/articles/{id}")
    Call<DeleteArticleResponse> deleteArticle(@Path("id") int id);

    @POST("/v1/api/articles")
    Call<ArticleFormResponse> create(@Body ArticleForm article);

    @PUT("/v1/api/articles/{id}")
    Call<ArticleFormResponse> update(@Path("id")int id, @Body ArticleForm article);

    //end point upload image
    @POST("/v1/api/uploadfile/single")
    @Multipart
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part file);


}
