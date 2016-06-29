package com.hubery.testbinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

	private IBinder mBinder = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mBinder = new MyBinder(getApplicationContext());
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

}
