package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DragVerticallyTouchListener implements View.OnTouchListener {

    private int yDelta;
    private final WindowManager windowManager;

    private DragVerticallyTouchListener(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public static DragVerticallyTouchListener newInstance(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return new DragVerticallyTouchListener(manager);
    }

    @Override
    final public boolean onTouch(View view, MotionEvent event) {
        final int Y = (int) event.getRawY();

        WindowManager.LayoutParams lParams = (WindowManager.LayoutParams) view.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDelta = Y - lParams.y;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.y = Y - yDelta;
                view.setLayoutParams(lParams);
                windowManager.updateViewLayout(view, lParams);
                break;
        }

        return false;
    }

}
