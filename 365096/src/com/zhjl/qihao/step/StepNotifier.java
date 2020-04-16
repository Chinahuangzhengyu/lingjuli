package com.zhjl.qihao.step;


/**
 * Created by Administrator on 2016-05-05.
 */
public class StepNotifier implements StepListener{

    public interface Listener {
        public void valueChanged(int value);

        public void passValue();
    }

    private Listener mListener;

    int steps = 0;
    public StepNotifier(Listener listener) {
        mListener = listener;
    }
    @Override
    public void onStep() {
        steps++;
        notifyListener();
    }

    @Override
    public void passValue() {

    }

    void notifyListener() {
        mListener.valueChanged(steps);
    }

    void reloadSettings() {
        notifyListener();
    }

    public void resetValues() {
        steps = 0;
        // 更新数值之后通知监听器
        notifyListener();
    }

    public void setStep(int step) {
        this.steps = step;
        notifyListener();
    }
}
