package com.zhenqi.baselibrary.util;

import android.util.Log;

public class Lg {

    private static final String TAG = "MTJ";
    private static boolean isDebug = true;

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}
