package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.WindowManager;

public class ScreenBounds {

    final int top;
    final int bottom;

    public static ScreenBounds getAvailableScreenBounds(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        windowManager.getDefaultDisplay().getSize(screenSize);

        int statusBarHeight = getStatusBarHeight(context.getResources());
        int availableHeight = screenSize.y - statusBarHeight;

        return new ScreenBounds(0, availableHeight);
    }


    public ScreenBounds(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    private static int getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
