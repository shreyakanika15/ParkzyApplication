package com.example.kanika.parkzyapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MarkerService extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {



    private String PREFS_NAME1 = "pricedata";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private LatLng latLng;
    private Marker mCurrLocationMarker;
    private FirebaseUser user;
    private GoogleMap mMap;
    private String availability1;
    private String price1=null;
    private final  HashMap<String, post> posts = new HashMap<String, post>();
    private final  HashMap<String, post> nearbyPosts = new HashMap<String, post>();
    public String name=null;
    private Button bMeter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= firebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);
        bMeter=(Button) findViewById( R.id.buttonMeter);
        bMeter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences bookings = getSharedPreferences( PREFS_NAME1, 0 );
                SharedPreferences.Editor editor = bookings.edit();

                editor.putString( user.getUid(), price1);
                editor.commit();
                startActivity( new Intent( getApplicationContext(), MeterService.class ) );
            }
        } );


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
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        ArrayList<DatabaseReference> databaseReferences=new ArrayList<DatabaseReference>();
        databaseReferences.add(myRef);

        myRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                getPostList((Map<String,Object>) snapshot.getValue(), (String)snapshot.getKey());
                LatLng trinity = new LatLng(53.3438, -6.2546);
                post post=new post();
                String latitude=null;
                String longitude=null;
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

                        if (true) {
                            LatLng sydney = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                            final   AlertDialog.Builder builder= new AlertDialog.Builder(context);
                            name="yash";

                            mMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener(){
                                public boolean onMarkerClick(Marker marker)
                                {
                                    availability1=null;
                                    price1=null;
                                    for(Map.Entry<String,post>entry:posts.entrySet())
                                    {
                                        post  post1=entry.getValue();
                                        String  latitude1=post1.getLatitude();
                                        String longitude1=post1.getLongitude();
                                        LatLng latLng=new LatLng( Double.valueOf( latitude1 ),Double.valueOf( longitude1 ) );

                                        if(marker.getPosition().equals( latLng ))
                                        {
                                            availability1=post1.getAvailability();
                                            price1=post1.getPrice();
                                        }
                                    }

                                    builder.show();
                                    builder.setMessage("Availibility  " +  availability1 +"\n\n" + "Price  " +  price1);

                                    mMap.getUiSettings().setMapToolbarEnabled( true );

                                    builder.setNeutralButton("Select", new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();

                                        }
                                    });

                                    return true;
                                }


                            });

                        }
                    }

                }

                mMap.addMarker(new MarkerOptions().position(trinity).title(name));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(trinity));

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

            }

        });



    }
    public void getPostList(Map<String,Object> dataSnapshot,String abc) {
        Map<String,String> maps=new HashMap<String, String>();
        int index=0;
        post post=new post();
        Integer index1=0;
        for(Map.Entry<String,Object>entry:dataSnapshot.entrySet())

        {
            index1++;
            maps.put(entry.getKey().toString(),entry.getValue().toString());

        }

        Set keys=maps.keySet();
        post.setName(maps.get("name"));
        post.setAvailability(maps.get("availability"));
        post.setLatitude(maps.get("latitude"));
        post.setLongitude(maps.get("longitude"));
        post.setPrice(maps.get("price"));
        posts.put(abc,post);
        Integer size=posts.size();

    }

    //adding current location

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {

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


    protected synchronized void buildGoogleApiClient() {
        Log.d("insideGoogleCLient","GoogleCLient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

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
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            //Place current location marker
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");

            // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

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
