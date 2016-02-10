package com.alexstyl.gif.debug;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.alexstyl.gif.PermissionChecker;
import com.alexstyl.gif.R;
import com.alexstyl.gif.overlay.OverlayService;
import com.alexstyl.gif.overlay.OverlayServiceEnabler;
import com.novoda.notils.caster.Views;

public class DebugActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private SwitchCompat enableSwitch;
    private PermissionStatusLayout permissionStatusLayout;

    private OverlayServiceEnabler overlayServiceEnabler;
    private PermissionChecker permissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        overlayServiceEnabler = OverlayServiceEnabler.newInstance(this);
        permissionChecker = PermissionChecker.newInstance(this);

        enableSwitch = Views.findById(this, R.id.checkbox_enable);
        permissionStatusLayout = Views.findById(this, R.id.debug_permission_status_layout);

        setupEnableSwitch();
        setupPermissionStatus();
    }

    private void setupPermissionStatus() {
        if (needsToAskForPermission()) {
            permissionStatusLayout.setVisibility(View.VISIBLE);
            permissionStatusLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    permissionChecker.requestForOverlayPermission(DebugActivity.this);
                }
            });
        } else {
            permissionStatusLayout.setVisibility(View.GONE);
        }
    }

    private boolean needsToAskForPermission() {
        return Build.VERSION_CODES.M >= Build.VERSION.SDK_INT;
    }

    private void setupEnableSwitch() {
        enableSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            overlayServiceEnabler.enableService();
                        } else {
                            overlayServiceEnabler.disableService();
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOverlaySwitch();
        updatePermissionLayoutLabel();
    }

    private void updateOverlaySwitch() {
        boolean isServiceRunning = OverlayService.isRunning(this);
        enableSwitch.setChecked(isServiceRunning);
        enableSwitch.setEnabled(permissionChecker.hasOverlayPermission());
    }

    private void updatePermissionLayoutLabel() {
        if (permissionChecker.hasOverlayPermission()) {
            permissionStatusLayout.displayPermissionGranted();
        } else {
            permissionStatusLayout.displayPermissionRequired();
        }
    }
}
