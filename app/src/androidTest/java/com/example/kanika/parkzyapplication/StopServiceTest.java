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
public class StopServiceTest {
    @Rule
    public ActivityTestRule<StopService> mActivityTestRule = new ActivityTestRule<StopService>( StopService.class );

    private StopService stop = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(PayService.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        stop = mActivityTestRule.getActivity();


    }

    @Test
    public void testStopLaunch(){

        assertNotNull(stop.findViewById(R.id.textView));
    }

    @Test
    public void testStopClick(){
        onView(withId(R.id.stopButton)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,500000 );
        assertNotNull( userActivity );
        userActivity.finish();
        //assertNotNull(sign.findViewById( R.id.buttonRegister ));



        // assertNotNull( view );
    }

    @After
    public void tearDown() throws Exception {
        stop=null;
    }

}