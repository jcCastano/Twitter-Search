package com.example.twittersearch.presenters;

import com.example.repo.search.model.SearchMetadata;
import com.example.repo.search.model.Status;

import java.util.List;

public interface TweetDataPresenter extends BaseDataPresenter {

    void addTweetsToTop(List<Status> statusesResults, SearchMetadata searchMetadata);

    void addTweetsToBottom(List<Status> statusesResults, SearchMetadata searchMetadata);

}
