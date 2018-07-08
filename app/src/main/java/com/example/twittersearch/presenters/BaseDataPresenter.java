package com.example.twittersearch.presenters;

public interface BaseDataPresenter {

    void onRetrievingData();

    void onErrorRetrievingData(Throwable e);

    void onDataReceived();

}
