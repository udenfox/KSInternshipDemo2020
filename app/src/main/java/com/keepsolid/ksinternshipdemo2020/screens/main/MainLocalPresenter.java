package com.keepsolid.ksinternshipdemo2020.screens.main;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.utils.ApplicationSettingsManager;
import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

import java.util.List;

public class MainLocalPresenter extends MainPresenter {

    private MainContract.View view;
    private ApplicationSettingsManager applicationSettingsManager;
    private AppDatabase appDatabase;
    private LiveData<List<GitRepoItem>> searchLiveData;


    public MainLocalPresenter(ApplicationSettingsManager applicationSettingsManager, AppDatabase database) {
        super(applicationSettingsManager, database);
        this.appDatabase = database;
        this.applicationSettingsManager = applicationSettingsManager;
    }

    @Override
    public void takeView(MainContract.View view) {
        this.view = view;

        view.setDontClearChecked(applicationSettingsManager.isDontClearListEnabled());
        view.setSearchByRepoChecked(applicationSettingsManager.isSearchByUserEnabled());
    }

    @Override
    public void dropView() {
        view.stopObserving(searchLiveData);
        this.view = null;
        searchLiveData = null;
    }

    @Override
    public void searchRepos(@NonNull String query) {

        if(searchLiveData != null && searchLiveData.hasObservers()) {
            view.stopObserving(searchLiveData);
        }

        if (TextUtils.isEmpty(query)) {
            view.showInputError();
        }

        view.hideKeyboard();

        boolean searchByUserEnabled = applicationSettingsManager.isSearchByUserEnabled();

        if (searchByUserEnabled) {
            searchLiveData = appDatabase.repoItemDao().searchByUserLogin(query);
        } else {
            searchLiveData = appDatabase.repoItemDao().searchByRepoName(query);
        }

        view.observeItems(searchLiveData);
    }

}
