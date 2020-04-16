package com.zhjl.qihao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-05-05.
 */
public class DateFormatUtils {

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        return sdf.format(new Date().getTime());
    }

    public static String getDateDetail(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return sdf.format(new Date().getTime());
    }

    public static int getHours(){
        return new Date().getHours();
    }
}
