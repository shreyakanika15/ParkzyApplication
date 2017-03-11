package com.example.kanika.parkzyapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.cast.framework.Session;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



import static android.R.attr.start;

/**
 * Created by Kanika on 03-03-2017.
 */

public class readPosts{
    //Context activityContext;

   ArrayList allpostarraylist=new ArrayList();
    final  HashMap<String, post> posts = new HashMap<String, post>();






    // Firebase ref = new Firebase("https://mapsactivity-c245c.firebaseio.com/posts");
    public HashMap<String,post> readPost() {

        Log.d("inside readpost", "onclick");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        Log.d("myRef", myRef.toString());
        ArrayList<DatabaseReference> databaseReferences=new ArrayList<DatabaseReference>();
        databaseReferences.add(myRef);

        myRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Log.d("onchild", "hi");

//                Log.d("previouschild",previousChildKey);
               getPostList((Map<String,Object>) snapshot.getValue(), (String)snapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });




// Attach an listener to read the data at our posts reference
        Log.d("datab2", posts.toString());
 return posts;

    }


    public void getPostList(Map<String,Object> dataSnapshot,String abc) {
        Map<String,String> maps=new HashMap<String, String>();
        Log.d("inside getpostlist", "hi");
        int index=0;
        post post=new post();

     //   Iterable<DataSnapshot> children=dataSnapshot.getChildren();
        //for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
        Integer index1=0;
        for(Map.Entry<String,Object>entry:dataSnapshot.entrySet())

        {
            index1++;
           // Log.d("entry", entry.getValue().toString());
            Log.d("index", ( index1.toString()));
            Log.d("entry", entry.getKey().toString());

            maps.put(entry.getKey().toString(),entry.getValue().toString());

           /* Log.d("dat", dataSnapshot.getChildren().toString());
            Log.d("inside for datasnapshot", postSnapshot.toString());
            Log.d("availability", postSnapshot.child("availability").getValue().toString());*/
           /*post.setAvailability(postSnapshot.child("availability").getValue().toString());
            post.setLatitude(postSnapshot.child("latitude").getValue().toString());
            post.setLongitude(postSnapshot.child("longitude").getValue().toString());

            post.setName((String) postSnapshot.child("name").getValue());
            post.setPrice(postSnapshot.child("price").getValue().toString());

          //  post post= postSnapshot.getValue(com.example.kanika.parkzyapplication.post.class);


            posts.put(postSnapshot.getKey(),post);*/


           // Object post1=new post();
            /*Map singleUser = (Map) entry.getValue();
            post.setName(singleUser.get("availability").toString());
            post.setName(singleUser.get("latitude").toString());
            post.setName(singleUser.get("longitude").toString());
            post.setName(singleUser.get("name").toString());
            post.setName(singleUser.get("price").toString());
            posts.put(entry.getKey(),post);*/

            // allpostarraylist.add(posts);
        }
        post.setName(maps.get("name"));
        post.setName(maps.get("availability"));
        post.setName(maps.get("latitude"));
        post.setName(maps.get("longitude"));
        post.setName(maps.get("price"));
        posts.put(abc,post);
Log.d("after posts","hi");
        Integer size=posts.size();
     Log.d( "getkey" , size.toString());
        /*if(posts.size()>0)
        {
            Log.d( "getkeynjkdj" , size.toString());
          //  Intent intent= new Intent(activityContext,ProfileActivity.class);
          //  intent.putExtra("yash",posts);
            Log.d("after intent", "after intent");
          //  activityContext.startActivity(intent);
            //finish();
            //starting login activity
            //startActivity(new Intent(this,ProfileActivity.class));
            // startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


            //  intent= new Intent(MapsActivity.class);
        }*/



    }


   /* @Override
    public void onDataAdded(String string) {
        allpostarraylist.add(string);

    }*/
}
