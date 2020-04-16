package com.zhjl.qihao.abutil;

/**
 * 作者： 黄郑宇
 * 时间： 2018/5/2
 * 类作用：防止按钮重复点击
 */

public class ButtonRepect {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}