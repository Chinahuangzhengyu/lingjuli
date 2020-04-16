package com.zhjl.qihao.abrefactor.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 作者： 黄郑宇
 * 时间： 2019/5/10
 * 类作用：
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("+++++++", "onReceive: ");
    }
}
