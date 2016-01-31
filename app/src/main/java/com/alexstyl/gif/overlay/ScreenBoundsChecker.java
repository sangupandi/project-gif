package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;

public class ScreenBoundsChecker {

    private final WindowManager windowManager;
    private final Resources resources;

    private int topBoundary;
    private int bottomBoundary;

    ScreenBoundsChecker(WindowManager windowManager, Resources resources) {
        this.windowManager = windowManager;
        this.resources = resources;
    }

    public static ScreenBoundsChecker newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Resources resources = context.getResources();
        return new ScreenBoundsChecker(windowManager, resources);
    }

    public boolean isClippingTop(View view) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        return layoutParams.y < topBoundary;
    }

    public boolean isClippingBottom(View view) {
        int touchCenterY = view.getMeasuredHeight() / 2;

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        return layoutParams.y + touchCenterY > bottomBoundary;
    }

    void calculateEdges() {
        Point screenBoundaries = new Point();
        windowManager.getDefaultDisplay().getSize(screenBoundaries);

        topBoundary = getStatusBarHeight();
        bottomBoundary = screenBoundaries.y - getNavigationBarHeight();
    }

    public int getTop() {
        return topBoundary;
    }

    public int getBottom() {
        return bottomBoundary;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getNavigationBarHeight() {
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
