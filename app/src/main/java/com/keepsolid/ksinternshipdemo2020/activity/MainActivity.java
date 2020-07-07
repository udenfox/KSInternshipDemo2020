package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.keepsolid.ksinternshipdemo2020.R;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton explicitBtn;
    private AppCompatButton implicitBtn;
    private AppCompatButton emailBtn;
    private AppCompatButton browserBtn;
    private AppCompatButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();

    }

    private void setListeners() {
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

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("somemail@mail.com", "Test message!", "Hello world!");
            }
        });

        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl("https://www.google.com");
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });
    }

    private void initViews() {
        explicitBtn = findViewById(R.id.btn_open_explicit);
        implicitBtn = findViewById(R.id.btn_open_implicit);
        emailBtn = findViewById(R.id.btn_send_email);
        browserBtn = findViewById(R.id.btn_open_google);
        settingsBtn = findViewById(R.id.btn_open_settings);
    }

    public void openSecondActivityExplicit() {
        Intent explicitIntent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(explicitIntent);
    }

    public void openThirdActivityImplicit() {
        Intent explicitIntent = new Intent("com.keepsolid.ksinternshipdemo2020.ACTION_THIRD_ACTIVITY");
        startActivity(explicitIntent);
    }

    public void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void sendEmail(String email, String subject, String message) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(intent);
    }

    public void openSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(settingsIntent);
    }

}
