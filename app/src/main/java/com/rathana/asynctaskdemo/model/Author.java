package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("IMAGE_URL")
    private String imageUrl;
    @SerializedName("FACEBOOK_ID")
    private String facebookId;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("TELEPHONE")
    private String telephone;
    @SerializedName("GENDER")
    private String gender;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("NAME")
    private String name;
    @SerializedName("ID")
    private int id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
