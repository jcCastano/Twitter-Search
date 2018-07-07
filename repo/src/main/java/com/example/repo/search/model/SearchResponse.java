package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    public List<Status> statuses;

    @SerializedName("search_metadata")
    public SearchMetadata searchMetadata;

}
