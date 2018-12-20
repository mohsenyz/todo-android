package com.mphj.todo.utils;

import android.content.Context;

public class Auth {

    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_EMAIL = "auth_email";

    public static void login(String email, String token, Context context) {
        Prefs.set(KEY_TOKEN, token, context);
        Prefs.set(KEY_EMAIL, email, context);
    }

    public static void logout(Context context) {
        Prefs.setNull(KEY_TOKEN, context);
        Prefs.setNull(KEY_EMAIL, context);
    }

    public static boolean isLoggedIn(Context context) {
        return Prefs.asString(KEY_EMAIL, context) != null;
    }

    public static String token(Context context) {
        return Prefs.asString(KEY_TOKEN, context);
    }
}
