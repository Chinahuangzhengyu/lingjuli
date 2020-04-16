package com.zhjl.qihao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeText extends TextView implements Runnable {

	private int currentScrollX;// 
	private boolean isStop = false;
	private int textWidth;
	private boolean isMeasure = false;
	private CharSequence text;

	public MarqueeText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	public void updateText(CharSequence text){
		this.text = text;
	}
	
	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(text, BufferType.NORMAL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (!isMeasure) {// 
			getTextWidth();
			if(TextUtils.isEmpty(text)){
				text = getText();
			}
			isMeasure = true;
//			this.postDelayed(this, 1000);
			post(this);
		}
	}

	/**
	 * ��ȡ���ֿ��
	 */
	private void getTextWidth() {
		Paint paint = this.getPaint();
		String str = this.getText().toString();
		textWidth = (int) paint.measureText(str);
		currentScrollX = -getWidth();
	}

	@Override
	public void run() {
		currentScrollX += 3;// 
		scrollTo(currentScrollX, 0); 
		if (isStop) { 
		return; 
		} 
		if (getScrollX() >= textWidth) { 
			if(!text.equals(getText())){
				setText(text);
				getTextWidth();
			}
			currentScrollX = -getWidth();
			scrollTo(currentScrollX, 0);  
		 
		// return; 
		} 
		postDelayed(this, 30);
	} 

	// 
	public void startScroll() {
		isStop = false;
		this.removeCallbacks(this);
		post(this);
	}

	// 
	public void stopScroll() {
		isStop = true;
	}

	// 
	public void startFor0() {
		currentScrollX = 0;
		startScroll();
	}

}
