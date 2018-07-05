package com.example.twittersearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.twittersearch.interactor.CheckForTokenUseCase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static AppPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = new AppPref(getApplicationContext());

        new CheckForTokenUseCase(pref).execute();
    }

}