package com.rathana.asynctaskdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {
    @SerializedName("IMAGE")
    private String image;
    @SerializedName("CATEGORY")
    private Category category;
    @SerializedName("STATUS")
    private String status;
    @SerializedName("AUTHOR")
    private Author author;
    @SerializedName("CREATED_DATE")
    private String createdDate;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("ID")
    private int id;

    @Override
    public String toString() {
        return "Article{" +
                "image='" + image + '\'' +
                ", category=" + category +
                ", status='" + status + '\'' +
                ", author=" + author +
                ", createdDate='" + createdDate + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    protected Article(Parcel in) {
        image = in.readString();
        status = in.readString();
        createdDate = in.readString();
        description = in.readString();
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(status);
        dest.writeString(createdDate);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeInt(id);
    }
}
