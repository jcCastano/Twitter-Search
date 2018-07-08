package com.example.twittersearch.presenters.tweet;

import android.util.Log;

import com.example.repo.search.model.SearchMetadata;
import com.example.repo.search.model.SearchResponse;
import com.example.repo.search.model.Status;
import com.example.twittersearch.presenters.TweetDataPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TweetSearchObserver implements Observer<SearchResponse> {

    private static final String TAG = "TweetSearchObserver";
    protected TweetDataPresenter presenter;

    public TweetSearchObserver(TweetDataPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: " + d);
        presenter.onRetrievingData();
    }

    protected void updateData(List<Status> statuses, SearchMetadata searchMetadata) {
        presenter.addTweetsToBottom(statuses, searchMetadata);
    }

    @Override
    public void onNext(SearchResponse searchResponse) {
        Log.d(TAG, "onNext: " + searchResponse.statuses);

        updateData(searchResponse.statuses, searchResponse.searchMetadata);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        presenter.onErrorRetrievingData(e);
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        presenter.onDataReceived();
    }

}
