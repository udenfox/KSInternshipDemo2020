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

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Consts.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setSearchByUserEnabled(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(Consts.PREFS_USERS_REPO, isEnabled).apply();
    }

    public static boolean isSearchByUserEnabled(Context context) {
        return getPrefs(context).getBoolean(Consts.PREFS_USERS_REPO, false);
    }

    public static void setDontClearListEnabled(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(Consts.PREFS_DONT_CLEAR_LIST, isEnabled).apply();
    }

    public static boolean isDontClearListEnabled(Context context) {
        return getPrefs(context).getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false);
    }

    public static void cacheLoadedItems(Context context, List<GitRepoItem> items) {
        getPrefs(context).edit().putString(Consts.PREFS_REPO_LIST, RestClient.getInstance().getGson().toJson(items)).apply();
    }

    public static List<GitRepoItem> getCachedItems(Context context) {

        Type listType = new TypeToken<List<GitRepoItem>>() {}.getType();
        String jsonList = getPrefs(context).getString(Consts.PREFS_REPO_LIST, null);
        if(TextUtils.isEmpty(jsonList)) {
            return null;
        }
        return RestClient.getInstance().getGson().fromJson(jsonList, listType);
    }


}
