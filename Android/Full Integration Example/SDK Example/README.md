##Integration Steps

Integrating the Wind Meter SDK into an application is fairly straightforward and only requires a minimal number of steps.

###Project Setup
* Put the "SensorSdkLib.jar" into your libs directory.
* Put the "gson.jar" into your libs directory.
* Put the "m16k60s.wav" into your assets directory.

###Usage

####Setup Sensor Object

```java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpeed = (TextView) findViewById(R.id.speed);

        mHeadphonesReceiver = new HeadphonesReceiver(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        WFSensor.getInstance(MainActivity.this).setOnValueChangedListener(MainActivity.this);
        
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
```

####Start Listening for Data
```java
WFSensor.getInstance(MainActivity.this).setOnValueChangedListener(MainActivity.this); //starts listening for data
```

####Stop Listening for Data
```java
WFSensor.getInstance(MainActivity.this).setOnValueChangedListener(null); //stops listening for data
```


An [example project] has also been provided with a working example.

[example project]:https://github.com/WeatherFlow/WindMeterSDK/tree/master/Android/Full%20Integration%20Example/SDK%20Example
