package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

class ClipToVerticalEdgesTouchListener implements View.OnTouchListener {

    private final Point screenBoundaries = new Point();

    private final OrientationChangedChecker orientationChangedChecker;
    private final WindowManager windowManager;
    private final Resources resources;

    private int topBoundary;
    private int bottomBoundary;

    static ClipToVerticalEdgesTouchListener newInstance(Context context) {
        OrientationChangedChecker checker = OrientationChangedChecker.newInstance(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Resources resources = context.getResources();

        return new ClipToVerticalEdgesTouchListener(checker, windowManager, resources);
    }

    ClipToVerticalEdgesTouchListener(OrientationChangedChecker orientationChangedChecker, WindowManager windowManager, Resources resources) {
        this.orientationChangedChecker = orientationChangedChecker;
        this.windowManager = windowManager;
        this.resources = resources;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (orientationChanged()) {
            calculateEdges();
        }

        if (isMovingEvent(event)) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
            int viewHeight = view.getMeasuredHeight() / 2;

            if (isClippingTop(layoutParams, viewHeight)) {
                layoutParams.y = 0;
            } else if (isClippingBottom(layoutParams, viewHeight)) {
                layoutParams.y = bottomBoundary - viewHeight;
            }

            view.setLayoutParams(layoutParams);

            windowManager.updateViewLayout(view, layoutParams);
        }

        return false;
    }

    private boolean isClippingBottom(WindowManager.LayoutParams layoutParams, int viewHeight) {
        return layoutParams.y + viewHeight > bottomBoundary;
    }

    private boolean isClippingTop(WindowManager.LayoutParams layoutParams, int viewHeight) {
        return layoutParams.y + viewHeight < topBoundary;
    }

    private boolean isMovingEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_MOVE;
    }

    private boolean orientationChanged() {
        return orientationChangedChecker.orientationChanged();
    }

    private void calculateEdges() {
        windowManager.getDefaultDisplay().getSize(screenBoundaries);
        topBoundary = getStatusBarHeight();
        bottomBoundary = screenBoundaries.y - getNavigationBarHeight();
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
