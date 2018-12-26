package com.mphj.todo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mphj.todo.utils.DimensionUtils;
import com.mphj.todo.utils.FontUtils;

import net.danlew.android.joda.JodaTimeAndroid;

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
                .setTextSize(DimensionUtils.spToPx(10, this))
                .apply();
        JodaTimeAndroid.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
