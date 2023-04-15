package com.demo.appvideov2.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    // get token
    @Override
    public void onNewToken(@NonNull String token) {
            super.onNewToken(token);
        Log.d("FCM", "onNewToken: "+token);

    }
    // kiem tra tin nhan tu firebase
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if(message.getNotification() != null){
            Log.d("FCM",
                    "onMessageReceived: " +message.getNotification().getBody()
            );
        }
    }
}
