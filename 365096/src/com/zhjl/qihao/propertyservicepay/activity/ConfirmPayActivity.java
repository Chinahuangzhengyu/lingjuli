package com.zhjl.qihao.propertyservicepay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.pay.MobileSecurePayer;
import com.zhjl.qihao.pay.PayResult;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.view.TimeTextView;
import com.zhjl.qihao.wxapi.WXPayEntryActivity;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ConfirmPayActivity extends VolleyBaseActivity {
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.rg_pay_type)
    RadioGroup rgPayType;
    @BindView(R.id.rb_zfb)
    RadioButton rbZfb;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.img_white_back)
    ImageView imgWhiteBack;
    @BindView(R.id.tv_time_pay)
    TimeTextView tvTimePay;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.tv_pay_name)
    TextView tvPayName;
    private int type = 2;
    private String orderSn;
    private static final int ZFB_PAY = 1;
    private static final int WX_PAY = 12;
    private final MyHandler myHandler = new MyHandler(this);
    IWXAPI api;


    private static class MyHandler extends Handler {
        private final WeakReference<ConfirmPayActivity> mActivity;
        private ConfirmPayActivity instance;

        public MyHandler(ConfirmPayActivity activity) {
            mActivity = new WeakReference<ConfirmPayActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            instance = mActivity.get();
            switch (msg.what) {
                case ZFB_PAY:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {//下单成功
                        Toast.makeText(instance, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(instance, PaySuccessActivity.class);
                        intent.putExtra("payType", "支付宝支付");
                        intent.putExtra("price", instance.totalAmount);
                        intent.putExtra("from", "物业");
                        instance.startActivity(intent);
                        instance.finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(instance, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
//                            WizardAPI.sendOrderId(ConfirmPayActivity.this, orderSn, ConfirmPayActivity.this);
                            Toast.makeText(instance, "支付失败", Toast.LENGTH_SHORT).show();
//						PayDemoActivity.this.finish();
                        }
                    }
                    break;
                case WX_PAY:
                    int code = (int) msg.obj;
                    if (code == 0) {
                        Intent intent = new Intent();
                        intent.setClass(instance, PaySuccessActivity.class);
                        intent.putExtra("payType", "微信支付");
                        intent.putExtra("price", instance.totalAmount);
                        intent.putExtra("from", "物业");
                        instance.startActivity(intent);
                    } else {
                        Toast.makeText(instance, "支付失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private double totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        registerBroadcast();
        //微信注册
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WX);
        api.registerApp(Constants.APP_ID_WX);

        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        orderSn = getIntent().getStringExtra("orderSn");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0);
        boolean isCarPay = getIntent().getBooleanExtra("isCarPay", false);
        if (isCarPay){
            tvPayName.setText("停车费");
        }
        long inValid = getIntent().getLongExtra("inValid", 0);
        Calendar calendar = Calendar.getInstance();
        long time = inValid - calendar.getTime().getTime();
        Date date = new Date(time);
        tvTimePay.setTime(date);
        tvPayPrice.setText("共需支付¥" + totalAmount);
        btnPay.setOnClickListener(this);
        imgWhiteBack.setOnClickListener(this);
        rgPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zfb:
                        type = 1;
                        break;
                    case R.id.rb_wx:
                        type = 2;
                        break;
                }
            }
        });
        tvTimePay.setGetTimeChange(new TimeTextView.GETTimeChange() {
            @Override
            public void getTime() {
                Toast.makeText(mContext, "订单超时！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_white_back:
                finish();
                break;
            case R.id.btn_pay:
//                if (tvTimePay.getTime().equals("00:00")) {
//                    Toast.makeText(mContext, "订单已超时！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (type == 2) {
                    // 进入页面先检查微信版本是否支持微支付
                    boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                    if (!isPaySupported) {
                        ToastUtils.showToast(mContext, "您的微信版本不支持微信支付，请选择其他支付方式或更新您的微信版本");
                        return;
                    }
                }
                btnPay.setEnabled(false);
                orderPay();
                break;
        }
    }

    private void orderPay() {
        PropertyPayInterface payInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("orderSn", orderSn);
        map.put("type", type);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = payInterface.orderPay(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    JSONObject data = object.optJSONObject("data");
                    orderSn = data.optString("orderSn");
                    if (type == 1) {
                        String sign = data.optString("payUrl");
                        MobileSecurePayer msp = new MobileSecurePayer();
                        msp.pay(sign, myHandler, ZFB_PAY, ConfirmPayActivity.this);
                    } else {
                        JSONObject params = data.optJSONObject("params");
                        PayReq req = new PayReq();
                        req.appId = Constants.APP_ID_WX;
                        req.partnerId = params.optString("partnerid");
                        req.prepayId = params.optString("prepayid");
                        req.packageValue = params.optString("package");
                        req.nonceStr = params.optString("noncestr");
                        req.timeStamp = params.optString("timestamp");
                        req.sign = params.optString("sign");
                        api.sendReq(req);
                    }
                    btnPay.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                btnPay.setEnabled(true);
            }
        });
    }

    /**
     * 微信支付成功后发送的广播
     */
    MyBroadcastReciver myBroadcastReciver;

    public void registerBroadcast() {
        //生成广播处理
        myBroadcastReciver = new MyBroadcastReciver();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(WXPayEntryActivity.WEIXIN_PAY_RESULT);
        //注册广播
        this.registerReceiver(myBroadcastReciver, intentFilter);
    }


    //  接收 微信支付成功的广播
    private class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WXPayEntryActivity.WEIXIN_PAY_RESULT)) {
                int code = intent.getIntExtra("code", -3);
                Message message = myHandler.obtainMessage(code);
                message.what = WX_PAY;
                message.obj = code;
                myHandler.sendMessage(message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvTimePay.cancel();
        if (myBroadcastReciver != null) {
            this.unregisterReceiver(myBroadcastReciver);
        }
    }
}
