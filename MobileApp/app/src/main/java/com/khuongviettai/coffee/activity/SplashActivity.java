package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.local.CheckFirstInstallApp;
import com.khuongviettai.coffee.local.DataStoreManager;
import com.khuongviettai.coffee.utils.GlobalFuntion;
import com.khuongviettai.coffee.utils.StringUtil;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String KEY_FIRST_INSTALL_APP = "KEY_FIRST_INSTALL_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        CheckFirstInstallApp checkFirstInstallApp = new CheckFirstInstallApp(this);
        if (checkFirstInstallApp.getBooleanValue(KEY_FIRST_INSTALL_APP)) {
            goToActivity();
        }
        else {
            startActivity(OnboardingActivity.class);
            checkFirstInstallApp.putBooleanValue(KEY_FIRST_INSTALL_APP, true);
            finish();
        }
    }

    private void startActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void goToActivity() {
        if (DataStoreManager.getUser() != null && !StringUtil.isEmpty(DataStoreManager.getUser().getEmail())) {
            GlobalFuntion.startActivity(this, MainActivity.class);
        } else {
            GlobalFuntion.startActivity(this, SignInActivity.class);
        }
        finish();
    }

}
