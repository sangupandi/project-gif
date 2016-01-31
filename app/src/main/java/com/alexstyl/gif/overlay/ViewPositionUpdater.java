package com.alexstyl.gif.overlay;

import android.view.View;
import android.view.WindowManager;

class ViewPositionUpdater {
    private final WindowManager windowManager;

    ViewPositionUpdater(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    void moveViewToPosition(View view, int position) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        layoutParams.y = position;
        view.setLayoutParams(layoutParams);
        windowManager.updateViewLayout(view, layoutParams);
    }
}
