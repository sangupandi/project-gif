package com.alexstyl.gif.overlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.R;
import com.alexstyl.gif.util.CombinationTouchListener;

public class OverlayDisplayer {

    private final WindowManager windowManager;
    private final View overlayView;

    public static OverlayDisplayer newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View overlayView = LayoutInflater.from(context).inflate(R.layout.layout_overlay, null, false);
        setupTouchListeners(overlayView, context);
        return new OverlayDisplayer(windowManager, overlayView);
    }

    private static void setupTouchListeners(View view, Context context) {
        CombinationTouchListener listener = new CombinationTouchListener();
        listener.addListener(DragVerticallyTouchListener.newInstance(context));
        listener.addListener(ClipToVerticalEdgesTouchListener.newInstance(context));

        view.setOnTouchListener(listener);
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
        return Gravity.TOP | Gravity.RIGHT;
    }

    public void destroy() {
        windowManager.removeView(overlayView);
    }
}
