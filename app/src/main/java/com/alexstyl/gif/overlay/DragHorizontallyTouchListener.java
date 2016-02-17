package com.alexstyl.gif.overlay;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class DragHorizontallyTouchListener implements View.OnTouchListener {

    private final Scroller scroller;
    private final ViewPositionUpdater positionUpdater;

    private int xDelta;

    public static DragHorizontallyTouchListener newInstance(Context context) {
        Scroller scroller = new Scroller(context, sInterpolator);
        ViewPositionUpdater positionUpdater = ViewPositionUpdater.newInstance(context);

        return new DragHorizontallyTouchListener(scroller, positionUpdater);
    }

    public DragHorizontallyTouchListener(Scroller scroller, ViewPositionUpdater positionUpdater) {
        this.scroller = scroller;
        this.positionUpdater = positionUpdater;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();

        WindowManager.LayoutParams lParams = (WindowManager.LayoutParams) view.getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scroller.forceFinished(true);
                xDelta = x - lParams.x;
                break;
            case MotionEvent.ACTION_MOVE:
                int xPosition = x - xDelta;
                positionUpdater.moveViewHorizontally(view, xPosition);
                break;
        }

        return false;
    }

    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            // _o(t) = t * t * ((tension + 1) * t + tension)
            // o(t) = _o(t - 1) + 1
            t -= 1.0f;
            return t * t * t + 1.0f;
        }
    };
}
