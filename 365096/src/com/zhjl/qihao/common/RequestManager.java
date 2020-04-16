package com.zhjl.qihao.common;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zhjl.qihao.abcommon.RequestListener;
import com.zhjl.qihao.util.JSONObjectUtil;
import com.zhjl.qihao.util.SsX509TrustManager;
import com.zhjl.qihao.volley.VolleyRequestManager;
public class RequestManager {

	/**
	 * 返回String
	 * 
	 * @param url
	 *            连接
	 * @param tag
	 *            上下文
	 * @param listener
	 *            回调
	 */
	public static void get(String url, Object tag,int action, RequestListener<JSONObject> listener) {
		SsX509TrustManager.allowAllSSL();
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), responseListener(listener,action), responseError(listener,action));
		VolleyRequestManager.addRequest(request, tag);
	}
	
	/**
	 * 返回String
	 * 
	 * @param url
	 *            接口
	 * @param tag
	 *            上下文
	 * @param //params
	 *            post需要传的参数
	 * @param listener
	 *            回调
	 */
	public static void post(String url, Object tag, JSONObjectUtil json,
			int action,RequestListener<JSONObject> listener) {
		SsX509TrustManager.allowAllSSL();
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, json,responseListener(listener,action), responseError(listener,action));
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		VolleyRequestManager.addRequest(jsonObjectRequest, tag);
	}


	/*public static void post1(String url, Object tag, JSONObjectUtil json,
							int action,RequestListener<JSONArray> listener) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, json,responseListener(listener,action), responseError(listener,action));
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		VolleyRequestManager.addRequest(jsonObjectRequest, tag);
	}*/
	
	/**
	 * 成功消息监听 返回String
	 * 
	 * @param l
	 *            String 接口
	 * @param //flag
	 *            true 带进度条 flase不带进度条
	 * @param //p
	 *            进度条的对象
	 * @return
	 */
	protected static Response.Listener<JSONObject> responseListener(
			final RequestListener<JSONObject> l, final int action) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject data) {
				try {
					l.requestSuccess(data,action);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	protected static Response.Listener<JSONArray> responseListener1(
			final RequestListener<JSONArray> l, final int action) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray data) {
				try {
					l.requestSuccess(data,action);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}



	/**
	 * String 返回错误监听
	 * 
	 * @param l
	 *            String 接口
	 * @param //flag
	 *            true 带进度条 flase不带进度条
	 * @param //p
	 *            进度条的对象
	 * @return
	 */
	protected static Response.ErrorListener responseError(
			final RequestListener l,final int action) {
		return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError e) {
				l.requestError(e,action);
			}
		};
	}
}
