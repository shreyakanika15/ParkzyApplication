package com.example.kanika.parkzyapplication;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
//import com.firebase.database.FirebaseDatabase;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Kanika on 21-02-2017.
 */

public class ParkzyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

       if(!FirebaseApp.getApps(this).isEmpty()){
           FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       }
    }
}
