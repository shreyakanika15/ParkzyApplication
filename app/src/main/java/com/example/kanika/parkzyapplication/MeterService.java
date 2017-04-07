package com.example.kanika.parkzyapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MeterService extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private String PREFS_NAME = "bookingData";
    private Button nStart;
    private Double startTime;
    private boolean start =false;
    private Double bookingTime;
    private FirebaseUser user;
    private ZXingScannerView mScannerView;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );

        user= firebaseAuth.getInstance().getCurrentUser();
        bookings.getString( user.getUid(), "" );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_meter );

        nStart = (Button) findViewById( R.id.buttonStart );

    }

    public void onClick(View v){
        if(v == nStart) {
            mScannerView = new ZXingScannerView( this );
            setContentView( mScannerView );
            mScannerView.setResultHandler( this );
            mScannerView.startCamera();
            startTime= Double.valueOf( System.currentTimeMillis() ) ;
            start=true;
        }


    }

    @Override
    protected void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result result) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable( false );
        builder.setTitle( "Scan result" );
        final TextView tv = new TextView(this);
        builder.setView( tv );
        builder.setMessage( result.getText()+startTime ).setNeutralButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(start==true){
                    bookingTime=startTime;
                }
                SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
                SharedPreferences.Editor editor = bookings.edit();
                editor.putString( user.getUid(), bookingTime.toString());
                editor.commit();
            }
        } );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
