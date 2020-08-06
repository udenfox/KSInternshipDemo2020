package com.keepsolid.ksinternshipdemo2020.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.keepsolid.ksinternshipdemo2020.api.RestClient;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;

import java.lang.reflect.Type;
import java.util.List;

public class ApplicationSettingsManager {

    private Context context;

    public ApplicationSettingsManager(Context ctx) {
        this.context = ctx;
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(Consts.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setSearchByUserEnabled(boolean isEnabled) {
        getPrefs().edit().putBoolean(Consts.PREFS_USERS_REPO, isEnabled).apply();
    }

    public boolean isSearchByUserEnabled() {
        return getPrefs().getBoolean(Consts.PREFS_USERS_REPO, false);
    }

    public void setDontClearListEnabled(boolean isEnabled) {
        getPrefs().edit().putBoolean(Consts.PREFS_DONT_CLEAR_LIST, isEnabled).apply();
    }

    public boolean isDontClearListEnabled() {
        return getPrefs().getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false);
    }

    public void cacheLoadedItems(List<GitRepoItem> items) {
        getPrefs().edit().putString(Consts.PREFS_REPO_LIST, RestClient.getInstance().getGson().toJson(items)).apply();
    }

    public List<GitRepoItem> getCachedItems() {

        Type listType = new TypeToken<List<GitRepoItem>>() {
        }.getType();
        String jsonList = getPrefs().getString(Consts.PREFS_REPO_LIST, null);
        if (TextUtils.isEmpty(jsonList)) {
            return null;
        }
        return RestClient.getInstance().getGson().fromJson(jsonList, listType);
    }


}
