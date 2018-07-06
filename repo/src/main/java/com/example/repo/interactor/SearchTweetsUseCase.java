package com.example.repo.interactor;

import com.example.repo.TwitterClient;
import com.example.repo.search.TweetsAPI;
import com.example.repo.search.model.SearchResponse;

import io.reactivex.Observable;

public class SearchTweetsUseCase extends UseCaseObservable<SearchResponse> {

    static final String AUTHORIZATION = "Bearer";
    private String token;
    private String query;
    private String maxId;
    private boolean includeEntities;

    public SearchTweetsUseCase(String token, String query, String maxId, boolean includeEntities) {
        this.token = token;
        this.query = query;
        this.maxId = maxId;
        this.includeEntities = includeEntities;
    }

    @Override
    protected Observable<SearchResponse> buildUseCaseObservable() {
        TweetsAPI service = TwitterClient.makeService(TweetsAPI.class);
        String authorizationStr = AUTHORIZATION + " " + token;
        return service.search(authorizationStr, query, maxId, includeEntities);
    }

}
