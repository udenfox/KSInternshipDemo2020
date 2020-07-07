package com.keepsolid.ksinternshipdemo2020.app;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class InternshipDemoApp extends Application {

    private final static String LOG_TAG = InternshipDemoApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
