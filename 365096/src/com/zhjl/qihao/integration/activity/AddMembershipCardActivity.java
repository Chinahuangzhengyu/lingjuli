package com.zhjl.qihao.integration.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class AddMembershipCardActivity extends VolleyBaseActivity {
    @BindView(R.id.et_membership_card_number)
    EditText etMembershipCardNumber;
    @BindView(R.id.et_membership_card_password)
    EditText etMembershipCardPassword;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.btn_get_sms)
    Button btnGetSms;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.et_card_pay_password)
    EditText etCardPayPassword;
    private boolean isAllInput = false;
    private PopupWindow smsPopupWindow;
    private TimeCount time;
    private ImageView imgSms;
    private TextView tvCancel;
    private EditText etInput;
    private TextView tvSure;
    private View view;
    public static final int ADD_CARD_RESULT_CODE = 0x113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership_card);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "添加会员卡", this);
        isInputFinish(etPhoneNumber, etSms, etMembershipCardNumber, etMembershipCardPassword, etCardPayPassword);
        initView();
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

    /**
     * 是否输入完成
     *
     * @param texts
     */
    private void isInputFinish(final EditText... texts) {
        for (int i = 0; i < texts.length; i++) {
            texts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for (int j = 0; j < texts.length; j++) {
                        if (texts[j].getText().toString().trim().length() > 0) {
                            isAllInput = true;
                        } else {
                            isAllInput = false;
                            break;
                        }
                    }
                    if (isAllInput) {
                        btnRegister.setEnabled(true);
                        btnRegister.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_gradient_green_22));
                    } else {
                        btnRegister.setEnabled(false);
                        btnRegister.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_d8d8d8_22));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    /**
     * 设置获取验证码按钮
     */
    public void setSendCodeBtn(boolean clickable, String text, boolean enabled) {
        btnGetSms.setText(text);
        btnGetSms.setClickable(clickable);
        btnGetSms.setEnabled(enabled);
    }

    @OnClick({R.id.btn_get_sms, R.id.btn_register})
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
            case R.id.btn_register:
                requestBuilding();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 验证码请求
     */
    private void requestSms() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.getPhotoSms(mSession.getmToken(), "bind_card", integralInterface);
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
                    Utils.phpIsLogin(AddMembershipCardActivity.this, object.optInt("type"), object.optString("message"));
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
            Utils.hideSoftInput(AddMembershipCardActivity.this);
            Utils.darkenBackground(1f, AddMembershipCardActivity.this);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
    }

    private void requestSmsCheck(String trim) {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.getSms(mSession.getmToken(), etPhoneNumber.getText().toString().trim(), trim, integralInterface);
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
                    Utils.phpIsLogin(AddMembershipCardActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {
                setSendCodeBtn(true, getString(R.string.resend), true);
            }
        });
    }

    private void requestBuilding() {
        String membershipCardNumber = etMembershipCardNumber.getText().toString().trim();
        String membershipCardPassword = etMembershipCardPassword.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String cardPayPassword = etCardPayPassword.getText().toString().trim();
        String sms = etSms.getText().toString().trim();
        if (!Utils.isPhoneNumber(etPhoneNumber.getText().toString().trim())) {
            Toast.makeText(mContext, "手机号码错误！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(membershipCardNumber)) {
            Toast.makeText(mContext, "请填写会员卡号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(membershipCardPassword)) {
            Toast.makeText(mContext, "请填写会员卡号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(mContext, "请填写手机号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(cardPayPassword)) {
            Toast.makeText(mContext, "请填写线上支付密码！", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(sms)) {
            Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        btnRegister.setEnabled(false);
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.membershipCardBuilding(mSession.getmToken(), membershipCardNumber, membershipCardPassword, phoneNumber, cardPayPassword, sms, integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                if (object.optBoolean("status")) {
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    setResult(ADD_CARD_RESULT_CODE, getIntent());
                    finish();
                } else {
                    Utils.phpIsLogin(AddMembershipCardActivity.this, object.optInt("type"), object.optString("message"));
                }
                btnRegister.setEnabled(true);
            }

            @Override
            public void fail() {
                btnRegister.setEnabled(true);
            }
        });
    }
}
