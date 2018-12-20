package com.mphj.todo.utils;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtils {

    public static String getId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
