package com.example.repo.interactor;

import android.util.Base64;

import com.example.repo.TwitterClient;
import com.example.repo.authentication.OAuthAPI;
import com.example.repo.authentication.model.OAuthResponse;

import io.reactivex.Single;

public class FetchTokenUseCase extends UseCaseSingle<OAuthResponse> {

    private static final String AUTHORIZATION = "Basic";
    private static final String CLIENT_TYPE = "client_credentials";
    private String encodedValues;

    public FetchTokenUseCase(String consumerKey, String consumerSecret) {
        String joinedValue = consumerKey + ":" + consumerSecret;
        byte[] encodedBytes = Base64.encode(joinedValue.getBytes(), Base64.NO_WRAP);
        encodedValues = new String(encodedBytes);
    }

    @Override
    protected Single<OAuthResponse> buildUseCaseSingle() {
        OAuthAPI service = TwitterClient.makeService(OAuthAPI.class);
        String authorizationStr = AUTHORIZATION + " " + encodedValues;
        return service.getToken(authorizationStr, CLIENT_TYPE);
    }

}
