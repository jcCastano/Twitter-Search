package com.example.twittersearch.presenters;

public interface MainView {

    void setQuery(String query);

    void results(boolean empty);

    void progressBar(boolean show);

    void updateUI(int position, int count);

    void showError(Throwable e);

}