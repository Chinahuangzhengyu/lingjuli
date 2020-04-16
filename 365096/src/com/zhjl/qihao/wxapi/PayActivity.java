package com.zhjl.qihao.wxapi;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.util.JSONObjectUtil;
import com.zhjl.qihao.abutil.ToastUtils;

public class PayActivity extends Activity {

	private static final String TAG = "MicroMsg.SDKSample.PayActivity";

	/**
	 * 微信公众平台商户模块和商户约定的密钥
	 * 
	 * 注意：不能hardcode在客户端，建议genPackage这个过程由服务器端完成
	 */
	/**
	 * 微信开放平台和商户约定的密钥
	 * 
	 * 注意：不能hardcode在客户端，建议genSign这个过程由服务器端完成
	 */
	private static final String APP_SECRET = Constants.APP_SECRET; 

	/**
	 * 微信开放平台和商户约定的支付密钥
	 * 
	 * 注意：不能hardcode在客户端，建议genSign这个过程由服务器端完成
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);

		Button payBtn = (Button) findViewById(R.id.pay_btn);
		payBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new GetAccessTokenTask().execute();
			}
		});

		Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
		checkPayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进入页面先检查微信版本是否支持微支付
				
			}
		});
	}

	

	private String genPackage(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");

		// 进行md5摘要前，params内容为原始内容，未经过url encode处理
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();

		return URLEncodedUtils.format(params, "utf-8") + "&sign=" + packageSign;
	}



	private class GetAccessTokenTask extends
			AsyncTask<Void, Void, GetAccessTokenResult> {

		private ProgressDialog dialog;

		// 第一步
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(PayActivity.this, "提示",
					"正在获取acesstoken");
		}

		// 该类第三步方法
		@Override
		protected void onPostExecute(GetAccessTokenResult result) {
			if (dialog != null) {
				dialog.dismiss();
			}

			if (result.localRetCode == LocalRetCode.ERR_OK) {
				Toast.makeText(PayActivity.this,
						"获取acesstoken", Toast.LENGTH_LONG)
						.show();

				GetPrepayIdTask getPrepayId = new GetPrepayIdTask(
						result.accessToken);
				getPrepayId.execute();
			} else {
				ToastUtils.showToast(PayActivity.this, "支付失败");
//				Toast.makeText(
//						PayActivity.this,
//						getString(R.string.get_access_token_fail,result.localRetCode.name()), Toast.LENGTH_LONG)
//						.show();
			}
		}

		// 该类第2步方法获取AcessToken
		@Override
		protected GetAccessTokenResult doInBackground(Void... params) {
			GetAccessTokenResult result = new GetAccessTokenResult();

			String url = String
					.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
							Constants.APP_ID_WX, APP_SECRET);

			byte[] buf = Util.httpGet(url);
			if (buf == null || buf.length == 0) {
				result.localRetCode = LocalRetCode.ERR_HTTP;
				return result;
			}

			String content = new String(buf);
			result.parseFrom(content);
			return result;
		}
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, GetPrepayIdResult> {

		private ProgressDialog dialog;
		private String accessToken;

		public GetPrepayIdTask(String accessToken) {
			this.accessToken = accessToken;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(PayActivity.this,
					"提示",
					getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(GetPrepayIdResult result) {
			if (dialog != null) {
				dialog.dismiss();
			}

			if (result.localRetCode == LocalRetCode.ERR_OK) {
//				Toast.makeText(PayActivity.this, R.string.get_prepayid_succ,
//						Toast.LENGTH_LONG).show();
				sendPayReq(result);
			} else {
				ToastUtils.showToast(PayActivity.this, "支付失败");
//				Toast.makeText(
//						PayActivity.this,
//						getString(R.string.get_prepayid_fail,
//								result.localRetCode.name()), Toast.LENGTH_LONG)
//						.show();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected GetPrepayIdResult doInBackground(Void... params) {

			String url = String.format(
					"https://api.weixin.qq.com/pay/genprepay?access_token=%s",
					accessToken);
			String entity = genProductArgs();


			GetPrepayIdResult result = new GetPrepayIdResult();

			byte[] buf = Util.httpPost(url, entity);
			if (buf == null || buf.length == 0) {
				result.localRetCode = LocalRetCode.ERR_HTTP;
				return result;
			}

			String content = new String(buf);
			result.parseFrom(content);
			Log.i("111111111", result + "");
			return result;

		}
	}

	private static enum LocalRetCode {
		ERR_OK, ERR_HTTP, ERR_JSON, ERR_OTHER
	}

	private static class GetAccessTokenResult {

		private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetAccessTokenResult";

		public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
		public String accessToken;
		public int expiresIn;
		public int errCode;
		public String errMsg;

		public void parseFrom(String content) {

			if (content == null || content.length() <= 0) {
				localRetCode = LocalRetCode.ERR_JSON;
				return;
			}

			try {
				JSONObject json = new JSONObject(content);
				if (json.has("access_token")) { // success case
					accessToken = json.getString("access_token");
					expiresIn = json.getInt("expires_in");
					localRetCode = LocalRetCode.ERR_OK;
				} else {
					errCode = json.getInt("errcode");
					errMsg = json.getString("errmsg");
					localRetCode = LocalRetCode.ERR_JSON;
				}

			} catch (Exception e) {
				localRetCode = LocalRetCode.ERR_JSON;
			}
		}
	}

	private static class GetPrepayIdResult {

		private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetPrepayIdResult";

		public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
		public String prepayId;
		public int errCode;
		public String errMsg;

		public void parseFrom(String content) {

			if (content == null || content.length() <= 0) {
				localRetCode = LocalRetCode.ERR_JSON;
				return;
			}

			try {
				JSONObject json = new JSONObject(content);
				if (json.has("prepayid")) { // success case
					prepayId = json.getString("prepayid");
					localRetCode = LocalRetCode.ERR_OK;
				} else {
					localRetCode = LocalRetCode.ERR_JSON;
				}

				errCode = json.getInt("errcode");
				errMsg = json.getString("errmsg");

			} catch (Exception e) {
				localRetCode = LocalRetCode.ERR_JSON;
			}
		}
	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 建议 traceid 字段包含用户信息及订单信息，方便后续对订单状态的查询和跟踪
	 */
	private String getTraceId() {
		return "crestxu_" + genTimeStamp();
	}

	/**
	 * 注意：商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一
	 */
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long timeStamp;
	private String nonceStr, packageValue;

	private String genSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (; i < params.size() - 1; i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append(params.get(i).getName());
		sb.append('=');
		sb.append(params.get(i).getValue());

		String sha1 = Util.sha1(sb.toString());
		return sha1;
	}

	private String genProductArgs() {
		JSONObjectUtil json = new JSONObjectUtil();

		try {
			json.put("appid", Constants.APP_ID_WX);
			String traceId = getTraceId(); // traceId
											// 由开发者自定义，可用于订单的查询与跟踪，建议根据支付用户信息生成此id
			json.put("traceid", traceId);
			nonceStr = genNonceStr();
			json.put("noncestr", nonceStr);

			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("bank_type", "WX"));
			packageParams.add(new BasicNameValuePair("body", "千足金箍棒"));
			packageParams.add(new BasicNameValuePair("fee_type", "1"));
			packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://weixin.qq.com"));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					genOutTradNo()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"196.168.1.1"));
			packageParams.add(new BasicNameValuePair("total_fee", "1"));
			packageValue = genPackage(packageParams);
			json.put("package", packageValue);
			timeStamp = genTimeStamp();
			json.put("timestamp", timeStamp);

			List<NameValuePair> signParams = new LinkedList<NameValuePair>();
			signParams.add(new BasicNameValuePair("appid", Constants.APP_ID_WX));
			signParams.add(new BasicNameValuePair("noncestr", nonceStr));
			signParams.add(new BasicNameValuePair("package", packageValue));
			signParams.add(new BasicNameValuePair("timestamp", String
					.valueOf(timeStamp)));
			signParams.add(new BasicNameValuePair("traceid", traceId));
			json.put("app_signature", genSign(signParams));

			json.put("sign_method", "sha1");
		} catch (Exception e) {
			return null;
		}

		return json.toString();
	}

	private void sendPayReq(GetPrepayIdResult result) {

	}
}
