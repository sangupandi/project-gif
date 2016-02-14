package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.Intent;

public class OverlayServiceEnabler {

    private final Context context;

    public static OverlayServiceEnabler newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        return new OverlayServiceEnabler(appContext);
    }

    private OverlayServiceEnabler(Context context) {
        this.context = context;
    }

    public void enableService() {
        Intent intent = new Intent(context, OverlayService.class);
        context.startService(intent);
    }

    public void disableService() {
        Intent intent = new Intent(context, OverlayService.class);
        context.stopService(intent);
    }
}
