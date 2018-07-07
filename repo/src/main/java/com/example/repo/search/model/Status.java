package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class Status {

    public SearchMetadata searchMetadata;

    @SerializedName("created_at")
    public String createdAt;

    public String source;

    @SerializedName("retweet_count")
    public int retweetCount;

    @SerializedName("retweeted")
    public boolean retweeted;

    @SerializedName("is_quote_status")
    public boolean isQuoteStatus;

    @SerializedName("id_str")
    public String idStr;

    @SerializedName("favorite_count")
    public int favoriteCount;

    public long id;

    public String text;

    public String lang;

    public boolean favorited;

    @SerializedName("possibly_sensitive")
    public String possiblySensitive;

    public String truncated;

    public Entities entities;

    public User user;

}
