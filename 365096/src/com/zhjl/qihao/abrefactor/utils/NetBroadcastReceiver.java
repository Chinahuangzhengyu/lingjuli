package com.zhjl.qihao.abrefactor.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetConnectedListener netConnectedListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        //网络及其不稳定可能会造成空指针
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//            //WIFI和移动网络均未连接
//            netConnectedListener.netContent(false);
//
//        } else {
//            //WIFI连接或者移动网络连接
//            netConnectedListener.netContent(true);
//        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            /**
             * 获取网络信息实体
             * 由于从系统服务中获取数据属于进程间通信，基本类型外的数据必须实现Parcelable接口，
             * NetworkInfo实现了Parcelable，获取到的activeNetInfo相当于服务中网络信息实体对象的一个副本（拷贝），
             * 所以，不管系统网络服务中的实体对象是否置为了null，此处获得的activeNetInfo均不会发生变化
             */
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                netConnectedListener.netContent(true);
            }else {
                netConnectedListener.netContent(false);
            }
        }
    }

    public void setNetConnectedListener(NetConnectedListener netConnectedListener) {
        this.netConnectedListener = netConnectedListener;
    }

    public interface NetConnectedListener {
        void netContent(boolean isConnected);
    }
}
