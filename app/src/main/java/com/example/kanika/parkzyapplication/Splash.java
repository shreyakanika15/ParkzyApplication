package com.example.kanika.parkzyapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Kanika on 19-03-2017.
 */

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.splashh );

        final ImageView iv= (ImageView) findViewById( R.id.imageView );
        final Animation an = AnimationUtils.loadAnimation( getBaseContext(),R.anim.rotate );


        iv.startAnimation( an );
        an.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i;
                i = new Intent(getApplicationContext(),SignUp.class);
               startActivity( i );
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        } );
    }
}
