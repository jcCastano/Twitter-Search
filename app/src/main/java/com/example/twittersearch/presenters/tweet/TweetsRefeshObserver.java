package com.example.twittersearch.presenters.tweet;

import com.example.repo.search.model.SearchMetadata;
import com.example.repo.search.model.Status;
import com.example.twittersearch.presenters.TweetsViewPresenter;

import java.util.List;

public class TweetsRefeshObserver extends TweetSearchObserver {

    public TweetsRefeshObserver(TweetsViewPresenter presenter) {
        super(presenter);
    }

    @Override
    protected void updateData(List<Status> statuses, SearchMetadata searchMetadata) {
        presenter.addTweetsToTop(statuses, searchMetadata);
    }

}
