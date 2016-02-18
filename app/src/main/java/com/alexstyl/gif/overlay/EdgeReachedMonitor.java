package com.alexstyl.gif.overlay;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class EdgeReachedMonitor implements View.OnTouchListener {

    private final OnEdgeReachedListener onEdgeReachedListener;
    private boolean hasMovedView;

    EdgeReachedMonitor(OnEdgeReachedListener onEdgeReachedListener) {
        this.onEdgeReachedListener = onEdgeReachedListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            hasMovedView = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (hasMovedView) {
                hasMovedView = false;
                if (hasReachedEdge(v)) {
                    onEdgeReachedListener.onViewReachedEdge(v);
                }
            }

        }
        return false;
    }

    private boolean hasReachedEdge(View v) {
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) v.getLayoutParams();
        return params.x == 0;
    }

}
