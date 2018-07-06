package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("display_url")
    public String displayUrl;

    public int[] indices;

    @SerializedName("expanded_url")
    public String expandedUrl;

    public String url;

}
