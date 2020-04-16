package com.zhjl.qihao.service.utils;

/**
 * @author south Created by  on 2016-06-23.
 * @version 1.0
 * @description xxx
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.UUID;

import com.zhjl.qihao.abutil.LogUtils;

/**
 * 蓝牙客户端连接线程
 */
public class BluetoothClientConnThread  extends Thread {
    private Handler serviceHandler; // 用于向客户端Service回传消息的handler
    private BluetoothDevice serverDevice; // 服务器设备
    private BluetoothSocket socket; // 通信Socket

    /**
     * 构造函数
     */
    public BluetoothClientConnThread(Handler handler,
                                     BluetoothDevice serverDevice) {

        this.serviceHandler = handler;
        this.serverDevice = serverDevice;
//        try {
//            socket = serverDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                try {
                    //建立连接00001800-0000-1000-8000-00805F9B34FB
//            socket = serverDevice.createRfcommSocketToServiceRecord(BluetoothTools.PRIVATE_UUID);//00001101-0000-1000-8000-00805F9B34FB
//            socket = serverDevice.createRfcommSocketToServiceRecord(UUID
//                    .fromString("00001101-0000-1000-8000-00805F9B34FB"));//00001101-0000-1000-8000-00805F9B34FB
                    //00000000-0000-1000-8000-00805F9B34FB
                    //00000000-0000-1000-8000-00805F9B34FB
                    socket = serverDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//            socket =(BluetoothSocket) serverDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(serverDevice,1);

                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//                    if(socket == null){
//                        serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
//                                .sendToTarget();
//                        return;
//                    }

                    socket.connect();
                } catch (Exception ex) {
                    LogUtils.exception(ex);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("BluetoothClientConnThread，e=" + ex.getMessage());
                    // 发送连接失败消息
                    serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
                            .sendToTarget();
                    return;
                }

                // 发送连接成功消息，消息的obj参数为连接的socket
                Message msg = serviceHandler.obtainMessage();
                msg.what = BluetoothTools.MESSAGE_CONNECT_SUCCESS;
                msg.obj = socket;
                msg.sendToTarget();
            }
        }).start();

    }

}
