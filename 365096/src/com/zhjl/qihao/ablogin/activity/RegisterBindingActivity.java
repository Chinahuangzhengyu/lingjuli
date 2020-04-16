package com.zhjl.qihao.ablogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.chooseCity.activty.ChooseCityActivity;
import com.zhjl.qihao.chooseCity.activty.ChoosePropertyActivity;
import com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBindingActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_binding);
        ButterKnife.bind(this);
        isRegister = getIntent().getBooleanExtra("isRegister", false);
    }

    @OnClick({R.id.tv_no, R.id.tv_yes})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_no:
                intent.setClass(mContext, ChoosePropertyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_yes:
                intent.setClass(mContext, AddHomeAddressBindingActivity.class);
                intent.putExtra("isRegister",isRegister);
                startActivity(intent);
                break;
        }
    }
}
