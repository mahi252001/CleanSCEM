package com.example.cleanscem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    Button addbutton;
    TextInputEditText nameedittext,emailedittext,passwordedittext;
    TextView tvsignin;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog loader;
    public static String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailedittext = findViewById(R.id.emailEditText);
        passwordedittext = findViewById(R.id.passwordEditText);
        nameedittext = findViewById(R.id.nameEditText);
        addbutton=findViewById(R.id.addbutton);
        tvsignin=findViewById(R.id.tvsignin);
        loader = new ProgressDialog(this);
        addbutton.setOnClickListener(view -> {


            email = emailedittext.getText().toString();
            password = passwordedittext.getText().toString();
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
                Toast.makeText(Login.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
            }

           else if (!isValidPassword(password)) {
                Toast.makeText(Login.this, "Password Does not match the rules", Toast.LENGTH_LONG).show();
                return;
            }
           else
            {
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, task -> {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(Login.this, "Sign Up Unsuccessfull, please try again", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        loader.setMessage("Registering You....");
                        loader.setCanceledOnTouchOutside(false);
                        loader.show();
                        startActivity(new Intent(Login.this,Verify.class));
                    }

                });
            }


        });
        tvsignin.setOnClickListener(view -> {
            Intent i=new Intent(Login.this,Verify.class);
            startActivity(i);
        });

    }
    Pattern lowercase = Pattern.compile("^.*[a-z].*$");
    Pattern uppercase = Pattern.compile("^.*[A-Z].*$");
    Pattern number = Pattern.compile("^.*[0-9].*$");
    Pattern specialCharacter = Pattern.compile("^.*[^a-zA-Z0-9].*$");

    private Boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!lowercase.matcher(password).matches()) {
            return false;
        }
        if (!uppercase.matcher(password).matches()) {
            return false;
        }
        if (!number.matcher(password).matches()) {
            return false;
        }
        if (!specialCharacter.matcher(password).matches()) {
            return false;
        }
        return true;
    }
    boolean doubleBackToExitPressedOnce;

    {
        doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
