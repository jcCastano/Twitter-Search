package com.example.twittersearch.presenters;

import android.util.Log;

import com.example.repo.authentication.model.OAuthResponse;
import com.example.repo.interactor.FetchTokenUseCase;
import com.example.repo.interactor.SearchTweetsUseCase;
import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.model.SearchResponse;
import com.example.repo.search.model.Status;
import com.example.twittersearch.AppPref;
import com.example.twittersearch.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TweetsPresenter implements Presenter {

    private static final String TAG = "TweetsPresenter";
    private List<Status> statuses = new ArrayList<>();
    private AppPref pref;
    private MainView mainView;
    private SearchTweetsUseCase searchTweetsUseCase;
    private boolean hasToken = false;
    private boolean ready = false;

    public TweetsPresenter(AppPref pref, MainView mainView) {
        this.pref = pref;
        this.mainView = mainView;
        String token = pref.getAuthToken();
        if (token == null || token.isEmpty()) {
            FetchTokenUseCase fetchTokenUseCase = new FetchTokenUseCase(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
            fetchTokenUseCase.execute(tokenObserver, AndroidSchedulers.mainThread());
        } else {
            hasToken = true;
            start();
        }
    }

    @Override
    public void load() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void start() {
        if (hasToken) {
            ready = true;
            searchTweetsUseCase = new SearchTweetsUseCase(pref.getAuthToken());
        }
    }

    @Override
    public void stop() {
        ready = false;
        searchTweetsUseCase = null;
    }

    public boolean isReady() {
        return ready;
    }

    public void searchTweets(String query, String maxId, boolean includeEntities) {
        searchTweetsUseCase.execute(searchObserver, new TweetSearchRequest(query, maxId, includeEntities), AndroidSchedulers.mainThread());
    }

    public void clearResults() {
        statuses.clear();
    }

    public int getTweetCount() {
        return statuses.size();
    }

    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        holder.userNameView.setText(statuses.get(position).user.name);
    }

    Observer<SearchResponse> searchObserver = new Observer<SearchResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: " + d);
            mainView.progressBar(true);
        }

        @Override
        public void onNext(SearchResponse searchResponse) {
            Log.d(TAG, "onNext: " + searchResponse.statuses);
            statuses.addAll(searchResponse.statuses);

            int count = statuses.size();
            int startPosition = count + 1;

            mainView.updateUI(startPosition, count);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete: ");
            mainView.progressBar(false);
        }
    };

    SingleObserver<OAuthResponse> tokenObserver = new SingleObserver<OAuthResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: " + d);
        }

        @Override
        public void onSuccess(OAuthResponse oAuthResponse) {
            Log.d(TAG, "onSuccess: " + oAuthResponse.accessToken);
            pref.setAuthToken(oAuthResponse.accessToken);
            ready = true;
            start();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }
    };

}