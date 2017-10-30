package com.mjurinic.argus.argus.helpers;


import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPrefsHelper {

    public static String fetchString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static void storeString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }
}
