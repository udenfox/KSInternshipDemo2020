package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.fragment.FirstFragment;
import com.keepsolid.ksinternshipdemo2020.fragment.SecondFragment;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;

    private AppCompatButton btnAdd;
    private AppCompatButton btnRemove;
    private AppCompatButton btnReplace;
    private SwitchCompat backstackSwitch;

    private FirstFragment fragmentOne;
    private SecondFragment fragmentTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        fragmentOne = FirstFragment.newInstance("Denis", "Shevtsov");
        fragmentTwo = new SecondFragment();

        setListeners();
        initToolbar(getString(R.string.app_name));

    }

    private void initViews() {
        btnAdd = findViewById(R.id.btn_add);
        btnRemove = findViewById(R.id.btn_remove);
        btnReplace = findViewById(R.id.btn_replace);
        backstackSwitch = findViewById(R.id.backstack_switch);

    }

    private void setListeners() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(fragmentOne);
            }
        });

        btnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragmentTwo);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(fragmentOne);
            }
        });

    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        if (backstackSwitch.isChecked()) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        if (backstackSwitch.isChecked()) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (backstackSwitch.isChecked()) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
