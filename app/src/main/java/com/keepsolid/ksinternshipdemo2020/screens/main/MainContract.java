package com.keepsolid.ksinternshipdemo2020.screens.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.keepsolid.ksinternshipdemo2020.base.BaseView;
import com.keepsolid.ksinternshipdemo2020.base.BasePresenter;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showInputError();

        void showRequestError(@NonNull String message);

        void observeItems(LiveData<List<GitRepoItem>> itemsLiveData);

        void setDontClearChecked(boolean isChecked);

        void setSearchByRepoChecked(boolean isChecked);

        void stopObserving(LiveData<List<GitRepoItem>> liveRepoData);
    }

    interface Presenter extends BasePresenter<View> {

        void searchRepos(@NonNull String query);

        void setDontClearResults(boolean dontClearResults);

        void setSearchByUserNameEnabled(boolean searchByUserNameEnabled);


    }

}
