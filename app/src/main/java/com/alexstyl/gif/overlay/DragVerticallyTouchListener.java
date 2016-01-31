package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

class DragVerticallyTouchListener implements View.OnTouchListener {

    private final ViewPositionUpdater positionUpdater;

    private int yDelta;

    static DragVerticallyTouchListener newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ViewPositionUpdater positionUpdater = new ViewPositionUpdater(windowManager);

        return new DragVerticallyTouchListener(positionUpdater);
    }

    DragVerticallyTouchListener(ViewPositionUpdater positionUpdater) {
        this.positionUpdater = positionUpdater;
    }

    @Override
    final public boolean onTouch(View view, MotionEvent event) {
        int y = (int) event.getRawY();

        WindowManager.LayoutParams lParams = (WindowManager.LayoutParams) view.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDelta = y - lParams.y;
                break;

            case MotionEvent.ACTION_MOVE:
                lParams.y = y - yDelta;
                positionUpdater.moveViewToPosition(view, y - yDelta);
                break;
        }

        return false;
    }

}
