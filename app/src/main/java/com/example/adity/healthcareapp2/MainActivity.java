package com.example.adity.healthcareapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button learnButton, signupButton, loginButton;
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    FirebaseAuth.AuthStateListener firebaseAuthStateLIstner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        firebaseAuthStateLIstner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //check user

                user = firebaseAuth.getCurrentUser();
                if(user!=null){

                    finish();
                    startActivity(new Intent(getApplicationContext(),NavActivity.class));


                }

            }
        };

        firebaseAuth.addAuthStateListener(firebaseAuthStateLIstner);
        //if(firebaseAuth.getCurrentUser()!=null){

        //    finish();
        //  startActivity(new Intent(getApplicationContext(),TrendzMag.class));

        //}
        learnButton = (Button)findViewById(R.id.learnButton);
        signupButton=(Button)findViewById(R.id.signupButton);
        loginButton=(Button)findViewById(R.id.loginButton);
        emailEditText=(EditText)findViewById(R.id.emailEditTExt);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        progressDialog = new ProgressDialog(this);

        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),NavActivity.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateLIstner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateLIstner);
    }

    public void loginUser(){

        String email= emailEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required !");
            emailEditText.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Password is required!");
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordEditText.setError("Password too short!");
            passwordEditText.requestFocus();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(),NavActivity.class));
                            Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Server Busy!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
