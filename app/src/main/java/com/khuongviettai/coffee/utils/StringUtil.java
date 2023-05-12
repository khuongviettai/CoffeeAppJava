package com.khuongviettai.coffee.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty() || ("").equals(input.trim());
    }

}
