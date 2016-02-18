package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

class ViewPositionUpdater {

    private final OrientationChangedChecker orientationChangedChecker;
    private final ScreenBoundsChecker boundsChecker;
    private final WindowManager windowManager;

    static ViewPositionUpdater newInstance(Context context) {
        OrientationChangedChecker orientationChangedChecker = OrientationChangedChecker.newInstance(context);
        ScreenBoundsChecker boundsChecker = new ScreenBoundsChecker(ScreenBounds.getAvailableScreenBounds(context), context.getApplicationContext());
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        return new ViewPositionUpdater(orientationChangedChecker, boundsChecker, windowManager);
    }

    ViewPositionUpdater(OrientationChangedChecker orientationChangedChecker, ScreenBoundsChecker boundsChecker, WindowManager windowManager) {
        this.windowManager = windowManager;
        this.boundsChecker = boundsChecker;
        this.orientationChangedChecker = orientationChangedChecker;
    }

    void moveViewHorizontally(View view, int xPosition) {
        checkForOrientationChange();

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();

        if (boundsChecker.isPositionOutsideLeft(xPosition)) {
            layoutParams.x = boundsChecker.getLeftBound();
        } else if (boundsChecker.isPositionOutsideRight(xPosition + view.getWidth())) {
            layoutParams.x = boundsChecker.getRightBound() - view.getWidth();
        } else {
            layoutParams.x = xPosition;
        }

        windowManager.updateViewLayout(view, layoutParams);
        view.setLayoutParams(layoutParams);
    }

    void moveViewVertically(View view, int yPosition) {
        checkForOrientationChange();

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();

        if (boundsChecker.isPositionOutsideTop(yPosition)) {
            layoutParams.y = boundsChecker.getTopBound();
        } else if (boundsChecker.isPositionOutsideBottom(yPosition + view.getHeight())) {
            layoutParams.y = boundsChecker.getBottomBound() - view.getHeight();
        } else {
            layoutParams.y = yPosition;
        }

        windowManager.updateViewLayout(view, layoutParams);
        view.setLayoutParams(layoutParams);
    }

    private void checkForOrientationChange() {
        if (orientationChangedChecker.orientationChanged()) {
            boundsChecker.recalculateBoundaries();
        }
    }

}
