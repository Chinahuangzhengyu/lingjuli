package com.zhjl.qihao.abutil;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {

	private String finish_text;
	private String tick_text;
	private Button btn;
	public TimeCount(long millisInFuture, long countDownInterval,String finish_text,String tick_text,Button btn ) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		this.finish_text = finish_text;
		this.tick_text = tick_text;
		this.btn = btn;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		if(btn != null){
			btn.setText(String.format(tick_text, millisUntilFinished / 1000));
			btn.setEnabled(false);
			btn.setClickable(true);
		}
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		if(btn != null){
			btn.setText(finish_text);
			btn.setEnabled(true);
		}
	}

}
