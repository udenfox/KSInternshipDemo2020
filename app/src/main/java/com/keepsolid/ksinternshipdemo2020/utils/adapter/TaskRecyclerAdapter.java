package com.keepsolid.ksinternshipdemo2020.utils.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.model.TaskItem;

import java.util.ArrayList;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    private ArrayList<TaskItem> items;
    private Context ctx;

    public TaskRecyclerAdapter(ArrayList<TaskItem> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskRecyclerAdapter.ViewHolder holder, int position) {
        holder.isCompleted.setChecked(items.get(position).isCompleted());
        holder.taskTitle.setText(items.get(position).getTaskName());
        holder.taskDate.setText(items.get(position).getTaskDate());
        holder.taskTime.setText(items.get(position).getTaskTime());

        switch (items.get(position).getTaskType()) {
            case ALARM:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_alarm_grey600_24dp));
                break;
            case NOTE:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_note_outline_grey600_24dp));
                break;
            case PLACE:
                holder.taskTypeImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_map_marker_radius_grey600_24dp));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox isCompleted;
        TextView taskTitle;
        ImageView taskTypeImage;
        TextView taskTime;
        TextView taskDate;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("TaskRecyclerAdapter", "finding views!");

            isCompleted = itemView.findViewById(R.id.item_task_check);
            taskTitle = itemView.findViewById(R.id.item_task_title);
            taskTypeImage = itemView.findViewById(R.id.item_task_type_icon);
            taskTime = itemView.findViewById(R.id.item_task_alarm_time);
            taskDate = itemView.findViewById(R.id.item_task_alarm_date);

        }
    }

}
