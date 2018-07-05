package com.example.twittersearch;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {

    static private final String PREFERENCE_FILE_KEY = "com.example.twittersearch.app.pref";
    static private final String AUTH_TOKEN_KEY = "token";

    SharedPreferences sharedPref;

    public AppPref(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        setPrefString(AUTH_TOKEN_KEY, token);
    }

    public String getAuthToken() {
        return sharedPref.getString(AUTH_TOKEN_KEY, "");
    }

    private void setPrefString(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
