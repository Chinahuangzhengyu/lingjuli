package com.zhjl.qihao.freshshop.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.freshshop.adapter.CardListAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.fragment.PayPasswordFragment;
import com.zhjl.qihao.integration.activity.AddMembershipCardActivity;
import com.zhjl.qihao.integration.activity.ForgetPayPasswordActivity;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.bean.CardListBean;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.pay.MobileSecurePayer;
import com.zhjl.qihao.pay.PayResult;
import com.zhjl.qihao.propertyservicepay.activity.PaySuccessActivity;
import com.zhjl.qihao.propertyservicepay.view.TimeTextView;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.wxapi.WXPayEntryActivity;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.order.activity.OrderDetailActivity.RESULT_ORDER;

public class ShopPayActivity extends VolleyBaseActivity {
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.img_white_back)
    ImageView imgWhiteBack;
    @BindView(R.id.tv_shop_pay_name)
    TextView tvShopPayName;
    @BindView(R.id.tv_time_pay)
    TimeTextView tvTimePay;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.rb_zfb)
    RadioButton rbZfb;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.rg_pay_type)
    RadioGroup rgPayType;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.img_shop)
    ImageView imgShop;
    @BindView(R.id.gv_card)
    GridView gvCard;
    private String type = "wxpay";
    private long orderId;
    private static final int ZFB_PAY = 1;
    private static final int WX_PAY = 11;
    private final MyHandler myHandler = new MyHandler(this);
    IWXAPI api;
    private double currentMoney;
    private boolean formCar;
    public static final int RESULT_PAY = 0x118;
    private CardListAdapter cardListAdapter;
    private List<CardListBean> list = new ArrayList<>();
    private int balancePosition;
    private String payPassword = "";
    private ProgressDialog dialog;
    private PayPasswordFragment payPasswordFragment;

    private static class MyHandler extends Handler {
        private final WeakReference<ShopPayActivity> mActivity;
        private ShopPayActivity instance;

        public MyHandler(ShopPayActivity activity) {
            mActivity = new WeakReference<ShopPayActivity>(activity);
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

                        if (instance.orderId != 0) {
                            Toast.makeText(instance, "支付成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(instance, PaySuccessActivity.class);
                            intent.putExtra("payType", "支付宝支付");
                            intent.putExtra("price", instance.currentMoney);
                            intent.putExtra("formCar", instance.formCar);
                            intent.putExtra("orderId", instance.orderId);
                            intent.putExtra("from", "商城");
                            instance.startActivity(intent);
                            instance.setResult(RESULT_PAY, instance.getIntent());
                            instance.finish();

                        } else {
                            String result = payResult.result;
                            Toast.makeText(instance, "支付成功", Toast.LENGTH_SHORT).show();
//                            thirdPartyPaySuccess();
                        }


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
                        intent.putExtra("price", instance.currentMoney);
                        intent.putExtra("orderId", instance.orderId);
                        intent.putExtra("formCar", instance.formCar);
                        intent.putExtra("from", "商城");
                        instance.startActivity(intent);
                        instance.setResult(RESULT_PAY, instance.getIntent());
                        instance.finish();
                    } else {
                        Toast.makeText(instance, "支付失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_pay);
        ButterKnife.bind(this);
        //微信回调广播
        registerBroadcast();
        //微信注册
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WX);
        api.registerApp(Constants.APP_ID_WX);

        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        //设置订单时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 30, 0);
        tvTimePay.setTime(calendar.getTime());
        tvTimePay.setGetTimeChange(() -> {
            Toast.makeText(mContext, "订单超时！", Toast.LENGTH_SHORT).show();
            finish();
        });

        //得到确认订单页的值
        orderId = getIntent().getLongExtra("order_id", 0);
        String shopName = getIntent().getStringExtra("shopName");
        String shopImg = getIntent().getStringExtra("shopImg");
        formCar = getIntent().getBooleanExtra("formCar", false);
        currentMoney = getIntent().getDoubleExtra("currentMoney", 0.00);
        PictureHelper.setImageView(mContext, shopImg, imgShop, R.drawable.img_err);
        DecimalFormat df = new DecimalFormat("0.00");   //保留2位小数
        tvPayPrice.setText("共需支付¥" + df.format(currentMoney));
        tvShopPayName.setText(shopName);
        cardListAdapter = new CardListAdapter(mContext, list);
        gvCard.setAdapter(cardListAdapter);
        gvCard.setOnItemClickListener((parent, view, position, id) -> {
            rgPayType.clearCheck();
            cardListAdapter.check(position);
            type = "yue";
            balancePosition = position;
        });

        rgPayType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_zfb:
                    if (rbZfb.isChecked()) {
                        type = "alipay";
                        clearCardListCheck();
                    }
                    break;
                case R.id.rb_wx:
                    if (rbWx.isChecked()) {
                        type = "wxpay";
                        clearCardListCheck();
                    }
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCarList();
    }

    private void initCarList() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.membershipCardList(mSession.getmToken(), integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                list.clear();
                if (status) {
                    JSONArray cards = object.optJSONArray("cards");
                    for (int i = 0; i < cards.length(); i++) {
                        JSONObject cardObj = cards.optJSONObject(i);
                        CardListBean cardListBean = new CardListBean();
                        cardListBean.setCard_id(cardObj.optString("card_id"));
                        cardListBean.setMoney(cardObj.optString("money"));
                        list.add(cardListBean);
                    }
                    cardListAdapter.addData(list);
                } else {
                    Utils.phpIsLogin(ShopPayActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 清除选中状态
     */
    private void clearCardListCheck() {
        cardListAdapter.clearCheck();
    }

    @OnClick({R.id.img_white_back, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_white_back:
                finish();
                break;
            case R.id.btn_pay:
                if (type.equals("wxpay")) {
                    // 进入页面先检查微信版本是否支持微支付
                    boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                    if (!isPaySupported) {
                        ToastUtils.showToast(mContext, "您的微信版本不支持微信支付，请选择其他支付方式或更新您的微信版本");
                        return;
                    }
                }
                btnPay.setEnabled(false);
                if (type.equals("yue") && list.size() > 0) {
                    showPayPassword();
                } else {
                    requestPay();
                }
                break;
        }
    }

    /**
     * 输入支付密码
     *
     */
    private void showPayPassword() {
        payPasswordFragment = PayPasswordFragment.getInstance(currentMoney);
        payPasswordFragment.show(getSupportFragmentManager(), "3");
        payPasswordFragment.OnIsFinishClickListener((isFinish, password) -> {
            if (isFinish) {
                requestPay();
                payPassword = password;
                dialog = new ProgressDialog(mContext);
                dialog.setMessage("支付中。。。");
                dialog.show();
            }
        });
    }

    private void requestPay() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("order_id", orderId);
        map.put("payment_type", type);
        if (type.equals("yue")) {
            if (list.size() > 0) {
                map.put("card_id", list.get(balancePosition).getCard_id());
            }
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.orderPay(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws JSONException {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                btnPay.setEnabled(true);
                if (status) {
                    if (type.equals("alipay")) {
                        String keyId = object.optString("key_id");
                        MobileSecurePayer msp = new MobileSecurePayer();
                        msp.pay(keyId, myHandler, ZFB_PAY, ShopPayActivity.this);
                    } else if (type.equals("wxpay")) {
                        JSONObject keyId = object.optJSONObject("key_id");
                        PayReq req = new PayReq();
                        req.appId = Constants.APP_ID_WX;
                        req.partnerId = keyId.optString("partnerid");
                        req.prepayId = keyId.optString("prepayid");
                        req.packageValue = keyId.optString("package");
                        req.nonceStr = keyId.optString("noncestr");
                        req.timeStamp = keyId.optString("timestamp");
                        req.sign = keyId.optString("sign");
                        api.sendReq(req);
                    } else {
                        String keyId = object.optString("key_id");
                        requestBalancePay(keyId);
                    }
                } else {
                    if (type.equals("yue")){
                        dialog.dismiss();
                    }
                    int type = object.optInt("type");
                    if (type == 0) {
                        Toast.makeText(ShopPayActivity.this, object.optString("message"), Toast.LENGTH_SHORT).show();
                    } else if (type == 1) {
                        Utils.loginPopWindow(ShopPayActivity.this);
                    } else {
                        Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }

            @Override
            public void fail() {
                btnPay.setEnabled(true);
            }
        });

    }

    private void requestBalancePay(String keyId) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("key_id", keyId);
        map.put("pay_password", payPassword);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.balancePay(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                dialog.dismiss();
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                if (status) {
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(mContext, PaySuccessActivity.class);
                    intent.putExtra("payType", "余额支付");
                    intent.putExtra("price", currentMoney);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("formCar", formCar);
                    intent.putExtra("from", "商城");
                    startActivity(intent);
                    setResult(RESULT_PAY, getIntent());
                    finish();
                } else {
                    if (object.optInt("type") == 2) {
                        PopWindowUtils.getInstance().show("会员卡未绑定，是否绑定？", ShopPayActivity.this);
                        PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                            Intent intent = new Intent(ShopPayActivity.this, AddMembershipCardActivity.class);
                            startActivity(intent);
                        });
                    } else if (object.optInt("type") == 0) {
                        PopWindowUtils.getInstance().show("支付密码错误，请重试", ShopPayActivity.this);
                        TextView noView = PopWindowUtils.getInstance().getNoView();
                        TextView yesView = PopWindowUtils.getInstance().getYesView();
                        yesView.setText("重试");
                        yesView.setTypeface(Typeface.DEFAULT_BOLD);
                        noView.setText("忘记密码");
                        noView.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                        noView.setTypeface(Typeface.DEFAULT_BOLD);
                        PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                            if (list.size()>0){
                                showPayPassword();
                            }
                        });
                        PopWindowUtils.getInstance().setNoOnClickListener(() -> {
                            Intent intent = new Intent(ShopPayActivity.this, ForgetPayPasswordActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        Utils.phpIsLogin(ShopPayActivity.this, object.optInt("type"), object.optString("message"));
                    }
                }
            }

            @Override
            public void fail() {
                dialog.dismiss();
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
        setResult(RESULT_ORDER, getIntent());
    }
}
