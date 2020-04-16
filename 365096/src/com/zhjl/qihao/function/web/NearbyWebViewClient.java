package com.zhjl.qihao.function.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhjl.qihao.base.BaseWebFragment;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.WebviewErrorCode;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.view.LoadingAlertDialog;

/**
 * @author 黄南榆
 * @function
 * @date 2014-11-15
 */
public class NearbyWebViewClient extends WebViewClient {
	// private AlertDialog alertDialog;
	private LoadingAlertDialog alertDialog;
	private ZHJLApplication va;
	private BaseWebFragment bwf;
	private Handler progressDialogHandler;
	public static int HANDLER_FINISH = 0x01;
	public static int HANDLER_STARDED = 0x02;
	private String tokent;
	private Timer timer;
	private Activity activity;

	public NearbyWebViewClient(Handler handler) {
		this.progressDialogHandler = handler;
	}

	public NearbyWebViewClient(BaseWebFragment baseWebFragment, Activity activity) {
		this.bwf = baseWebFragment;
		this.activity = activity;
		this.progressDialogHandler = bwf.progressDialogHandler;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if (null == alertDialog) {
			alertDialog = new LoadingAlertDialog(activity);
			alertDialog.show();
		} else {
			alertDialog.show();
		}
		String reurl = url;
		super.onPageStarted(view, reurl, favicon);

	}

	@Override
	public void onPageFinished(WebView view, String url) {

		Log.i("NearbyWebViewClient", "PageFinished url-------" + url);
		Log.i("NearbyWebViewClient", "onPageFinished bwf.isLoadHomeUrl-------"
				+ bwf.isLoadHomeUrl);
		if (null != alertDialog) {
			alertDialog.dismiss();
		}
		if (bwf.isLoadHomeUrl) {
			bwf.isLoadHomeUrl = false;
			settokent();
			if (!url.contains("?")) {
				if (!url.contains("token")) {
					url = url + "?token=" + tokent;
				}
			} else {
				if (!url.contains("token")) {
					url = url + "&token=" + tokent;
				}
			}
			if (!url.contains("touchstyle/404.png")
					&& !url.startsWith("file:///")) {
				// getRespStatus(url);
			}

			if (null != timer) {
				timer.cancel();
				timer.purge();
			}
		}
		if (url.contains("localerror/error.html")) {
			bwf.wv.stopLoading();

			Message msg = progressDialogHandler.obtainMessage();
			msg.arg1 = WebviewErrorCode.timeoutError;
			msg.sendToTarget();

		}
		Message msg = progressDialogHandler.obtainMessage();
		msg.arg1 = NearbyWebViewClient.HANDLER_FINISH;
		msg.sendToTarget();
		bwf.wv.loadUrl("javascript:delHear()");
		super.onPageFinished(view, url);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (url.contains("tel:")) {// 页面上有数字会导致连接电话
			StringBuffer sbf = new StringBuffer();
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(sbf
					.append("tel://")
					.append(url.replace("tel:", "").replaceAll("/", ""))
					.toString()));
			bwf.getActivity().startActivity(intent);
			return true;
		} else if (url.startsWith("http://www.kotihome.cn")
				&& !url.contains("&u_user=") && !url.contains("?u_user=")
				&& !url.contains("&u_password=")
				&& !url.contains("&smallUnitCode=")
				&& !url.contains("&houseNo=")
				&& !url.contains("&version=versionnew")
				&& !url.contains("&edition=sdkalipay")) {
			String reurl = "";
			if (url.contains("?")) {
				Tools tools = new Tools(bwf.getActivity(),
						Constants.NEARBYSETTING);
				String user = tools.getValue(Constants.REGISTERMOBILE2);
				StringBuffer cache = new StringBuffer();
				String pwd = Md5(cache.append("user")
						.append(tools.getValue(Constants.REGISTERMOBILE2))
						.append("koti.cn").toString());
				StringBuffer sbf = new StringBuffer();
				reurl = sbf.append(url).append("&u_user=").append(user)
						.append("&u_password=").append(pwd)
						.append("&smallUnitCode=")
						.append(tools.getValue(Constants.SMALLCOMMUNITYCODE))
						.append("&houseNo=")
						.append(tools.getValue(Constants.NEW_ROOM_CODE))
						.append("&version=versionnew")
						.append("&edition=sdkalipay").toString();
			} else {
				Tools tools = new Tools(bwf.getActivity(),
						Constants.NEARBYSETTING);
				String user = tools.getValue(Constants.REGISTERMOBILE2);
				StringBuffer cache = new StringBuffer();
				String pwd = Md5(cache.append("user")
						.append(tools.getValue(Constants.REGISTERMOBILE2))
						.append("koti.cn").toString());
				StringBuffer sbf = new StringBuffer();
				reurl = sbf.append(url).append("?u_user=").append(user)
						.append("&u_password=").append(pwd)
						.append("&smallUnitCode=")
						.append(tools.getValue(Constants.SMALLCOMMUNITYCODE))
						.append("&houseNo=")
						.append(tools.getValue(Constants.NEW_ROOM_CODE))
						.append("&version=versionnew")
						.append("&edition=sdkalipay").toString();
			}
			Log.d("NearbyWebViewClient", "OverrideUrlLoading url+++++++++++"
					+ url + "--");
			Log.i("NearbyWebViewClient", "url----------" + reurl);
			return super.shouldOverrideUrlLoading(view, reurl);
		} else {
			String reurl = url;
			Log.d("NearbyWebViewClient", "OverrideUrlLoading url+++++++++++"
					+ url + "--");
			Log.i("NearbyWebViewClient", "url----------" + reurl);
			return super.shouldOverrideUrlLoading(view, reurl);
		}
		// super.onLoadResource(view, url);
	}

	/**
	 * MD5加密算法
	 */
	private String Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String md5code = buf.toString();
			if (md5code.length() > 16) {

				return md5code.substring(0, md5code.length() - 16);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {

		// super.onReceivedError(view, errorCode, description, failingUrl);
		// 替换404界面
		Log.i("NearbyWebviewClient", "error-------" + errorCode + "----url"
				+ failingUrl);

		if (bwf.isLoadHomeUrl) {
			Message msg = progressDialogHandler.obtainMessage();
			msg.arg1 = WebviewErrorCode.networkError;
			msg.sendToTarget();

			if (null != timer) {
				timer.cancel();
				timer.purge();
			}
		}
	}

	private boolean getRespStatu(final String url) {

		int status = -1;
		try {
			HttpHead head = new HttpHead(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse resp = client.execute(head);
			status = resp.getStatusLine().getStatusCode();
			Log.i("NearbyWebViewClient", "respon code:--------------" + status);

			if (status >= 400) {
				return false;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

	private void getRespStatus(final String url) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				int status = -1;
				try {
					HttpHead head = new HttpHead(url);
					HttpClient client = new DefaultHttpClient();
					HttpResponse resp = client.execute(head);
					status = resp.getStatusLine().getStatusCode();
					Log.i("NearbyWebViewClient", "respon code:--------------"
							+ status);
					if (status >= 400) {
						Message msg = progressDialogHandler.obtainMessage();
						msg.arg1 = WebviewErrorCode.networkError;
						msg.sendToTarget();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}).start();
	}

	public ZHJLApplication getVa() {
		return va;
	}

	public void setVa(ZHJLApplication va) {
		this.va = va;
	}

	public void getmetho(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void settokent() {
		if (tokent == null) {
			Tools tools = new Tools(va, Constants.NEARBYSETTING);
			tokent = tools.getValue(Constants.KEY_TONE);

		}
	}

}
