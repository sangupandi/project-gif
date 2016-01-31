package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

class ClipToVerticalEdgesTouchListener implements View.OnTouchListener {

    private final OrientationChangedChecker checker;
    private final ScreenBoundsChecker screenBoundsChecker;

    private final ViewPositionUpdater positionUpdater;

    static ClipToVerticalEdgesTouchListener newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        OrientationChangedChecker checker = OrientationChangedChecker.newInstance(context);
        ScreenBoundsChecker screenBoundsChecker = ScreenBoundsChecker.newInstance(context);
        ViewPositionUpdater positionUpdater = new ViewPositionUpdater(windowManager);

        return new ClipToVerticalEdgesTouchListener(checker, screenBoundsChecker, positionUpdater);
    }

    ClipToVerticalEdgesTouchListener(OrientationChangedChecker checker, ScreenBoundsChecker screenBoundsChecker, ViewPositionUpdater positionUpdater) {
        this.checker = checker;
        this.screenBoundsChecker = screenBoundsChecker;
        this.positionUpdater = positionUpdater;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (isMovingEvent(event)) {
            if (orientationChanged()) {
                recalculateScreenEdges();
            }

            if (screenBoundsChecker.isClippingTop(view)) {
                clipViewToTop(view);
            } else if (screenBoundsChecker.isClippingBottom(view)) {
                clipViewToBottom(view);
            }
        }

        return false;
    }

    private void clipViewToTop(View view) {
        int topBound = screenBoundsChecker.getTop() + view.getHeight() / 2;
        positionUpdater.moveViewToPosition(view, topBound);
    }

    private void clipViewToBottom(View view) {
        int bottomBound = screenBoundsChecker.getBottom();
        positionUpdater.moveViewToPosition(view, bottomBound);
    }

    private boolean orientationChanged() {
        return checker.orientationChanged();
    }

    private void recalculateScreenEdges() {
        screenBoundsChecker.calculateEdges();
    }

    private boolean isMovingEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_MOVE;
    }
}
