package com.example.twittersearch.utils;

import android.view.View;

public class ViewUtil {

    static public void changeViewVisibility(View view, boolean visable) {
        if (visable)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.INVISIBLE);
    }

}
