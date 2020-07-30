package com.keepsolid.ksinternshipdemo2020.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationSettingsManager {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Consts.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setSearchByUserEnabled(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(Consts.PREFS_USERS_REPO, isEnabled).apply(); //TODO: or commit()?
    }

    public static boolean isSearchByUserEnabled(Context context) {
        return getPrefs(context).getBoolean(Consts.PREFS_USERS_REPO, false);
    }

    public static void setDontClearListEnabled(Context context, boolean isEnabled) {
        getPrefs(context).edit().putBoolean(Consts.PREFS_DONT_CLEAR_LIST, isEnabled).apply(); //TODO: or commit()?
    }

    public static boolean isDontClearListEnabled(Context context) {
        return getPrefs(context).getBoolean(Consts.PREFS_DONT_CLEAR_LIST, false);
    }

}
