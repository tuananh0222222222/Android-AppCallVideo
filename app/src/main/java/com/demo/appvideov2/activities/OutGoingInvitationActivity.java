package com.demo.appvideov2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.appvideov2.R;
import com.demo.appvideov2.models.User;


public class OutGoingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going_invitation);

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

    }
}