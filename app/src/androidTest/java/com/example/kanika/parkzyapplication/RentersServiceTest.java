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
public class RentersServiceTest {

    @Rule
    public ActivityTestRule<RentersService> mActivityTestRule = new ActivityTestRule<RentersService>( RentersService.class );

    private RentersService renters = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ProfileService.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        renters = mActivityTestRule.getActivity();

    }

    @Test
    public void testRentersLaunch(){
        assertNotNull(renters.findViewById( R.id.textView ));

    }

    @Test
    public void testAddImage(){
        onView(withId(R.id.addImage)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,5000000 );
        assertNotNull( userActivity );
        userActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
        renters=null;
    }

}