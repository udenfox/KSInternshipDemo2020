package com.keepsolid.ksinternshipdemo2020.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.app.InternshipDemoApp;
import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Toolbar toolbar;

    public void initToolbarWithNavigation(String title, View parentView) {
        toolbar = parentView.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    public void initToolbar(String title, View parentView) {
        toolbar = parentView.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

}
