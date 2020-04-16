package com.zhjl.qihao.service.utils;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.zhjl.qihao.abutil.LogUtils;

/**
 * @author south Created by  on 2016-06-23.
 * @version 1.0
 * @description xxx
 */
public class BluetoothCommunThread extends Thread{
    private Handler serviceHandler; // 与Service通信的Handler
    private BluetoothSocket socket;
    private ObjectInputStream inStream; // 对象输入流
    private ObjectOutputStream outStream; // 对象输出流
    public volatile boolean isRun = true; // 运行标志位

    /**
     * 构造函数
     *
     * @param handler
     *            用于接收消息
     * @param socket
     */
    public BluetoothCommunThread(Handler handler, BluetoothSocket socket) {
        this.serviceHandler = handler;
        this.socket = socket;
        try {
            this.outStream = new ObjectOutputStream(socket.getOutputStream());
            this.inStream = new ObjectInputStream(new BufferedInputStream(
                    socket.getInputStream()));
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // 发送连接失败消息
            serviceHandler.obtainMessage(BluetoothTools.MESSAGE_CONNECT_ERROR)
                    .sendToTarget();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!isRun) {
                break;
            }
            try {
                Object obj = inStream.readObject();
                // 发送成功读取到对象的消息，消息的obj参数为读取到的对象
                Message msg = serviceHandler.obtainMessage();
                msg.what = BluetoothTools.MESSAGE_READ_OBJECT;
                msg.obj = obj;
                msg.sendToTarget();
            } catch (Exception ex) {
                // 发送连接失败消息
                serviceHandler.obtainMessage(
                        BluetoothTools.MESSAGE_CONNECT_ERROR).sendToTarget();
                ex.printStackTrace();
                return;
            }
        }

        // 关闭流
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outStream != null) {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入一个可序列化的对象
     *
     * @param obj
     */
    public void writeObject(Object obj) {
        try {
            outStream.flush();
            outStream.writeObject(obj);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeCMD(String cmd) {
        LogUtils.d("send cmd is "+cmd);
        try {
            outStream.flush();
            outStream.write(cmd.getBytes(),0,cmd.length());
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
