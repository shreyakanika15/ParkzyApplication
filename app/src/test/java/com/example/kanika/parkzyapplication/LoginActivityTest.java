package com.example.kanika.parkzyapplication;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Kanika on 13-02-2017.
 */

public class LoginActivityTest {

    @RunWith(RobolectricTestRunner.class)
    public class MyActivityTest {

        @Test
        public void clickingButton_shouldChangeResultsViewText() throws Exception {
            MyActivity activity = Robolectric.setupActivity(MyActivity.class);

            Button button = (Button) activity.findViewById(R.id.button);
            TextView results = (TextView) activity.findViewById(R.id.results);

            button.performClick();
            assertThat(results.getText().toString()).isEqualTo("Robolectric Rocks!");
        }
    }
}
