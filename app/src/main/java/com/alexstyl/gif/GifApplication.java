package com.alexstyl.gif;

import android.app.Application;

import com.alexstyl.gif.debug.DebugScreenEnabler;

public class GifApplication extends Application {

    private DebugScreenEnabler debugScreenEnabler;

    @Override
    public void onCreate() {
        super.onCreate();

        toggleDebugScreen();
    }

    private void toggleDebugScreen() {
        debugScreenEnabler = DebugScreenEnabler.newInstance(this);

        boolean isDebug = isRunningInDebug();
        debugScreenEnabler.setEnabled(isDebug);
    }

    public boolean isRunningInDebug() {
        return BuildConfig.DEBUG;
    }
}
