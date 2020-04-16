package com.zhjl.qihao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class HttpUtil {
	private HttpClient client;
	private HttpResponse response;
	private URI uri;
	private StatusLine ResponseState;
	public static String SESSIONID = null;

	public HttpUtil(String url) throws URISyntaxException {
		uri = new URI(url);
		client = new DefaultHttpClient();
	}

	public HttpUtil(String url, int timeOut) throws URISyntaxException {
		uri = new URI(url);
		initDefaultHttpClient(timeOut);
	}

	public String executeGet() {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpGet request = new HttpGet();
			request.setURI(uri);
			response = client.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public String executePost(Map<String, String> args) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			if (null != SESSIONID) {
				request.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			}
			request.setURI(uri);
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			Iterator<String> keys = args.keySet().iterator();
			String key;
			while (keys.hasNext()) {
				key = keys.next();
				postParameters.add(new BasicNameValuePair(key, args.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			request.setEntity(formEntity);
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			CookieStore mCookieStore = ((DefaultHttpClient) client)
					.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			if (cookies != null && cookies.size() > 0) {
				for (int i = 0; i < cookies.size(); i++) {
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						SESSIONID = cookies.get(i).getValue();
						break;
					}

				}
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String executePost(String json) {

		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			if (null != SESSIONID) {
				request.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			}
			request.setURI(uri);
			StringEntity formEntity = new StringEntity(json, HTTP.UTF_8);
			request.setEntity(formEntity);
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			CookieStore mCookieStore = ((DefaultHttpClient) client)
					.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			if (cookies != null && cookies.size() > 0) {
				for (int i = 0; i < cookies.size(); i++) {
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						SESSIONID = cookies.get(i).getValue();
						break;
					}

				}
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String executePost(File file) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();

			request.setURI(uri);
			MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
			ContentBody cbFile = new FileBody(file);
			mpEntity.addPart("uploadify", cbFile); // <input type="file"
													// name="userfile" /> 对应的
			StringBody sb=new StringBody("3.0");
			mpEntity.addPart("versionTag",sb);

			request.setEntity(mpEntity);
			Log.i("111111", mpEntity+"");
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}

	public String executePostMorePic(List<File> file) {
		String result = null;
		Log.i("111155555", file + "");
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();

			request.setURI(uri);
			MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
			for (int i = 0; i < file.size(); i++) {
				ContentBody cbFile = new FileBody(file.get(i));
				mpEntity.addPart("fileArray", cbFile);
			}
			StringBody sb=new StringBody("3.0");
			mpEntity.addPart("versionTag",sb);
			request.setEntity(mpEntity);
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			Log.i("11111", result+"");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	public String executeShopPostMorePic(List<File> file) {
		String result = null;
		Log.i("111155555", file + "");
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();

			request.setURI(uri);
			MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
			for (int i = 0; i < file.size(); i++) {
				ContentBody cbFile = new FileBody(file.get(i));
				mpEntity.addPart("uploadify"+(i+1), cbFile);
			}
			StringBody sb=new StringBody("3.0");
			mpEntity.addPart("versionTag",sb);
			request.setEntity(mpEntity);
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public String executeJsonPost(String json) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			if (null != SESSIONID) {
				request.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			}
			request.setURI(uri);
			try {
				StringEntity se = new StringEntity(json);
				se.setContentType("text/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				request.setEntity(se);
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			CookieStore mCookieStore = ((DefaultHttpClient) client)
					.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			if (cookies != null && cookies.size() > 0) {
				for (int i = 0; i < cookies.size(); i++) {
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						SESSIONID = cookies.get(i).getValue();
						break;
					}

				}
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @description 商城音频文件上传
	 * @version 1.0
	 * @author 黄南榆
	 * @date 2014年12月11日 
	 * @param num
	 * @param //json
	 * @param file
	 * @return
	 * @return String
	 * @throws
	 */
	public String executeShopAudioPost(String num, File file) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			if (null != SESSIONID) {
				request.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			}
			try {
				request.setURI(uri);
				MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
				FileBody body = new FileBody(file, "audio/mp3");
				mpEntity.addPart("file", body);
				mpEntity.addPart("HAS_UPLOADED_NUM", new StringBody(num));
				StringBody sb=new StringBody("3.0");
				mpEntity.addPart("versionTag",sb);
				request.setEntity(mpEntity);
				Log.i("111111", mpEntity+"");
				response = client.execute(request);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			CookieStore mCookieStore = ((DefaultHttpClient) client)
					.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			if (cookies != null && cookies.size() > 0) {
				for (int i = 0; i < cookies.size(); i++) {
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						SESSIONID = cookies.get(i).getValue();
						break;
					}
				}
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @description 商城图片文件上传
	 * @version 1.0
	 * @param num
	 * @param json
	 * @param file
	 * @return
	 * @return String
	 * @throws
	 */
	public String executeShopImagePost(String num, File file) {
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			if (null != SESSIONID) {
				request.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			}
			try {
				request.setURI(uri);
				MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
				FileBody body = new FileBody(file, "image/jpeg");
				mpEntity.addPart("file", body);
				mpEntity.addPart("HAS_UPLOADED_NUM", new StringBody(num));
				StringBody sb=new StringBody("3.0");
				mpEntity.addPart("versionTag",sb);
				request.setEntity(mpEntity);
				response = client.execute(request);
				Log.i("111111", mpEntity+"");
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("HttpUtil", "HttpUtil:" + e.toString());
			}
			CookieStore mCookieStore = ((DefaultHttpClient) client)
					.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			if (cookies != null && cookies.size() > 0) {
				for (int i = 0; i < cookies.size(); i++) {
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						SESSIONID = cookies.get(i).getValue();
						break;
					}
				}
			}
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			byte[]  by=strBuffer.toString().getBytes();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("HttpUtil", "IOException:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	
	public static final int ERROR_LOG_UPLOAD_SUCCESS = 88888;
	public static final int ERROR_LOG_UPLOAD_FAIL = 55555;
	/**
	 * 
	 * @description 错误日志上传
	 * @version 1.0
	 * @throws
	 */
	public String uploadErrorLog(Map<String, String> errorLogMap, File file) {
		
		String result = null;
		BufferedReader reader = null;
		try {
			HttpPost request = new HttpPost();
			request.setURI(uri);
			MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
			FileBody body = new FileBody(file);
			mpEntity.addPart("fileurl", body);
			for (Entry<String, String> entry : errorLogMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				mpEntity.addPart(key, new StringBody(value));
			}
			request.setEntity(mpEntity);
			response = client.execute(request);
			ResponseState = response.getStatusLine();
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			JSONObject jsonObject  = new JSONObject(result);
			if(jsonObject!=null && "0".equals(jsonObject.optString("result"))){
				Log.e("HttpUtil", "错误日志上传成功");
				String path = BaseCrashHandler.getLogDirPath() + File.separator + BaseCrashHandler.LOG_NAME;
				File mFile = new File(path);
				File pFile = mFile.getParentFile();
				if (!pFile.exists()){
					pFile.mkdirs();
				}
				mFile.delete();
			}else{
				Log.e("HttpUtil", "错误日志上传失败");
			}
		} catch (IllegalStateException e) {
			Log.e("HttpUtil", "错误日志上传失败");
			Log.e("HttpUtil", "IllegalStateException:" + e.toString());
		} catch (IOException e) {
			Log.e("HttpUtil", "错误日志上传失败");
			Log.e("HttpUtil", "IOException:" + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("HttpUtil", "HttpUtil:" + e.toString());
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public StatusLine getResponseState() {
		return ResponseState;
	}

	public void setResponseState(StatusLine responseState) {
		ResponseState = responseState;
	}

	private void initDefaultHttpClient(int timeout) {
		HttpParams params = new BasicHttpParams();
		/* 设置一些基本参数 */
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUseExpectContinue(params, true);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 2000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, timeout);
		client = new DefaultHttpClient(params);
	}

}
