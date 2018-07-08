package com.example.repo.interactor.model;

import android.util.Log;

public class TweetSearchRequest {

    private String query;
    private String maxId;
    private String sinceId;
    private boolean includeEntities;

    private static final String TAG = "TweetSearchRequest";

    public TweetSearchRequest(String queryRequestStr) {

        String[] requestSplit = queryRequestStr.replace("?", "").split("[&]");
        for (String field : requestSplit) {
            if (!field.isEmpty()) {
                String[] queryFieldSplit = field.split("=");
                String key = queryFieldSplit[0];
                String value = queryFieldSplit[1];
                switch (key) {
                    case "q":
                        query = value;
                        break;
                    case "max_id":
                        maxId = value;
                        break;
                    case "since_id":
                        sinceId = value;
                        break;
                    case "include_entities":
                        includeEntities = Boolean.valueOf(value);
                        break;
                    default:
                        Log.d(TAG, "TweetSearchRequest: no property for key: " + key + " value: " + value);
                        break;
                }
            }
        }

    }

    public TweetSearchRequest(String query, String maxId, boolean includeEntities) {
        this.query = query;
        this.maxId = maxId;
        this.includeEntities = includeEntities;
    }

    public TweetSearchRequest(String query, boolean includeEntities, String sinceId) {
        this.query = query;
        this.includeEntities = includeEntities;
        this.sinceId = sinceId;
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

    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(String sinceId) {
        this.sinceId = sinceId;
    }
}
