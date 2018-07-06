package com.example.repo.authentication;

import com.example.repo.authentication.model.OAuthResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OAuthAPI {

    @FormUrlEncoded
    @POST("/oauth2/token")
    Single<OAuthResponse> getToken(@Header("Authorization") String authorization,
                                   @Field("grant_type") String grantType);

}
