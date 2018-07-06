package com.example.repo.interactor;

import com.example.repo.TwitterClient;
import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.TweetsAPI;
import com.example.repo.search.model.SearchResponse;

import io.reactivex.Observable;

public class SearchTweetsUseCase extends UseCaseObservableWithParams<SearchResponse, TweetSearchRequest> {

    static final String AUTHORIZATION = "Bearer";
    private String token;

    public SearchTweetsUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable<SearchResponse> buildUseCaseObservable(TweetSearchRequest request) {
        TweetsAPI service = TwitterClient.makeService(TweetsAPI.class);
        String authorizationStr = AUTHORIZATION + " " + token;
        return service.search(authorizationStr, request.getQuery(), request.getMaxId(), request.getIncludeEntities());
    }

}
