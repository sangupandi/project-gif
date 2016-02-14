package com.alexstyl.gif;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class Notifier {
    private final Context context;

    public static Notifier newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        return new Notifier(appContext);
    }

    Notifier(Context context) {
        this.context = context;
    }

    @SuppressLint("StringFormatInvalid")
    // the contentTitle is linking to a string with no arguments and Lint is complaining.
    // In fact, that string links to a strings that actually links to a string with arguments
    public Notification createNotificationForOverlayService() {
        String appName = context.getString(R.string.app_name);
        CharSequence contentTitle = context.getString(R.string.overlayservice_notification_title, appName);
        CharSequence contentText = context.getString(R.string.overlayservice_notification_text);

        int notificationColor = context.getResources().getColor(R.color.colorAccent);

        return new NotificationCompat.Builder(context)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_stat_overlay_service)
                .setColor(notificationColor)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();
    }
}
