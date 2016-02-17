package com.alexstyl.gif.overlay;

import android.content.Context;
import android.widget.FrameLayout;

import com.alexstyl.gif.R;

public class OverlayView extends FrameLayout {

    public OverlayView(Context context) {
        super(context);
        addLayout();
    }

    private void addLayout() {
        inflate(getContext(), R.layout.merge_overlay, this);
    }
}
