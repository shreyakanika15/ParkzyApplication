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
public class UserSelectionTest {
    @Rule
    public ActivityTestRule<UserSelection> mActivityTestRule = new ActivityTestRule<UserSelection>( UserSelection.class );

    private UserSelection userservice = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MapsService.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        userservice = mActivityTestRule.getActivity();


    }

    @Test
    public void testUserScreen() {
        assertNotNull(userservice.findViewById(R.id.textView));


    }

    @Test
    public void testDrivers(){
        onView(withId(R.id.buttonForDrivers)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,500000 );
        assertNotNull( userActivity );
        userActivity.finish();
        assertNotNull(userservice.findViewById( R.id.buttonForDrivers ));


    }

    @Test
    public void testRenters() {
        onView(withId(R.id.buttonForRenters)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,500000 );
        assertNotNull( userActivity );
        userActivity.finish();
        assertNotNull(userservice.findViewById( R.id.buttonForRenters ));


    }


    @After
    public void tearDown() throws Exception {
        userservice=null;
    }


}

