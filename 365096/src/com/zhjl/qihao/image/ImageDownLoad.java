package com.zhjl.qihao.image;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.webkit.MimeTypeMap;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.update.PreferencesUtils;

/**
 * 
 * @description APK下载工具类
 * @version 1.0
 * @author 黄南榆
 * @date 2014-10-29 
 */
public class ImageDownLoad {

	public static final String DOWNLOAD_FOLDER_NAME = "零距离的图片";
	public static String DOWNLOAD_FILE_NAME = "wizard.apk";
	public static final String APK_DOWNLOAD_ID = "apkDownloadId";

	private Context context;
	private String url;
	private String notificationTitle;
	private String notificationDescription;

	private DownloadManager downloadManager;
	private CompleteReceiver completeReceiver;
	public static final String TAG = "MainActivity";
	private NotificationManager manager;
	private Notification notification;
	private AjaxCallBack<File> callBack;
	private RemoteViews contentView;
	private long progress;
	private Message msg;

	Handler handler = new Handler() {// 更改进度条的进度
		@Override
		public void handleMessage(Message msg) {
			contentView.setProgressBar(R.id.pb, 100, (int) progress, false);
			contentView.setTextViewText(R.id.tx_title, notificationTitle
					+ "\u3000\u3000" + notificationDescription);
			contentView.setTextViewText(R.id.tx_percent, progress + "%");
			notification.contentView = contentView;
			manager.notify(0, notification);
			super.handleMessage(msg);
		};
	};
	
	/**
	 * @param context
	 * @param url
	 *            下载apk的url
	 * @param notificationTitle
	 *            通知栏标题
	 * @param notificationDescription
	 *            通知栏描述
	 */
	public ImageDownLoad(final Context context, String url, String notificationTitle,
			String notificationDescription) {
		super();
		DOWNLOAD_FILE_NAME=url.substring(url.lastIndexOf("/"), url.length());
		this.context = context;
		this.url = url;
		this.notificationTitle = notificationTitle;
		this.notificationDescription = notificationDescription;
		File file = new File(new StringBuilder(Environment
				.getExternalStorageDirectory().getAbsolutePath())
				.append(File.separator).append(DOWNLOAD_FOLDER_NAME)
				.append(File.separator).append(DOWNLOAD_FILE_NAME).toString());
		if (file.exists()) {
			file.delete();
		}
		try {
			downloadManager = (DownloadManager) context
					.getSystemService(Context.DOWNLOAD_SERVICE);
			completeReceiver = new CompleteReceiver();
			/** register download success broadcast **/
			context.registerReceiver(completeReceiver, new IntentFilter(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.logo, "开始下载",
					System.currentTimeMillis());
			callBack = new AjaxCallBack<File>() {
				@Override
				public void onFailure(Throwable t, int errorNo,
						String strMsg) {// 下载失败
					super.onFailure(t, errorNo, strMsg);
					Toast.makeText(context, "文件下载失败", Toast.LENGTH_SHORT)
							.show();
				}

				@Override
				public void onStart() {// 开始下载
					super.onStart();
					sendNotification();
				}

				@Override
				public void onSuccess(File t) {// 下载成功
					super.onSuccess(t);
					manager.cancel(0);
					Toast.makeText(context, "图片保存于手机存储的根目录中的零距离的图片文件夹下", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onLoading(long count, long current) {// 正在下载
					super.onLoading(count, current);
					if (current != count && current != 0) {
						progress = (int) (current / (float) count * 100);
					} else {
						progress = 100;
					}
					handler.handleMessage(msg);
				}

			};
			download(url);
		}
	}
	
	/**
	 * 从指定的地址下载文件
	 * 
	 * @param url
	 *            下载地址
	 */
	public void download(String url) {
		FinalHttp http = new FinalHttp();
		if (!isExternalStorageAvaliable()) {
			return;
		}
		String apkPath = getPath();
		File f = new File(apkPath);
		if (f.exists()) {
			f.delete();
		}
		http.download(url, apkPath, callBack);
	}
	
	private String getPath() {
		// TODO Auto-generated method stub
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			return new StringBuilder(Environment.getExternalStorageDirectory()
					.getAbsolutePath()).append(File.separator)
					.append(DOWNLOAD_FOLDER_NAME).append(File.separator)
					.append(DOWNLOAD_FILE_NAME).toString();
		}
		return "";
	}
	
	/**
	 * 判断SD卡是否可用
	 * 
	 * @return
	 */
	public boolean isExternalStorageAvaliable() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			Toast.makeText(context, "未检测到SD卡...", Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	public void execute() {
		// 清除已下载的内容重新下载
		long downloadId = PreferencesUtils.getLong(context, APK_DOWNLOAD_ID);
		if (downloadId != -1) {
			downloadManager.remove(downloadId);
			PreferencesUtils.removeSharedPreferenceByKey(context,
					APK_DOWNLOAD_ID);
		}

		Request request = new Request(Uri.parse(url));
		// 设置Notification中显示的文字
		request.setTitle(notificationTitle);
		request.setDescription(notificationDescription);
		// 设置可用的网络类型
		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);
		// 设置状态栏中显示Notification
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		// 不显示下载界面
		request.setVisibleInDownloadsUi(false);
		// 设置下载后文件存放的位置
		File folder = Environment
				.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME,
				DOWNLOAD_FILE_NAME);
		// 设置文件类型
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
				.getFileExtensionFromUrl(url));
		request.setMimeType(mimeString);
		// 保存返回唯一的downloadId
		PreferencesUtils.putLong(context, APK_DOWNLOAD_ID,
				downloadManager.enqueue(request));
	}

	class CompleteReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			/**
			 * get the id of download which have download success, if the id is
			 * my id and it's status is successful, then install it
			 **/
			long completeDownloadId = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, 0);
			long downloadId = PreferencesUtils
					.getLong(context, APK_DOWNLOAD_ID);

			if (completeDownloadId == downloadId) {

				// if download successful
				if (queryDownloadStatus(downloadManager, downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
					Toast.makeText(context, "图片保存于手机存储的根目录中的零距离的图片文件夹下", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	/**
	 * 发送通知
	 */
	public void sendNotification() {
		contentView = new RemoteViews(context.getPackageName(),
				R.layout.layout_updatenotification);
		contentView.setProgressBar(R.id.pb, 100, 0, false);
		notification.contentView = contentView;
		manager.notify(0, notification);
	}

	/** 查询下载状态 */
	public static int queryDownloadStatus(DownloadManager downloadManager,
			long downloadId) {
		int result = -1;
		DownloadManager.Query query = new DownloadManager.Query()
				.setFilterById(downloadId);
		Cursor c = null;
		try {
			c = downloadManager.query(query);
			if (c != null && c.moveToFirst()) {
				result = c.getInt(c
						.getColumnIndex(DownloadManager.COLUMN_STATUS));
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}
}
