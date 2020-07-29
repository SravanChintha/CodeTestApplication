package com.sravan.codetestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Set<Hit> articles;

    private List<Hit> hits = new ArrayList<>();

    public DataAdapter(Set<Hit> articles, Context context) {
        this.articles=articles;

        hits.addAll(articles);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

        final String title=hits.get(i).getTitle();
        final String createdAt=hits.get(i).getCreatedAt();


        viewHolder.title.setText(title);
        viewHolder.createdAt.setText(createdAt);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,createdAt;


        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.tv_title);
            createdAt = (TextView)view.findViewById(R.id.tv_createdAt);
        }
    }

}
