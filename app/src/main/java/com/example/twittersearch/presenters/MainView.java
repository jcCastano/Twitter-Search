package com.example.twittersearch.presenters;

public interface MainView {

    void results(boolean empty);

    void progressBar(boolean show);

    void updateUI(int position, int count);

    void updateUI();

    void showError(Throwable e);

}
