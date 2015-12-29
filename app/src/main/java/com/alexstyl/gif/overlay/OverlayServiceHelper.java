package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.Intent;

public class OverlayServiceHelper {

    private final Context context;

    private OverlayServiceHelper() {
        context = null;
    }

    private OverlayServiceHelper(Context context) {
        this.context = context;
    }

    public static OverlayServiceHelper newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        return new OverlayServiceHelper(appContext);
    }

    public void disableService() {
        Intent intent = new Intent(context, OverlayService.class);
        context.stopService(intent);
    }

    public void enableService() {
        Intent intent = new Intent(context, OverlayService.class);
        context.startService(intent);
    }
}
