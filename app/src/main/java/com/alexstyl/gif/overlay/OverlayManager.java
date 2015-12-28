package com.alexstyl.gif.overlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.R;

public class OverlayManager {

    private final WindowManager windowManager;
    private final View overlayView;

    public static OverlayManager newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View overlayView = inflateOverlayView(layoutInflater);

        return new OverlayManager(windowManager, overlayView);
    }

    public OverlayManager(WindowManager windowManager, View overlayView) {
        this.windowManager = windowManager;
        this.overlayView = overlayView;
    }

    private static View inflateOverlayView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.layout_overlay, null, false);
    }

    public void startOverlay() {
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
        layoutParams.gravity = Gravity.CENTER;

        return layoutParams;
    }

    public void destroy() {
        windowManager.removeView(overlayView);
    }
}
