package com.alexstyl.gif.overlay;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alexstyl.gif.Notifier;
import com.novoda.notils.meta.AndroidUtils;

public class OverlayService extends Service {

    private OverlayDisplayer overlayDisplayer;

    private static final int NOTIFICATION_ID = 1;

    public static boolean isRunning(Context context) {
        return AndroidUtils.isServiceRunning(OverlayService.class, context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        overlayDisplayer = OverlayDisplayer.newInstance(this, onEdgeReachedListener);
        moveToForeground();
    }

    private static OnEdgeReachedListener onEdgeReachedListener = new OnEdgeReachedListener() {
        @Override
        public void onViewReachedEdge(View view) {
            Log.d("lolz", "View reached end. Start new activity");
        }
    };

    private void moveToForeground() {
        Notifier notifier = Notifier.newInstance(this);
        Notification notification = notifier.createNotificationForOverlayService();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        overlayDisplayer.showOverlay();
        overlayDisplayer.moveToInitialPosition();

        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        overlayDisplayer.destroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
