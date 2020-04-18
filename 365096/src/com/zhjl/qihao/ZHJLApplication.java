package com.zhjl.qihao;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.webkit.CookieManager;

import com.baidu.mapapi.SDKInitializer;
import com.michoi.unlock.UnlockManager;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhjl.qihao.abutil.SetSpWayUtils;
import com.zhjl.qihao.greendao.DaoMaster;
import com.zhjl.qihao.greendao.DaoSession;
import com.zhjl.qihao.service.InformationService;
import com.zhjl.qihao.service.LocationService;
import com.zhjl.qihao.util.PropertiesUtil;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.util.Until;
import com.zhjl.qihao.volley.VolleyRequestManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.jpush.android.api.JPushInterface;

/***
 *
 * @author south
 *
 */
public class ZHJLApplication extends MultiDexApplication {

    public List<Activity> activities;
    private static ZHJLApplication instance;
    private boolean receviemsg = false;

    public Vibrator mVibrator;

    private static Context context;
    /**
     * 百度地图授权id
     */
    static String StrKey;
    public boolean isOffMap;

    private int timeperiod = 60 * 1000;


    PendingIntent restartIntent;
    private int startOnCreate = 0;
    public LocationService locationService;
    private static RefWatcher sRefWatcher;


    public static Context getContext() {
        return context;
    }

    public ZHJLApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //屏幕适配初始化
        SetSpWayUtils.initAppDensity(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
//                UMConfigure.init(getApplicationContext(), "5d6e34a94ca3579f69000371"
//                        , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
                // UMeng 统计   在清单文件中已经添加过 appid  和  chanel
                // 必须在调用任何统计SDK接口之前调用初始化函数
                UMConfigure.init(getApplicationContext(), "5d6e34a94ca3579f69000371", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
                //初始化greenDao
                initGreenDao();

            }
        }.start();
        //腾讯bugly查找异常
//        CrashReport.initCrashReport(getApplicationContext(), "54c83d9769", false);
        //麦驰门禁初始化
        try {
            UnlockManager.initLock(getApplicationContext(), "lock.mcsqfw.com", 11050, "512b619a5a35a468", Short.parseShort("55"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //友盟分享
        PlatformConfig.setWeixin(Constants.APP_ID_WX, Constants.APP_SECRET);
        PlatformConfig.setQQZone(Constants.APP_QQ_ID, Constants.APP_QQ_SECRET);

        locationService = new LocationService(this);
        //百度地图
        SDKInitializer.initialize(this);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.resumePush(getApplicationContext());

        if (startOnCreate < 1) {

            // 1、上下文环境
            ZHJLApplication.context = this;

            instance = this;

            // 初始化volley队列
            VolleyRequestManager.init(this);

        }
        startOnCreate++;
        //new Thread(runnable).start();
        //检查内存泄漏
//        if (LeakCanary.isInAnalyzerProcess(this))
//        {
//            return;
//        }
//        sRefWatcher = LeakCanary.install(this);

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }

    /*
     * 当操作系统确定是进程从其进程中删除不需要的内存的好时机时调用;
     * 例如，当它进入后台并且没有足够的内存来保持尽可能多的后台进程运行时，就会发生这种情况;
     * 通常比较该值是否大于或等于
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                break;
        }
    }

    /*
     *当整个系统内存不足时调用此方法，也可采用手动方式重写此方法来清空缓存或者释放不必要的资源
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /*
     * 应用程序结束时调用，
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
            activity.finish();
        }
    }

    public void finishAll() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public void exitAPP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finishAll();
        } else {
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
            for (ActivityManager.AppTask appTask : appTaskList) {
                appTask.finishAndRemoveTask();
            }
        }
    }
//	public UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
//		
//		
//		
//		@Override
//		public void uncaughtException(Thread thread, Throwable ex) {
//			AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//					restartIntent); // 1秒钟后重启应用
//			finishProgram();
//		}
//	};
//
//	/**
//	 * 结束线程
//	 */
//	public void finishProgram() {
//		for (Activity activity : VolleyBaseActivity.activityList) {
//			if (null != activity) {
//				activity.finish();
//			}
//		}
//		android.os.Process.killProcess(android.os.Process.myPid());
//	}

    /***
     * 清除webview中的cookies
     * @param context
     */
    public static void clearCookies(Context context) {
        // Edge case: an illegal state exception is thrown if an instance of
        // CookieSyncManager has not be created. CookieSyncManager is normally
        // created by a WebKit view, but this might happen if you start the
        // app, restore saved state, and click logout before running a UI
        // dialog in a WebView -- in which case the app crashes
        @SuppressWarnings("unused")
//		CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
                CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }


    /**
     * 获取文件夹内的所有内容的大小
     *
     * @param file File实例
     * @return long 单位为M
     * @throws
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;

        File[] fileList = file.listFiles();
        for (int i = 0; fileList != null && i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size / 1024 / 1024;
    }


    /**
     * 重复闹钟获取消息
     * 启动服务获取消息
     * 初始化信息服务通知并获取消息
     * 默认一分钟获取一次
     */
    private void initInformationNotify() {
        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intentToFire = new Intent(this, InformationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intentToFire, PendingIntent.FLAG_UPDATE_CURRENT);
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), getTimeperiod(), pendingIntent);
    }


    public static String getStrKey() {
        return StrKey;
    }

    public static void setStrKey(String strKey) {
        StrKey = strKey;
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            if (version == null || "".equals(version)) {
                version = "1.0";
            }
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取模块版本号
     *
     * @return 当前模块的版本号 ，如果没有版本号，默认为1.0
     */
    public String getversion(String module) {
        Properties pro = PropertiesUtil.loadConfig(Constants.MAIN_COPY_URL
                + module + "/version.properties");
        String pro_version = pro.getProperty("version");
        if (pro_version == null || "".equals(pro_version)) {
            pro_version = "1.0";
        }
        return pro_version;
    }

    /**
     * 获取touchstyle版本号
     *
     * @return 当前touchstyle的版本号
     */
    public String gettouchstyleversion() {
        Properties pro = PropertiesUtil.loadConfig(Constants.COPY_URL
                + "version.properties");
        String pro_version = pro.getProperty("version");
        if (pro_version == null || "".equals(pro_version)) {
            pro_version = "1.0";
        }
        return pro_version;
    }

    /**
     * 获取morepage版本号
     *
     * @return 当前morepage的版本号
     */
    public String getmorepageversion() {
        Properties pro = PropertiesUtil.loadConfig(Constants.COPYMOR_URL
                + "version.properties");
        String pro_version = pro.getProperty("version");
        if (pro_version == null || "".equals(pro_version)) {
            pro_version = "1.0";
        }
        return pro_version;
    }

    /**
     * 获取morepage版本号
     *
     * @return 当前morepage的版本号
     */
    public String getindexpageversion() {
        Properties pro = PropertiesUtil.loadConfig(Constants.COPYINDEX_URL
                + "version.properties");
        String pro_version = pro.getProperty("version");
        if (pro_version == null || "".equals(pro_version)) {
            pro_version = "1.0";
        }
        return pro_version;
    }

    /**
     * 获取javascript版本号
     *
     * @return 当前javascript的版本号
     */
    public String getjavascriptversion() {
        Properties pro = PropertiesUtil.loadConfig(Constants.COPYJAVASCRIPT_URL
                + "version.properties");
        String pro_version = pro.getProperty("version");
        if (pro_version == null || "".equals(pro_version)) {
            pro_version = "1.0";
        }
        return pro_version;
    }

    /**获取DisplayMetrics*/
//	public static DisplayMetrics getDisplayMetrics() {
//		return dm;
//	}

//	private String checkdeviceid() {
//		Tools tools = new Tools(this, Constants.NEARBYSETTING);
//		String PhoneDeviceId = tools.getValue(Constants.DEVICEID);
//		if (PhoneDeviceId == null || "".equals(PhoneDeviceId)) {
//			TelephonyManager tm = (TelephonyManager) this
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			PhoneDeviceId = tm.getDeviceId();
//		}
//		return PhoneDeviceId;
//	}

    /***
     *
     * @return 获取手机登陆令牌
     */
    public String gettokent() {
        Tools tools = new Tools(this.getApplicationContext(),
                Constants.NEARBYSETTING);
        String tokent = tools.getValue(Constants.KEY_TONE);
        return tokent;
    }

    /***
     *
     * @return 用户类型 0 游客类型 1业主类型 2租客类型 3家人类型
     */
    public String getut() {
        Tools tools = new Tools(this.getApplicationContext(),
                Constants.NEARBYSETTING);
        String ut = tools.getValue(Constants.USERTYPE);
        return ut;
    }

    /***
     * 默认10分钟
     * @return
     */
    public int getTimeperiod() {
        return timeperiod;
    }

    public void setTimeperiod(int timeperiod) {
        this.timeperiod = timeperiod;
    }

    public boolean isReceviemsg() {
        return receviemsg;
    }

    public void setReceviemsg(boolean receviemsg) {
        this.receviemsg = receviemsg;
    }


    public static ZHJLApplication getInstance() {
        if (null == instance) {
            instance = new ZHJLApplication();
        }
        return instance;
    }

    // 递归
    public long getFileSize(File f) throws Exception {// 取得文件夹大小
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    // 删除文件夹
    // param folderPath 文件夹完整绝对路径

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
