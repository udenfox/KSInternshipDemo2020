package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.keepsolid.ksinternshipdemo2020.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openSecondActivityExplicit(View v) {
        Intent explicitIntent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(explicitIntent);
    }

    public void openThirdActivityImplicit(View v) {
        Intent explicitIntent = new Intent("com.keepsolid.ksinternshipdemo2020.ACTION_THIRD_ACTIVITY");
        startActivity(explicitIntent);
    }

}
