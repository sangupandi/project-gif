package com.alexstyl.gif.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.alexstyl.gif.R;
import com.alexstyl.gif.overlay.OverlayService;
import com.alexstyl.gif.overlay.OverlayServiceEnabler;

public class DebugActivity extends AppCompatActivity {

    private OverlayServiceEnabler overlayServiceEnabler;
    private SwitchCompat enableSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        overlayServiceEnabler = OverlayServiceEnabler.newInstance(this);
        enableSwitch = (SwitchCompat) findViewById(R.id.checkbox_enable);
        setupEnableSwitch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOverlaySwitch();
    }

    private void updateOverlaySwitch() {
        boolean isServiceRunning = OverlayService.IS_RUNNING;
        enableSwitch.setChecked(isServiceRunning);
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
}
