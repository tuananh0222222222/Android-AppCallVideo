package com.demo.appvideov2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.appvideov2.R;
import com.demo.appvideov2.models.User;
import com.demo.appvideov2.network.ApiClient;
import com.demo.appvideov2.network.ApiService;
import com.demo.appvideov2.utillities.Constants;
import com.demo.appvideov2.utillities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;


public class OutGoingInvitationActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private  String inviterToken = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going_invitation);

        preferenceManager = new PreferenceManager(getApplicationContext());

     FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
         @Override
         public void onComplete(@NonNull Task<String> task) {
             if(task.isSuccessful() && task.getResult() !=null){
                 inviterToken = task.getResult();
             }
         }
     });


        ImageView imageMeeting = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeeting.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUserName = findViewById(R.id.textUserName);
        TextView textEmail = findViewById(R.id.textEmail);

        User user = (User) getIntent().getSerializableExtra("user");

        if(user!=null){
            textFirstChar.setText(user.firstName.substring(0,1));
            textUserName.setText(String.format("%s %s",user.firstName,user.lastName));
            textEmail.setText(user.email);

        }

        ImageView imageStop = findViewById(R.id.imageStop);
        imageStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(meetingType != null && user != null){
            initiateMeeting(meetingType,user.token);
        }

    }

    private void initiateMeeting(String meetingType,String receiverToken){
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);
            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constants.REMOTE_MSG_TYPE,Constants.REMOTE_MSG_INVITATION);
            data.put(Constants.REMOTE_MSG_MEETING_TYPE,meetingType);
            data.put(Constants.KEY_FIRST_NAME,preferenceManager.getString(Constants.KEY_FIRST_NAME));
            data.put(Constants.KEY_LAST_NAME,preferenceManager.getString(Constants.KEY_LAST_NAME));
            data.put(Constants.KEY_EMAIL,preferenceManager.getString(Constants.KEY_EMAIL));
            data.put(Constants.REMOTE_MSG_INVITER_TOKEN,inviterToken);

            body.put(Constants.REMOTE_MSG_DATA,data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS,tokens);

            sendRemoteMessage(body.toString(),Constants.REMOTE_MSG_INVITATION);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage()+"loi", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private  void sendRemoteMessage(String remoteMessageBody ,String type){
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constants.getRemoteMessageHeader(),remoteMessageBody
       ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull  Response<String> response) {
                    if(response.isSuccessful()){
                        if(type.equals(Constants.REMOTE_MSG_INVITATION)){
                            Toast.makeText(OutGoingInvitationActivity.this, "sent succesfuly" , Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(OutGoingInvitationActivity.this, response.message()+"loi", Toast.LENGTH_SHORT).show();
                        finish();

                    }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(OutGoingInvitationActivity.this, t.getMessage()+"loi", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}