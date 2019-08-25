package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

public class ArticleForm {

    @SerializedName("IMAGE")
    private String image;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("CATEGORY_ID")
    private int categoryId;
    @SerializedName("AUTHOR")
    private int author;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("TITLE")
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
