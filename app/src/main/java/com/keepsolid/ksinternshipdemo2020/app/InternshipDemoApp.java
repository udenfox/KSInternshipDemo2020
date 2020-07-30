package com.keepsolid.ksinternshipdemo2020.app;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

public class InternshipDemoApp extends Application {

    private final static String LOG_TAG = InternshipDemoApp.class.getSimpleName();

    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "gititems")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(LOG_TAG, "onTerminate()");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged()");
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
