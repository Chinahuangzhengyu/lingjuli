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

public class UpdatePayPasswordActivity extends VolleyBaseActivity {
    @BindView(R.id.et_pay_password)
    EditText etPayPassword;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pay_password);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"修改密码",this);
        cardId = getIntent().getStringExtra("cardId");
    }

    private void initData() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.updatePayPassword(mSession.getmToken(), cardId, etPayPassword.getText().toString().trim(), integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status){
                    String code = object.optString("code");
                    Intent intent = new Intent(mContext,SettingPayPasswordActivity.class);
                    intent.putExtra("code",code);
                    intent.putExtra("cardId",cardId);
                    startActivity(intent);
                }else {
                    Utils.phpIsLogin(UpdatePayPasswordActivity.this,object.optInt("type"),object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next:
                if (etPayPassword.getText().toString().trim().length()!=6){
                    Toast.makeText(mContext, "请输入6位数的支付密码！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    initData();
                }
                break;
        }
    }
}
