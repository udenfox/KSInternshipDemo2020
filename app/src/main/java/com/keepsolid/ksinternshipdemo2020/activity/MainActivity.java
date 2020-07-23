package com.keepsolid.ksinternshipdemo2020.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.model.TaskItem;
import com.keepsolid.ksinternshipdemo2020.utils.TaskLoader;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.TaskRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnTaskRecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;
    ArrayList<TaskItem> items;
    ArrayList<TaskItem> secondaryItems;
    TaskRecyclerAdapter mainTasksAdapter;
    TaskRecyclerAdapter secondaryTasksAdapter;
    TabLayout tabLayout;
    ProgressBar progressBar;
    LinearLayout loaderBlock;
    TextView progressText;
    FloatingActionButton refreshBtn;

    private TaskLoader loader;

    private OnTaskRecyclerItemClickListener onMainTaskRecyclerItemClickListener = new OnTaskRecyclerItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            showDialog(mainTasksAdapter.getItems().get(position).getTaskName());
        }
    };

    private OnTaskRecyclerItemClickListener onSecondaryTaskRecyclerItemClickListener = new OnTaskRecyclerItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            showDialog(secondaryTasksAdapter.getItems().get(position).getTaskName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(getString(R.string.app_name));

        recyclerView = findViewById(R.id.rv_recycler);
        loaderBlock = findViewById(R.id.loader_block);
        progressBar = findViewById(R.id.pb_progress);
        progressText = findViewById(R.id.tv_progress);
        refreshBtn = findViewById(R.id.btn_refresh);

        tabLayout = findViewById(R.id.main_tabs);
        initTabs();

        loader = new TaskLoader();

        initMainTasksAdapter();
        initSecondaryTasksAdapter();

        // Can be changed to any layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainTasksAdapter);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskItemsApiTask().execute(loader.getTaskCount());
            }
        });

    }

    private void initMainTasksAdapter() {
        items = new ArrayList<>();
        mainTasksAdapter = new TaskRecyclerAdapter(items, this);
        mainTasksAdapter.setListener(onMainTaskRecyclerItemClickListener);
    }

    private void initSecondaryTasksAdapter() {
        secondaryItems = new ArrayList<>();
        secondaryItems.add(new TaskItem(true, "Второстепенная задача", TaskItem.Type.ALARM, "11:30", "08/10/2020"));
        secondaryItems.add(new TaskItem(true, "Еще одна не очень важная задача", TaskItem.Type.NOTE, "00:25", "08/05/2020"));
        secondaryItems.add(new TaskItem(true, "Переключить таб", TaskItem.Type.PLACE, "13:00", "08/01/2020"));
        secondaryItems.add(new TaskItem(false, "Вернуть его на место", TaskItem.Type.PLACE, "11:55", "07/05/2020"));
        secondaryItems.add(new TaskItem(false, "Завершить список", TaskItem.Type.ALARM, "16:00", "09/05/2020"));

        secondaryTasksAdapter = new TaskRecyclerAdapter(secondaryItems, this);

        secondaryTasksAdapter.setListener(onSecondaryTaskRecyclerItemClickListener);
    }

    private void showDialog(@NonNull String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Full task name")
                .setMessage(message)
                .setCancelable(true)
                .create().show();
    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Main"));
        tabLayout.addTab(tabLayout.newTab().setText("Secondary"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        recyclerView.swapAdapter(mainTasksAdapter, false);
                        break;
                    case 1:
                        recyclerView.swapAdapter(secondaryTasksAdapter, false);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setLoadingProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressText.setText(progress + "%");
        }

    }

    private void showProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.GONE);
        }
    }

    public class TaskItemsApiTask extends AsyncTask<Integer, Integer, ArrayList<TaskItem>> {

        @Override
        protected ArrayList<TaskItem> doInBackground(Integer... integers) {
            int itemsCount = integers[0];
            for (int i = 0; i < itemsCount; i++) {
                items.add(loader.loadTaskItemByIndex(i));
                publishProgress(i, itemsCount);

            }
            return items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            items.clear();
            setLoadingProgress(0);
            showProgressBlock();

        }

        @Override
        protected void onPostExecute(ArrayList<TaskItem> taskItems) {
            super.onPostExecute(taskItems);
            mainTasksAdapter.notifyDataSetChanged();
            hideProgressBlock();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int completed = values[0];
            int total = values[1];

            int percentsCompleted = (int) (((float) completed / total) * 100);

            setLoadingProgress(percentsCompleted);
        }
    }


}
