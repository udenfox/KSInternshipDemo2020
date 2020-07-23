package com.keepsolid.ksinternshipdemo2020.utils;


import androidx.annotation.NonNull;

import com.keepsolid.ksinternshipdemo2020.model.TaskItem;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnTaskItemLoadingCallback;

public class HardTasks {

    private final static int THREAD_SLEEP_TIME = 4000;

    public void getTaskItemHardly(@NonNull String taskName, OnTaskItemLoadingCallback callback) {

        if (callback != null) {
            callback.onLoadingStarted();
        }

        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        if (callback != null) {
            callback.onLoadingFinish(new TaskItem(true, taskName, TaskItem.Type.PLACE, "13:00", "15/05/2017"));
        }

    }


}

