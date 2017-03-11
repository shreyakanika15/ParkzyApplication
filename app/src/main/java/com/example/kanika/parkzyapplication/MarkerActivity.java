package com.example.kanika.parkzyapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarkerActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {



    //private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    LatLng latLng;
    Marker mCurrLocationMarker;
    private GoogleMap mMap;
    final  HashMap<String, post> posts = new HashMap<String, post>();
    final  HashMap<String, post> nearbyPosts = new HashMap<String, post>();
    public String name=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("oncreate", "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //new code starts

                mapFragment.getMapAsync(this);
        Log.d("after mapfragment", "after mapfragment");



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Context context=this;
// code for current location

Log.d("onMapReady","insideOnMapReady");
                //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                Log.d("mMap.setMyLocat", "hi");
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        //ends
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
                getPostList((Map<String,Object>) snapshot.getValue(), (String)snapshot.getKey());
                LatLng trinity = new LatLng(53.3438, -6.2546);
                post post=new post();
                String latitude=null;
                String longitude=null;
            //   String availability=null;
            //   String price=null;
               // Log.d("mGoogleApiClient",mGoogleApiClient.toString());
               // Log.d("mLocationRequest",mLocationRequest.toString());
                //Log.d("mLastLocation",mLastLocation.toString());
               // Log.d("mCurrLocationMarker",mCurrLocationMarker.toString());
                Location location=new Location("");
                if(posts.size()>0){


                    for(Map.Entry<String,post>entry:posts.entrySet())

                    {
                        post=entry.getValue();
                        latitude=post.getLatitude();
                        longitude=post.getLongitude();
                        final    String     availability=post.getAvailability();
                    final String    price=post.getPrice();
                        location.setLatitude(Double.valueOf(latitude));
                        location.setLongitude(Double.valueOf(longitude));
                        //Log.d("lastlocation",getIntent().getExtras().getBinder("Last_Location").toString());
                       // final LatLng currentLocation = (LatLng) ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("Last_Location")).getData();
                       // Log.d("currentLocation", currentLocation.toString());

                        Log.d("entry in for",entry.getValue().toString());
                    Log.d("Latitude",latitude);
                        Log.d("Longitude",longitude);
                        if (true) {
                            LatLng sydney = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                            Log.d("posts", posts.toString());
                            /*List<Marker> markers = new ArrayList<Marker>();
                            Marker marker = mMap.addMarker(new MarkerOptions().position(sydney));

                            markers.add( marker );*/
                           mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                            final   AlertDialog.Builder builder= new AlertDialog.Builder(context);
                            Log.d("after marker","after marker");
                            name="yash";


                            builder.setNeutralButton( "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            } );

                            mMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener(){
                               public boolean onMarkerClick(Marker marker)
                                {
                                    String availability1=null;
                                    String price1=null;
                                    for(Map.Entry<String,post>entry:posts.entrySet())
                                    {
                                      post  post1=entry.getValue();
                                      String  latitude1=post1.getLatitude();
                                        String longitude1=post1.getLongitude();
                                       /* Location location1=new Location( "" );*/
                                        LatLng latLng=new LatLng( Double.valueOf( latitude1 ),Double.valueOf( longitude1 ) );
                                        Log.d( "latlong",latLng.toString() );
                                       /* location1.setLongitude( Double.valueOf( longitude1 ) );
                                        location1.setLatitude( Double.valueOf( latitude1 ) );*/
                                        Log.d( "marker",marker.getPosition() .toString());
                                        if(marker.getPosition().equals( latLng ))
                                        {
                                            availability1=post1.getAvailability();
                                            Log.d( "avail1",availability1.toString() );
                                            price1=post1.getPrice();
                                        }
                                    }

                                    Log.d( "inside onclickmarker","inside" );
                                    builder.show();
                                    builder.setView( R.layout.dialog );
                                  //  TextView textView2=(TextView)findViewById( R.id.textView2 );
                                    //Log.d( "settext2",textView2.toString() );
                                 //   TextView textView4=(TextView)findViewById( R.id.textView4 );
                                    // Log.d( "settext4",textView4.toString() );
                                 //   textView2.setText( "yash" );
                                   // textView2.setText( availability1 );
                                    Log.d( "avail","availa" );
                                  //  textView4.setText( price1 );

                                    return true;
                                }


                           });


                        }
                    }

                }

                mMap.addMarker(new MarkerOptions().position(trinity).title(name));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(trinity));
            /* final   AlertDialog.Builder builder= new AlertDialog.Builder( context );
                Log.d("after marker","after marker");
                name="yash";
                mMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener(){
                    public boolean onMarkerClick(Marker marker)
                    {
                     builder.show();

                        return false;
                    }


                });
builder.setView( R.layout.dialog );
                TextView textView2=(TextView)findViewById( R.id.textView2 );
                textView2.setText( "" );
                builder.setNeutralButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );*/
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

            protected void onPostExecute(Boolean result) {
                //showDialog("Downloaded " + result + " bytes");
                Log.d("onpostexecyte","onpostexecyte");
            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });


        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);

        Log.d("posts", posts.toString());
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        Log.d("sydney","sydney");
        Log.d("posts after sydney",posts.toString());


    }




    public void getPostList(Map<String,Object> dataSnapshot,String abc) {
        Map<String,String> maps=new HashMap<String, String>();
        Log.d("inside getpostlist", "hi");
        int index=0;
        post post=new post();
      Integer index1=0;
        for(Map.Entry<String,Object>entry:dataSnapshot.entrySet())

        {
            index1++;
          //  Log.d("entry", entry.getValue().toString());
            //Log.d("index", ( index1.toString()));
            //Log.d("entry", entry.getKey().toString());

            maps.put(entry.getKey().toString(),entry.getValue().toString());
            Log.d("loop over","loop");


        }

        Set keys=maps.keySet();
        Log.d("mapsget",maps.get("name"));
        post.setName(maps.get("name"));
        post.setAvailability(maps.get("availability"));
        post.setLatitude(maps.get("latitude"));
        post.setLongitude(maps.get("longitude"));
        post.setPrice(maps.get("price"));
        Log.d("post data", post.getAvailability());
        Log.d("post data", post.getLatitude());
        Log.d("post data",post.getLongitude());
        posts.put(abc,post);
        Log.d("after posts","hi");
        Integer size=posts.size();
        Log.d( "getkey" , size.toString());




    }



    //adding current location

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {

        Log.d("checkLocationPerm","LocationPermission");
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    protected synchronized void buildGoogleApiClient() {
        Log.d("insideGoogleCLient","GoogleCLient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        System.out.print("mGoogleApiClient" +mGoogleApiClient);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("insideOnConnected","onConnected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //  LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.d("inside fused location" , "onco");
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Log.d("ReqPermission","OnPermission");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        {
            Log.d("onLocationChanged","LocationChanged");
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
Log.d("LocChanged","LocationChanged");
            //Place current location marker
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");

            // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
            Log.d("mCurrLocationMarker" ,"mCurrLocationMarker");

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

            }

        }
    }



}
