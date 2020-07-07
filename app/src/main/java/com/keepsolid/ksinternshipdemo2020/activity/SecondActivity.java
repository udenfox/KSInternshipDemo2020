package com.keepsolid.ksinternshipdemo2020.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class SecondActivity extends AppCompatActivity {

    private final static String LOG_TAG = SecondActivity.class.getSimpleName();

    private AppCompatEditText editText;
    private AppCompatButton sendBtn;
    private AppCompatTextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate()");

        setContentView(R.layout.activity_second);

        editText = findViewById(R.id.et_name_input);
        sendBtn = findViewById(R.id.btn_send);
        titleText = findViewById(R.id.tv_input_activity);

        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    private void setListeners() {

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendBtn();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                titleText.setTextColor(getResources().getColor(R.color.titleColor));
            }
        });

    }

    private void handleSendBtn() {

        if (TextUtils.isEmpty(editText.getText())) {
            titleText.setTextColor(getResources().getColor(R.color.colorError));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_NAME, editText.getText().toString());
        setResult(RESULT_OK, intent);

        setResult(Activity.RESULT_OK, intent);
        finish();

    }

}
