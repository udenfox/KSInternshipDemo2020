package com.keepsolid.ksinternshipdemo2020.utils;


import androidx.annotation.NonNull;

import com.keepsolid.ksinternshipdemo2020.model.TaskItem;

public class HardTasks {

    public final static int THREAD_SLEEP_TIME = 4000;

    public static TaskItem getTaskItemHardly(@NonNull String taskName) {

        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        return new TaskItem(true, taskName, TaskItem.Type.PLACE, "13:00", "15/05/2017");
    }

}
