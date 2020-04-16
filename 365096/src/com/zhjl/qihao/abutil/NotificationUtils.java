package com.zhjl.qihao.abutil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.zhjl.qihao.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者： 黄郑宇
 * 时间： 2018/11/5
 * 类作用：通知工具类
 */
public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "零距离通知";

    public NotificationUtils(Context context) {
        super(context);
    }

    public void createNotificationChannel() {
        @SuppressLint("WrongConstant")
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        channel.enableLights(true);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content, PendingIntent contentIntent) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.new_logo)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content, PendingIntent contentIntent) {
        return new NotificationCompat.Builder(getApplicationContext(), "ljl")
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.new_logo)
                .setAutoCancel(true);
    }

    //适配vivo手机悬浮弹窗
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getVivoChannelNotification(String title, String content, PendingIntent contentIntent) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(contentIntent, true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.new_logo)
                .setAutoCancel(true);
    }
    //适配vivo手机悬浮弹窗
    public NotificationCompat.Builder getVivoNotification_25(String title, String content, PendingIntent contentIntent) {
        return new NotificationCompat.Builder(getApplicationContext(), "ljl")
                .setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(contentIntent, true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.new_logo)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content, PendingIntent contentIntent) {
        String name = Build.MANUFACTURER;
        Notification notification;
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            if (name.equals("vivo")) {
                notification = getVivoChannelNotification
                        (title, content, contentIntent).build();
            } else {
                notification = getChannelNotification
                        (title, content, contentIntent).build();
            }
            getManager().notify(1, notification);
        } else {
            if (name.equals("vivo")) {
                notification = getVivoNotification_25
                        (title, content, contentIntent).build();
            } else {
                notification = getNotification_25
                        (title, content, contentIntent).build();
            }
            getManager().notify(1, notification);
        }
    }

    //检查系统是否关闭app应用的通知权限
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                    Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                    AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final String SETTINGS_ACTION =
            "android.settings.APPLICATION_DETAILS_SETTINGS";

    public  void goToSet() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getApplicationContext().getPackageName());
            startActivity(intent);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            Intent intent = new Intent()
                    .setAction(SETTINGS_ACTION)
                    .setData(Uri.fromParts("package",
                            getApplicationContext().getPackageName(), null));
            startActivity(intent);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
//            Intent intent = new Intent(Settings.ACTION_SETTINGS);//进入设置页面
            Intent intent = new Intent()
                    .setAction(SETTINGS_ACTION)
                    .setData(Uri.fromParts("package",
                            getApplicationContext().getPackageName(), null));
            startActivity(intent);
            return;
        }
    }
}
