package com.zhjl.qihao.abutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/9/20.
 */

public class DateTransform {
   /* public static void main(String[] args) {
        String dataStr = "2014-09-01 19:47";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        try {
            Date ckDate = sdf.parse(dataStr);
            System.out.println("原始时间为：" + dataStr);
            System.out.println("转换后时间为：" + getTime(ckDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/



    // 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
    public static String getTime(String mTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date = null;
        try {
            date = sdf.parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean sameYear = false;
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天";
        String beforYesterDaySDF = "前天";
        String otherSDF = "MM-dd";
        String otherYearSDF = "yyyy-MM-dd";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }

        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                // sfd = new SimpleDateFormat(yesterDaySDF);
                // time = sfd.format(date);
                time = yesterDaySDF;
                return time;
            }
            todayCalendar.add(Calendar.DATE, -2);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天
                // sfd = new SimpleDateFormat(beforYesterDaySDF);
                // time = sfd.format(date);
                time = beforYesterDaySDF;
                return time;
            }
        }

        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }
}
