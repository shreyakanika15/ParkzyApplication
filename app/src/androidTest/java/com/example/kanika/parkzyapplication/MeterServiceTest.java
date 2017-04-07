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
 * Created by Kanika on 03-04-2017.
 */
public class MeterServiceTest {
    @Rule
    public ActivityTestRule<MeterService> mActivityTestRule = new ActivityTestRule<MeterService>( MeterService.class );

    private MeterService meterservice = null;
    //Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(UserSelection.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        meterservice = mActivityTestRule.getActivity();

    }



    @Test
    public void testMeter(){
        assertNotNull(meterservice.findViewById(R.id.trip));


    }

    @After
    public void tearDown() throws Exception {
        meterservice=null;
    }


}