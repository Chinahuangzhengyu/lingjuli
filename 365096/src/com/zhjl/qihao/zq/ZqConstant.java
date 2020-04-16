package com.zhjl.qihao.zq;

import com.zhjl.qihao.BuildConfig;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

/**
 * time   :  2018/11/7
 * author :  Z
 * des    :  自己个用的一个常量类
 */
public class ZqConstant {


    // 这个是 给我的维修 tab 单例模式时使用的 一个 标签
    public static String MINESERVICE_TAB = "mine_service_tab";


//    // php 测试服
//    public static String BASE_URL_PHP_LOCAL = "http://192.168.1.191:80";
//
//
//    // JAVA 测试服
//    public static String BASE_URL_JAVA_LOCAL = "http://192.168.1.191:8080";


    // php 正式服
    public static String BASE_URL_PHP_ONLINE = API_HOST + ":80";

    // JAVA 正式服
    public static String BASE_URL_JAVA_ONLINE = API_HOST + JAVA_PORT_NUMBER;

    // 我的维修 -- 进行中
    public static final int SERVICE_DOING = 1;

    // 我的维修 -- 已完成
    public static final int SERVICE_DONE = 2;

    // 我的维修 -- 已退单
    public static final int SERVICE_RETURN = 3;


    // 维修记录状态 - 进行中
    public static String SERVER_STATUS_DOING = "0";

    // 维修记录状态  - 已完成
    public static String SERVER_STATUS_DONE = "2";
    //维修记录 状态  - 已退单
    public static String SERVER_STATUS_CHARGE_BACK = "6";


}
