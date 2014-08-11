##Wind Meter SDK for Android
####Lightweight Integration
The lightweight integration option allows developers to leverage the WeatherFlow Wind Meter app's user interface directly eliminating the need to build and maintain their own interface for the purpose of displaying real-time wind data.  This option passes data between the WeatherFlow Wind Meter app and the developer's app.

This option does NOT require embedding an SDK but rather leverages the WeatherFlow Wind Meter app.

####Setup

```java
 	static int WINDMETER_REQUEST_CODE = 2000;
 
 	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == WINDMETER_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				String jsonWindDataArray = intent.getStringExtra("windDataArray");
				Log.d("WindMeter_JSON_Data", jsonWindDataArray);
			}
		}
	}

	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
		case R.id.button1:
			PackageManager manager = this.getPackageManager();
			if (manager.getLaunchIntentForPackage("com.weatherflow.windmeter")!=null) {
				Intent intent = new Intent("android.intent.action.MAIN");
				intent.putExtra("fromExternal",true);
				intent.setComponent(ComponentName.unflattenFromString("com.weatherflow.windmeter/com.weatherflow.windmeter.ui.activities.MainActivity"));
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivityForResult(intent,WINDMETER_REQUEST_CODE);
			}else{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=com.weatherflow.windmeter"));
				startActivity(intent);
			}
			break;
		}
	}
```