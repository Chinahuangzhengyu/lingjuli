package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
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

public class UpdateNumberActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_binding_number)
    TextView tvBindingNumber;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms)
    TextView tvGetSms;
    @BindView(R.id.tv_phone_number_explain)
    TextView tvPhoneNumberExplain;
    @BindView(R.id.btn_sure_update)
    Button btnSureUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_number);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"修改手机号",this);
    }

    @OnClick({R.id.tv_get_sms, R.id.tv_phone_number_explain, R.id.btn_sure_update,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:
                break;
            case R.id.tv_phone_number_explain:
                break;
            case R.id.btn_sure_update:
                Intent intent= new Intent(mContext,SuccessActivity.class);
                intent.putExtra("successType","手机号码更改成功！");
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
