package com.example.kanika.parkzyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();// initialise firebase object
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
           finish();
           startActivity(new Intent(getApplicationContext(),UserActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    // initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);//add listener and passing this as in same class
        textViewSignIn.setOnClickListener(this);


    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Log.d("is it empty", "yes its empty");
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        //if validations are ok
        //we will first show a progressbar
        progressDialog.setMessage("Registering User...");
       progressDialog.show();
//takes 2 string argument create user on firebase console.. attach listener whether registration is completed or not
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //user is successfully registered
                            //progressDialog.hide();﻿
                                //profile activity here
                               finish();
                               startActivity(new Intent(getApplicationContext(),UserActivity.class));

                        }
                        else {
                            Toast.makeText(SignUp.this,"Failed to register..Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {

        if(v == buttonRegister){
            registerUser();
        }

        if(v == textViewSignIn){
            //will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
