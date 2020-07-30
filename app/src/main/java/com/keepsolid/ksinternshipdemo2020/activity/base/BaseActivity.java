package com.keepsolid.ksinternshipdemo2020.activity.base;

import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.app.InternshipDemoApp;
import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public void initToolbarWithNavigation(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public AppDatabase getDatabase() {
        return ((InternshipDemoApp) getApplication()).getDatabase();
    }


}
