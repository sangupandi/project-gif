package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Configuration;

class OrientationChangedChecker {

    private static final int UNDEFINED = -1;

    private final Configuration configuration;
    private int previousOrientation = UNDEFINED;

    OrientationChangedChecker(Configuration configuration) {
        this.configuration = configuration;
    }

    static OrientationChangedChecker newInstance(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return new OrientationChangedChecker(configuration);
    }

    boolean orientationChanged() {
        if (previousOrientation != configuration.orientation) {
            previousOrientation = configuration.orientation;
            return true;
        }
        return false;
    }
}
