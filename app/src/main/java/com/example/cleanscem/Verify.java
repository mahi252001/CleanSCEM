package com.example.cleanscem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verify extends AppCompatActivity {
    Button signin;
    TextInputEditText emailedittext,passwordedittext,nameedittext;
    ImageView stepImageView, grpImageView, fieldImageView;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog loader;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static String email,name,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailedittext=findViewById(R.id.emailEditText);
        passwordedittext=findViewById(R.id.passwordEditText);
        nameedittext = findViewById(R.id.nameEditText);
        signin=findViewById(R.id.signin);
        loader = new ProgressDialog(this);
        stepImageView = findViewById(R.id.stepimageview);
        grpImageView = findViewById(R.id.imageView5);
        fieldImageView = findViewById(R.id.imageView3);

        signin.setOnClickListener(view -> {
            email=emailedittext.getText().toString();
            password=passwordedittext.getText().toString();
            name=nameedittext.getText().toString();
            if(email.isEmpty())
            {
                emailedittext.setError("please enter email id");
                emailedittext.requestFocus();
            }
            else if(password.isEmpty())
            {
                passwordedittext.setError("please enter password");
                passwordedittext.requestFocus();
            }
            else if(email.isEmpty() && password.isEmpty())
            {
                Toast.makeText(Verify.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
            }
            else
            {
                mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Verify.this, task -> {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(Verify.this, "Login Error, please try again", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        loader.setMessage("Signing in....");
                        loader.setCanceledOnTouchOutside(false);
                        loader.show();
                        startActivity(new Intent(Verify.this,Home.class));
                    }

                });
            }
        });

    }


}