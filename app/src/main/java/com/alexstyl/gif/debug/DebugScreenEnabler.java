package com.alexstyl.gif.debug;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

public class DebugScreenEnabler {

    private final ComponentName componentName;
    private final PackageManager packageManager;

    public DebugScreenEnabler(Context context, PackageManager packageManager) {
        this.componentName = new ComponentName(context, DebugActivity.class);
        this.packageManager = packageManager;
    }

    public static DebugScreenEnabler newInstance(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return new DebugScreenEnabler(context, packageManager);
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            enableDebugScreen();
        } else {
            disableDebugScreen();
        }
    }

    private void enableDebugScreen() {
        packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    private void disableDebugScreen() {
        packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
    }
}
