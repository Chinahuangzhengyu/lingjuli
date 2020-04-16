package com.zhjl.qihao.function.web;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.base.BaseWebFragment;
import com.zhjl.qihao.view.LoadingAlertDialog;

/**
 * @author 黄南榆
 * @function 
 * @date 2014-11-15
 */
public class NearbyWebChromeClient extends WebChromeClient{  
//	private AlertDialog alertDialog;
	private LoadingAlertDialog alertDialog;
	private BaseWebFragment bwf;
	private Fragment fragment;
//	private CustomProgressDialog customProgressDialog;
	private static final String errorHtmlString = "file:///android_asset/localerror/error.html";
	private static final String timeoutHtmlString = "file:///android_asset/localerror/timeout_error.html";
	private Activity activity;
	//private String mainUrlString;
	public NearbyWebChromeClient(Fragment fragment) {
		this.fragment = fragment;		
	}
	
	public NearbyWebChromeClient(BaseWebFragment bwf, LoadingAlertDialog alertDialog, Activity activity) {
		this.activity = activity;
		this.bwf = bwf;
		this.alertDialog = alertDialog;
		//this.mainUrlString = bwf.commualJsInterface.homeUrl;
	}
	
	@Override
	public boolean onJsConfirm(android.webkit.WebView view, java.lang.String url, java.lang.String message, android.webkit.JsResult result){
	Log.e("NearbyWebChromeClient", "-------------onJsConfirm");
		return false;
		
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, final JsPromptResult result) {
		Log.e("NearbyWebChromeClient", "-------------onJsPrompt");
		String [] Values=    defaultValue.split(",");
		List<CharSequence> listvalues=new ArrayList<CharSequence>();
		for (String value:Values){
			if (value!=null&&!"".equals(value.trim())){
				listvalues.add(value);
			}
		}
		listvalues.toArray(Values);
		Builder alter=	new AlertDialog.Builder(view.getContext()).setTitle(message).setItems(Values
				, new DialogInterface.OnClickListener () {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
						result.confirm(String.valueOf(which));
						
					}
				}).setNegativeButton(
				R.string.button_cancel, new DialogInterface.OnClickListener () {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
						result.cancel();
						
					}
				});
		alter.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				
				result.cancel();
			 }
		    });
				alter.show();
		
	 return true;	
	}
	
	@Override
	public void onProgressChanged(WebView view, int progress) {
		
		//确保当前加载的url不是错误的html或者是超时的html界面
		if(bwf.isLoadHomeUrl){
			if(null != alertDialog){
				
				//bwf.isStopWebChromeClient控制当超时加载时不会同时出现超时界面和进度框
		        if((progress<100) && (bwf.isStopWebChromeClient == false)){  
		        	if (null == alertDialog) {
						alertDialog = new LoadingAlertDialog(activity);
						alertDialog.show();
					}else{
						alertDialog.show();
					}
		        	
		        }else if((progress == 100) && (bwf.isStopWebChromeClient == false)){
		        	alertDialog.dismiss();
		        	Log.i("NearbyWebChromeClient", "ProgressDialog hideLoding");
		        	//TODO  web加载完毕
		        	bwf.wvReload = false;
		        	
		        	
		        }
			}
		}
        super.onProgressChanged(view, progress);  
	}
} 
