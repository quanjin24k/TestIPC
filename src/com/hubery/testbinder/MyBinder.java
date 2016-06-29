package com.hubery.testbinder;

import android.content.Context;
import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MyBinder extends Binder {

	private Context context;
	
	public MyBinder(Context context) {
		this.context = context;
	}
	
	@Override
	protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
			throws RemoteException {
		String receviceStr = data.readString();
		
		Log.i("MyBinder-receive", "receiveMsg="+receviceStr+", code="+code);
		reply.writeString("[Received]::"+receviceStr+" [success]."+"code="+code);
		
		return true;
	}
	
}
