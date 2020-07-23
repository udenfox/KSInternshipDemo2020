package com.keepsolid.ksinternshipdemo2020.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.model.TaskItem;
import com.keepsolid.ksinternshipdemo2020.utils.HardTasks;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.TaskRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnTaskRecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;
    ArrayList<TaskItem> items;
    ArrayList<TaskItem> secondaryItems;
    TaskRecyclerAdapter mainTasksAdapter;
    TaskRecyclerAdapter secondaryTasksAdapter;
    FloatingActionButton addBtn;
    TabLayout tabLayout;
    ProgressBar progressBar;

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
        addBtn = findViewById(R.id.fab_add);
        progressBar = findViewById(R.id.pb_progress);

        tabLayout = findViewById(R.id.main_tabs);
        initTabs();


        initMainTasksAdapter();
        initSecondaryTasksAdapter();

        // Can be changed to any layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainTasksAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (items != null && mainTasksAdapter != null) {

                    showProgressBar();

                    items.add(HardTasks.getTaskItemHardly("HAAARD task name"));

                    hideProgressBar();

                    mainTasksAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                }
            }
        });

    }

    private void initMainTasksAdapter() {
        items = new ArrayList<>();
        items.add(new TaskItem(true, "Создать этот список", TaskItem.Type.ALARM, "10:00", "07/05/2020"));
        items.add(new TaskItem(true, "Отметить первый пункт как готовый", TaskItem.Type.ALARM, "11:25", "07/05/2020"));
        items.add(new TaskItem(true, "Осознать что 2 пункта уже готовы", TaskItem.Type.NOTE, "11:30", "07/05/2020"));
        items.add(new TaskItem(false, "Отдохнуть", TaskItem.Type.PLACE, "11:35", "07/05/2020"));
        items.add(new TaskItem(false, "Заказать воду", TaskItem.Type.ALARM, "16:00", "09/05/2020"));
        items.add(new TaskItem(true, "Заплатить за интернет", TaskItem.Type.NOTE, "10:00", "11/05/2020"));
        items.add(new TaskItem(true, "Придумать следующий элемент списка", TaskItem.Type.NOTE, "12:00", "11/05/2020"));
        items.add(new TaskItem(false, "Сходить на работу", TaskItem.Type.PLACE, "09:00", "12/05/2020"));
        items.add(new TaskItem(false, "Провести занятие интернатуры", TaskItem.Type.PLACE, "13:00", "12/05/2020"));
        items.add(new TaskItem(false, "Развидеть увиденное", TaskItem.Type.ALARM, "00:00", "13/05/2020"));
        items.add(new TaskItem(false, "Перевернуть пингвина", TaskItem.Type.NOTE, "22:00", "13/05/2020"));
        items.add(new TaskItem(true, "Залипнуть в инете", TaskItem.Type.NOTE, "12:00", "14/05/2020"));
        items.add(new TaskItem(true, "Впихнуть невпихуемое", TaskItem.Type.PLACE, "13:00", "15/05/2020"));

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

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

}
