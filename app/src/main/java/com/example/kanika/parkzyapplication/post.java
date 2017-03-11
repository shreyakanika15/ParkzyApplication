package com.example.kanika.parkzyapplication;

import android.media.Image;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kanika on 03-03-2017.
 */

public class post {

    private String name;
    private String latitude;
    private String longitude;
    private String availability;
    private String price;

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        Log.d("inside last get","hello");



    }

}

