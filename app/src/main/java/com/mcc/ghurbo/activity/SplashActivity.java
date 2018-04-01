package com.mcc.ghurbo.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashIcon;

    private static final int SPLASH_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        splashIcon = (ImageView) findViewById(R.id.splash_icon);
    }

    private void initFunctionality() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            splashIcon.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityUtils.getInstance().invokeActivity(SplashActivity.this, MainActivity.class, true);
                }
            }, SPLASH_DURATION);
        } else {
            Utils.noInternetWarning(splashIcon, getApplicationContext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }
}
