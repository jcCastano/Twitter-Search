package com.example.repo.search;

import com.example.repo.search.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TweetsAPI {

    @GET("1.1/search/tweets.json")
    Observable<SearchResponse> search(@Header("Authorization") String authorization,
                                      @Query("q") String query,
                                      @Query("max_id") String maxId,
                                      @Query("include_entities") boolean includeEntities);

    @GET("1.1/search/tweets.json")
    Observable<SearchResponse> refresh(@Header("Authorization") String authorization,
                                      @Query("q") String query,
                                      @Query("since_id") String sinceId,
                                      @Query("include_entities") boolean includeEntities);

}
