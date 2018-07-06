package com.example.twittersearch.presenters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twittersearch.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private TweetsPresenter presenter;

    public SearchAdapter(TweetsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_row_card_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getTweetCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userNameView;

        public ViewHolder(View itemView) {
            super(itemView);
            userNameView = itemView.findViewById(R.id.userNameView);
        }
    }

}
