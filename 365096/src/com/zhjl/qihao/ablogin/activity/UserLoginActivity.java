package com.zhjl.qihao.ablogin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.api.LoginInterface;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abutil.TimeCount;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.chooseCity.activty.ChoosePropertyActivity;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
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

/**
 * @Description:用户登录
 */
public class
UserLoginActivity extends VolleyBaseActivity {

    public PopupHandler myHandler = new PopupHandler(this);
    @BindView(R.id.tv_login_status)
    TextView tvLoginStatus;
    @BindView(R.id.rb_phone_login)
    RadioButton rbPhoneLogin;
    @BindView(R.id.rb_pwd_login)
    RadioButton rbPwdLogin;
    @BindView(R.id.rg_login_status)
    RadioGroup rgLoginStatus;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_tourist_login)
    TextView tvTouristLogin;
    @BindView(R.id.img_off_eyes)
    ImageView imgOffEyes;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.btn_get_sms)
    Button btnGetSms;
    private boolean isOff = false;
    private PopupWindow smsPopupWindow;
    private TimeCount time;
    private boolean isLoginType = true;
    private View view;
    private ImageView imgSms;
    private TextView tvCancel;
    private EditText etInput;
    private TextView tvSure;

    @OnClick({R.id.tv_login_status, R.id.btn_get_sms, R.id.img_off_eyes, R.id.tv_forget_password, R.id.btn_login, R.id.tv_tourist_login})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_login_status:
                intent.setClass(mContext, UserRegisterActivity.class);
                intent.putExtra("isRegister", true);
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
            case R.id.img_off_eyes:
                isOff = !isOff;
                if (isOff) {
                    imgOffEyes.setImageResource(R.drawable.img_on_eyes);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().trim().length());
                } else {
                    imgOffEyes.setImageResource(R.drawable.img_off_eyes);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().toString().trim().length());
                }
                break;
            case R.id.tv_forget_password:
                intent.setClass(mContext, UserRegisterActivity.class);
                intent.putExtra("isRegister", false);
                startActivity(intent);
                break;
            case R.id.btn_login:
                if (isLoginType && etPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请输入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isLoginType && etPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
            case R.id.tv_tourist_login:
                intent.setClass(mContext, ChoosePropertyActivity.class);
                intent.putExtra("Tourist", "tourist");
                startActivity(intent);
                break;
        }
    }

    private void initSmsPop(byte[] drawable) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(drawable, 0, drawable.length);
        imgSms.setImageBitmap(bitmap);
        imgSms.setOnClickListener(v -> {
            requestPicCheck();
        });
        etInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Utils.openKeyboard(etInput);
            }
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
            smsPopupWindow.showAtLocation(llLogin, Gravity.CENTER, 0, 0);
        }
        btnGetSms.setEnabled(true);
        Utils.darkenBackground(0.3f, this);

        smsPopupWindow.setOnDismissListener(() -> {
            etInput.getText().clear();
            Utils.hideSoftInput(UserLoginActivity.this);
            Utils.darkenBackground(1f, UserLoginActivity.this);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
    }

    private static class PopupHandler extends Handler {
        private UserLoginActivity activity;
        private WeakReference<UserLoginActivity> weakReference;

        public PopupHandler(UserLoginActivity activity) {
            weakReference = new WeakReference<UserLoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            activity = weakReference.get();
            switch (msg.what) {
                case 0:


                    break;
            }
        }
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        ButterKnife.bind(this);
//        initShadow();
        // 构造CountDownTimer对象
        time = new TimeCount(60000, 1000, getString(R.string.send_verificationCode), "%d秒", btnGetSms);
        initViewPop();
        rgLoginStatus.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                RadioButton rb = (RadioButton) group.getChildAt(i);
                if (rb.getId() == checkedId) {
                    rb.setTextColor(Color.parseColor("#1F1F1F"));
                    rb.setTextSize(18);
                    rb.setTypeface(Typeface.DEFAULT_BOLD);
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.circle_green);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    rb.setCompoundDrawables(null, null, null, drawable);
                    String string = rb.getText().toString();
//                    etPhoneNumber.getText().clear();
                    etPassword.getText().clear();
                    if (string.equals("手机号登录")) {
                        isLoginType = true;
                        etPassword.setHint("请输入验证码");
                        etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        btnGetSms.setVisibility(View.VISIBLE);
                        imgOffEyes.setVisibility(View.GONE);
                        tvForgetPassword.setVisibility(View.GONE);
                        btnLogin.setText("注册/登录");
                    } else {
                        isLoginType = false;
                        etPassword.setHint("请输入密码");
                        etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        btnGetSms.setVisibility(View.GONE);
                        imgOffEyes.setVisibility(View.VISIBLE);
                        tvForgetPassword.setVisibility(View.VISIBLE);
                        btnLogin.setText("登录");
                    }
                } else {
                    rb.setTextColor(Color.parseColor("#999999"));
                    rb.setTextSize(16);
                    rb.setTypeface(Typeface.DEFAULT);
                    rb.setCompoundDrawables(null, null, null, null);
                }
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.hideInput(UserLoginActivity.this);
                    return true;
                }
                return false;
            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPassword.getText().toString().length() > 0 && etPhoneNumber.getText().toString().length() > 0) {
                    btnLogin.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_gradient_green_22));
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_d8d8d8_22));
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPassword.getText().toString().length() > 0 && etPhoneNumber.getText().toString().length() > 0) {
                    btnLogin.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_gradient_green_22));
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_d8d8d8_22));
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewPop() {
        view = View.inflate(mContext, R.layout.sms, null);
        imgSms = view.findViewById(R.id.img_sms);
        tvCancel = view.findViewById(R.id.tv_cancel);
        etInput = view.findViewById(R.id.et_input);
        tvSure = view.findViewById(R.id.tv_sure);
    }

//    private void initShadow() {
//        ShadowDrawable.setShadowDrawable(btnLogin,Color.parseColor("#D8D8D8"), Utils.dip2px(mContext,22),
//                Color.parseColor("#E3E3E3"),  Utils.dip2px(mContext,6), Utils.dip2px(mContext,3), Utils.dip2px(mContext,10));
//    }

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
                            ToastUtils.showToast(UserLoginActivity.this, json.getString(Constants.MESSAGE));
                            time.start();// 开始计时
                        } else {
                            ToastUtils.showToast(mContext, json.getString(Constants.MESSAGE));
                            setSendCodeBtn(true, getString(R.string.resend), true);
                        }
                    }
                } catch (Exception e) {
                    setSendCodeBtn(true, getString(R.string.resend), true);
                    Toast.makeText(UserLoginActivity.this, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                setSendCodeBtn(true, getString(R.string.resend), true);
                Toast.makeText(UserLoginActivity.this, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
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


    /**
     * 进行登陆
     */
    public void login() {
        showprocessdialog(false);
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        HashMap<String, Object> map = new HashMap();
        map.put("mobile", etPhoneNumber.getText().toString().trim());
        if (isLoginType) {
            map.put("loginType", 2);
            map.put("code", etPassword.getText().toString().trim());
        } else {
            map.put("loginType", 1);
            map.put("password", etPassword.getText().toString().trim());
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> login = loginInterface.getLogin(body);
        activityRequestData(login, LoginBean.class, new RequestResult<LoginBean>() {
            @Override
            public void success(LoginBean result, String message) {
                dismissdialog();
                btnLogin.setEnabled(true);
                if (result.getCode() == 200) {
                    Intent intent = new Intent();
                    mSession.setNewBasic(result);
                    if (result.getData().getUserInfo().isSelectModel()) {
                        intent.setClass(UserLoginActivity.this, RefactorMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (result.getData().getUserInfo().getUserRooms().size() > 1) {
                            intent.setClass(mContext, ChooseSmallCity.class);
                            intent.putParcelableArrayListExtra("rooms", result.getData().getUserInfo().getUserRooms());
                        } else {
                            intent.setClass(mContext, RegisterBindingActivity.class);
                            intent.putExtra("isRegister", true);
                        }
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void fail() {
                dismissdialog();
                btnLogin.setEnabled(true);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ZHJLApplication.getInstance().exitAPP();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
