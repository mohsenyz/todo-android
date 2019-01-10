package com.mphj.todo;

import android.app.Application;

import com.mphj.todo.utils.ConstraintsUtils;
import com.mphj.todo.utils.DimensionUtils;
import com.mphj.todo.utils.FontUtils;
import com.mphj.todo.utils.Prefs;
import com.mphj.todo.workers.DBSyncer;
import com.mphj.todo.workers.FcmTokenUploader;

import net.danlew.android.joda.JodaTimeAndroid;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import es.dmoral.toasty.Toasty;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class TodoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("iran.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        Toasty.Config.getInstance()
                .setToastTypeface(FontUtils.def(this))
                .setTextSize(DimensionUtils.spToPx(9, this))
                .apply();
        JodaTimeAndroid.init(this);

        WorkManager.getInstance().cancelUniqueWork(DBSyncer.NAME);
        PeriodicWorkRequest dbSync = new PeriodicWorkRequest.Builder(DBSyncer.class, 15, TimeUnit.SECONDS)
                .setConstraints(ConstraintsUtils.requireInternet())
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(DBSyncer.NAME, ExistingPeriodicWorkPolicy.REPLACE, dbSync);
    }
}
