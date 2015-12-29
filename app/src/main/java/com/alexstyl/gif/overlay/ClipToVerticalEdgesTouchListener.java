package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.util.Log;
import com.alexstyl.gif.util.Utils;

public class ClipToVerticalEdgesTouchListener implements View.OnTouchListener {

    private final OrientationChangedChecker orientationChangedChecker;
    private final WindowManager windowManager;
    private final Resources resources;
    private final Point screenBoundaries = new Point();
    private int topBoundary;
    private int bottomBoundary;

    public static ClipToVerticalEdgesTouchListener newInstance(Context context) {
        OrientationChangedChecker checker = OrientationChangedChecker.newInstance(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Resources resources = context.getResources();

        return new ClipToVerticalEdgesTouchListener(checker, windowManager, resources);
    }

    public ClipToVerticalEdgesTouchListener(OrientationChangedChecker orientationChangedChecker, WindowManager windowManager, Resources resources) {
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
                Log.d("Clipping Top");
            } else if (isClippingBottom(layoutParams, viewHeight)) {
                layoutParams.y = bottomBoundary - viewHeight;
                Log.d("Clipping Bottom");
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
        topBoundary = Utils.getStatusBarHeight(resources);
        bottomBoundary = screenBoundaries.y - Utils.getNavigationBarHeight(resources);
    }
}
