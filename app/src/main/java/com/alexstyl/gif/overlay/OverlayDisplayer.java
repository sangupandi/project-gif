package com.alexstyl.gif.overlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.R;

public class OverlayDisplayer {

    private final WindowManager windowManager;
    private final View overlayView;

    public static OverlayDisplayer newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        OverlayView overlayView = new OverlayView(context);
        overlayView.setOnTouchListener(DragHorizontallyTouchListener.newInstance(context));
        return new OverlayDisplayer(windowManager, overlayView);
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
}
