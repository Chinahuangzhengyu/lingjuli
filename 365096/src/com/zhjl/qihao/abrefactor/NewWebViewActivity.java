package com.zhjl.qihao.abrefactor;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NetWorkUtils;
import com.zhjl.qihao.abutil.NewStatusBarUtils;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/8
 * 类作用：wbeView页面（数据固定页面）
 */

public class NewWebViewActivity extends VolleyBaseActivity {

    private String webUrl;
    private String name;
    private WebView newWebView;
    private ImageView imgBack;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_web);
        initView();
        webUrl = getIntent().getStringExtra("webUrl");
        name = getIntent().getStringExtra("name");
//        StatusBarutil.StatusBarLightMode(this, StatusBarutil.StatusBarLightMode(this));
//        NewStatusBarUtils.setStatusBarColor(this,R.color.app_color);
        if (webUrl!=null&&!webUrl.equals("")&&name!=null&&!name.equals("")){
            tvName.setText(name);
            WebSettings settings = newWebView.getSettings();
            settings.setAppCacheEnabled(true); //启用应用缓存
            settings.setDomStorageEnabled(true); //启用或禁用DOM缓存。
            settings.setDatabaseEnabled(true); //启用或禁用DOM缓存。
            newWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
            newWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
            if (NetWorkUtils.isConnect(mContext)) { //判断是否联网
                settings.setCacheMode(WebSettings.LOAD_DEFAULT); //默认的缓存使用模式
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不从网络加载数据，只从缓存加载数据。
            }
            newWebView.loadUrl(webUrl);
            newWebView.getSettings().setJavaScriptEnabled(true);
            newWebView.setWebViewClient(new MyWebViewClient());
            showprocessdialog();
        }else {
            Toast.makeText(mContext, "加载出错！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.iv_back);
        imgBack.setOnClickListener(this);
        newWebView = (WebView) findViewById(R.id.new_web);
        tvName = (TextView) findViewById(R.id.tv_name);
//        ImageView ivShare= (ImageView) findViewById(R.id.iv_share);
//        ivShare.setVisibility(View.VISIBLE);
//        ivShare.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (newWebView!=null&&newWebView.canGoBack()) {
                newWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                if (newWebView!=null&&newWebView.canGoBack()){
                    newWebView.goBack();
                }else {
                    finish();
                }
                break;
            case R.id.iv_share:
//                showSharedDialog2(urlTitle, urlAddress, urlPhoto ,urlNote);
                break;
        }
    }
    private class MyWebViewClient extends WebViewClient{
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissdialog();
        }
    }
    @Override
    public void onBackPressed() {
        if (newWebView.canGoBack()){
            if(newWebView.getUrl().equals(webUrl)){
                super.onBackPressed();
            }else{
                newWebView.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newWebView != null) {
            newWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            newWebView.clearHistory();
            ((ViewGroup) newWebView.getParent()).removeView(newWebView);
            newWebView.destroy();
            newWebView = null;
        }
    }
}
