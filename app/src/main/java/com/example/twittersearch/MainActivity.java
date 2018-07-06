package com.example.twittersearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.twittersearch.presenters.MainView;
import com.example.twittersearch.presenters.SearchAdapter;
import com.example.twittersearch.presenters.TweetsPresenter;


public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    private static AppPref pref;
    private TweetsPresenter presenter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SearchAdapter searchAdapter;
    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressView);
        mRecyclerView = findViewById(R.id.searchResultsView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = new AppPref(getApplicationContext());

        presenter = new TweetsPresenter(pref, this);

        searchAdapter = new SearchAdapter(presenter);
        mRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(searchListener);

        if (presenter.isReady())
            searchView.setQuery("", false);
        return true;
    }

    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            presenter.clearResults();
            presenter.searchTweets(query, null, false);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            query = newText;
            return true;
        }
    };


    @Override
    public void setQuery(String query) {

    }

    @Override
    public void results(boolean empty) {

    }

    @Override
    public void progressBar(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateUI(int position, int count) {
        searchAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void showError(Throwable e) {

    }
}
