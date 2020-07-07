package com.keepsolid.ksinternshipdemo2020.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;

public class SecondActivity extends AppCompatActivity {

    private AppCompatEditText editText;
    private AppCompatButton sendBtn;
    private AppCompatTextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText = findViewById(R.id.et_name_input);
        sendBtn = findViewById(R.id.btn_send);
        titleText = findViewById(R.id.tv_input_activity);

        setListeners();


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
