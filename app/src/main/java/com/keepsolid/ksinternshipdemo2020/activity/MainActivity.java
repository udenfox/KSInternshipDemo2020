package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.keepsolid.ksinternshipdemo2020.R;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton explicitBtn;
    private AppCompatButton implicitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        explicitBtn = findViewById(R.id.btn_open_explicit);
        implicitBtn = findViewById(R.id.btn_open_implicit);

        explicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivityExplicit();
            }
        });

        implicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdActivityImplicit();
            }
        });

    }

    public void openSecondActivityExplicit() {
        Intent explicitIntent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(explicitIntent);
    }

    public void openThirdActivityImplicit() {
        Intent explicitIntent = new Intent("com.keepsolid.ksinternshipdemo2020.ACTION_THIRD_ACTIVITY");
        startActivity(explicitIntent);
    }

}
