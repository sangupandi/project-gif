package com.alexstyl.gif.overlay;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alexstyl.gif.Notifier;

public class OverlayService extends Service {

    private OverlayManager overlayManager;

    private static final int NOTIFICATION_ID = 1;

    public static boolean IS_RUNNING = false;

    @Override
    public void onCreate() {
        super.onCreate();
        overlayManager = OverlayManager.newInstance(this);
        moveToForeground();
    }

    private void moveToForeground() {
        Notifier notifier = Notifier.newInstance(this);
        Notification notification = notifier.createNotificationForOverlayService();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        overlayManager.startOverlay();
        IS_RUNNING = true;
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        overlayManager.destroy();
        IS_RUNNING = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
