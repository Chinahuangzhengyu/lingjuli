package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ConfirmPayPasswordActivity extends VolleyBaseActivity {
    @BindView(R.id.et_pay_password)
    EditText etPayPassword;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    private String password;
    private String cardId;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay_password);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "设置密码", this);
        password = getIntent().getStringExtra("password");
        cardId = getIntent().getStringExtra("cardId");
        code = getIntent().getStringExtra("code");
    }

    @OnClick({R.id.iv_back, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_finish:
                btnFinish.setEnabled(false);
                requestUpdatePassword();
                break;
        }
    }

    private void requestUpdatePassword() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.resetPayPassword(mSession.getmToken(), cardId, password, etPayPassword.getText().toString().trim(),code, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                btnFinish.setEnabled(true);
                if (status){
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmPayPasswordActivity.this, MembershipCardDetailActivity.class);
                    intent.putExtra("cardId",cardId);
                    startActivity(intent);
                    finish();
                }else {
                    if (object.optInt("type")!=1&&object.optInt("type")!=0){
                        Intent intent = new Intent(ConfirmPayPasswordActivity.this, MembershipCardDetailActivity.class);
                        intent.putExtra("cardId",cardId);
                        startActivity(intent);
                        finish();
                    }else {
                        Utils.phpIsLogin(ConfirmPayPasswordActivity.this, object.optInt("type"), object.optString("message"));
                    }
                }
            }

            @Override
            public void fail() {
                btnFinish.setEnabled(true);
            }
        });
    }
}
