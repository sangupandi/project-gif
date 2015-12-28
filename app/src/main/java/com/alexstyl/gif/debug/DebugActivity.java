package com.alexstyl.gif.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.alexstyl.gif.R;
import com.alexstyl.gif.overlay.OverlayEnabler;

public class DebugActivity extends AppCompatActivity {

    private SwitchCompat enableSwitch;
    private OverlayEnabler overlayEnabler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        overlayEnabler = OverlayEnabler.newInstance(this);

        enableSwitch = (SwitchCompat) findViewById(R.id.checkbox_enable);
        enableSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            overlayEnabler.disableService();
                        } else {
                            overlayEnabler.enableService();
                        }
                    }
                }
        );
    }
}
