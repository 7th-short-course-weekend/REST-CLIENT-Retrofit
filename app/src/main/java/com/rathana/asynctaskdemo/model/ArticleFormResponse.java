package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleFormResponse {

    @SerializedName("DATA")
    private Article article;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private String code;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
