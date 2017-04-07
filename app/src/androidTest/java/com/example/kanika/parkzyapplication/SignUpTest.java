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
 * Created by Kanika on 25-03-2017.
 */
public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUp> mActivityTestRule = new ActivityTestRule<SignUp>( SignUp.class );

    private SignUp sign = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(UserSelection.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        sign = mActivityTestRule.getActivity();


    }

    @Test
    public void testSignUpLaunch(){

        assertNotNull(sign.findViewById(R.id.textView));
    }

    @Test
    public void testSignupClick(){
        onView(withId(R.id.buttonRegister)).perform(click());
        Activity userActivity = getInstrumentation().waitForMonitorWithTimeout( monitor,500000 );
        assertNotNull( userActivity );
        userActivity.finish();
        assertNotNull(sign.findViewById( R.id.buttonRegister ));



       // assertNotNull( view );
    }

    @After
    public void tearDown() throws Exception {
        sign=null;
    }

}