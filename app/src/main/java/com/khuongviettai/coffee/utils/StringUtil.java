package com.khuongviettai.coffee.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty() || ("").equals(input.trim());
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
