package com.alexstyl.gif;

import android.app.Notification;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.NotificationCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.util.TypedValue;

public class Notifier {
    private final Context context;

    private Notifier() {
        context = null;
    }

    public Notifier(Context context) {
        this.context = context;
    }

    public static Notifier newInstance(Context context) {
        Context appContext = createThemeContext(context);
        return new Notifier(appContext);
    }

    private static Context createThemeContext(Context context) {
        return new ContextThemeWrapper(context, R.style.AppTheme);
    }

    public Notification createNotificationForOverlayService() {
        String appName = context.getString(R.string.app_name);
        CharSequence contentTitle = context.getString(R.string.overlayservice_notification_title, appName);
        CharSequence contentText = context.getString(R.string.overlayservice_notification_text);

        int notificationColor = getSelectedThemeAccentColor();
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

    private int getSelectedThemeAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

}
