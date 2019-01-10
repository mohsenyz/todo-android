package com.mphj.todo.utils;

import android.content.Context;

public class Prefs {

    private static final String SHARED_PREFERENCES_NAME = "todo";

    public static void set(String key, String value, Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static void set(String key, long value, Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putLong(key, value).apply();
    }

    public static void setNull(String key, Context context) {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putString(key, null).apply();
    }

    public static String asString(String key, Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(key, null);
    }

    public static long asLong(String key, Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getLong(key, -1l);
    }
}
