package com.alexstyl.gif.util;

import com.alexstyl.gif.BuildConfig;

final public class Log {

    private static final boolean DISPLAY_LOGS = BuildConfig.DEBUG;
    private static final String DEFAULT_TAG = BuildConfig.APPLICATION_ID;

    public static void d(String message) {
        if (DISPLAY_LOGS) {
            android.util.Log.d(DEFAULT_TAG, message);
        }
    }
}
