package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

class DragHorizontallyTouchListener implements View.OnTouchListener {

    private final ScreenBoundsChecker boundsChecker;
    private final ViewPositionUpdater positionUpdater;

    private int xDelta;

    public static DragHorizontallyTouchListener newInstance(Context context) {
        ViewPositionUpdater positionUpdater = ViewPositionUpdater.newInstance(context);
        ScreenBoundsChecker boundsChecker = ScreenBoundsChecker.createCheckerForExistingBounds(context);

        return new DragHorizontallyTouchListener(boundsChecker, positionUpdater);
    }

    public DragHorizontallyTouchListener(ScreenBoundsChecker boundsChecker, ViewPositionUpdater positionUpdater) {
        this.boundsChecker = boundsChecker;
        this.positionUpdater = positionUpdater;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();

        WindowManager.LayoutParams lParams = (WindowManager.LayoutParams) view.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.x;
                break;
            case MotionEvent.ACTION_MOVE:
                int xPosition = x - xDelta;
                positionUpdater.moveViewHorizontally(view, xPosition);
                return isNotClippedToEdge(view, lParams);
        }

        return false;
    }

    private boolean isNotClippedToEdge(View view, WindowManager.LayoutParams lParams) {
        return lParams.x + view.getWidth() < boundsChecker.getRightBound();
    }

}
