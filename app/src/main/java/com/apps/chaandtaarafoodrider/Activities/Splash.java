package com.apps.chaandtaarafoodrider.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



       /* mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    // Utils checking

                    if(!utils.isLoggedIn()){
                        Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                        startActivity(intent);
                        finish();
                    }

//                    //Go to next page i.e, start the next activity.
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);

//                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
//                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);  // Give a 3 seconds delay.*/

    }





    @Override
    protected void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        SharedPreference utils = new SharedPreference(this);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(utils.isLoggedIn()){
                    if (currentUser != null) {
                        startActivity(new Intent(Splash.this, EmailVerification.class));
                        finish();
                    }
                    else{
                        Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                        startActivity(mainIntent);
                        finish();

                    }
                }

                else {
                    Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();

                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
