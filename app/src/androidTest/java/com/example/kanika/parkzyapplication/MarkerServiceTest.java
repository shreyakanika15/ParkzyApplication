package com.example.kanika.parkzyapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Kanika on 02-04-2017.
 */
public class MarkerServiceTest {


        @Rule
        public ActivityTestRule<MarkerService> mActivityTestRule = new ActivityTestRule<MarkerService>(MarkerService.class);

        private MarkerService markerservice = null;
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MeterService.class.getName(), null, false);

        @Before
        public void setUp() throws Exception {

            markerservice = mActivityTestRule.getActivity();


        }

        @Test
        public void testMarkerLaunch() {

            assertNotNull(markerservice.findViewById( R.id.buttonMeter ));


        }

        @After
        public void tearDown() throws Exception {
            markerservice = null;
        }


}