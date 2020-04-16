package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingPayPasswordActivity extends VolleyBaseActivity {
    @BindView(R.id.et_pay_password)
    EditText etPayPassword;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String cardId;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pay_password);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "设置密码", this);
        cardId = getIntent().getStringExtra("cardId");
        code = getIntent().getStringExtra("code");
    }

    @OnClick({R.id.btn_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (etPayPassword.getText().toString().length()==6){
                    Intent intent = new Intent(this, ConfirmPayPasswordActivity.class);
                    intent.putExtra("password",etPayPassword.getText().toString().trim());
                    intent.putExtra("cardId",cardId);
                    intent.putExtra("code",code);
                    startActivity(intent);
                }else {
                    Toast.makeText(mContext, "请输入6位数的支付密码！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_back:
                showPop();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            showPop();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showPop() {
        PopWindowUtils popWindowUtils = PopWindowUtils.getInstance();
        popWindowUtils.show("是否放弃设置支付密码",this);
        popWindowUtils.setSetYesOnClickListener(() -> {
            popWindowUtils.dismiss();
            finish();
        });
    }
}
