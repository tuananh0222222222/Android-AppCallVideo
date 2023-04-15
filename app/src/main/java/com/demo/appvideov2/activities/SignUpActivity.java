package com.demo.appvideov2.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.demo.appvideov2.R;
import com.demo.appvideov2.utillities.Constants;
import com.demo.appvideov2.utillities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputFirsrName,inputLastName,inputEmail,inputPassWord,inputComfirmPass;
    private AppCompatButton buttonSignUp;
    private ProgressBar signUpprogressBar;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferenceManager = new PreferenceManager(getApplicationContext());
        // click quay tro lai login
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.text_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // goi id tu activity
        inputFirsrName = findViewById(R.id.input_firstName);
        inputLastName = findViewById(R.id.input_lastname);
        inputEmail = findViewById(R.id.input_email);
        inputPassWord = findViewById(R.id.input_password);
        inputComfirmPass = findViewById(R.id.input_Confirmpassword);
        buttonSignUp = findViewById(R.id.button_signup);
        signUpprogressBar = findViewById(R.id.signUpProgressbar);
        // validation
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputFirsrName.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (inputLastName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else if (inputEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Không được để trống ", Toast.LENGTH_SHORT).show();
                }else if (inputPassWord.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                else if (inputComfirmPass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "không được để trống", Toast.LENGTH_SHORT).show();
                } else if (!inputComfirmPass.getText().toString().equals(inputPassWord.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            }else {
                    signUp();
                }
            }
        });

    }
    // ham sign up
    private  void signUp(){
        buttonSignUp.setVisibility(View.INVISIBLE);
        signUpprogressBar.setVisibility((View.VISIBLE));

        //get input
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> user = new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME,inputFirsrName.getText().toString());
        user.put(Constants.KEY_LAST_NAME,inputLastName.getText().toString());
        user.put(Constants.KEY_EMAIL,inputEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD,inputPassWord.getText().toString());

        // colection db
        db.collection((Constants.KEY_COLLECTION_USER))
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                preferenceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                preferenceManager.putString(Constants.KEY_FIRST_NAME,inputFirsrName.getText().toString());
                preferenceManager.putString(Constants.KEY_LAST_NAME,inputLastName.getText().toString());
                preferenceManager.putString(Constants.KEY_EMAIL,inputEmail.getText().toString());

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }).addOnFailureListener(e -> {
            signUpprogressBar.setVisibility(View.INVISIBLE);
            buttonSignUp.setVisibility(View.VISIBLE);
            Toast.makeText(SignUpActivity.this, "Error"+e.getMessage() , Toast.LENGTH_SHORT).show();
        });
    }
}