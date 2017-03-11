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
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MeterActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private String PREFS_NAME = "bookingData";
    private Button nStart;
    private Button nEnd;
    private Double startTime;
    private Double endTime;
    private Double convert=3600000.0;
    private boolean start =false;
    private TextView nMeter;
    private Button nLogout;
    private Double time;
    private Double amount;
    private Double difference;
    private Double bookingTime;
    private ZXingScannerView mScannerView;
    private ZXingScannerView mScannerEnd;

   // private Firebase nRootRef,child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
        bookings.getString( "kanika", "" );
        Log.d( "inside Oncreate", "scanneroncreate" );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_meter );

        nStart = (Button) findViewById( R.id.buttonStart );
        nEnd = (Button) findViewById( R.id.buttonEnd );
        nMeter = (TextView) findViewById( R.id.amount );
        nLogout = (Button) findViewById( R.id.buttonLog1 );

    }

        /*nStart.setOnClickListener(new View.OnClickListener(){

         *//*   @Override
            public void onClick(View view) {

               if (view == nStart) {
                   Log.d("inside start","inside qrcode");
                   mScannerView=new ZXingScannerView();
                    // userSelect();
                    startTime= System.currentTimeMillis();
                     start=true;


                }
            }

        });*//*

        nEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (view == nEnd) {
                    // userSelect();
                    if(start== true){

                      endTime = System.currentTimeMillis();

                    }

                }
            }

        });
        nMeter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (view == nMeter) {
                    // userSelect();
                    long difference= endTime-startTime;
                   // Toast.makeText(MeterActivity.this,difference, Toast.LENGTH_SHORT).show();
                    nMeter.setText(String.valueOf(difference));


                }
            }

        });
*/

        //long difference= endTime-startTime;

    public void onClick(View v){
        if(v == nStart) {
            mScannerView = new ZXingScannerView( this );
            Log.d( "insideStart","insideStart" );
            setContentView( mScannerView );
            mScannerView.setResultHandler( this );
            mScannerView.startCamera();
            startTime= Double.valueOf( System.currentTimeMillis() ) ;
            Log.d( "startTime",startTime.toString() );
            start=true;
        }
        /*if(v== nEnd){
            if(start=true) {
                Log.d("Inside end","InsideEnd");
                mScannerEnd = new ZXingScannerView( this );
                setContentView( mScannerEnd );
                mScannerView.setResultHandler( this );
                mScannerView.startCamera();
                endTime = System.currentTimeMillis();
            }
        }*/
       /* if(v==nLogout)
        {
            Log.d( "inside logout","inside logout" );
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }*/

        /*if(v== nMeter){
            Double difference= endTime-startTime;
            // Toast.makeText(MeterActivity.this,difference, Toast.LENGTH_SHORT).show();
            nMeter.setText(String.valueOf(difference));
            Log.d( "difference",difference.toString());
        }*/

    }

    @Override
    protected void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result result) {
        Log.v( "handleResult",result.getText() );
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /*nRootRef = new Firebase("https://parkzyapplication.firebaseio.com/Users");
        child=nRootRef.child( result.getText().toString() );
        String availability=child.getKey();*/
        builder.setCancelable( false );
        builder.setTitle( "Scan result" );
        final TextView tv = new TextView(this);
        tv.setText( "Kanika" );
        builder.setView( tv );
        Log.d("Inside Dialog","insideDialog");
        builder.setMessage( result.getText()+startTime ).setNeutralButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d( "inside endTime","endTime" );
                /*if(start = true){
                    Log.d( "for end","end" );
                    endTime = Double.valueOf( System.currentTimeMillis() );
                    Log.d("endTime",endTime.toString());
                    difference=endTime-startTime;
                    Log.d( "difference",difference.toString());
                    time=difference/convert;
                    Log.d( "time",time.toString() );
                    amount=time*15.0;
                    Log.d( "amount",amount.toString() );
                    //start of new dialogue box
                    builder.setMessage( amount.toString() );
                }

*/
                if(start=true){
                    bookingTime=startTime;
                }
                SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
                SharedPreferences.Editor editor = bookings.edit();
                editor.putString( "kanika", "shreya" );
                editor.commit();
            }
        } );
    /*    builder.setPositiveButton( "positive btn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(start = true){
                    Log.d( "for end","end" );
                    endTime = Double.valueOf( System.currentTimeMillis() );
                    Log.d("endTime",endTime.toString());
                    difference=endTime-startTime;
                    Log.d( "difference",difference.toString());
                    time=difference/convert;
                    Log.d( "time",time.toString() );
                    amount=time*15.0;
                    Log.d( "amount",amount.toString() );
                    //start of new dialogue box
                    tv.setText( amount.toString() );
                }
            }
        } );*/
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Resume Scanning
        //mScannerView.resumeCameraPreview( this );
    }
}
