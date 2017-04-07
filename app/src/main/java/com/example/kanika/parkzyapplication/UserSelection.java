package com.example.kanika.parkzyapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserSelection extends AppCompatActivity implements View.OnClickListener{

    private String PREFS_NAME = "bookingData";

    private Button buttonForRenters;
    private Button buttonForDrivers;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user= firebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        buttonForDrivers = (Button) findViewById(R.id.buttonForDrivers);
        buttonForRenters = (Button) findViewById(R.id.buttonForRenters);
        buttonForDrivers.setOnClickListener(this);
        buttonForRenters.setOnClickListener(this);
    }

    private void renterSelect() {

        progressDialog.setMessage("Going to renters page...");
        progressDialog.show();
        progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(),RentersService.class));


    }



    private void driverSelect(){
        progressDialog.setMessage("Going to drivers page...");
        progressDialog.show();
        progressDialog.dismiss();
        finish();
        SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
        String bookingTime = bookings.getString(user.getUid(), "" );

            if (bookingTime != "") {
                startActivity( new Intent( getApplicationContext(), StopService.class ) );

            }
            else {

                startActivity( new Intent( getApplicationContext(), MapsService.class ) );
            }

        }


    @Override
    public void onClick(View v) {

        if(v==buttonForRenters)
        {
            renterSelect();
        }

        if(v==buttonForDrivers)
        {
            driverSelect();
        }


    }
}
