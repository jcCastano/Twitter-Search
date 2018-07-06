package com.example.repo.interactor.model;

public class TweetSearchRequest {

    private String query;
    private String maxId;
    private boolean includeEntities;

    public TweetSearchRequest(String query, String maxId, boolean includeEntities) {
        this.query = query;
        this.maxId = maxId;
        this.includeEntities = includeEntities;
    }

    public TweetSearchRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public boolean getIncludeEntities() {
        return includeEntities;
    }

    public void setIncludeEntities(boolean includeEntities) {
        this.includeEntities = includeEntities;
    }

}
