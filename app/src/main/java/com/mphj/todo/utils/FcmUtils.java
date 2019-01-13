package com.mphj.todo.utils;

import android.content.Context;

import com.mphj.todo.workers.FcmTokenUploader;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static com.mphj.todo.services.AppFirebaseMessagingService.PREFS_KEY_FCM_TOKEN;

public class FcmUtils {

    public static void pushToken(Context context) {
        String token = Prefs.asString(PREFS_KEY_FCM_TOKEN, context);
        if (token == null)
            return;
        WorkManager.getInstance().cancelUniqueWork(FcmTokenUploader.NAME);
        Data data = new Data.Builder()
                .putString(FcmTokenUploader.ARG_TOKEN, token)
                .build();
        OneTimeWorkRequest fcmTokenUploader = new OneTimeWorkRequest.Builder(FcmTokenUploader.class)
                .setConstraints(ConstraintsUtils.requireInternet())
                .setInputData(data)
                .build();
        WorkManager.getInstance().enqueueUniqueWork(FcmTokenUploader.NAME, ExistingWorkPolicy.REPLACE, fcmTokenUploader);
    }

}
