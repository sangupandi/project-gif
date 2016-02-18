package com.alexstyl.gif.overlay;

import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;

final class CompositeTouchListener implements View.OnTouchListener {

    private final HashSet<View.OnTouchListener> listeners = new HashSet<>(1);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (View.OnTouchListener listener : listeners) {
            boolean eventConsumed = listener.onTouch(v, event);
            if (eventConsumed) {
                return true;
            }
        }
        return false;
    }

    void addListeners(View.OnTouchListener listener) {
        this.listeners.add(listener);
    }

    public void addListeners(View.OnTouchListener... listeners) {
        for (View.OnTouchListener listener : listeners) {
            this.listeners.add(listener);
        }
    }

    void removeListener(View.OnTouchListener listener) {
        this.listeners.remove(listener);
    }
}
