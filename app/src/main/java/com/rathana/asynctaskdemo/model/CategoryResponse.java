package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("CODE")
    private  String code;
    @SerializedName("MESSAGE")
    private String  message;
    @SerializedName("DATA")
    private List<Category> categories;
    @SerializedName("PAGINATION")
    private Paging paging;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", categories=" + categories +
                ", paging=" + paging +
                '}';
    }
}
