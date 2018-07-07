package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class SearchMetadata {

    @SerializedName("max_id_str")
    public String maxIdStr;

    @SerializedName("next_results")
    public String nextResults;

    @SerializedName("since_id_str")
    public String sinceIdStr;

    @SerializedName("query")
    public String query;

    @SerializedName("count")
    public int count;

    @SerializedName("max_id")
    public long maxId;

    @SerializedName("since_id")
    public int sinceId;

    @SerializedName("completed_in")
    public double completedIn;

    @SerializedName("refresh_url")
    public String refreshUrl;

}
