package com.keepsolid.ksinternshipdemo2020.utils.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import java.util.ArrayList;

public class GitRepoRecyclerAdapter extends RecyclerView.Adapter<GitRepoRecyclerAdapter.ViewHolder> {

    private ArrayList<GitRepoItem> items;
    private Context ctx;
    private OnGitRepoRecyclerItemClickListener listener;

    public GitRepoRecyclerAdapter(ArrayList<GitRepoItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    public GitRepoRecyclerAdapter(ArrayList<GitRepoItem> items, Context ctx, OnGitRepoRecyclerItemClickListener listener) {
        this.items = items;
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    public GitRepoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.git_repo_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GitRepoRecyclerAdapter.ViewHolder holder, int position) {

        String descr = items.get(position).getDescription();

        if(TextUtils.isEmpty(descr)) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setText(descr);
            holder.description.setVisibility(View.VISIBLE);
        }

        holder.name.setText(items.get(position).getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, holder.getAdapterPosition(), items.get(holder.getAdapterPosition()).getUrl());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<GitRepoItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<GitRepoItem> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        View container;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("TaskRecyclerAdapter", "finding views!");

            description = itemView.findViewById(R.id.tv_repo_desc);
            name = itemView.findViewById(R.id.tv_repo_name);
            container = itemView;

        }
    }

}
