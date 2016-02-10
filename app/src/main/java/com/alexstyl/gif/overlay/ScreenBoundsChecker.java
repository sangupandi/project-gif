package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
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

    void calculateEdges() {
        Point screenBoundaries = new Point();
        windowManager.getDefaultDisplay().getSize(screenBoundaries);

        topBoundary = 0;//getStatusBarHeight();
        bottomBoundary = screenBoundaries.y; // - getNavigationBarHeight();
    }

    public boolean isPositionOutsideTop(int yPosition) {
        return yPosition < topBoundary;
    }

    public boolean isPositionOutsideBottom(int yPosition) {
        return yPosition > bottomBoundary;
    }

    public int getTopBound() {
        return topBoundary;
    }

    public int getBottomBound() {
        return bottomBoundary;
    }

//    private int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = resources.getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
//
//    private int getNavigationBarHeight() {
//        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            return resources.getDimensionPixelSize(resourceId);
//        }
//        return 0;
//    }
}
