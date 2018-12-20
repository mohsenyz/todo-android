package com.mphj.todo.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.mphj.todo.utils.ConstraintsUtils;
import com.mphj.todo.utils.Prefs;
import com.mphj.todo.workers.FcmTokenUploader;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String PREFS_KEY_FCM_TOKEN = "fcm_token";

    @Override
    public void onNewToken(String token) {
        Prefs.set(PREFS_KEY_FCM_TOKEN, token, getApplicationContext());
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
