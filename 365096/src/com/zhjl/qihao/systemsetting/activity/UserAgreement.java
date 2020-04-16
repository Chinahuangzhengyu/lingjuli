package com.zhjl.qihao.systemsetting.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.HeaderBar;
/***
 * 
 * @description 用户协议
 * @author south
 *
 */
public class UserAgreement extends VolleyBaseActivity  {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_agreement);

		initView();

	}

	private void initView() {
		HeaderBar.createCommomBack(this, "用户协议", getString(R.string.back), this);

//		WebView web_useragreement = (WebView) findViewById(R.id.web_useragreement);
//		WebSettings webSettings = web_useragreement.getSettings();
//		web_useragreement.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webSettings.setJavaScriptEnabled(true);
//		web_useragreement.getSettings().setBlockNetworkImage(false);
//		web_useragreement.loadUrl("file:///android_asset/phonehtml/agreement.html");
	}

}
