package com.keepsolid.ksinternshipdemo2020.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.model.Vehicle;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class ThirdActivity extends AppCompatActivity {

    private String someString;
    private int someInt;
    private Vehicle vehicle;
    private AppCompatTextView textView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        if (getIntent().getExtras() != null) {
            someString = getIntent().getStringExtra(Constants.EXTRA_STRING);
            someInt = getIntent().getIntExtra(Constants.EXTRA_INT, 0);
            vehicle = getIntent().getParcelableExtra(Constants.EXTRA_VEHICLE);
        }

        initToolbar(getString(R.string.third_activity_title));

        textView = findViewById(R.id.intent_data_text);

        textView.setText("String: " + someString + "\n"
                + "Int: " + String.valueOf(someInt) + "\n"
                + vehicle.toString());

    }

    public void initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_back_btn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThirdActivity.this.onBackPressed();
            }
        });
    }

}
