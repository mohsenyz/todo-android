package com.mphj.todo.utils;

import android.content.Context;

import com.mphj.todo.workers.DBSyncer;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class RealTimeDatabase {

    private static final String LAST_UPDATE_TIME = "db_last_update_time";

    public static void setUpdatedAt(long time, Context context) {
        Prefs.set(LAST_UPDATE_TIME, time, context);
    }

    public static long getLastUpdate(Context context) {
        return Prefs.asLong(LAST_UPDATE_TIME, context);
    }

    public static void sync() {
        OneTimeWorkRequest dbSync = new OneTimeWorkRequest.Builder(DBSyncer.class)
                .setConstraints(ConstraintsUtils.requireInternet())
                .build();
        WorkManager.getInstance().enqueue(dbSync);
    }

    public static void initSync() {
        WorkManager.getInstance().cancelUniqueWork(DBSyncer.NAME);
        PeriodicWorkRequest dbSync = new PeriodicWorkRequest.Builder(DBSyncer.class, 15, TimeUnit.MINUTES)
                .setConstraints(ConstraintsUtils.requireInternet())
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(DBSyncer.NAME, ExistingPeriodicWorkPolicy.REPLACE, dbSync);
    }
}
