package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("max_id_str")
    String maxIdStr;

    @SerializedName("next_results")
    String nextResults;

    @SerializedName("since_id_str")
    String sinceIdStr;

    @SerializedName("query")
    String query;

    @SerializedName("count")
    int count;

    @SerializedName("max_id")
    long maxId;

    @SerializedName("since_id")
    int sinceId;

    @SerializedName("completed_in")
    double completedIn;

    @SerializedName("refresh_url")
    String refreshUrl;

}
