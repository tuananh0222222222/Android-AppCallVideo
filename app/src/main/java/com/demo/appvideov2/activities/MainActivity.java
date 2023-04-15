package com.demo.appvideov2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.appvideov2.R;
import com.demo.appvideov2.adapters.UserAdapter;
import com.demo.appvideov2.models.User;
import com.demo.appvideov2.utillities.Constants;
import com.demo.appvideov2.utillities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private PreferenceManager preferenceManager;
    private List<User> users;
    private UserAdapter userAdapter;
    private  TextView textErrorMess;
    private ProgressBar userProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceManager = new PreferenceManager(getApplicationContext());

        TextView texttitle = findViewById(R.id.textTitle);
        texttitle.setText(String.format(
                "%s %s",preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));
        // gui token len firebase
       FirebaseInstallations.getInstance().getId().addOnCompleteListener(new OnCompleteListener<String>() {
           @Override
           public void onComplete(@NonNull Task<String> task) {
               if(task.isSuccessful() && task.getResult() != null){
                   String token = task.getResult();
                   sendCFMTokenToDatabase(token);
               }
           }
       });
       //
        findViewById(R.id.textSignout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        RecyclerView userRecyclerView= findViewById(R.id.userRecycleView);
        textErrorMess = findViewById(R.id.textErrorMessage);
        userProgressBar = findViewById(R.id.userprogressbar);
        users = new ArrayList<>();
        userAdapter = new UserAdapter(users);
        userRecyclerView.setAdapter(userAdapter);

        getUser();
    }

    private  void getUser(){
        userProgressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_USER)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        userProgressBar.setVisibility(View.GONE);
                        String myUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                        if(task.isSuccessful() && task.getResult() != null){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if(myUserId.equals(documentSnapshot.getId())){
                                    continue;
                                }
                                User user = new User();
                                user.firstName = documentSnapshot.getString(Constants.KEY_FIRST_NAME);
                                user.lastName = documentSnapshot.getString(Constants.KEY_LAST_NAME);
                                user.email = documentSnapshot.getString(Constants.KEY_EMAIL);
                                user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                                users.add(user);
                            }
                            if(users.size() > 0){
                                userAdapter.notifyDataSetChanged();
                            }else{
                                textErrorMess.setText(String.format("%s","No user available"));
                                textErrorMess.setVisibility(View.VISIBLE);

                            }
                        }
                        else {
                            textErrorMess.setText(String.format("%s","No user available"));
                            textErrorMess.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    private  void sendCFMTokenToDatabase(String token){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(Constants.KEY_COLLECTION_USER).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
//.addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(MainActivity.this, "Token update success", Toast.LENGTH_SHORT).show();
//            }
//        })
        documentReference.update(Constants.KEY_FCM_TOKEN,token).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "unable send token" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void signOut(){
        Toast.makeText(this,"sign out" ,Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(Constants.KEY_COLLECTION_USER).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        HashMap<String,Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                preferenceManager.clearPreference();
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}