package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.local.CheckFirstInstallApp;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String KEY_FIRST_INSTALL_APP = "KEY_FIRST_INSTALL_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CheckFirstInstallApp checkFirstInstallApp = new CheckFirstInstallApp(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkFirstInstallApp.getBooleanValue(KEY_FIRST_INSTALL_APP)) {
                    startActivity(SignInActivity.class);
                }
                else {
                    startActivity(OnboardingActivity.class);
                    checkFirstInstallApp.putBooleanValue(KEY_FIRST_INSTALL_APP, true);
                }
            }
        }, 2000);
    }
    private void startActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }

}