package com.hubery.testbinder;

import com.hubery.testUI.GameLoopView;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

//	private Button button1;//start send
//	private Button button2;//stop send
//	private Button button3;//finish
	private TextView receiveMsg;//receive
	private LinearLayout gameLoopView1;//
	
	private IBinder iBinder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        
        startService(new Intent("com.hubery.testbinder.REMOTE_SERVICE"));
        bindService(new Intent("com.hubery.testbinder.REMOTE_SERVICE"), 
        		mConnection, Context.BIND_AUTO_CREATE);
    }
    
    private void initView() {
    	findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        receiveMsg = (TextView) findViewById(R.id.textView2);
        
        gameLoopView1 = (LinearLayout) findViewById(R.id.gameLoopView1);
        
        gameLoopView1.addView(new GameLoopView(this));
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder ibinder) {
			iBinder = ibinder;//由jni层搭桥 连接到其他进程的binder
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Parcel pc = Parcel.obtain();
			Parcel pc_reply = Parcel.obtain();//接收返回消息
			pc.writeString("startSendMsg");
			
			try {
				iBinder.transact(1, pc, pc_reply, 0);
				String pc_r = pc_reply.readString();
				receiveMsg.setText(pc_r);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			break;
		case R.id.button2:
			
			pc = Parcel.obtain();
			pc_reply = Parcel.obtain();
			pc.writeString("stopSendMsg");
			
			try {
				iBinder.transact(2, pc, pc_reply, 0);
				receiveMsg.setText(pc_reply.readString());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			break;
		case R.id.button3:
			unbindService(mConnection);
			stopService(new Intent("com.hubery.testbinder.REMOTE_SERVICE"));
			finish();
			break;
			
		default:
			break;
		}
	}
}
