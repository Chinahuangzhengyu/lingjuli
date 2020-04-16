package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.abutil.TimeCount;
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

public class ForgetPayPasswordActivity extends VolleyBaseActivity {
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_get_sms)
    Button btnGetSms;
    @BindView(R.id.btn_next)
    Button btnNext;
    private PopupWindow smsPopupWindow;
    private TimeCount time;
    private ImageView imgSms;
    private TextView tvCancel;
    private EditText etInput;
    private TextView tvSure;
    private View view;
    private IntegralInterface integralInterface;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_password);
        ButterKnife.bind(this);
        initView();
        NewHeaderBar.createCommomBack(this, "忘记支付密码", this);
        cardId = getIntent().getStringExtra("cardId");
        integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        time = new TimeCount(60000, 1000, getString(R.string.send_verificationCode), "%d秒", btnGetSms);
    }

    private void initView() {
        view = View.inflate(mContext, R.layout.sms, null);
        imgSms = view.findViewById(R.id.img_sms);
        tvCancel = view.findViewById(R.id.tv_cancel);
        etInput = view.findViewById(R.id.et_input);
        tvSure = view.findViewById(R.id.tv_sure);
        ViewGroup.LayoutParams imgParams = imgSms.getLayoutParams();
        imgParams.width = Utils.dip2px(mContext, 100);
        imgParams.height = Utils.dip2px(mContext, 50);
        imgSms.setLayoutParams(imgParams);
        imgSms.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @OnClick({R.id.btn_get_sms, R.id.btn_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_sms:
                boolean phoneNumber = Utils.isPhoneNumber(etPhoneNumber.getText().toString().trim());
                if (phoneNumber) {
                    requestSms();
                } else {
                    Toast.makeText(mContext, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next:
                boolean phoneNumberCheck = Utils.isPhoneNumber(etPhoneNumber.getText().toString().trim());
                if (!phoneNumberCheck) {
                    Toast.makeText(mContext, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etSmsCode.getText().toString().trim().length()==0){
                    return;
                }
                requestCheck();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void requestCheck() {
        Call<ResponseBody> call = RequestUtils.checkForgetPasswordSms(mSession.getmToken(), etPhoneNumber.getText().toString().trim(), etSmsCode.getText().toString().trim(), integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status){
                    String code = object.optString("code");
                    Intent intent = new Intent(ForgetPayPasswordActivity.this,SettingPayPasswordActivity.class);
                    intent.putExtra("cardId",cardId);
                    intent.putExtra("code",code);
                    startActivity(intent);
                }else {
                    Utils.phpIsLogin(ForgetPayPasswordActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 验证码请求
     */
    private void requestSms() {
        Call<ResponseBody> call = RequestUtils.getPhotoSms(mSession.getmToken(), "find_card_pay_password", integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    initSmsPop(object.optString("code"));
                } else {
                    if (smsPopupWindow != null && smsPopupWindow.isShowing()) {
                        smsPopupWindow.dismiss();
                    }
                    Utils.phpIsLogin(ForgetPayPasswordActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {
                if (smsPopupWindow != null && smsPopupWindow.isShowing()) {
                    smsPopupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 设置获取验证码按钮
     */
    public void setSendCodeBtn(boolean clickable, String text, boolean enabled) {
        btnGetSms.setText(text);
        btnGetSms.setClickable(clickable);
        btnGetSms.setEnabled(enabled);
    }

    private void initSmsPop(String bitmap) {
        PictureHelper.setImageView(this, bitmap, imgSms, R.drawable.img_loading);
        etInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Utils.openKeyboard(etInput);
            }
        });
        imgSms.setOnClickListener(v -> {
            requestSms();
        });
        tvCancel.setOnClickListener(v -> smsPopupWindow.dismiss());
        tvSure.setOnClickListener(v -> {
            if (etInput.getText().toString().trim().equals("")) {
                Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            requestSmsCheck(etInput.getText().toString().trim());
            Utils.hideSoftInput(ForgetPayPasswordActivity.this);
            smsPopupWindow.dismiss();
        });
        int width = Utils.dip2px(mContext, 256);
        if (smsPopupWindow == null) {
            smsPopupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        smsPopupWindow.setFocusable(true);
        smsPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        smsPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (!smsPopupWindow.isShowing()) {
            smsPopupWindow.showAtLocation(findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }
        btnGetSms.setEnabled(true);
        Utils.darkenBackground(0.3f, this);

        smsPopupWindow.setOnDismissListener(() -> {
            etInput.getText().clear();
            Utils.hideSoftInput(ForgetPayPasswordActivity.this);
            Utils.darkenBackground(1f, ForgetPayPasswordActivity.this);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
    }

    private void requestSmsCheck(String trim) {
        Call<ResponseBody> call = RequestUtils.forgetPasswordSms(mSession.getmToken(), etPhoneNumber.getText().toString().trim(), trim, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    time.start();
                } else {
                    if (smsPopupWindow != null && smsPopupWindow.isShowing()) {
                        smsPopupWindow.dismiss();
                    }
                    setSendCodeBtn(true, getString(R.string.resend), true);
                    Utils.phpIsLogin(ForgetPayPasswordActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {
                setSendCodeBtn(true, getString(R.string.resend), true);
            }
        });
    }
}
