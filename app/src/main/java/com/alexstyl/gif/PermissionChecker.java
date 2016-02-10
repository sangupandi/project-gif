package com.alexstyl.gif;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.novoda.notils.exception.DeveloperError;

public class PermissionChecker {
    private final Context context;

    public static PermissionChecker newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        return new PermissionChecker(appContext);
    }

    PermissionChecker(Context context) {
        this.context = context;
    }

    private boolean needsToAskForPermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasOverlayPermission() {
        if (needsToAskForPermissions()) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestForOverlayPermission(Context context) {
        if (needsToAskForPermissions()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
            context.startActivity(intent);
        } else {
            throw new DeveloperError("No need to ask for permissions prior to Android 6.0 (M)");
        }

    }
}
