package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entities {

    public List<Urls> urls;

    public List<Hashtags> hashtags;

    @SerializedName("user_mentions")
    public List<UserMentions> userMentions;

}
