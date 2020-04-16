package com.zhjl.qihao.order.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.zhjl.qihao.propertyservicepay.view.TimeTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTextView extends android.support.v7.widget.AppCompatTextView {

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String time = (String) msg.obj;
            String[] split = time.split(":");
            DateTextView.this.setText("剩余" + split[0] + "小时" + split[1] + "分自动退款");
        }
    };
    @SuppressLint("SimpleDateFormat")
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");
    private Date addDate = new Date();
    private Date date;
    private String time;
    public int i;

    public void setTime(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public DateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
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
                }
            }
        }).start();
    }
}