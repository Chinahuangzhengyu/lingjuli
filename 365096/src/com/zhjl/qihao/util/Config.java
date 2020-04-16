package com.zhjl.qihao.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class Config {
    public static String mUrl = "http://119.147.23.84:8383/2.0/index.php?c=%1$s&a=%2$s";
    // static String mUrl1 = "http://119.147.23.84:8383/2.0/index.php?c=%1$s&a=%2$s";

    public static String pagecount = "15";
    public static String mSetting = "nysetting";
    public static int mOutTime = 15000;
    public static String yzm = "";
    public static String img = "";
    public static String phone = "";
    public static String quxiaoshoucang = "";
    public static String quxiaoyuyue = "";
    public static String memlist = "";
    public static long mTimeReLoad = 7 * 24 * 60 * 60 * 1000;// 7天
    public static String isshowad = "0";
    public static String UPDATE_ALLSTATUS = "com.nykjjy160.updateAllStatus";
    public static String UPDATE_ASK = "com.nykjjy160.updateAsk";
    public static String UPDATE_NEWMSG = "com.nykjjy160.newMsg";//我的-显示新
    public static String UPDATE_NEWMINEMSG = "com.nykjjy160.newMineMsg";

    public static String getSDCardPath(Context pContext) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/.cloudcity";
            File PathDir = new File(path);
            if (!PathDir.exists()) {
                PathDir.mkdirs();
            }
            return path;
        } else {
            return pContext.getCacheDir().getAbsolutePath();
        }
    }
}
