package com.keepsolid.ksinternshipdemo2020.utils.listener;

import com.keepsolid.ksinternshipdemo2020.model.TaskItem;

public interface OnTaskItemLoadingCallback {

    void onLoadingStarted();

    void onLoadingFinish(TaskItem taskItem);

}
