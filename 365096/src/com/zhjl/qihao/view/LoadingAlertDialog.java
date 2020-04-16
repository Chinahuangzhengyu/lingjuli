package com.zhjl.qihao.view;
import com.zhjl.qihao.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;


/*
���ؿ�
 */
public class LoadingAlertDialog extends Dialog {
	
	public LoadingAlertDialog(Context context, int theme) { 
		super(context, theme);
		// TODO Auto-generated constructor stub 
	}
	
	public LoadingAlertDialog(Context context) {
//		super(context, R.style.loadingdialog_style);
		
		super(context);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.layout_webview_loading);
		Window mWindow = getWindow();
		mWindow.setBackgroundDrawableResource(R.color.transparent);
		WindowManager.LayoutParams lp = mWindow.getAttributes(); 
		lp.dimAmount =0f;
//		lp.alpha = 0.3f;

	} 
	

}
