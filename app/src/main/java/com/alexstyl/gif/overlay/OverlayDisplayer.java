package com.alexstyl.gif.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.R;

public class OverlayDisplayer {

    private final WindowManager windowManager;
    private final View overlayView;

    public static OverlayDisplayer newInstance(Context context, OnEdgeReachedListener onEdgeReachedListener) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        OverlayView overlayView = createOverlayView(context, onEdgeReachedListener);

        return new OverlayDisplayer(windowManager, overlayView);
    }

    private static OverlayView createOverlayView(Context context, OnEdgeReachedListener onEdgeReachedListener) {
        OverlayView overlayView = new OverlayView(context);

        CompositeTouchListener touchListeners = new CompositeTouchListener();
        touchListeners.addListeners(
                DragVerticallyTouchListener.newInstance(context),
                DragHorizontallyTouchListener.newInstance(context),
                new EdgeReachedMonitor(onEdgeReachedListener)
        );
        overlayView.setOnTouchListener(touchListeners);

        return overlayView;
    }

    OverlayDisplayer(WindowManager windowManager, View overlayView) {
        this.windowManager = windowManager;
        this.overlayView = overlayView;
    }

    public void showOverlay() {
        if (overlayIsAttached()) {
            return;
        }

        WindowManager.LayoutParams params = buildOverlayLayoutParams();
        windowManager.addView(overlayView, params);
    }

    private boolean overlayIsAttached() {
        return overlayView.getParent() != null;
    }

    private WindowManager.LayoutParams buildOverlayLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.windowAnimations = R.style.Overlay;
        layoutParams.gravity = getOverlayGravity();

        return layoutParams;
    }

    private int getOverlayGravity() {
        // we are setting Top + Left in order for the coordinates that we give into our view
        // have 0,0 the toppest left corder of the screen. This should make the math easier
        return Gravity.TOP | Gravity.LEFT;
    }

    public void destroy() {
        windowManager.removeView(overlayView);
    }

    public void moveToInitialPosition() {
        Context context = overlayView.getContext();
        ViewPositionUpdater positionUpdater = ViewPositionUpdater.newInstance(context);
        ScreenBoundsChecker checker = new ScreenBoundsChecker(ScreenBounds.getAvailableScreenBounds(context), context);

        Resources resources = overlayView.getContext().getResources();
        int actionbarHeight = resources.getDimensionPixelSize(android.support.v7.appcompat.R.dimen.abc_action_bar_default_height_material);
        positionUpdater.moveViewVertically(overlayView, actionbarHeight);
        int overlayWidth = resources.getDimensionPixelOffset(R.dimen.overlay_default_width);
        positionUpdater.moveViewHorizontally(overlayView, checker.getRightBound() - overlayWidth);

    }

}
