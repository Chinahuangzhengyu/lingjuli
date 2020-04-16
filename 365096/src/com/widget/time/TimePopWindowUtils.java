package com.widget.time;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.LogUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-12-07.
 */
public class TimePopWindowUtils {

    public static void initPopuptWindow(View view, final Activity activity, final View.OnClickListener listener, boolean hasSelectTime) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View timepickerview = layoutInflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(activity);
        final WheelMain wheelMain = new WheelMain(timepickerview, hasSelectTime);
        wheelMain.screenheight = screenInfo.getHeight();
        // wheelMain2 wheelMain2 = new WheelMain2(timepickerview, true);
        // wheelMain2.screenheight = screenInfo.getHeight();
        // Time t = new Time(); // or Time t=new Time("GMT+8"); ����Time Zone����
        // Time t = new Time("GMT+8"); // or Time t=new Time("GMT+8"); ����Time Zone���
        //t.setToNow(); //
        /*int year = t.year;
        int month = t.month;
        int day = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;*/
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        final PopupWindow mPopupWindow = new PopupWindow(timepickerview,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.9f;
        activity.getWindow().setAttributes(lp);
        wheelMain.initDateTimePicker(year, month, day, hour, minute);
        Drawable draw = new ColorDrawable(activity.getResources().getColor(
                android.R.color.transparent));
        mPopupWindow.setBackgroundDrawable(draw);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.AnimationPopupPush);
       // mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        timepickerview.findViewById(R.id.btn_ok).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //生成二维码进行分享
                        LogUtils.w("select time is " + wheelMain.getTime().replace("-", ""));

//                        tvTime.setText(wheelMain.getTime());

                        mPopupWindow.dismiss();
                        WindowManager.LayoutParams lp = activity.getWindow()
                                .getAttributes();
                        lp.alpha = 1f;
                        activity.getWindow().setAttributes(lp);
                        String time = wheelMain.getTime().replace("-", "");
                        TextView tv = new TextView(activity);
                        tv.setText(time);
                        tv.setTag(time);
                        // String a=wheelMain.getTime();

                        listener.onClick(tv);
                    }
                });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });

    }
}
