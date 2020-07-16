package com.keepsolid.ksinternshipdemo2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.fragment.FragmentViewer;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class SecondActivity extends AppCompatActivity {

    private FragmentViewer fragmentViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int resIdToDisplay = getIntent().getIntExtra(Constants.KEY_RES_ID, -1);

        fragmentViewer = (FragmentViewer) getSupportFragmentManager().findFragmentById(R.id.fragment_two);

        fragmentViewer.displayResource(resIdToDisplay);

    }
}