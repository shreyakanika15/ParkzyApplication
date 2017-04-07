package com.example.kanika.parkzyapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;

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
 * Created by Kanika on 01-04-2017.
 */
public class LoginServiceTest {

    @Rule
    public ActivityTestRule<LoginService> mActivityTestRule = new ActivityTestRule<LoginService>( LoginService.class );

    private LoginService login = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(UserSelection.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        login = mActivityTestRule.getActivity();

    }

    @Test
    public void testLoginLaunch(){
        assertNotNull(login.findViewById( R.id.textView ));

    }

    @Test
    public void testLoginClick(){
        onView(withId(R.id.buttonSignIn)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,5000000 );
        assertNotNull( userActivity );
        userActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
        login=null;
    }


}