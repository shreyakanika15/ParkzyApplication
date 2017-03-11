package com.example.kanika.parkzyapplication;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kanika on 04-03-2017.
 */

public class DataListener implements ValueEventListener {

    private DataCallback dataCallback;

    public DataListener(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
       // Attributes snapshot;
        String data = dataSnapshot.getValue(String.class);

        //This is step 4
        if(dataCallback!=null) {
            dataCallback.onContactAdded(data);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}