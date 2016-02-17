package com.alexstyl.gif.overlay;

import android.content.Context;

public class ScreenBoundsChecker {

    private final Context context;
    private ScreenBounds screenBounds;

    public ScreenBoundsChecker(ScreenBounds screenBounds, Context appContext) {
        this.screenBounds = screenBounds;
        this.context = appContext;
    }

    void recalculateBoundaries() {
        this.screenBounds = ScreenBounds.getAvailableScreenBounds(context);
    }

    public boolean isPositionOutsideLeft(int xPosition) {
        return xPosition < screenBounds.left;
    }

    boolean isPositionOutsideTop(int yPosition) {
        return yPosition < screenBounds.top;
    }

    public boolean isPositionOutsideRight(int xPosition) {
        return xPosition > screenBounds.right;
    }

    boolean isPositionOutsideBottom(int yPosition) {
        return yPosition > screenBounds.bottom;
    }

    int getTopBound() {
        return screenBounds.top;
    }

    int getBottomBound() {
        return screenBounds.bottom;
    }

    public int getLeftBound() {
        return screenBounds.left;
    }

    public int getRightBound() {
        return screenBounds.right;
    }
}
