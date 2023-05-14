package com.khuongviettai.coffee.local;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckFirstInstallApp {
    private static final String CHECK_FIRST_INSTALL_APP = "CHECK_FIRST_INSTALL_APP";
    private Context context;

    public CheckFirstInstallApp(Context context) {
        this.context = context;
    }
    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHECK_FIRST_INSTALL_APP, 0);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHECK_FIRST_INSTALL_APP, 0);
        return sharedPreferences.getBoolean(key, false);
    }
}