package com.keepsolid.ksinternshipdemo2020.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.model.Vehicle;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class ThirdActivity extends AppCompatActivity {

    private String someString;
    private int someInt;
    private Vehicle vehicle;
    private AppCompatTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        if (getIntent().getExtras() != null) {
            someString = getIntent().getStringExtra(Constants.EXTRA_STRING);
            someInt = getIntent().getIntExtra(Constants.EXTRA_INT, 0);
            vehicle = getIntent().getParcelableExtra(Constants.EXTRA_VEHICLE);
        }

        textView = findViewById(R.id.intent_data_text);

        textView.setText("String: " + someString + "\n"
                + "Int: " + String.valueOf(someInt) + "\n"
                + vehicle.toString());


    }
}
