package com.zhjl.qihao.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.zhjl.qihao.step.IStepCallback;
import com.zhjl.qihao.step.StepCounter;
import com.zhjl.qihao.step.StepNotifier;
import com.zhjl.qihao.util.DateFormatUtils;
import com.zhjl.qihao.abutil.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StepService extends Service {

    public static List<IStepCallback>  callbacks = new ArrayList<>();
    SharedPreferences sp = null;
    public static  String STEP = "step";
    public static  String STEP_DATE = "step_date";

    public static String OLD_STEP = "old_step";
    public static String OLD_STEP_DATE = "old_step_date";

   public String mCurrentDate = DateFormatUtils.getDate();
    // 传感器事件监听器
    private StepCounter mStepCounter;
    private SensorManager mSensorManager;
    private static StepNotifier mStepNotifier;
    static public int mSteps; // 行走的步数
    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        LogUtils.d("StepService onCreate");
        if(mStepCounter == null){
            mStepCounter = new StepCounter();
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            // 注册传感器时间监听器
            registSensorEvent();

            // 步数变化监听器
            mStepNotifier = new StepNotifier(stepListener);
            // 初始化步数为0
     //       mStepNotifier.setStep(0);
            String currentDate = DateFormatUtils.getDate();
            String date = sp.getString(STEP_DATE, "");

            LogUtils.d("--date is "+date);
            int step = sp.getInt(STEP,0);

            if(TextUtils.isEmpty(date)){

                SharedPreferences.Editor edit = sp.edit();
                edit.putString(STEP_DATE,currentDate);
                edit.putString(OLD_STEP_DATE,currentDate);
                edit.putInt(STEP,0);//
                edit.commit();
                mStepNotifier.setStep(0);
            }else if(date != null && date.equals(currentDate)){//日期相同
                mStepNotifier.setStep(step);
            }else{//日期不同更新

                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(OLD_STEP,step);//设置更新步数
                edit.putString(OLD_STEP_DATE,date);//设置更新步数的时间

                edit.putString(STEP_DATE,currentDate);
                edit.putInt(STEP,0);//
                edit.commit();
                mStepNotifier.setStep(0);
            }
            // 作为监听器放入stepCounter
            mStepCounter.addListener(mStepNotifier);
        }else{
            String currentDate = DateFormatUtils.getDate();
            String date = sp.getString(STEP_DATE, currentDate);

            int step = sp.getInt(STEP,0);
            LogUtils.d("date is "+date);
            if(date != null && date.equals(currentDate)){//日期相同
                mStepNotifier.setStep(step);
            }else{//日期不同更新

                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(OLD_STEP,step);//设置更新步数
                edit.putString(OLD_STEP_DATE,date);//设置更新步数的时间

                edit.putString(STEP_DATE,currentDate);
                edit.putInt(STEP,0);//
                edit.commit();
                mStepNotifier.setStep(0);
            }
        }


    }

    public void reset(){
        mSteps = 0;
        String currentDate = DateFormatUtils.getDate();
        String date = sp.getString(STEP_DATE, currentDate);
        int step = sp.getInt(STEP,0);
        // 初始化步数为0

        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(OLD_STEP,step);//设置更新步数
        edit.putString(OLD_STEP_DATE,date);//设置更新步数的时间

        edit.putString(STEP_DATE,currentDate);
        edit.putInt(STEP,0);//
        edit.commit();
        mStepNotifier.setStep(mSteps);

    }

    @Override
    public void onDestroy() {
        unregistSensorEvent();
        LogUtils.d("StepService onDestroy");
        super.onDestroy();
        // 注销传感器监听事件



    }

    public void registSensorEvent() {
        // 获取加速度计
        Sensor mSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 使用事件监听器mStepCounter监听加速度计mSensor
        mSensorManager.registerListener(mStepCounter, mSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregistSensorEvent() {
        // 取消传感器事件监听器对传感器的监听
        mSensorManager.unregisterListener(mStepCounter);
    }

    // 回传数值
    private StepNotifier.Listener stepListener = new StepNotifier.Listener() {

        @Override
        public void valueChanged(int value) {
            // TODO Auto-generated method stub
   //         LogUtils.d("step is ==== "+value);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt(STEP,value);
            edit.commit();
            mSteps = value;
            passValue();
        }

        @Override
        public void passValue() {
            // TODO Auto-generated method stub

            //更新昨天步数并从新计数
            String today = sp.getString(STEP_DATE,"");
            String currientDate = DateFormatUtils.getDate();
            String yesterday = sp.getString(OLD_STEP_DATE,"");
            if(!today.equals(currientDate)){
                for(IStepCallback callback : callbacks){
                    callback.historyChanged(mSteps ,sp.getString(STEP_DATE,""));

                }
                reset();
            }
            for(IStepCallback callback : callbacks){
                callback.stepChanged(mSteps);

            }



        }
    };



}
