package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

public class DeleteArticleResponse {

    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private String code;

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
