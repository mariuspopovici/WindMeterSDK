package com.weatherflow.lightweightintegrationexample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	static int WINDMETER_REQUEST_CODE = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

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

}
