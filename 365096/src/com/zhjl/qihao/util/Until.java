package com.zhjl.qihao.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.MediaColumns;
import android.provider.Settings;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zhjl.qihao.R;
import com.zhjl.qihao.volley.VolleyRequestManager;

public class Until {
	public static final String DATETIME = "yyyyMMddHHmmss";


	/**
	 * 用来判断服务是否运行.
	 *  
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	public static String getSDCardPath(Context pContext) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/.cloudcity";
			File PathDir = new File(path);
			if (!PathDir.exists()) {
				PathDir.mkdirs();
			}
			return path;
		} else {
			return pContext.getCacheDir().getAbsolutePath();
		}
	}

	public static String getVersion(Context mContext) {
		PackageManager packageManager = mContext.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(mContext.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String version = packInfo.versionName;
		if (!TextUtils.isEmpty(version))
			return version;
		else
			return "1.0";
	}

	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static boolean tologin(Context pContext) {
		PreferencesHelper vHelper = new PreferencesHelper(pContext);
		// if (vHelper.getValue("f_id", "").equals("")) {
		// Toast.makeText(pContext, "请先登录", Toast.LENGTH_SHORT).show();
		// pContext.startActivity(new Intent(pContext, LoginActivity.class));
		// return false;
		// }
		return true;
	}

	/**
	 * 获取系统时间
	 * 
	 * @param dateFormatStr
	 *            日期格式字符串 如： yyyy-MM-dd
	 * @return 系统时间字符串
	 */
	public static String getSysDate(String dateFormatStr) {
		String systemDate = "";
		if (dateFormatStr != null) {
			Calendar date = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			systemDate = dateFormat.format(date.getTime()); // 系统时间
		}
		return systemDate;
	}

	public static byte[] BitmaptoBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
		return baos.toByteArray();
	}

	public static String getSex(String sex) {
		if (sex.equals("1")) {
			return "女";
		} else if (sex.equals("0")) {
			return "男";
		} else {
			return sex;
		}
	}

	/**
	 * 
	 * @description 加载网络图片的方法
	 * @version 1.0
	 * @author 黄南榆
	 * @date 2014年8月19日 
	 * @param context
	 * @param imageview
	 * @param url
	 * @return void
	 * @throws
	 */
	public static void loadnetworkimage(Context context,
			final ImageView imageview, String url) {
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						imageview.setImageBitmap(response);
					}
				}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						imageview.setImageResource(R.drawable.no_picture);
					}
				});
		VolleyRequestManager.addRequest(imageRequest, context);
	}

	public static String getHunyin(String hunyin) {
		if (hunyin.equals("0")) {
			return "未婚";
		} else if (hunyin.equals("1")) {
			return "已婚";
		} else {
			return hunyin;
		}
	}

	public static String getDate(String date) {
		long time1 = Long.valueOf(date);
		long time2 = Calendar.getInstance().getTimeInMillis() / 1000;
		long time = time2 - time1;
		int i = (int) (time / (60 * 60 * 24));
		Date vDate = new Date(time1 * 1000);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // 打印年份
		String t = timeFormat.format(vDate);
		if (i == 0) {
			return "今天 " + t;
		}
		if (i == 1) {
			return "昨天 " + t;
		}
		if (i == 2) {
			return "前天 " + t;
		}
		if (i > 2) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm"); // 打印年份
			return dateFormat.format(vDate);
		}
		return "-";
	}

	public static void Log(Object pLog) {
		Log.i("sssss", pLog + "");
	}

	public static boolean isGpsEnable(Context pContext) {
		LocationManager locationManager = ((LocationManager) pContext
				.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static void OpenGpsSetting(Context pContext) {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			pContext.startActivity(intent);
		} catch (ActivityNotFoundException ex) {
			intent.setAction(Settings.ACTION_SETTINGS);
			try {
				pContext.startActivity(intent);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 设置弹�?�?
	 */
	public static void ShowDialog(Activity pContext, String pMsg,
			String pTitle, String btnOk, String btnCancel,
			final ICallback pCallback) {
		AlertDialog.Builder builder = new Builder(pContext);
		builder.setMessage(pMsg);

		builder.setTitle(pTitle);

		builder.setPositiveButton(btnOk, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pCallback.onFlishOnclik();
			}
		});

		builder.setNegativeButton(btnCancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	public static Builder GetDialog(Activity pContext, String pMsg,
			String pTitle, String btnOk, String btnCancel,
			final ICallback pCallback) {
		AlertDialog.Builder builder = new Builder(pContext);
		builder.setMessage(pMsg);

		builder.setTitle(pTitle);

		builder.setPositiveButton(btnOk, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pCallback.onFlishOnclik();
			}
		});

		builder.setNegativeButton(btnCancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create();
		return builder;
	}

	/**
	 * 设置弹�?�?
	 */
	public static void ShowDialog(Activity pContext, String pMsg,
			String pTitle, String btnOk, String btnCancel,
			final ICallback pCallback, final ICallback pCallbackCancle) {
		AlertDialog.Builder builder = new Builder(pContext);
		builder.setMessage(pMsg);

		builder.setTitle(pTitle);

		builder.setPositiveButton(btnOk, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pCallback.onFlishOnclik();
			}
		});

		builder.setNegativeButton(btnCancel, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pCallbackCancle.onFlishOnclik();
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	public static void writeFileSdcard(Context pContext, String fileName,
			String message) {
		try {
			File vFile = new File(Config.getSDCardPath(pContext));
			if (!vFile.exists()) {
				vFile.mkdir();
			}
			FileOutputStream fout = new FileOutputStream(
					Config.getSDCardPath(pContext) + "/" + fileName);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFileSdcard(Context pContext, String fileName) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(
					Config.getSDCardPath(pContext) + "/" + fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return res;

	}

	public static String Getdistance(String pLat, String pLng, String pMap) {
		if (TextUtils.isEmpty(pMap) || TextUtils.isEmpty(pLat) || TextUtils.isEmpty(pLng)) {
			return "未知";
		}

		String[] distance = pMap.split(",");
		double lng = Double.valueOf(distance[0]);
		double lat = Double.valueOf(distance[1]);
		double dis = GetDistance(lat, lng, Double.valueOf(pLat),
				Double.valueOf(pLng));
		return String.format("%.2f", dis) + "km";
	}

	private static double GetDistance(double lat1, double lon1, double lat2,
			double lon2) {
		double R = 6371;
		double distance = 0.0;
		double dLat = (lat2 - lat1) * Math.PI / 180;
		double dLon = (lon2 - lon1) * Math.PI / 180;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1 * Math.PI / 180)
				* Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R;
		return distance;
	}

	/**
	 * 根据url获取Bitmap对象
	 * 
	 * @param uri
	 * @return
	 */
	public static Bitmap getBitmapByUrl(Context pContext, String uri) {

		URLConnection url;
		Bitmap bitmap = null;
		String newuri;
		String iconFile = downloadFile(pContext, uri);

		if (iconFile == null)
			return null;

		if (iconFile.length() > 0) {
			newuri = Uri.fromFile(
					new File(Config.getSDCardPath(pContext) + "/" + iconFile))
					.toString();
		} else {
			newuri = uri;
		}
		try {
			url = new URL(newuri).openConnection();
			InputStream picStream = url.getInputStream();
			bitmap = BitmapFactory.decodeStream(picStream);
		} catch (MalformedURLException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @return
	 */
	public static String downloadFile(Context pContext, String url) {

		if (url == null || url == "")
			return null;

		String filename = url.substring(url.lastIndexOf("/") + 1);

		try {
			File downDir = new File(Config.getSDCardPath(pContext));
			downDir.mkdirs();

			File tryFile = new File(downDir, filename);
			if (tryFile.exists()) {
				if (tryFile.length() != 0) {
					return filename;
				} else {
					tryFile.delete();
				}
			}

			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();

			if (fileSize <= 0)
				throw new RuntimeException("");

			File outputFile = new File(downDir, filename);
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(outputFile);

			if (is == null)
				throw new RuntimeException("stream is null");

			byte buf[] = new byte[1024];

			do {
				int numread = is.read(buf);
				if (numread == -1) {
					break;
				}
				fos.write(buf, 0, numread);

			} while (true);

		} catch (Exception ex) {
			// ex.printStackTrace();
			return "";

		}
		return filename;
	}

	@SuppressLint({ "NewApi", "InlinedApi" })
	public static Bitmap getxtsldraw(Context c, String file) {
		File f = new File(file);
		Bitmap drawable = null;
		if (f.length() / 1024 < 100) {
			drawable = BitmapFactory.decodeFile(file);
		} else {
			Cursor cursor = c.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					new String[] { BaseColumns._ID },
					MediaColumns.DATA + " like ?", new String[] { "%" + file },
					null);
			if (cursor == null || cursor.getCount() == 0) {
				drawable = getbitmap(file, 720 * 1280);
			} else {
				if (cursor.moveToFirst()) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inPurgeable = true;
					options.inInputShareable = true;
					options.inPreferredConfig = Bitmap.Config.RGB_565;
					String videoId = cursor.getString(cursor
							.getColumnIndex(BaseColumns._ID));
					long videoIdLong = Long.parseLong(videoId);
					Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
							c.getContentResolver(), videoIdLong,
							Thumbnails.MINI_KIND, options);
					return bitmap;
				} else {
					// drawable = BitmapFactory.decodeResource(c.getResources(),
					// R.drawable.ic_doctor);
				}
			}
		}
		int degree = 0;
		ExifInterface exifInterface;
		try {
			exifInterface = new android.media.ExifInterface(file);

			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
			if (degree != 0 && drawable != null) {
				Matrix m = new Matrix();
				m.setRotate(degree, (float) drawable.getWidth() / 2,
						(float) drawable.getHeight() / 2);
				drawable = Bitmap.createBitmap(drawable, 0, 0,
						drawable.getWidth(), drawable.getHeight(), m, true);
			}
		} catch (java.lang.OutOfMemoryError e) {
			// Toast.makeText(c, "牌照出错，请重新牌照", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}

	public static Bitmap getbitmap(String imageFile, int length) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(imageFile, opts);
		int ins = computeSampleSize(opts, -1, length);
		opts.inSampleSize = ins;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inJustDecodeBounds = false;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(imageFile, opts);
			return bmp;
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static String creatfile(Context pContext, Bitmap bm, String filename) {
		if (bm == null) {
			return "";
		}
		File f = new File(Config.getSDCardPath(pContext) + "/" + filename
				+ ".jpg");
		try {
			FileOutputStream out = new FileOutputStream(f);
			if (bm.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();
	}

	public static void DownAPK(Context context, String url, Handler handler,
			String info_id) {

		int down_counter = 0;
		File outputFile = null;
		try {
			String filename = info_id + ".amr";
			File downDir = new File(Config.getSDCardPath(context)
					+ "/RecordFiles/");
			downDir.mkdirs();

			File tryFile = new File(downDir, filename);
			if (tryFile.exists()) {
				handler.sendEmptyMessage(-3);
			}
			if (downing.contains(url)) {
				handler.sendEmptyMessage(-2);
			} else {
				downing.add(url);
			}
			outputFile = new File(downDir, filename);
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(outputFile);

			// 获取文件名
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();// 根据响应获取文件大小
			if (fileSize <= 0)
				throw new RuntimeException("无法获知文件大小 ");

			if (is == null)
				throw new RuntimeException("stream is null");

			// 把数据存入路径+文件名
			byte buf[] = new byte[1024];

			int downLoadFileSize = 0;

			do {
				// 循环读取
				int numread = is.read(buf);
				if (numread == -1) {
					break;
				}

				fos.write(buf, 0, numread);
				downLoadFileSize += numread;
				int percent = (int) ((double) downLoadFileSize
						/ (double) fileSize * 100);

				down_counter++;
				if (down_counter % 50 == 0) {
					Message msg = new Message();
					msg.arg1 = percent;
					msg.what = 1;
					msg.obj = url;
					handler.sendMessage(msg);
				}
			} while (true);

			Message msg = new Message();
			msg.what = 0;
			msg.obj = url;
			handler.sendMessage(msg);
		} catch (Exception ex) {
			outputFile.delete();
			Log.i("sssss", "", ex);
			if (handler != null) {
				Message msg = new Message();
				msg.arg1 = -1;
				msg.obj = url;
				handler.sendMessage(msg);
			}
		}
		downing.remove(url);
	}

	public static List<String> downing = new ArrayList<String>();

	public static Bitmap drawstr(Context mContext, String title) {
		Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.daojia_cpt);
		int width = photo.getWidth();
		int height = photo.getHeight();
		Bitmap imgTemp = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(imgTemp);
		Paint paint = new Paint(); // 建立画笔
		paint.setDither(true);
		paint.setFilterBitmap(true);
		Rect src = new Rect(0, 0, width, height);
		Rect dst = new Rect(0, 0, width, height);
		// drawNinepath(canvas, photo, src);
		canvas.drawBitmap(photo, src, dst, paint);

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		float density = dm.density;

		TextPaint textPaint = new TextPaint();
		textPaint.setARGB(0xFF, 0xFF, 0, 0);
		textPaint.setTextSize(density * 20);
		textPaint.setTypeface(Typeface.DEFAULT); // 采用默认的宽度
		textPaint.setColor(Color.BLACK);
		StaticLayout layout = new StaticLayout(title, textPaint, width - 10,
				Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
		canvas.translate(10, height / 3 - 5);
		layout.draw(canvas);
		// canvas.drawText(title, 10, height / 2, textPaint);
		// canvas.save(Canvas.ALL_SAVE_FLAG);
		// canvas.restore();
		return imgTemp;

	}

	public static void drawNinepath(Canvas c, Bitmap bmp, Rect r1) {
		NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
		patch.draw(c, r1);
	}

	public static Bitmap getViewBitmap(View view) {
		int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(spec, spec);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.translate(-view.getScrollX(), -view.getScrollY());
		view.draw(c);
		view.setDrawingCacheEnabled(true);
		Bitmap cacheBmp = view.getDrawingCache();
		Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
		view.destroyDrawingCache();

		return viewBmp;
	}

	public static String getzc(String type) {
		if (type.equals("1")) {
			return "主任医师";
		} else if (type.equals("2")) {
			return "副主任医师";
		} else if (type.equals("3")) {
			return "主治医师";
		} else {
			return "医师";
		}
	}

	public static String getzffs(String type) {
		if (type.equals("1")) {
			return "小额支付";
		} else if (type.equals("2")) {
			return "现场支付";
		} else if (type.equals("10")) {
			return "支付宝支付";
		} else if (type.equals("11")) {
			return "财付通支付";
		} else if (type.equals("12")) {
			return "银联支付";
		} else {
			return "现场支付";
		}
	}

	public static String getUNIT_LEVEL(String UNIT_LEVEL) {
		String vUNIT_LEVEL = "未知等级";
		if (TextUtils.isEmpty(UNIT_LEVEL)) {
			return vUNIT_LEVEL;
		}
		switch (UNIT_LEVELList.valueOf(UNIT_LEVEL)) {
		case A:
			vUNIT_LEVEL = "特等医院";
			break;
		case B:
			vUNIT_LEVEL = "三级甲等";
			break;
		case C:
			vUNIT_LEVEL = "三级乙等";
			break;
		case D:
			vUNIT_LEVEL = "三级丙等";
			break;
		case E:
			vUNIT_LEVEL = "二级甲等";
			break;
		case F:
			vUNIT_LEVEL = "二级乙等";
			break;
		case G:
			vUNIT_LEVEL = "二等丙等";
			break;
		case H:
			vUNIT_LEVEL = "一级甲等";
			break;
		case I:
			vUNIT_LEVEL = "一级乙等";
			break;
		case J:
			vUNIT_LEVEL = "一级丙等";
			break;
		case K:
			vUNIT_LEVEL = "未知等级";
			break;
		case L:
			vUNIT_LEVEL = "三级医院";
			break;
		case M:
			vUNIT_LEVEL = "二级医院";
			break;
		default:
			vUNIT_LEVEL = "未知等级";
			break;
		}
		return vUNIT_LEVEL;
	}

	public static enum UNIT_LEVELList {
		A, B, C, D, E, F, G, H, I, J, K, L, M
	}
	
	
	/***
	 * 排序
	 */
	
}
