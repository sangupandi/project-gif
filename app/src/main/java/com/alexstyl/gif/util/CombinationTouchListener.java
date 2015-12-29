package com.alexstyl.gif.util;

import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;

final public class CombinationTouchListener implements View.OnTouchListener {

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

    public void addListener(View.OnTouchListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(View.OnTouchListener listener) {
        this.listeners.remove(listener);
    }

}
