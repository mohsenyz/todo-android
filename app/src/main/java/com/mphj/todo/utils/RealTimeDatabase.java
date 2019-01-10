package com.mphj.todo.utils;

import android.content.Context;

public class RealTimeDatabase {

    private static final String LAST_UPDATE_TIME = "db_last_update_time";

    public static void setUpdatedAt(long time, Context context) {
        Prefs.set(LAST_UPDATE_TIME, time, context);
    }

    public static long getLastUpdate(Context context) {
        return Prefs.asLong(LAST_UPDATE_TIME, context);
    }

}
