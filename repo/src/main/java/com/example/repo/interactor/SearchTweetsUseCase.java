package com.example.repo.interactor;

import com.example.repo.TwitterClient;
import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.TweetsAPI;
import com.example.repo.search.model.SearchResponse;

import io.reactivex.Observable;

public class SearchTweetsUseCase extends UseCaseObservableWithParams<SearchResponse, TweetSearchRequest> {

    static final String AUTHORIZATION_TYPE = "Bearer";
    protected String authorization;
    TweetsAPI service;

    public SearchTweetsUseCase(String token) {
        service = TwitterClient.makeService(TweetsAPI.class);
        authorization = AUTHORIZATION_TYPE + " " + token;
    }

    @Override
    protected Observable<SearchResponse> buildUseCaseObservable(TweetSearchRequest request) {
        return service.search(authorization, request.getQuery(), request.getMaxId(), request.getIncludeEntities());
    }

}
