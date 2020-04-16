package com.zhjl.qihao.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.service.utils.BleWrapperUiCallbacks;
import com.zhjl.qihao.service.utils.BluetoothCommunThread;

import java.util.ArrayList;
import java.util.List;

import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.util.Utils;

public class OpendoorBleService extends Service {

//    Context context;
    // 搜索到的远程设备集合
    private List<BluetoothDevice> discoveredDevices = new ArrayList<BluetoothDevice>();

    // 蓝牙适配器
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothManager mBluetoothManager = null;            // BluetoothManager    的作用就是实例化 BluetoothAdapter
    private BluetoothDevice mBluetoothDevice = null;
    private List<BluetoothDevice> mBluetoothDevices = new ArrayList<>();
    private boolean isItalk = false;


    // 蓝牙通讯线程
    private BluetoothCommunThread communThread;

    private boolean TempB = false;// 判断是否是主动取消的搜索

    private String cmd = null;

    private static final BleWrapperUiCallbacks NULL_CALLBACK = new BleWrapperUiCallbacks.Null();

    private boolean done = false;

    private OpendoorCallBack callback;

    private boolean mConnected;
    private boolean reConnected = false;
    private boolean mConnecting = false;
    private int connectCount = 0;


    private BluetoothGatt mBluetoothGatt = null;

    private boolean mOutTime = true;
    private Handler mHandler = new Handler();


    /**
     * 获取通讯线程
     *
     * @return
     */
    public BluetoothCommunThread getBluetoothCommunThread() {
        return communThread;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("OpendoorBleService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public OpendoorBleService getService() {
            return OpendoorBleService.this;
        }
    }

    /**
     * Service创建时的回调函数
     */
    @Override
    public void onCreate() {
        //LogUtils.d("OpendoorBleService onCreate");
        //HandlerThread thread = new HandlerThread();
       /* thread.start();
        // 获取当前线程中的looper对象
        looper = getLooper();
        //创建Handler对象，把looper传递过来使得handler、
        //looper和messageQueue三者建立联系
        handler = new ServiceHandler(looper);*/

        discoveredDevices.clear(); // 清空存放设备的集合

        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) OpendoorBleService.this.getSystemService(Context.BLUETOOTH_SERVICE);

        }
        if (bluetoothAdapter == null) {
            bluetoothAdapter = mBluetoothManager.getAdapter();

            //    bluetoothAdapter.enable(); // 打开蓝牙
        }


        super.onCreate();
    }


    public boolean isBtEnabled() {
        final BluetoothManager manager = (BluetoothManager) OpendoorBleService.this.getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager == null) return false;

        final BluetoothAdapter adapter = manager.getAdapter();
        if (adapter == null) return false;

        return adapter.isEnabled();
    }

    /**
     * Service销毁时的回调函数
     */
    @Override
    public void onDestroy() {
        LogUtils.d("OpendoorBleService onDestroy");
        if (communThread != null) {
            communThread.isRun = false;
        }
        if (bluetoothAdapter != null) {
            //  bluetoothAdapter.disable();// 关闭蓝牙
        }

        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        mOutTime = true;

        super.onDestroy();
    }


    public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
        LogUtils.e("blut999", "writeDataToCharacteristic uuid is " + ch.getUuid() + " data is " + Utils.bytesToHexString(dataToWrite) + " data.length = " + dataToWrite.length);
        if (bluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        // first set it locally....
        boolean a = ch.setValue(dataToWrite);
        // ... and then "commit" changes to the peripheral
        boolean b = mBluetoothGatt.writeCharacteristic(ch);

        LogUtils.e("blut999", a + "---" + b);


    }

    private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {

        @Override//当连接上设备或者失去连接时会回调该函数
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            mConnecting = false;
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {//连接成功
                reConnected = true;
                LogUtils.e("blut999", "连接成功");
//                if (callback != null) {
//                    callback.tips("连接成功");
//                    callback.connectedAction(1);
//                }
                mConnected = true;
//                mBluetoothGatt.readRemoteRssi();
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.discoverServices();//连接成功后就去找出该设备中的服务
                    if (callback != null) {
                        callback.tips("正在开门...");
                        LogUtils.e("blut999", "正在开门.");
                        callback.connectedAction(3);
                    }
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {//连接失败
                LogUtils.e("blut999", "连接失败");
                //   int  reconnect=0;
                //          stopSelf();
                if (callback != null) {
                    callback.tips("断开连接");
                    if (!reConnected) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        callback.connectedAction(2);
                    }
                }
                reConnected = false;
                mConnected = false;
//                diconnect();
                mBluetoothGatt.close();
            } else {
//                diconnect();
//                if (callback != null) {
////                    try {
////                        Thread.sleep(1000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    callback.tips("断开连接");
//                    callback.connectedAction(2);
//                }
//
//                mConnected = false;
//                mBluetoothGatt.close();
            }
        }

        @Override//当设备是否找到服务时，会回调该函数
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("blut999", "发送数据");
                    List<BluetoothGattService> mBluetoothGattServices = mBluetoothGatt.getServices();
                    //写入数据
                    if (mBluetoothGattServices != null) {
                        //          LogUtils.d("mBluetoothGatt != null ");
                        if (mBluetoothGatt.getServices() != null) {
                            //                LogUtils.d("mBluetoothGattServices.size " + mBluetoothGattServices.size());
                        } else {
                            //                LogUtils.d("mBluetoothGattServices == null ");
                        }
                        for (BluetoothGattService gattService : mBluetoothGattServices) {
                            done = true;
                            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                            for (BluetoothGattCharacteristic ch : gattCharacteristics) {
                                ch.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                                String uuid = ch.getUuid().toString();
                                if (uuid.toLowerCase().contains("0000ffe1")) {
                                   /* String a=cmd+"@"+uuid;
                                    try {
                                        byte[] b = a.getBytes("UTF-8");
                                        writeDataToCharacteristic(ch, b);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }*/
                                    byte[] bytes = cmd.getBytes();
                                    //  writeDataToCharacteristic(ch, bytes);
                                    LogUtils.e("blut999", "cmd is " + Utils.bytesToHexString(bytes));
                                    if (bytes.length > 20) {
                                        int i = 0;
                                        do {
                                            int length = bytes.length - i > 20 ? 20 : bytes.length - i;
                                            byte[] b = new byte[length];
                                            System.arraycopy(bytes, i, b, 0, length);
                                            if (i + length >= bytes.length) {
                                                done = false;
                                            }
                                            writeDataToCharacteristic(ch, b);
                                            i += length;
                                            try {
                                                Thread.sleep(40);
                                                LogUtils.e("blut999", "sleep(50)");
                                            } catch (InterruptedException e) {
                                                disonnect();
                                                done = false;
                                                e.printStackTrace();
                                            }
                                        } while (i < bytes.length);
                                    }

                                }
                            }
                            done = false;

                        }

                    }
                }
            }).start();

        }


        @Override//当向设备中写数据时，会回调该函数
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (!done) {
                if (callback != null) {
                    try {
                        Thread.sleep(100);
                        LogUtils.e("blut999", "sleep(100)");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    callback.tips("断开连接");
//                    callback.connectedAction(2);
                    disonnect();
                }

            }
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.d("write success done is " + done);

            } else {
                LogUtils.d("write fail done is " + done);
            }
        }
    };

    /**
     * 连接-建立传输通道-发送cmd-关闭连接
     *
     * @param cmd
     */
    public void openDoor(String cmd, BluetoothDevice device) {

        LogUtils.e("blut999", "cmd---" + cmd + "---" + device.getName());

        Log.e("== main - bluetooth = ", "指令 = " + cmd);
        //如果正在开门或连接，则返回
//        diconnect();
        if (mConnected) {
            // LogUtils.e("links111","connect--" + mConnected);

//            int i = 0;
//            while (mConnected && i < 5) {
//                try {
//                    i++;
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

        }

        if (mConnecting) {
            LogUtils.e("links111", "Connecting--");
        }
        if (!mConnecting && !mConnected) {
//            for (BluetoothDevice bluetoothDevice : mBluetoothDevices) {
            String address = device.getAddress();
            String name = device.getName();
            if ((name != null && name.startsWith("ITALK"))) {
                //  if (name != null) {
                TempB = true;
                //         bluetoothAdapter.cancelDiscovery();// 取消搜索
                // LogUtils.e("blut","connect count is  " + connectCount);

                this.cmd = cmd;
                if (callback != null) {
                    callback.tips("正在连接...");
                    callback.connectedAction(0);
                }

                mConnecting = true;
                mBluetoothDevice = device;
                connectCount++;
                if (mBluetoothGatt != null) {
//                        mBluetoothGatt.disconnect();
                    mBluetoothGatt.close();
//                        mBluetoothGatt = null;
                }

                if (mOutTime) {
                    mOutTime = false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            do {
                                int count = connectCount;
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (count == connectCount) {
                                    disonnect();
                                }
                            } while (!mOutTime);

                        }
                    }).start();

                }
                //blu进行连接
                mBluetoothGatt = device.connectGatt(OpendoorBleService.this, false, mBleCallback);
            }
//            }
        }
    }


    public boolean isBleOpenDoor() {
//        diconnect();
        boolean iTalk = false;
        if (mBluetoothDevices != null) {
            for (BluetoothDevice bluetoothDevice : mBluetoothDevices) {
                String address = bluetoothDevice.getAddress();
                String name = bluetoothDevice.getName();
                if ((name != null && name.startsWith("ITALK"))) {
                    //if (name != null) {
                    iTalk = true;
                    break;
                }
            }
        }
        return iTalk;
    }

    public void startScan() {
        TempB = false;
        mBluetoothDevices.clear();
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) OpendoorBleService.this.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        if (bluetoothAdapter == null) {
            bluetoothAdapter = mBluetoothManager.getAdapter();
        }
        /*mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bluetoothAdapter != null) {
                    LogUtils.e("blutest","开始搜索设备");
                    bluetoothAdapter.stopLeScan(mDeviceFoundCallback);
                    bluetoothAdapter.startLeScan(mDeviceFoundCallback);
                }
            }
        }, 10);//10s停止搜索*/

        bluetoothAdapter.startLeScan(mDeviceFoundCallback);//搜索蓝牙
        //  bluetoothAdapter.s
        //bluetoothAdapter.startDiscovery();
        Log.e(" blue server - ", "开始搜索蓝牙");

    }

    public void stopScan() {
        if (bluetoothAdapter != null) {
            bluetoothAdapter.stopLeScan(mDeviceFoundCallback);
        }
    }
//

    public void disonnect() {
        if (mBluetoothGatt != null) {
            mConnecting = false;
            mConnected = false;
            LogUtils.d("diconnect");
            //         LogUtils.d("mBluetoothGatt != null");
            mBluetoothGatt.disconnect();
//            mBluetoothGatt = null;
            //    mBluetoothGatt.close();
            //   mBluetoothGatt = null;
        }
    }

    public void close() {
//        diconnect();
//        if (callback != null) {
//            callback.tips("断开连接");
//        }
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
    }


    //扫描获得回调
    private BluetoothAdapter.LeScanCallback mDeviceFoundCallback = new BluetoothAdapter.LeScanCallback() {
        public int i = 0;

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
//            Log.e("== ble server - ", "扫描到的蓝牙设备 = " + device.getName());
            if (!TextUtils.isEmpty(device.getName()) && device.getName().contains("ITALK")) {
//                BluetoothVo vo = RefactorMainActivity.NewHome_addBluet(device, rssi);

                //        //如果信号较强，则开门,判断距离，防止不断连接发送命令
//        if (!bleDevices.contains(device)) {
//            bleDevices.add(device);
//            vo = new BluetoothVo(device,rssi);
//            devices.add(vo);
//        }else{
//            int index = bleDevices.indexOf(device);
//            vo = devices.get(index);
//            vo.deviceRssi = rssi;
//        }
                callback.connectedAction(5);

//                if (rssi > -53 && vo.isAutoOpen && !mConnected && !mConnecting) {
//                    openDoor(Utils.getOpenCmd(OpendoorBleService.this), device);
//                    vo.isAutoOpen = false;
//                } else if (rssi < -60) {
//                    vo.isAutoOpen = true;
//                }
//                mBluetoothDevice=device;
                stopScan();
                i = 0;
            } else {
                i++;
                if (i == 30) {
                    callback.connectedAction(6);
                } else if (i == 100) {
                    callback.connectedAction(6);
                } else if (i == 200) {
                    callback.connectedAction(6);
                } else if (i == 300) {
                    callback.connectedAction(7);
                    i = 0;
                }
            }

            if (!mBluetoothDevices.contains(device)) {
                mBluetoothDevices.add(device);

//                Log.e("== BLE - list", "扫描获取到蓝牙列表 = " +device);

//                stopScan();
//                if (isBleOpenDoor()) {
//                }
//                if (callback != null) {
//                    callback.scanDevice(device);
//                }

            }
        }
    };

    class ConnectTimeOut extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }


    public void setCallback(OpendoorCallBack callback) {
        this.callback = callback;
    }

    public interface OpendoorCallBack {

        public void tips(String tips);

        public void connectedAction(int action);

        public void scanDevice(BluetoothDevice bluetoothDevice);
    }


}
