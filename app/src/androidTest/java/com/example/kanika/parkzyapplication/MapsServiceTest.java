package com.example.kanika.parkzyapplication;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.test.AndroidTestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Kanika on 02-04-2017.
 */
public class MapsServiceTest extends AndroidTestCase{
    private LocationManager locationManager;

    public void testGPS() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider("Test1", false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled("Test1", true);

        // Set up your test

        Location location = new Location("Test1");
        Method locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
        if (locationJellyBeanFixMethod != null) {
            locationJellyBeanFixMethod.invoke(location);
        }
        location.setLatitude(53.337583);
        location.setLongitude(-6.329764);
        locationManager.setTestProviderLocation("Test1", location);

        // Check if your listener reacted the right way

        locationManager.removeTestProvider("Test1");
    }

}