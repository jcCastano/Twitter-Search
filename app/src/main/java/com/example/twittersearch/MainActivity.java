package com.example.twittersearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twittersearch.presenters.MainView;
import com.example.twittersearch.presenters.SearchAdapter;
import com.example.twittersearch.presenters.TweetsPresenter;
import com.example.twittersearch.utils.ViewUtil;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    private static AppPref pref;
    private TweetsPresenter presenter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SearchAdapter searchAdapter;
    private TextView emptyResults;
    private MenuItem menuItemSearch;
    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressView);
        mRecyclerView = findViewById(R.id.searchResultsView);
        emptyResults = findViewById(R.id.emptyResultsView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = new AppPref(getApplicationContext());

        presenter = new TweetsPresenter(pref, this);

        searchAdapter = new SearchAdapter(presenter);
        mRecyclerView.setAdapter(searchAdapter);

        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menuItemSearch = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(searchListener);
        searchView.setQuery("", false);
        menuItemSearch.setVisible(presenter.isReady());

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

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "onScrollStateChanged: " + newState);
            if (newState == SCROLL_STATE_IDLE) {
                if (!recyclerView.canScrollVertically(-1)) {
                    // TODO: Refresh to show new tweets
                } else if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadMore();
                }
            }
        }
    };

    @Override
    public void results(boolean empty) {
        ViewUtil.changeViewVisibility(emptyResults, empty);
    }

    @Override
    public void progressBar(boolean show) {
        ViewUtil.changeViewVisibility(progressBar, show);
        if (menuItemSearch != null)
            menuItemSearch.setVisible(!show);
    }

    @Override
    public void updateUI(int position, int count) {
        searchAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void showError(Throwable e) {

    }
}
