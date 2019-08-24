package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("PAGINATION")
    private Paging pagination;
    @SerializedName("DATA")
    private List<Article> articles;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private String code;

    public Paging getPagination() {
        return pagination;
    }

    public void setPagination(Paging pagination) {
        this.pagination = pagination;
    }

    public List<Article> getArticle() {
        return articles;
    }

    public void setArticle(List<Article> data) {
        this.articles = data;
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
