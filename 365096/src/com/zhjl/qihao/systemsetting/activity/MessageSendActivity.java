package com.zhjl.qihao.systemsetting.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MessageSendActivity extends VolleyBaseActivity {
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "意见反馈", this);
        btnSubmit.setOnClickListener(this);
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etMessage.getText().toString().trim().length() > 0) {
                    btnSubmit.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_22));
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_d8d8d8_22));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (etMessage.getText().toString().trim().equals("")){
                    Toast.makeText(mContext, "请输入反馈内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestSubmit();
                break;
        }
    }

    private void requestSubmit() {
        SettingInterface settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("content", etMessage.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.feedbackSend(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }
}
