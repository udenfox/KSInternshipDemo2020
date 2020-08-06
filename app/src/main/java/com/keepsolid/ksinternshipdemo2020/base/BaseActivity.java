package com.keepsolid.ksinternshipdemo2020.base;

import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.app.InternshipDemoApp;
import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

public abstract class BaseActivity extends AppCompatActivity {

    public AppDatabase getDatabase() {
        return ((InternshipDemoApp) getApplication()).getDatabase();
    }


}
