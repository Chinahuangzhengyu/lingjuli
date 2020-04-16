package com.zhjl.qihao.abupapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.zhjl.qihao.BuildConfig;
import com.zhjl.qihao.R;

import org.json.JSONArray;
import org.json.JSONException;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:21
 */
class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Activity mContext;
    private int mType;
    private boolean mShowProgressDialog;
    private static final String url = API_HOST + JAVA_PORT_NUMBER + Constants.UPDATE_URL;

    CheckUpdateTask(Activity context, int type, boolean showProgressDialog) {

        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;

    }


    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }


    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }

    private void parseJson(String result) {
        try {

//            Log.e("=== UpDateTask ", "parseJson: " + result.toString());
           /* JSONObject obj = new JSONObject(result);
            String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
            String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL); */
            StringBuilder sb = new StringBuilder();
            JSONArray obj = new JSONArray(result);
            // String updateMessage = obj.getJSONObject(0).getString("appType");
            String apkUrl = obj.getJSONObject(0).getString("downLoadUrl");
            int apkCode = obj.getJSONObject(0).getInt("versionCode");
            for (int i = 0; i < obj.getJSONObject(0).getJSONArray("detail").length(); i++) {
                sb.append((i + 1) + "." + obj.getJSONObject(0).getJSONArray("detail").getJSONObject(i).getString("info") + "<br>");
            }
            String updateMessage = sb.toString();
            int versionCode = AppUtils.getVersionCode(mContext);

            if (apkCode > versionCode) {
                if (mType == Constants.TYPE_NOTIFICATION) {
                    showNotification(mContext, updateMessage, apkUrl);
                } else if (mType == Constants.TYPE_DIALOG) {
                    showDialog(mContext, updateMessage, apkUrl);
                }
            } else if (mShowProgressDialog) {
//                Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(Constants.TAG, "parse json error");
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Activity context, String content, String apkUrl) {
//        UpdateDialog.show(context, content, apkUrl);
    }

    /**
     * Show Notification
     */
    private void showNotification(Context context, String content, String apkUrl) {
        Intent myIntent = new Intent(context, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = context.getApplicationInfo().icon;
        Notification notify = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle(context.getString(R.string.android_auto_update_notify_content))
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        notify.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify);
    }

    @Override
    protected String doInBackground(Void... args) {
        return HttpUtils.get(url);
    }
}
