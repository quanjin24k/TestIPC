package com.hubery.testUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class GameLoopView extends View {

	private Paint paint= new Paint();
	private int radiusX = 100;//圆心
	private int radiusY = 100;
	private int radiusR = 100;//半径
	private float count = 0;
	private myHandler h;
	
	public GameLoopView(Context context) {
		super(context);
		h = new myHandler();
	}
	
	public GameLoopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public GameLoopView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		float hScale = 1.0f;
		float vScale = 1.0f;
		
		float dialWH = radiusR * 2;
		if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < dialWH) {
			hScale = (float) widthSize / (float) dialWH;
		}

		if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < dialWH) {
			vScale = (float) heightSize / (float) dialWH;
		}
		
		float scale = Math.min(hScale, vScale);

		setMeasuredDimension((int)(dialWH *scale), (int)(dialWH * scale));
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if( count > 12) {
			count = 0;
		}
		count++;
		
		int x = (int) (radiusR * Math.cos(2*Math.PI * count/12.0)); 
		int y = (int) (radiusR * Math.sin(2*Math.PI * count/12.0)); 
		
		canvas.drawColor(Color.GRAY); //画布背景
		canvas.drawCircle(radiusX, radiusY, radiusR, paint);
		
		//表盘
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(1);
		canvas.drawCircle(radiusX, radiusY, radiusR, paint);
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(2);
		canvas.drawCircle(radiusX, radiusY, radiusR-3, paint);
		
		//刻度
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(2);
		for(int i = 0; i < 12; i++) {
			int x_inner = (int) ((radiusR-20) * Math.cos(2*Math.PI * i/12.0 - 90-30)); 
			int y_inner = (int) ((radiusR-20) * Math.sin(2*Math.PI * i/12.0 - 90-30)); 
			canvas.drawText(String.valueOf(i+1), radiusX+x_inner, radiusY+y_inner, paint);
		}
		
		paint.setColor(Color.RED);//指针颜色
		paint.setStrokeWidth(3);
		canvas.drawLine(radiusX, radiusY, radiusX+x, radiusY+y, paint); //表－指针
		
		//表 －圆心
		paint.setStrokeWidth(2);
		paint.setColor(Color.BLUE);
		canvas.drawRect(radiusX-5, radiusY - 5, radiusX+5, radiusY + 5, paint); 
		paint.setColor(Color.YELLOW);
		canvas.drawRect(radiusX-3, radiusY - 3, radiusX+3, radiusY + 3, paint);
		
		h.removeMessages(0);
		Message msg = h.obtainMessage(0);
		h.sendMessageDelayed(msg, 1000);
	}
	
	class myHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			invalidate();//刷新UI
		}
	}

}
