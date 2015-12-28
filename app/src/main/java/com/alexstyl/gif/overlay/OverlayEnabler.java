package com.alexstyl.gif.overlay;

import android.content.Context;

import com.alexstyl.gif.util.DeveloperError;

public class OverlayEnabler {
    private final Context context;

    private OverlayEnabler() {
        throw new DeveloperError("asd");
    }

    public OverlayEnabler(Context context) {
        this.context = context;
    }

    public static OverlayEnabler newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        return new OverlayEnabler(appContext);
    }

    public void disableService() {
        notYetImplemented();
    }

    private void notYetImplemented() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void enableService() {
        notYetImplemented();
    }
}
