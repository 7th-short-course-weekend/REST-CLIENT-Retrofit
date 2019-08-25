package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorResponse {

    @SerializedName("CODE")
    private  String code;
    @SerializedName("MESSAGE")
    private String  message;
    @SerializedName("DATA")
    private List<Author> authors;
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


    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", categories=" + authors +
                ", paging=" + paging +
                '}';
    }
}
