package com.alexstyl.gif.overlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.gif.R;
import com.alexstyl.gif.util.CombinationTouchListener;

public class OverlayManager {

    public static OverlayManager newInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View overlayView = createOverlayView(context);
        return new OverlayManager(windowManager, overlayView);
    }

    private static View createOverlayView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = inflateOverlayView(layoutInflater);
        setupTouchListeners(view, context);
        return view;
    }

    private static void setupTouchListeners(View view, Context context) {
        CombinationTouchListener listener = new CombinationTouchListener();
        listener.addListener(DragVerticallyTouchListener.newInstance(context));
        listener.addListener(ClipToVerticalEdgesTouchListener.newInstance(context));

        view.setOnTouchListener(listener);
    }

    private static View inflateOverlayView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.layout_overlay, null, false);
    }

    private final WindowManager windowManager;
    private final View overlayView;

    public OverlayManager(WindowManager windowManager, View overlayView) {
        this.windowManager = windowManager;
        this.overlayView = overlayView;
    }

    public void startOverlay() {
        if (overlayIsAttached()) {
            return;
        }
        WindowManager.LayoutParams params = buildOverlayLayoutParams();
        windowManager.addView(overlayView, params);
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
        return Gravity.TOP | Gravity.LEFT;
    }

    private boolean overlayIsAttached() {
        return overlayView.getParent() != null;
    }

    public void destroy() {
        windowManager.removeView(overlayView);
    }
}
