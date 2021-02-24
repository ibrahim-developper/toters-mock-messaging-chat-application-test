package com.example.toters_test_mockmessaging.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {
    public static void sharedSave(Context ctx, String name, String value) {
        SharedPreferences s = ctx.getSharedPreferences("ibrahim", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString(name, value);
        edt.apply();
    }

    public static String sharedRead(Context ctx, String name, String defaultValue) {
        SharedPreferences s = ctx.getSharedPreferences("ibrahim", Context.MODE_PRIVATE);
        return s.getString(name, defaultValue);
    }
}
