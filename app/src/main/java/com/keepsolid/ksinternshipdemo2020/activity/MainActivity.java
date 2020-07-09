package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.model.Vehicle;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class MainActivity extends BaseActivity {

    private AppCompatButton requestNameBtn;
    private AppCompatButton sendDataBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
        initToolbar(getString(R.string.app_name));

    }

    private void setListeners() {
        requestNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivityForResult();
            }
        });

        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdActivityWithData();
            }
        });

    }

    private void initViews() {
        requestNameBtn = findViewById(R.id.btn_request_name);
        sendDataBtn = findViewById(R.id.btn_send_data);
    }

    public void openSecondActivityForResult() {
        Intent explicitIntent = new Intent(MainActivity.this, SecondActivity.class);
        startActivityForResult(explicitIntent, Constants.NAME_REQUEST_CODE);
    }

    public void openThirdActivityWithData() {
        Intent dataIntent = new Intent(MainActivity.this, ThirdActivity.class);
        dataIntent.putExtra(Constants.EXTRA_STRING, "Some string");
        dataIntent.putExtra(Constants.EXTRA_INT, "42");
        dataIntent.putExtra(Constants.EXTRA_VEHICLE, new Vehicle("Veyron", "Bugatti", 410, true));
        startActivity(dataIntent);
    }

    private void showNameToast(String name) {
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.NAME_REQUEST_CODE) {
            if (data != null) {
                if (data.getExtras() != null) {
                    String name = data.getExtras().getString(Constants.EXTRA_NAME);
                    showNameToast(name);
                }
            }
        }
    }
}
