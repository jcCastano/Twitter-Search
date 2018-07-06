package com.example.twittersearch.interactor;

import android.util.Log;

import com.example.repo.interactor.FetchTokenUseCase;
import com.example.repo.authentication.model.OAuthResponse;
import com.example.twittersearch.AppPref;
import com.example.twittersearch.BuildConfig;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CheckForTokenUseCase {

    private static final String TAG = "CheckForTokenUseCase";
    private AppPref pref;

    public CheckForTokenUseCase(AppPref pref) {
        this.pref = pref;
    }

    public boolean execute() {
        String token = pref.getAuthToken();

        if (token == null || token.equalsIgnoreCase("")) {
            FetchTokenUseCase fetchTokenUseCase = new FetchTokenUseCase(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
            fetchTokenUseCase.execute(tokenObserver, AndroidSchedulers.mainThread());
            return false;
        }
        return true;
    }

    SingleObserver<OAuthResponse> tokenObserver = new SingleObserver<OAuthResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe: " + d);
        }

        @Override
        public void onSuccess(OAuthResponse oAuthResponse) {
            Log.d(TAG, "onSuccess: " + oAuthResponse.accessToken);
            pref.setAuthToken(oAuthResponse.accessToken);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }
    };

}
