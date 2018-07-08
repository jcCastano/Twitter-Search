package com.example.twittersearch.presenters;

import android.util.Log;

import com.example.repo.authentication.model.OAuthResponse;
import com.example.repo.interactor.FetchTokenUseCase;
import com.example.repo.interactor.RefreshTweetsUseCase;
import com.example.repo.interactor.SearchTweetsUseCase;
import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.model.SearchMetadata;
import com.example.repo.search.model.Status;
import com.example.twittersearch.AppPref;
import com.example.twittersearch.BuildConfig;
import com.example.twittersearch.presenters.tweet.TweetSearchObserver;
import com.example.twittersearch.presenters.tweet.TweetsRefeshObserver;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TweetsViewPresenter implements ViewPresenter, TweetDataPresenter {

    private static final String TAG = "TweetsViewPresenter";
    private List<Status> statuses = new ArrayList<>();
    private SearchMetadata searchMetadata = null;
    private AppPref pref;
    private MainView mainView;
    private SearchTweetsUseCase searchTweetsUseCase;
    private TweetSearchObserver tweetSearchObserver;
    private RefreshTweetsUseCase refreshTweetsUseCase;
    private TweetsRefeshObserver tweetsRefeshObserver;
    private boolean hasToken = false;
    private boolean ready = false;

    public TweetsViewPresenter(AppPref pref, MainView mainView) {
        this.pref = pref;
        this.mainView = mainView;
        String token = pref.getAuthToken();
        if (token == null || token.isEmpty()) {
            getToken();
        } else {
            hasToken = true;
            start();
        }
    }

    public void getToken() {
        FetchTokenUseCase fetchTokenUseCase = new FetchTokenUseCase(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
        fetchTokenUseCase.execute(tokenObserver, AndroidSchedulers.mainThread());
    }

    @Override
    public void load() {

    }

    public void refreshSearch() {
        if (searchMetadata != null && searchMetadata.refreshUrl != null) {
            TweetSearchRequest request = new TweetSearchRequest(searchMetadata.refreshUrl);
            refreshTweetsUseCase.execute(tweetsRefeshObserver, request, AndroidSchedulers.mainThread());
        }
    }

    @Override
    public void loadMore() {
        if (searchMetadata != null && searchMetadata.nextResults != null) {
            TweetSearchRequest request = new TweetSearchRequest(searchMetadata.nextResults);
            searchTweets(request.getQuery(), request.getMaxId(), request.getIncludeEntities());
        }
    }

    @Override
    public void start() {
        if (hasToken) {
            ready = true;
            searchTweetsUseCase = new SearchTweetsUseCase(pref.getAuthToken());
            tweetSearchObserver = new TweetSearchObserver(this);
            refreshTweetsUseCase = new RefreshTweetsUseCase(pref.getAuthToken());
            tweetsRefeshObserver = new TweetsRefeshObserver(this);
        } else
            getToken();
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
        searchTweetsUseCase.execute(tweetSearchObserver, new TweetSearchRequest(query, maxId, includeEntities), AndroidSchedulers.mainThread());
    }

    public void clearResults() {
        statuses.clear();
        searchMetadata = null;
    }

    public int getTweetCount() {
        return statuses.size();
    }

    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Picasso.get().load(statuses.get(position).user.profileImageUrl.replace("_normal.jpg", ".jpg")).into(holder.profileImage);
        holder.userNameView.setText(getTextToDisplay(statuses.get(position).user.name));
        holder.tweet.setText(getTextToDisplay(statuses.get(position).text));
    }

    private String getTextToDisplay(String value) {
        return value + "\n";
    }

    private boolean emptyResults() {
        return statuses.size() == 0;
    }

    SingleObserver<OAuthResponse> tokenObserver = new SingleObserver<OAuthResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: " + d);
            mainView.progressBar(true);
        }

        @Override
        public void onSuccess(OAuthResponse oAuthResponse) {
            Log.d(TAG, "onSuccess: " + oAuthResponse.accessToken);
            pref.setAuthToken(oAuthResponse.accessToken);
            hasToken = true;
            start();
            mainView.progressBar(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }
    };

    @Override
    public void onRetrievingData() {
        Log.d(TAG, "onRetrievingData: ");
        mainView.progressBar(true);
        mainView.results(false);
    }

    @Override
    public void addTweetsToTop(List<Status> statusesResults, SearchMetadata searchMetadata) {
        this.statuses = statusesResults;
        this.searchMetadata = searchMetadata;
        mainView.updateUI();
    }

    @Override
    public void addTweetsToBottom(List<Status> statusesResults, SearchMetadata searchMetadata) {
        Log.d(TAG, "addTweetsToBottom: " + statusesResults.get(0).createdAt + "\n" + statusesResults.get(0).text);
        this.statuses.addAll(statusesResults);
        this.searchMetadata = searchMetadata;
        int count = statuses.size();
        int startPosition = count + 1;
        mainView.updateUI(startPosition, count);
    }

    @Override
    public void onErrorRetrievingData(Throwable e) {
        Log.e(TAG, "onErrorRetrievingData: ", e);
        mainView.progressBar(false);
        clearResults();
    }

    @Override
    public void onDataReceived() {
        Log.d(TAG, "onDataReceived: ");
        mainView.progressBar(false);
        mainView.results(emptyResults());
    }

}
