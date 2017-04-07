package com.example.kanika.parkzyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StopService extends AppCompatActivity {

    private Button bStop;
    private Button bPay;
    private Double endTime;
    private Double startTime;
    private Double convert=3600000.0;
    private Double time;
    private String price;
    private Double amount;
    private TextView tv1;
    private Double difference;
    private Double bookingTime;
    private String PREFS_NAME = "bookingData";
    private String PREFS_NAME1 = "pricedata";
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stop );
        tv1= (TextView) findViewById( R.id.textViewStop );
        user= firebaseAuth.getInstance().getCurrentUser();
        bStop=(Button) findViewById( R.id.stopButton );
        bPay=(Button) findViewById( R.id.payButton );

        bStop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences bookings = getSharedPreferences( PREFS_NAME, 0 );
                SharedPreferences pricedata = getSharedPreferences( PREFS_NAME1, 0 );
                String bookingTime = bookings.getString(user.getUid(), "" );
                startTime= Double.valueOf( bookingTime );
                endTime = Double.valueOf( System.currentTimeMillis() );
                String pri= pricedata.getString( user.getUid(),"" );

                difference=endTime-startTime;
                time=difference/convert;
                amount=time* Double.valueOf( pri );

                tv1.setVisibility( View.VISIBLE );
                tv1.setText("Pay Amount" +amount.toString());




            }
        } );

        bPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences( PREFS_NAME, 0 );
                SharedPreferences.Editor editor = settings.edit();
                editor.remove( user.getUid() );
                editor.commit();
                startActivity( new Intent( getApplicationContext(), PayService.class ) );
            }
        } ) ;

    }
}
