package com.zhjl.qihao.abupapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.utils.OkManager;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;
import static com.zhjl.qihao.abupapp.UpdateChecker.checkIsShowMainDialog;

public class UpdateDialog implements OkManager.IProgress {

    public static final int WRITE_EXTERNAL_CODE = 11121;
    public static final int REQUEST_CODE_APP_INSTALL = 0x177;
    public static final int REQUEST_CODE_NOTIFICATION = 0x178;
    public static String loadUrl = "";
    public static long download;
    private static boolean mIsCancel;
    private static int mProgress;
    private static Handler handler = new myHandler();
    public static String mSavePath;
    private static VolleyBaseActivity activity;
    private static String version;
    private static String content;
    private static boolean isSure;
    private static String CHANNEL_ID = "";
    private static NotificationCompat.Builder notify;
    private static NotificationManager notificationManager;
    private static int NotificationID = 1;
    public static AlertDialog dialog;
    public static AlertDialog sureDialog;
    private static boolean isConnected = true;
    private static ProgressBar progressApp;

    void show(final VolleyBaseActivity context, final String content, final String downloadUrl, String appVersion, final boolean isSure) {
        loadUrl = downloadUrl;
        activity = context;
        this.content = content;
        version = appVersion;
        this.isSure = isSure;

        if (isContextValid(context)) {
            createAppDialog(context, content, appVersion, isSure);
        }
    }

    private static void createAppDialog(VolleyBaseActivity context, String content, String appVersion, boolean isSure) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        View view = View.inflate(activity, R.layout.update_view, null);
        ImageView imgCancel = view.findViewById(R.id.img_cancel);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvVersion = view.findViewById(R.id.tv_version);
        tvContent.setText(content);
        tvVersion.setText("V" + appVersion);
        tvSure.setOnClickListener(v -> {    //更新
            if (RequestPermissionUtils.checkPermission(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String sdPath = Environment.getExternalStorageDirectory() + "/";
                    //文件保存路径
                    mSavePath = sdPath + "zhjl";
                    boolean isApk = installAPK();   //是否存在apk
                    if (!isApk) {
                        if (isSure) {        //强制更新对话框
                            createAppDialog();
                        } else {     //通知栏
                            NotificationManagerCompat manager = NotificationManagerCompat.from(activity);
                            // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
                            boolean isOpened = manager.areNotificationsEnabled();
                            if (!isOpened) {
                                PopWindowUtils.getInstance().show("通知权限未开启，无法收到app更新进度，请手动开启！", activity);
                                PopWindowUtils.getInstance().getYesView().setText("去设置");
                                PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                                    goToSet();
                                });
                            } else {
                                notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    createNotificationChannel(notificationManager);
                                }
                                notify = getNotificationBuilder();
                            }
                        }
                        downloadAPK();
                    }
                }
            } else {
                RequestPermissionUtils.requestPermission(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_CODE);
            }
            dialog.dismiss();
        });
        imgCancel.setOnClickListener(v -> {
            // 隐藏当前对话框
            dialog.dismiss();
        });
        if (isSure) {
            imgCancel.setVisibility(View.GONE);
        } else {
            imgCancel.setVisibility(View.VISIBLE);
        }

        dialog.setView(view);
        //设置背景透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (isSure) {
            dialog.setCancelable(false);
        }
        //点击对话框外面,对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setLayout(Utils.dip2px(activity, 300), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setOnDismissListener(dialog1 -> {
            if (isSure) {
//                    ZHJLApplication.getInstance().exitAPP();
            } else {
                dialog1.dismiss();
                if (!context.getIntent().getBooleanExtra("Tourist", false)) {
                    checkIsShowMainDialog(context);
                }
            }
        });
    }

    /**
     * 强制更新的对话框
     */
    private static void createAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        sureDialog = builder.create();
        View view = View.inflate(activity, R.layout.sure_update_view, null);
        progressApp = view.findViewById(R.id.progress_app);
        sureDialog.setView(view);
        //设置背景透明
        sureDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        sureDialog.setCancelable(false);
        //点击对话框外面,对话框不消失
        sureDialog.setCanceledOnTouchOutside(false);
        sureDialog.show();
        sureDialog.getWindow().setLayout(Utils.dip2px(activity, 300), ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public static final String SETTINGS_ACTION =
            "android.settings.APPLICATION_DETAILS_SETTINGS";

    public static void goToSet() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getApplicationContext().getPackageName());
            activity.startActivityForResult(intent, REQUEST_CODE_NOTIFICATION);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            Intent intent = new Intent()
                    .setAction(SETTINGS_ACTION)
                    .setData(Uri.fromParts("package",
                            activity.getApplicationContext().getPackageName(), null));
            activity.startActivityForResult(intent, REQUEST_CODE_NOTIFICATION);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);//进入设置页面
            Intent intent = new Intent()
                    .setAction(SETTINGS_ACTION)
                    .setData(Uri.fromParts("package",
                            activity.getApplicationContext().getPackageName(), null));
            activity.startActivityForResult(intent, REQUEST_CODE_NOTIFICATION);
            return;
        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(NotificationManager notificationManager) {
        CHANNEL_ID = "qhljl_=update" + System.currentTimeMillis();
        // 通知渠道
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "ander drowload apk default channel.", NotificationManager.IMPORTANCE_HIGH);
        //是否绕过请勿打扰模式
        channel.canBypassDnd();
        // 开启指示灯，如果设备有的话。
        channel.enableLights(true);
        // 开启震动
        channel.enableVibration(false);
        //是否会有灯光
        channel.shouldShowLights();
        //获取系统通知响铃声音的配置
//        channel.getAudioAttributes();
        //  设置指示灯颜色
        channel.setLightColor(Color.RED);
        // 设置是否应在锁定屏幕上显示此频道的通知
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        // 设置是否显示角标
        channel.setShowBadge(true);
        //  设置绕过免打扰模式
        channel.setBypassDnd(true);
        // 设置震动频率
        //最后在notificationmanager中创建该通知渠道
        channel.setSound(null, null);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
    }

    /**
     * 方法描述：createNotification方法
     *
     * @param
     * @return
     * @see DownloadService
     */

    public static NotificationCompat.Builder getNotificationBuilder() {
        return new NotificationCompat.Builder(ZHJLApplication.getContext(), CHANNEL_ID)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("7好零距离版本更新中。。。") //设置通知标题
                .setSmallIcon(R.drawable.new_logo)
                .setFullScreenIntent(null, true)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.new_logo)) //设置通知的大图标
                //修改小圆圈默认背景颜色
                .setColor(ContextCompat.getColor(activity,R.color.new_theme_color))
                .setDefaults(Notification.DEFAULT_ALL) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText(mProgress + "%")
                .setProgress(100, 0, false);
    }

    /* 开启新线程下载apk文件
     */
    private static void downloadAPK() {
        MainApiInterfaces service = ApiHelper.getInstance().buildRetrofit(activity).createService(MainApiInterfaces.class);
        Call<ResponseBody> call = service.downLoad(loadUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        try {
                            mIsCancel = false;
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                                mSavePath = sdPath + "zhjl";
                                File dir = new File(mSavePath);
                                if (!dir.exists()) {
                                    dir.mkdir();
                                }
                                InputStream is = response.body().byteStream();
                                long length = response.body().contentLength();

                                BufferedInputStream isBuffer = new BufferedInputStream(is);
                                File apkFile = new File(mSavePath, "qhljl" + version + ".temp");
                                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(apkFile));

                                int count = 0;
                                byte[] buffer = new byte[6 * 1024];
                                int preProgress = 0;
                                while (!mIsCancel) {
                                    int numread = isBuffer.read(buffer);
                                    count += numread;
                                    // 计算进度条的当前位置
                                    mProgress = (int) (((float) count / length) * 100);
                                    //防止界面卡死
                                    if (preProgress < mProgress) {
                                        if (notify != null) {
                                            notify.setProgress(100, mProgress, false);
                                            notify.setContentText(mProgress + "%");
                                            notificationManager.notify(NotificationID, notify.build());
                                        }
                                        // 更新进度条
                                        handler.sendEmptyMessage(1);
                                    }
                                    preProgress = mProgress;
                                    // 下载完成
                                    if (numread < 0) {
                                        handler.sendEmptyMessage(2);
                                        break;
                                    }
                                    fos.write(buffer, 0, numread);
                                }
                                fos.close();
                                is.close();

                            }
                        } catch (Exception e) {
                            if (isSure) {
//                                ZHJLApplication.getInstance().exitAPP();
                            } else {
                                if (notify != null) {
                                    notify.setAutoCancel(true);
                                    notificationManager.cancel(NotificationID);
                                }
                                if (isConnected) {
                                    dialog.dismiss();
                                }
                            }
                            e.printStackTrace();
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onProgress(int progress) {
//        tvProgress.setText(progress + "%");
    }

    /**
     * 接收消息
     */
    private static class myHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (progressApp != null) {
                        progressApp.setProgress(mProgress);
                    }
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    if (notify != null) {
                        notify.setAutoCancel(true);
                        notificationManager.cancel(NotificationID);
                    }
                    File apkFile = new File(mSavePath, "qhljl" + version + ".temp");
                    File dest = new File(mSavePath, "qhljl" + version + ".apk");
                    apkFile.renameTo(dest);
                    if (sureDialog != null) {  //隐藏强制更新按钮
                        sureDialog.dismiss();
                    }
                    if (isSure) {       //强制更新后  如果用户取消显示安装弹框
                        if (dialog != null) {
                            dialog.show();
                        } else {
                            createAppDialog(activity, content, version, true);
                        }
                    }
                    // 安装 APK 文件
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = isHasInstallPermissionWithO(activity);
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity(activity);
                            return;
                        } else {
                            installAPK();
                        }
                    }
            }

        }
    }


    /*
     * 下载到本地后执行安装
     */
    public static boolean installAPK() {
        File apkFile = new File(mSavePath, "qhljl" + version + ".apk");
        if (!apkFile.exists()) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // 生成文件的uri，，
            // 注意 下面参数com.ausee.fileprovider 为apk的包名加上.fileprovider，
            data = FileProvider.getUriForFile(activity, "com.zhjl.qihao.FileProvider", apkFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        return true;
    }


    public static long goToDownload(VolleyBaseActivity context, String title, String
            downloadUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //创建目录
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置 Notification 信息
        request.setTitle(title);
        // 允许在计费流量下下载
        request.setAllowedOverMetered(true);//允许流量下载
        request.setAllowedOverRoaming(true);    //允许漫游下载
        request.setDescription("下载完成后请点击打开");
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");
        // 实例化DownloadManager 对象
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(download);
        return downloadManager.enqueue(request);
    }

    private static void listener(Context context, final long Id) {
        // 注册广播监听系统的下载完成事件。
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 这里是通过下面这个方法获取下载的id，
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // 这里把传递的id和广播中获取的id进行对比是不是我们下载apk的那个id，如果是的话，就开始获取这个下载的路径
                if (ID == Id) {
                    InstallApk(context, Id);
                }
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public static void InstallApk(Context context, long Id) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(Id);

        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()) {
            // 获取文件下载路径
            String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

            // 如果文件名不为空，说明文件已存在,则进行自动安装apk
            if (fileName != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = isHasInstallPermissionWithO(context);
                    if (!hasInstallPermission) {
                        startInstallPermissionSettingActivity(context);
                        return;
                    } else {
                        openAPK(fileName, context);
                    }
                }
            }
        }
        cursor.close();
    }

    /**
     * 安装apk
     *
     * @param fileSavePath
     */
    private static void openAPK(String fileSavePath, Context context) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // 生成文件的uri，，
            // 注意 下面参数com.ausee.fileprovider 为apk的包名加上.fileprovider，
            data = FileProvider.getUriForFile(context, "com.zhjl.qihao.FileProvider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }
}
