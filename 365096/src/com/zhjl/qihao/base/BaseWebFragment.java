package com.zhjl.qihao.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.WebviewErrorCode;
import com.zhjl.qihao.abrefactor.RefactorMainActivity.reloadUrl;
import com.zhjl.qihao.function.web.NearbyWebViewClient;
import com.zhjl.qihao.service.SmsObserver;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.view.LoadingAlertDialog;

/**
 *
 * @description webview基类
 * @version 1.0
 * @author 黄南榆
 * @date 2014-10-29 
 */
public class BaseWebFragment extends Fragment implements reloadUrl {

	public static String PhoneDeviceId;

	public View root;
	public WebView wv;
	// 控制从其他fragment进入当前fragment时是否要重新加载webview
	public boolean wvReload;
	public boolean isStopWebChromeClient;
	public boolean isLoadHomeUrl;
	public static final int REQUEST_TAKE_PHOTO = 0;
	public static final int REQUEST_ADD_PHOTO = 1;
	public static final int REQUEST_SUCESS = 1;
	public static final int REQUEST_FAIL = 0;

	public static final int MESSAGE_GOTOREGEDIT = 11;

	// public CustomProgressDialog customProgressDialog;
//	public AlertDialog alertDialog;
	public LoadingAlertDialog alertDialog;
	SmsObserver smsObserver;
	public static final int REQUEST_PHONE_STATE = 55555;

	private Uri SMS_INBOX = Uri.parse("content://sms/");

	private callback callback;
	private TelephonyManager tm;
	private Tools tools;

	public BaseWebFragment() {
	}

	public void init() {
		// customProgressDialog = new CustomProgressDialog(getActivity());
		if (null == alertDialog) {
			alertDialog = new LoadingAlertDialog(getActivity());
		}
//		alertDialog = new AlertDialog.Builder(getActivity()).create();
//		View view = LayoutInflater.from(getActivity()).inflate(
//				R.layout.layout_webview_loading, null);
//		Window window = alertDialog.getWindow();
//		window.setBackgroundDrawableResource(android.R.color.transparent);
//		window.setContentView(view);
//		WindowManager.LayoutParams params = window.getAttributes();
//		params.dimAmount = 0f;
//		window.setAttributes(params);
		isStopWebChromeClient = false;
		isLoadHomeUrl = false;
	}

	@SuppressLint("HandlerLeak")
	public Handler progressDialogHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			if (msg.arg1 == NearbyWebViewClient.HANDLER_STARDED) {

				// customProgressDialog.showLoding(null, null);
				alertDialog.show();
				Log.i("BaseWebFragment", "ProgressDialog showLoading");

			} else if (msg.arg1 == NearbyWebViewClient.HANDLER_FINISH) {

				// customProgressDialog.hideLoding();
				alertDialog.dismiss();
				if (callback != null) {
					callback.webfinish();
				}
				Log.i("BaseWebFragment", "ProgressDialog hideLoding");
			}

			if (msg.arg1 == WebviewErrorCode.timeoutError) {

				// 加载超时设置重新加载以及显示超时界面
				wvReload = true;
				// 即时仍然在加载中也取消
				isStopWebChromeClient = true;

				wv.loadUrl("file:///android_asset/localerror/timeout_error.html");

				// if (customProgressDialog.isLodingShow()) {
				// customProgressDialog.hideLoding();
				// }
				if (null != alertDialog) {
					alertDialog.dismiss();
				}
				clearWebViewHistory();

				Log.i("BaseWebFragment",
						"ProgressDialog hideLoding because of timeout!");
			}

			if (msg.arg1 == WebviewErrorCode.networkError) {
				// webView.loadUrl("file:///android_asset/touchstyle/404.png");
				// 加载异常设置重新加载以及显示异常出错界面
				wvReload = true;

				wv.loadUrl("file:///android_asset/localerror/error.html");

				// if (customProgressDialog != null) {
				// customProgressDialog.hideLoding();
				// }
				if (null != alertDialog) {
					alertDialog.dismiss();
				}
				clearWebViewHistory();
			}

		}
	};

	public void showerrdialog(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	public void loginOut() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		tools.removeValue(Constants.NEW_ROOM_CODE);
		tools.removeValue(Constants.LOGINTYPE);
		tools.removeValue(Constants.PHONE);
		tools.removeValue("token");
		tools.removeValue(Constants.KEY_TONE);
		tools.removeValue(Constants.USERTYPE);
		tools.removeValue(Constants.REGISTERMOBILE);
		tools.removeValue(Constants.SMALLCOMMUNITYCODE);
	}

	public String gettokent() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		String tokent = tools.getValue(Constants.KEY_TONE);
		Log.i("Morepage", "chc------------gettokent---" + tokent);
		return tokent;
	}

	public String getseriNo() {
		checkdeviceid();
		Log.i("Morepage", "chc------------getphonedevice---" + PhoneDeviceId);
		return PhoneDeviceId;
	}

	public String getut() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		String ut = tools.getValue(Constants.USERTYPE);
		Log.i("Morepage", "chc------------getusertype---" + ut);
		return ut;
	}

	public String smallcommunitycode() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		String smallcommunitycode = tools
				.getValue(Constants.SMALLCOMMUNITYCODE);
		return smallcommunitycode;
	}

	public String getUserId() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		return tools.getValue(Constants.USERID);
	}

	public String registermobile() {
		Tools tools = new Tools(getActivity().getApplicationContext(),
				Constants.NEARBYSETTING);
		String registermobile = tools.getValue(Constants.REGISTERMOBILE);
		return registermobile;
	}

	public void checkdeviceid() {
		if (PhoneDeviceId == null) {
			tools = new Tools(getActivity(), Constants.NEARBYSETTING);
			PhoneDeviceId = tools.getValue(Constants.DEVICEID);
			if (PhoneDeviceId == null || "".equals(PhoneDeviceId)) {

				tm = (TelephonyManager) getActivity()
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					RequestPermissionUtils.requestFPermission(BaseWebFragment.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
				} else {
					PhoneDeviceId = tm.getDeviceId();
					tools.putValue(Constants.DEVICEID, PhoneDeviceId);
				}
			}
		}
	}

	public void freeWebViewMemory() {
		wv.clearCache(true);
		wv.freeMemory();
		wv.removeAllViews();
		wv.destroy();
	}

	public void clearWebViewHistory() {
		wv.clearHistory();
	}

	public interface callback {
		public void webfinish();
	}

	public void setcallback(callback callback) {
		this.callback = callback;
	}



	@Override
	public void reloadUrl() {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadLocalUrl(String url) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PHONE_STATE) {
			boolean isAllGranted = true;

			// 判断是否所有的权限都已经授予了
			for (int grant : grantResults) {
				if (grant != PackageManager.PERMISSION_GRANTED) {
					isAllGranted = false;
					break;
				}
			}
			for (int i = 0; i < grantResults.length; i++) {

				//判断权限的结果，如果点击不在提示
				if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

					if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								RequestPermissionUtils.showShortCut(getActivity(), "读取电话状态需要权限！", wv);
							}
						});
					}
				}
			}
			if (isAllGranted) {
				if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				PhoneDeviceId = tm.getDeviceId();
				tools.putValue(Constants.DEVICEID, PhoneDeviceId);
			} else {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), "读取电话状态需要权限！", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
}
