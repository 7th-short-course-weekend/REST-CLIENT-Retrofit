package com.rathana.asynctaskdemo.model;

import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("TOTAL_PAGES")
    private int totalPages;
    @SerializedName("TOTAL_COUNT")
    private int totalCount;
    @SerializedName("LIMIT")
    private int limit;
    @SerializedName("PAGE")
    private int page;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "totalPages=" + totalPages +
                ", totalCount=" + totalCount +
                ", limit=" + limit +
                ", page=" + page +
                '}';
    }
}
