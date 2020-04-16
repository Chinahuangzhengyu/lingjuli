package com.zhjl.qihao.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.DatabaseConnection;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.mymessage.activity.AllMessageCenterActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abutil.NotificationUtils;
import com.zhjl.qihao.database.DatabaseHelper;
import com.zhjl.qihao.entity.RecMessage;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.util.HtmlStorageHelper;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.util.Tools;

/***
 * 初始化信息服务通知 Notification通知
 * @author 黄南榆
 * @date 2015-7-1
 */
public class InformationService extends OrmLiteBaseService<DatabaseHelper> {
    private static final String TAG = "InformationService";
    public static final String ACTION = "com.zhjl.qihao.service.InformationService";
    // public static final String PHONE_REQ_MESSAGE =
    // "http://10.20.7.251:8080/bcportal/phoneReqMessage.html";
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    private Context context;

    public InformationService() {
        this.context = ZHJLApplication.getContext();
    }

    static Thread t;
    static int a = 1;
    private DatabaseHelper databaseHelper = null;

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
//		LogUtils.E("InformationService onStart()");
        if (t == null || !t.isAlive()) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Tools tools = new Tools(getApplicationContext(), Constants.NEARBYSETTING);
                    Time t = new Time();
                    t.setToNow();
//					LogUtils.E("t.hour = "+t.hour);
                    if (t.hour >= 8 && t.hour < 23) {
                        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (cwjManager.getActiveNetworkInfo() != null
                                && cwjManager.getActiveNetworkInfo().isAvailable()
                                && tools.getValue(Constants.M_TOKEN) != null) {
//                            ThreadPoolUtil.getInstant().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        MessageInterface messageInterface = ApiHelper.getInstance().buildRetrofit(context).createService(MessageInterface.class);
//                                        Map<String, Object> map = new HashMap<>();
//                                        RequestBody body = ParamForNet.put(map);
//                                        Call<ResponseBody> call = messageInterface.newReadMessage(body);
//                                        call.enqueue(new Callback<ResponseBody>() {
//                                            @Override
//                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                if (response.isSuccessful() && response.body() != null) {
//                                                    try {
//                                                        String string = response.body().string();
//                                                        getMessage(string);
//                                                    } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                            }
//                                        });
//                                    } catch (Exception ex) {
//                                        ex.printStackTrace();
//                                    }
//                                }
//                            });
                        }
                    }
                }
            });
            t.start();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

//        LogUtils.d("InformationService onDestroy");
//        Intent step = new Intent(getApplicationContext(), StepService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(step);
//        } else {
//            startService(step);
//        }
    }

    @Override
    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,
                    DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    /**
     * 处理接收到的消息，并保存到数据库中。
     *
     * @param json
     * @throws JSONException
     * @throws SQLException
     */
    public void saveJsonData(String json) throws JSONException, SQLException {

        DatabaseConnection dbconnect = null;
        Dao<RecMessage, String> recimessage = databaseHelper
                .getRecImessageDao();
        try {
            dbconnect = databaseHelper.getConnectionSource()
                    .getReadWriteConnection();
            recimessage.setAutoCommit(dbconnect, false);
            JSONArray js;
            try {
                js = new JSONArray(json);
            } catch (Exception e) {
                Log.e("InformationService", "json:" + json);
                try {
                    js = new JSONArray();
                    js.put(new JSONObject(json));
                } catch (Exception ex) {
                    return;
                }
            }
            int size = js.length();
            for (int i = 0; i < size; i++) {
                Object o = js.get(i);
                if (o instanceof JSONObject) {
                    JSONObject a = (JSONObject) o;
                    JSONArray names = a.names();
                    int namesLenth = names.length();
                    if (namesLenth > 0) {
                        RecMessage rec = new RecMessage();
                        for (int j = 0; j < namesLenth; j++) {
                            String name = (String) names.get(j);
                            try {
                                Method method = rec.getClass().getMethod(
                                        "set" + name.substring(0, 1).toUpperCase()
                                                + name.substring(1), String.class);
                                method.invoke(rec, a.getString(name));
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        if (rec.getPid() != null && !"".equals(rec.getPid())) {
                            if (recimessage.queryForId(rec.getPid()) == null) {
                                recimessage.create(rec);
                            }
                        }
                    }
                }
            }
            recimessage.commit(dbconnect);

        } catch (SQLException e) {
            try {
                recimessage.rollBack(dbconnect);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        selectNew();
    }

    public void selectNew() throws SQLException {
        Dao<RecMessage, String> recimessage = databaseHelper
                .getRecImessageDao();
        QueryBuilder qb = recimessage.queryBuilder();
        Where where = qb.where();
        where.eq("is_remin", "1");
        List<RecMessage> Messagelist = qb.query();
        // long size = qb.countOf();
        long size = Messagelist.size();
        if (size > 0) {
            Intent intent = new Intent(this, RefactorMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("recMessage", "true");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    intent, // 指定一个跳转的intent
                    0);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            if (NotificationUtils.isNotificationEnabled(getApplicationContext())) {
                notificationUtils.sendNotification("通知", "你有" + size + "条未读消息!", pendingIntent);
            }

            // 构建一个通知对象，指定了 图标，标题，和时间
//			Notification notification = new Notification(R.drawable.logo, "通知",
//					System.currentTimeMillis());
//			// 设定事件信息
//
//			notification.setLatestEventInfo(getApplicationContext(),
//					getString(R.string.app_name), "你有" + size + "条未读消息!",
//					pendingIntent);
//			notification.flags |= Notification.FLAG_AUTO_CANCEL; // 自动终止
//			notification.defaults |= Notification.DEFAULT_SOUND; // 默认声音
            String appCacheDir = this.getApplicationContext()
                    .getDir("cache", Context.MODE_PRIVATE).getPath();
            HtmlStorageHelper htmlhelperStorage = new HtmlStorageHelper(this,
                    appCacheDir);
            for (RecMessage remessage : Messagelist) {
                if (!remessage.getMessage_url().startsWith("file://")) {
                    try {
                        htmlhelperStorage.saveHtml(remessage.getPid(),
                                remessage.getTitle(),
                                remessage.getMessage_url());
                        remessage
                                .setLocal_message_url("file://" + appCacheDir
                                        + "/" + remessage.getPid() + "/"
                                        + "index.html");
                        remessage.setIs_loacl("1");
                        recimessage.update(remessage);
                    } catch (Exception e) {

                    }

                }
            }
        }
    }

    Tools tools;

    public void getMessage(String json) throws JSONException, SQLException {
        LogUtils.e("消息：" + json);
        JSONObject js;
        js = new JSONObject(json);
        JSONObject data = js.getJSONObject("data");
        int noReadNum = data.optInt("noReadNum");
        if (0 < noReadNum) {
            Tools tools = Tools.getInstance(context);
            tools.putValue(Constants.MESSAGE_COUNT, noReadNum);
            tools.putValue(Constants.MESSAGE_ONE, noReadNum);
            String content = "您有" + noReadNum + "条未读消息!";
            CharSequence tickerText = "通知";

            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());

            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
            contentView.setTextViewText(R.id.tx_content, content);
            Intent notificationIntent = new Intent(this, AllMessageCenterActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (NotificationUtils.isNotificationEnabled(getApplicationContext())) {
                notificationUtils.sendNotification(tickerText.toString(), content, contentIntent);
            }

//				Notification notification = new Notification(icon, tickerText,when);
//				notification.flags |= Notification.FLAG_AUTO_CANCEL;
//				notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
//				notification.defaults = Notification.DEFAULT_SOUND;
//				notification.contentView = contentView;

            //Intent notificationIntent = new Intent(this,MessageCenterActivity.class);

//				notification.contentIntent = contentIntent;


            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            //获取电源管理器对象
            @SuppressLint("InvalidWakeLockTag")
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            wl.acquire();
            //点亮屏幕
            wl.release();
            //释放
        }
    }

    public static void stopThread() {
        if (t != null) {
            t.stop();
        }
    }


}
