package com.mphj.todo.services;

import com.google.firebase.messaging.FirebaseMessagingService;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        // @Todo send token to server
    }


}
