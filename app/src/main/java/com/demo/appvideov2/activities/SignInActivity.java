package com.demo.appvideov2.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.demo.appvideov2.R;
import com.demo.appvideov2.utillities.Constants;
import com.demo.appvideov2.utillities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail ,inputPass ;
    private AppCompatButton buttonSignin;
    private ProgressBar progressBarSinIn;

    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        preferenceManager = new PreferenceManager(getApplicationContext());

        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
            startActivity(intent);
            finish();
        }
        //goi sign up
        findViewById(R.id.text_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });

        inputEmail = findViewById(R.id.input_email);
        inputPass = findViewById(R.id.input_password);
        buttonSignin = findViewById(R.id.button_signin);
        progressBarSinIn = findViewById(R.id.signInProgressBar);
        // validation input
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignInActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()) {
                    Toast.makeText(SignInActivity.this, "Không đúng định dạng email", Toast.LENGTH_SHORT).show();
                } else if (inputPass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    SignIn();
                }
            }
        });

    }

    private  void SignIn(){
        buttonSignin.setVisibility(View.INVISIBLE);
        progressBarSinIn.setVisibility(View.VISIBLE);
        FirebaseFirestore db =  FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_USER).whereEqualTo(Constants.KEY_EMAIL,inputEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,inputPass.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                            preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());

                            preferenceManager.putString(Constants.KEY_LAST_NAME,documentSnapshot.getString(Constants.KEY_LAST_NAME));
                            preferenceManager.putString(Constants.KEY_EMAIL,documentSnapshot.getString(Constants.KEY_EMAIL));

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        }else{
                            progressBarSinIn.setVisibility(View.INVISIBLE);
                            buttonSignin.setVisibility(View.VISIBLE);
                            Toast.makeText(SignInActivity.this, "Tài khoản hoặc mật khẩu không đúng!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}