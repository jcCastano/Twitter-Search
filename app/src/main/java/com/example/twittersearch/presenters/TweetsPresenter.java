package com.example.twittersearch.presenters;

import android.text.method.LinkMovementMethod;
import android.util.Log;

import com.example.repo.authentication.model.OAuthResponse;
import com.example.repo.interactor.FetchTokenUseCase;
import com.example.repo.interactor.SearchTweetsUseCase;
import com.example.repo.interactor.model.TweetSearchRequest;
import com.example.repo.search.model.SearchMetadata;
import com.example.repo.search.model.SearchResponse;
import com.example.repo.search.model.Status;
import com.example.twittersearch.AppPref;
import com.example.twittersearch.BuildConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TweetsPresenter implements Presenter {

    private static final String TAG = "TweetsPresenter";
    private List<Status> statuses = new ArrayList<>();
    private SearchMetadata searchMetadata = null;
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
        if (searchMetadata != null) {
            String max_id = null;
            boolean includeEntities = false;

            if (searchMetadata.nextResults != null) {
                String[] nextResultsSplit = searchMetadata.nextResults.replace("?", "").split("[&]");
                for (String value : nextResultsSplit) {
                    if (!value.isEmpty()) {
                        String[] queryFieldSplit = value.split("=");
                        if (queryFieldSplit[0].equalsIgnoreCase("max_Id"))
                            max_id = queryFieldSplit[1];
                        else if (queryFieldSplit[0].equalsIgnoreCase("include_entities"))
                            includeEntities = Boolean.valueOf(queryFieldSplit[1]);
                    }
                }

                searchTweets(searchMetadata.query, max_id, includeEntities);
            }
        }
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
        searchMetadata = null;
    }

    public int getTweetCount() {
        return statuses.size();
    }

    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Picasso.get().load(statuses.get(position).user.profileImageUrl.replace("_normal.jpg", ".jpg")).into(holder.profileImage);
        holder.userNameView.setText(statuses.get(position).user.name);
        holder.tweet.setText(statuses.get(position).text);
    }

    Observer<SearchResponse> searchObserver = new Observer<SearchResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: " + d);
            mainView.progressBar(true);
            mainView.results(false);
        }

        @Override
        public void onNext(SearchResponse searchResponse) {
            Log.d(TAG, "onNext: " + searchResponse.statuses);
            statuses.addAll(searchResponse.statuses);
            searchMetadata = searchResponse.searchMetadata;

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
            mainView.results(emptyResults());
        }
    };

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

}
