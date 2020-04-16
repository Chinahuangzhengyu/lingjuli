package com.zhjl.qihao.pay;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.zq.ZqBaseActivity;

import butterknife.BindView;

/**
 * 新的 支付 Activity ;
 */
public class ZPayActivity extends ZqBaseActivity implements RadioButton.OnCheckedChangeListener {

    private static String TAG = "== ZPayActivity =";

    @BindView(R.id.r_ali)
    RadioButton rAli;
    @BindView(R.id.ali)
    RelativeLayout ali;

    @BindView(R.id.r_wechat)
    RadioButton rWechat;
    @BindView(R.id.wechat)
    RelativeLayout wechat;

    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.pay)
    Button pay;

    @Override
    protected void initDatas() {
        setTitle("收银台");

        rAli.setOnCheckedChangeListener(this);
        rWechat.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_zpay;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.r_ali:
                break;
            case R.id.r_wechat:
                break;
        }
    }
}
