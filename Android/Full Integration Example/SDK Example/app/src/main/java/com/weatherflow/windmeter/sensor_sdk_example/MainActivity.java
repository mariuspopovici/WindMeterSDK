package com.weatherflow.windmeter.sensor_sdk_example;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherflow.windmeter.sensor_sdk.entities.HeadsetState;
import com.weatherflow.windmeter.sensor_sdk.sdk.AnemometerObservation;
import com.weatherflow.windmeter.sensor_sdk.sdk.HeadphonesReceiver;
import com.weatherflow.windmeter.sensor_sdk.sdk.IHeadphonesStateListener;
import com.weatherflow.windmeter.sensor_sdk.sdk.WFConfig;
import com.weatherflow.windmeter.sensor_sdk.sdk.WFSensor;


public class MainActivity extends ActionBarActivity implements IHeadphonesStateListener, WFSensor.OnValueChangedListener {
    private final static String HEADSET_ACTION = "android.intent.action.HEADSET_PLUG";
    private BroadcastReceiver mHeadphonesReceiver;
    private TextView mSpeed;
    private Button mStart, mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeed = (TextView) findViewById(R.id.speed);

        mHeadphonesReceiver = new HeadphonesReceiver(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WFSensor.getInstance(MainActivity.this).setOnValueChangedListener(MainActivity.this);
            }
        });

        mStop = (Button) findViewById(R.id.stop);
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WFSensor.getInstance(MainActivity.this).setOnValueChangedListener(null);
                mSpeed.setText(R.string.hello_world);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(mHeadphonesReceiver, new IntentFilter(HEADSET_ACTION));
        WFConfig.getAnoConfig(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WFSensor.getInstance(this).onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        WFSensor.getInstance(this).onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        HeadsetState state = new HeadsetState();
        state.setPluggedIn(false);
        onHeadphonesStateChanged(state);
        unregisterReceiver(mHeadphonesReceiver);
    }

    @Override
    public void onHeadphonesStateChanged(HeadsetState headsetState) {
        WFSensor.getInstance(this).onHeadphonesStateChanged(headsetState);
    }

    @Override
    public void onValueChanged(final AnemometerObservation anemometerObservation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSpeed.setText("" + anemometerObservation.getWindSpeed());
            }
        });
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

}
