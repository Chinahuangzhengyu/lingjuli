package com.zhjl.qihao.ablogin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.api.LoginInterface;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abutil.TimeCount;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public class UserRegisterActivity extends VolleyBaseActivity {
    @BindView(R.id.img_title)
    ImageView imgTitle;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.btn_get_sms)
    Button btnGetSms;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_is_login)
    TextView tvIsLogin;
    @BindView(R.id.ll_register_content)
    LinearLayout llRegisterContent;
    @BindView(R.id.tv_register_help)
    TextView tvRegisterHelp;
    private boolean isAgree = false;
    private boolean isAllInput = false;
    private boolean isRegister;
    private PopupWindow smsPopupWindow;
    private TimeCount time;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);
        isRegister = getIntent().getBooleanExtra("isRegister", false);
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        // 构造CountDownTimer对象
        time = new TimeCount(60000, 1000, getString(R.string.send_verificationCode), "%d秒", btnGetSms);
        if (isRegister) {
            NewHeaderBar.createCommomBack(this, "注册账号", this);
            llRegister.setVisibility(View.VISIBLE);
            imgTitle.setVisibility(View.VISIBLE);
            btnRegister.setText("注册");
            tvIsLogin.setVisibility(View.VISIBLE);
        } else {
            if (isUpdate) {
                NewHeaderBar.createCommomBack(this, "修改密码", this);
            } else {
                NewHeaderBar.createCommomBack(this, "忘记密码", this);
            }
            llRegister.setVisibility(View.GONE);
            imgTitle.setVisibility(View.GONE);
            btnRegister.setText("提交");
            tvIsLogin.setVisibility(View.GONE);
        }
        isInputFinish(etPhoneNumber, etSms, etPassword, etSurePassword);
        SpannableString string = new SpannableString(tvIsLogin.getText().toString());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#23AC38"));
        string.setSpan(foregroundColorSpan, 5, tvIsLogin.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvIsLogin.setText(string);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.hideInput(UserRegisterActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

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

    @OnClick({R.id.btn_get_sms, R.id.tv_agree, R.id.btn_register, R.id.iv_back, R.id.tv_is_login, R.id.tv_register_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_help:
                Intent intent = new Intent();
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("webContent", API_HOST + JAVA_PORT_NUMBER + "/static/protocol/user.html");
                intent.putExtra("name", "注册协议");
                startActivity(intent);
                break;
            case R.id.btn_get_sms:
                if (etPhoneNumber.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                btnGetSms.setEnabled(false);
                Utils.hideInput(this);
                requestPicCheck();
                break;
            case R.id.tv_agree:
                isAgree = !isAgree;
                if (isAgree) {
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.car_choose_click);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.car_choose);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                }
                break;
            case R.id.btn_register:
                if (isRegister) {
                    if (isAgree) {
                        requestRegister();
                    } else {
                        Toast.makeText(mContext, "请同意用户协议！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (etPassword.getText().toString().trim().equals(etSurePassword.getText().toString().trim())) {
                        requestPassword();
                    } else {
                        Toast.makeText(mContext, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_is_login:
                finish();
                break;
        }
    }

    private void requestPicCheck() {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhoneNumber.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.pictureCheck(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws JSONException {
                String string = (String) result;
                JSONObject parse = new JSONObject(string);
                JSONObject data = parse.getJSONObject("data");
                String image = data.getString("image");
                initSmsPop(Base64.decode(image, Base64.DEFAULT));
            }

            @Override
            public void fail() {
                btnGetSms.setEnabled(true);
            }
        });

    }

    private void initSmsPop(byte[] drawable) {
        View view = View.inflate(mContext, R.layout.sms, null);
        ImageView imgSms = view.findViewById(R.id.img_sms);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        final EditText etInput = view.findViewById(R.id.et_input);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        Bitmap bitmap = BitmapFactory.decodeByteArray(drawable, 0, drawable.length);
        imgSms.setImageBitmap(bitmap);
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Utils.openKeyboard(etInput);
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPopupWindow.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etInput.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestSmsCheck(etInput.getText().toString().trim());
                smsPopupWindow.dismiss();
            }
        });
        int width = Utils.dip2px(mContext, 256);
        int height = Utils.dip2px(mContext, 190);
        smsPopupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        smsPopupWindow.setFocusable(true);
        smsPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        smsPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        smsPopupWindow.showAtLocation(llRegisterContent, Gravity.CENTER, 0, 0);
        btnGetSms.setEnabled(true);
        Utils.darkenBackground(0.3f, this);

        smsPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, UserRegisterActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void requestSmsCheck(String code) {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("mobile", etPhoneNumber.getText().toString().trim());
        map.put("type", 2);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.getSmsCheck(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String string = response.body().string();
                        JSONObject json = new JSONObject(string);
                        String code = json.getString("code");
                        if (code.equals("200")) {
                            ToastUtils.showToast(mContext, json.getString(Constants.MESSAGE));
                            time.start();// 开始计时
                        } else {
                            ToastUtils.showToast(mContext, json.getString(Constants.MESSAGE));
                            setSendCodeBtn(true, getString(R.string.resend), true);
                        }
                    }
                } catch (Exception e) {
                    setSendCodeBtn(true, getString(R.string.resend), true);
                    Toast.makeText(mContext, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setSendCodeBtn(true, getString(R.string.resend), true);
                Toast.makeText(mContext, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
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

    private void requestPassword() {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhoneNumber.getText().toString().trim());
        map.put("password", etPassword.getText().toString().trim());
        map.put("token", etSms.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.updatePassword(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                btnRegister.setEnabled(true);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {
                btnRegister.setEnabled(true);
            }
        });
    }

    private void requestRegister() {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhoneNumber.getText().toString().trim());
        map.put("password", etPassword.getText().toString().trim());
        map.put("verifyCode", etSms.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.registerLogin(body);
        activityRequestData(call, LoginBean.class, new RequestResult<LoginBean>() {
            @Override
            public void success(LoginBean result, String message) {
                btnRegister.setEnabled(true);
                if (result.getCode() == 200) {
                    Intent intent = new Intent();
                    mSession.setNewBasic(result);
                    if (result.getData().getUserInfo().isSelectModel()) {
                        intent.setClass(mContext, RefactorMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        if (result.getData().getUserInfo().getUserRooms().size() > 0) {
//                            intent.setClass(mContext, ChooseSmallCity.class);
//                            intent.putParcelableArrayListExtra("rooms", result.getData().getUserInfo().getUserRooms());
//                        } else {
                            intent.setClass(mContext, RegisterBindingActivity.class);
                            intent.putExtra("isRegister", true);
//                        }
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void fail() {
                btnRegister.setEnabled(true);
            }
        });
    }
}
