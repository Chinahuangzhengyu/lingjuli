package com.zhjl.qihao.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.propertyservicepay.activity.PaySuccessActivity;

import static com.androidquery.util.AQUtility.postDelayed;

public class WXPayEntryActivity extends VolleyBaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "== WXPayEntryActivity";

    public static final String WEIXIN_PAY_RESULT = "com.zhjl.qihao.weixin.result";

    private IWXAPI api;

    private int errCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFlag(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    //回调方法
    @Override
    public void onResp(BaseResp baseResp) {
        errCode = baseResp.errCode;
        postDelayed(run, 1000);


        Log.e(TAG, " 微信支付回调: " + errCode);

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                Intent intent = new Intent(WXPayEntryActivity.WEIXIN_PAY_RESULT);
                intent.putExtra("code", errCode);
                sendBroadcast(intent);
                finish();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(WXPayEntryActivity.WEIXIN_PAY_RESULT);
            intent.putExtra("code", errCode);
            sendBroadcast(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
