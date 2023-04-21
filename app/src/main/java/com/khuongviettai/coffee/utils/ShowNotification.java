package com.khuongviettai.coffee.utils;

import android.content.Context;
import android.widget.Toast;

public class ShowNotification {
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
