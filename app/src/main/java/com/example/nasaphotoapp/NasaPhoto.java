package com.example.nasaphotoapp;

import com.google.gson.annotations.SerializedName;

public class NasaPhoto {
    @SerializedName("title")
    private String title;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("url")
    private String url;

    @SerializedName("date")
    private String date;
    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
