package com.mphj.todo.utils;

import android.content.Context;

public class Prefs {

    private static final String SHARED_PREFERENCES_NAME = "todo";

    public static void set(String key, String value, Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putString(key, value);
    }

    public static void setNull(String key, Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putString(key, null);
    }

    public static String asString(String key, Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(key, null);
    }
}
