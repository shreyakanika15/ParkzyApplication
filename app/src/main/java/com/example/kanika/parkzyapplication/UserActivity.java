package com.example.kanika.parkzyapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


//import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private String PREFS_NAME = "bookingData";

    private Button buttonForRenters;
    private Button buttonForDrivers;
    private Button buttonForMeter;

    //private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //firebaseAuth = FirebaseAuth.getInstance();// initialise firebase object
       /*if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
           startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }*/
        progressDialog = new ProgressDialog(this);
        buttonForDrivers = (Button) findViewById(R.id.buttonForDrivers);
        buttonForRenters = (Button) findViewById(R.id.buttonForRenters);
        buttonForMeter = (Button) findViewById(R.id.buttonForMeter);
        buttonForDrivers.setOnClickListener(this);
        buttonForRenters.setOnClickListener(this);
        buttonForMeter.setOnClickListener(this);
    }

    private void renterSelect() {

        progressDialog.setMessage("Going to renters page...");
        progressDialog.show();
        progressDialog.dismiss();

                            //progressDialog.hide();﻿
                            //start the profile activityToast.makeText(SignUp.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),RentersActivity.class));


    }



    private void driverSelect(){
        progressDialog.setMessage("Going to drivers page...");
        progressDialog.show();
        progressDialog.dismiss();

        //progressDialog.hide();﻿
        //start the profile activityToast.makeText(SignUp.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
        finish();
        SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
        String bookingTime =  bookings.getString( "kanika", "" );
        Log.d( "pref name", bookingTime );
        if(bookingTime != null) {
            Log.d( "new activity", "driverSelect: " );
        } else {
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        }
    }

    private void meterSelect(){
        progressDialog.setMessage("Going to meter page...");
        progressDialog.show();
        progressDialog.dismiss();

        //progressDialog.hide();﻿
        //start the profile activityToast.makeText(SignUp.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(),MeterActivity.class));
    }

    @Override
    public void onClick(View v) {

        if(v==buttonForRenters)
        {
           // userSelect();
           // finish();
            //starting login activity
            //startActivity(new Intent(this,ProfileActivity.class));
            renterSelect();
        }

        if(v==buttonForDrivers)
        {
            driverSelect();
        }

        if(v==buttonForMeter)
        {
            meterSelect();
        }

    }
}
