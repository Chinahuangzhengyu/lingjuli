package com.zhjl.qihao.systemsetting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePasswordActivity extends VolleyBaseActivity {
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms)
    TextView tvGetSms;
    @BindView(R.id.et_update_password)
    EditText etUpdatePassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;
    @BindView(R.id.tv_password_check)
    TextView tvPasswordCheck;
    @BindView(R.id.btn_sure_update)
    Button btnSureUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"修改密码",this);
    }

    @OnClick({R.id.tv_get_sms, R.id.btn_sure_update,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:
                break;
            case R.id.btn_sure_update:
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
