package com.zhjl.qihao.mymessage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.mymessage.api.MessageInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MessageDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.web_message_detail)
    WebView webMessageDetail;
    private String url;
    public static final int MESSAGE_RESULT = 0x115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "消息详情",  this);
        url = getIntent().getStringExtra("url");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            JSONObject object = null;
            try {
                object = new JSONObject(extras.getString(JPushInterface.EXTRA_EXTRA));
                String messageId = object.optString("messageId");
                String type = object.optString("type");
                if (!type.equals("1")) {
                    url = object.optString("url");
                    MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MessageInterface.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("messageId", messageId);
                    RequestBody body = ParamForNet.put(map);
                    Call<ResponseBody> call = messageInterface.messageAllRead(body);
                    activityRequestData(call, null, new RequestResult<Object>() {
                        @Override
                        public void success(Object result, String message) throws Exception {

                        }

                        @Override
                        public void fail() {

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initWebView();
    }

    private void initWebView() {
        //设置一些webView的属性
        webMessageDetail.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webMessageDetail.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webMessageDetail.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webMessageDetail.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webMessageDetail.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webMessageDetail.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webMessageDetail.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webMessageDetail.getSettings().setDomStorageEnabled(true);//DOM Storage
        webMessageDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webMessageDetail.loadUrl(url);
        webMessageDetail.setWebViewClient(new ExampleWebViewClient());//自定义WebViewClient
    }

    private class ExampleWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = getIntent();
                setResult(MESSAGE_RESULT,intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = getIntent();
            setResult(MESSAGE_RESULT,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
