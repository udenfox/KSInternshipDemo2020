package com.keepsolid.ksinternshipdemo2020.screens.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.keepsolid.ksinternshipdemo2020.api.ApiCallback;
import com.keepsolid.ksinternshipdemo2020.api.RestClient;
import com.keepsolid.ksinternshipdemo2020.base.BasePresenter;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoErrorItem;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.model.GitResponse;
import com.keepsolid.ksinternshipdemo2020.utils.ApplicationSettingsManager;
import com.keepsolid.ksinternshipdemo2020.utils.database.AppDatabase;

import java.util.List;

import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private ApplicationSettingsManager applicationSettingsManager;
    private AppDatabase appDatabase;
    private LiveData<List<GitRepoItem>> liveRepoData;


    public MainPresenter(ApplicationSettingsManager applicationSettingsManager, AppDatabase database) {
        this.applicationSettingsManager = applicationSettingsManager;
        this.appDatabase = database;
    }

    @Override
    public void searchRepos(@NonNull String query) {

        if (query.trim().isEmpty()) {
            view.showInputError();
            return;
        }

        view.hideKeyboard();
        view.showProgress();

        boolean searchByUser = applicationSettingsManager.isSearchByUserEnabled();
        OnRequestFinishedListener onRequestFinishedListener = new OnRequestFinishedListener() {
            @Override
            public void onRequestFinished(@Nullable GitRepoErrorItem errorItem) {
                view.hideProgress();
                if (errorItem != null) {
                    view.showRequestError(errorItem.getMessage() + "; " + errorItem.getDocumentation_url());
                }
            }
        };

        if (searchByUser) {
            getReposByUserName(query, onRequestFinishedListener);
        } else {
            searchReposByName(query, onRequestFinishedListener);
        }

    }

    @Override
    public void setDontClearResults(boolean dontClearResults) {
        applicationSettingsManager.setDontClearListEnabled(dontClearResults);
    }

    @Override
    public void setSearchByUserNameEnabled(boolean searchByUserNameEnabled) {
        applicationSettingsManager.setSearchByUserEnabled(searchByUserNameEnabled);
    }

    @Override
    public void takeView(MainContract.View view) {

        this.view = view;

        view.setDontClearChecked(applicationSettingsManager.isDontClearListEnabled());
        view.setSearchByRepoChecked(applicationSettingsManager.isSearchByUserEnabled());

        liveRepoData = appDatabase.repoItemDao().getAll();
        view.observeItems(liveRepoData);

    }

    @Override
    public void dropView() {
        view.stopObserving(liveRepoData);
        this.view = null;
        liveRepoData = null;
    }

    private void searchReposByName(String repoName, OnRequestFinishedListener onRequestFinishedListener) {
        RestClient.getInstance().getService().searchRepos(repoName).enqueue(new ApiCallback<GitResponse>() {

            @Override
            public void success(Response<GitResponse> response) {
                cacheItems(response.body().getItems());
                if (onRequestFinishedListener != null) {
                    onRequestFinishedListener.onRequestFinished(null);
                }
            }

            @Override
            public void failure(GitRepoErrorItem gitRepoError) {
                if (onRequestFinishedListener != null) {
                    onRequestFinishedListener.onRequestFinished(gitRepoError);
                }
            }
        });
    }

    private void getReposByUserName(String username, OnRequestFinishedListener onRequestFinishedListener) {
        RestClient.getInstance().getService().getReposByUserName(username).enqueue(new ApiCallback<List<GitRepoItem>>() {

            @Override
            public void success(Response<List<GitRepoItem>> response) {
                cacheItems(response.body());
                if (onRequestFinishedListener != null) {
                    onRequestFinishedListener.onRequestFinished(null);
                }
            }

            @Override
            public void failure(GitRepoErrorItem gitRepoError) {
                if (onRequestFinishedListener != null) {
                    onRequestFinishedListener.onRequestFinished(gitRepoError);
                }
            }
        });
    }

    private void cacheItems(List<GitRepoItem> items) {
        if (!applicationSettingsManager.isDontClearListEnabled()) {
            appDatabase.repoItemDao().deleteAll();
        }
        appDatabase.repoItemDao().insert(items);
    }

    interface OnRequestFinishedListener {

        void onRequestFinished(@Nullable GitRepoErrorItem errorItem);

    }

}
