package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class UserMentions {

    public int[] indices;

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("id_str")
    public String idStr;

    public String name;

    public String id;

}
