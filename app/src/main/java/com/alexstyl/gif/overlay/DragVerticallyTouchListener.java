package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class DragVerticallyTouchListener implements View.OnTouchListener {

    private final WindowManager windowManager;

    private int yDelta;

    public static DragVerticallyTouchListener newInstance(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return new DragVerticallyTouchListener(manager);
    }

    private DragVerticallyTouchListener(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    @Override
    final public boolean onTouch(View view, MotionEvent event) {
        int y = (int) event.getRawY();

        WindowManager.LayoutParams lParams = (WindowManager.LayoutParams) view.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDelta = y - lParams.y;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.y = y - yDelta;
                view.setLayoutParams(lParams);
                windowManager.updateViewLayout(view, lParams);
                break;
        }

        return false;
    }

}
