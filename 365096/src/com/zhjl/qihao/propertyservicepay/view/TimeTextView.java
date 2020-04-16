package com.zhjl.qihao.propertyservicepay.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTextView extends android.support.v7.widget.AppCompatTextView {

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String time = (String) msg.obj;
            String[] split = time.split(":");
            if (split[0].equals("00")&&split[1].equals("00")){
                getTimeChange.getTime();
            }
            TimeTextView.this.setText("请在" + split[0] + "分" + split[1] + "秒内完成支付");
        }
    };
    @SuppressLint("SimpleDateFormat")
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("mm:ss");
    private Date addDate = new Date();
    private Date date;
    private String time;
    private boolean isReturn;
    public int i;

    public void setTime(Date date) {
        this.date = date;
        init();
    }

    public void cancel(){
        isReturn = true;
        handler.removeCallbacksAndMessages(null);
    }

    public String getTime() {
        return time;
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Calendar calendar = Calendar.getInstance();
                    if (date != null && !date.toString().equals("")) {
                        i++;
                        calendar.setTime(date);
                        long timeScond = calendar.getTime().getTime() - i * 1000;
                        addDate.setTime(timeScond);
                        time = dateFormatter.format(addDate);
                    } else {
                        time = dateFormatter.format(calendar.getTime());
                    }
                    handler.sendMessage(handler.obtainMessage(100, time));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isReturn){
                        return;
                    }
                }
            }
        }).start();
    }

    private GETTimeChange getTimeChange;

    public void setGetTimeChange(GETTimeChange getTimeChange) {
        this.getTimeChange = getTimeChange;
    }

    public interface GETTimeChange{
        void getTime();
    }
}