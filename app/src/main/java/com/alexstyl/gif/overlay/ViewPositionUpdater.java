package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

class ViewPositionUpdater {

    private final WindowManager windowManager;
    private final ScreenBoundsChecker boundsChecker;
    private final OrientationChangedChecker orientationChangedChecker;

    public static ViewPositionUpdater newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ScreenBoundsChecker boundsChecker = ScreenBoundsChecker.newInstance(context);
        OrientationChangedChecker orientationChangedChecker = OrientationChangedChecker.newInstance(context);

        return new ViewPositionUpdater(windowManager, boundsChecker, orientationChangedChecker);
    }

    ViewPositionUpdater(WindowManager windowManager, ScreenBoundsChecker boundsChecker, OrientationChangedChecker orientationChangedChecker) {
        this.windowManager = windowManager;
        this.boundsChecker = boundsChecker;
        this.orientationChangedChecker = orientationChangedChecker;
    }

    void moveViewToYPosition(View view, int yPosition) {
        if (orientationChangedChecker.orientationChanged()) {
            boundsChecker.calculateEdges();
        }

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.y = yPosition;

        if (boundsChecker.isPositionOutsideTop(yPosition)) {
            layoutParams.y = boundsChecker.getTopBound();
        } else if (boundsChecker.isPositionOutsideBottom(layoutParams.y + view.getHeight())) {
            layoutParams.y = boundsChecker.getBottomBound() + view.getHeight();
        }

        windowManager.updateViewLayout(view, layoutParams);
        view.setLayoutParams(layoutParams);
    }
}
