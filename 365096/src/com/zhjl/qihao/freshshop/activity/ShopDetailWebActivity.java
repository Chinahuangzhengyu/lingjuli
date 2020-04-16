package com.zhjl.qihao.freshshop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;

public class ShopDetailWebActivity extends VolleyBaseActivity {
    @BindView(R.id.web_shop_detail)
    WebView webShopDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail_web);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,getIntent().getStringExtra("name"),this);
        String webContent = getIntent().getStringExtra("webContent");
        WebSettings webSettings = webShopDetail.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**关闭webview中缓存**/
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBlockNetworkImage(false);
        webShopDetail.loadUrl(webContent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && webShopDetail.canGoBack()) {
            webShopDetail.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
