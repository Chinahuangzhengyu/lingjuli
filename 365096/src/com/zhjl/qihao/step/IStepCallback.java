package com.zhjl.qihao.step;

/**
 * Created by Administrator on 2016-05-05.
 */
public interface IStepCallback {

    public void distanceChanged(double value);

    public void stepChanged(int value);

    public void caloriesChanged(double value);

    public void historyChanged(int step,String date);
}
