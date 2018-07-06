package com.example.repo.search.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("friends_count")
    public int friendsCount;

    @SerializedName("listed_count")
    public int listedCount;

    @SerializedName("default_profile_image")
    public boolean defaultProfileImage;

    @SerializedName("favourites_count")
    public int favouritesCount;

    public String description;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("is_translator")
    public boolean isTranslator;

    @SerializedName("profile_background_image_url_https")
    public String profileBackgroundImageUrlHttps;

    @SerializedName("protected")
    public boolean isProtected;

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("id_str")
    public String idStr;

    @SerializedName("profile_link_color")
    public String profileLinkColor;

    @SerializedName("is_translation_enabled")
    public boolean isTranslationEnabled;

    public long id;

    @SerializedName("geo_enabled")
    public boolean geoEnabled;

    @SerializedName("profile_background_color")
    public String profileBackgroundColor;

    public String lang;

    @SerializedName("has_extended_profile")
    public boolean hasExtendedProfile;

    @SerializedName("profile_sidebar_border_color")
    public String profileSidebarBorderColor;

    @SerializedName("profile_text_color")
    public String profileTextColor;

    public boolean verified;

    @SerializedName("profile_image_url")
    public String profileImageUrl;

    @SerializedName("contributors_enabled")
    public boolean contributorsEnabled;

    @SerializedName("profile_background_tile")
    public boolean profileBackgroundTile;

    public Entities entities;

    @SerializedName("statuses_count")
    public int statusesCount;

    @SerializedName("followers_count")
    public int followersCount;

    @SerializedName("profile_use_background_image")
    public boolean profileUseBackgroundImage;

    @SerializedName("default_profile")
    public boolean defaultProfile;

    public String name;

    public String location;

    @SerializedName("profile_sidebar_fill_color")
    public String profileSidebarFillColor;
    
}
