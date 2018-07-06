package com.example.repo.authentication.model;

import com.google.gson.annotations.SerializedName;

public class OAuthResponse {

    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("access_token")
    public String accessToken;

}
