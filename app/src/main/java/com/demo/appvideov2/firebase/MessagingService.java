package com.demo.appvideov2.firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.demo.appvideov2.activities.IncomingInvitationActivity;
import com.demo.appvideov2.utillities.Constants;
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

        String type =message.getData().get(Constants.REMOTE_MSG_TYPE);

        if(type != null){
            if(type.equals(Constants.REMOTE_MSG_INVITATION)){
                Intent intent = new Intent(getApplicationContext(),IncomingInvitationActivity.class);
                intent.putExtra(
                        Constants.REMOTE_MSG_MEETING_TYPE,
                        message.getData().get(Constants.REMOTE_MSG_MEETING_TYPE)
                );
                intent.putExtra(
                        Constants.KEY_FIRST_NAME,
                        message.getData().get(Constants.KEY_FIRST_NAME)
                );
                intent.putExtra(
                        Constants.KEY_LAST_NAME,
                        message.getData().get(Constants.KEY_LAST_NAME)
                );
                intent.putExtra(
                        Constants.KEY_EMAIL,
                        message.getData().get(Constants.KEY_EMAIL)
                );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }
        
    }
}
