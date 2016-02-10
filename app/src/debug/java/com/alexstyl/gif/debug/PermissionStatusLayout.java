package com.alexstyl.gif.debug;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexstyl.gif.R;
import com.novoda.notils.caster.Views;
import com.novoda.notils.exception.DeveloperError;

public class PermissionStatusLayout extends LinearLayout {

    private TextView headerView;
    private TextView subtitleView;

    public PermissionStatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.merge_permission_status_layout, this);
        headerView = Views.findById(this, R.id.debug_permission_status_header);
        subtitleView = Views.findById(this, R.id.debug_permission_status_subtitle);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void displayPermissionGranted() {
        headerView.setText(R.string.debug_permission_granted);
        subtitleView.setText(R.string.debug_permission_touch_to_revoke);
    }

    public void displayPermissionRequired() {
        headerView.setText(R.string.debug_permission_required);
        subtitleView.setText(R.string.debug_permission_touch_to_grant);
    }

    @Override
    public void setOrientation(int orientation) {
        throw new DeveloperError(PermissionStatusLayout.class.getSimpleName() + " does not support changing Orientation");
    }
}
