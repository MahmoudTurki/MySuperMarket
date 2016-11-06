package com.pentavalue.tomato.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pentavalue.tomato.R;

/**
 * @author Mahmoud Turki
 */
public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashTask();
    }

    private void splashTask() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over will start your app Switch Language activity
                startActivity(SwitchLanguageActivity.getActivityIntent(SplashActivity.this));
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
