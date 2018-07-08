package com.example.repo.interactor;

import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.model.SearchResponse;

import io.reactivex.Observable;

public class RefreshTweetsUseCase extends SearchTweetsUseCase {

    public RefreshTweetsUseCase(String token) {
        super(token);
    }

    @Override
    protected Observable<SearchResponse> buildUseCaseObservable(TweetSearchRequest request) {
        return service.refresh(authorization, request.getQuery(), request.getSinceId(), request.getIncludeEntities());
    }
}
