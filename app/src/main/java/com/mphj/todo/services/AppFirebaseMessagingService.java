package com.mphj.todo.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mphj.todo.utils.FcmUtils;
import com.mphj.todo.utils.Prefs;
import com.mphj.todo.utils.RealTimeDatabase;

import java.util.Map;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    public static final String PREFS_KEY_FCM_TOKEN = "fcm_token";

    @Override
    public void onNewToken(String token) {
        Prefs.set("temp_key", token, getApplicationContext());
        Prefs.set(PREFS_KEY_FCM_TOKEN, token, getApplicationContext());
        FcmUtils.pushToken(getApplicationContext());
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        if (data.get("type").equals("update")) {
            RealTimeDatabase.sync();
        }
    }
}
