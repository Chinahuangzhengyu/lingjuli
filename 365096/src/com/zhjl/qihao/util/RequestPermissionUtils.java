package com.zhjl.qihao.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/23
 * 类作用：请求权限的utils
 */
public class RequestPermissionUtils {

    public static final int REQUEST_PERMISSION_SETTING = 11111;
    public static PopupWindow shortCutPopWindow;
    public static PopupWindow shortCutPopWindow2;
    public static PopupWindow shortCutPopWindow3;
    private static String packageName = "com.zhjl.qihao";
    private static AlertDialog dialog;

    public static boolean checkPermission(Activity activity, String[] strings) {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                strings, activity
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private static boolean checkPermissionAllGranted(String[] permissions, Activity activity) {
//        String name = Build.MANUFACTURER;
//        if (name.equals("Xiaomi")) {
//            boolean xiaomi = checkPermissionForXiaomi(activity);
//            Log.e("+++++++++++++++++","小米手机"+xiaomi);
//            return xiaomi;
//        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    public static void requestPermission(Activity activity, String[] strings, int requestCode) {
        /**
         * 第 2 步: 请求activity权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                activity,
                strings,
                requestCode
        );
    }

    public static void requestFPermission(Fragment activity, String[] strings, int requestCode) {
        /**
         * 第 2 步: 请求fragment权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        activity.requestPermissions(
                strings,
                requestCode
        );
    }

    public static void showShortCut(final Activity mContext, String titlename, View location) {
        final View popView = LayoutInflater.from(mContext).inflate(R.layout.exit_popupwindow, null);
        TextView title = (TextView) popView.findViewById(R.id.tv_title);
        TextView msg = (TextView) popView.findViewById(R.id.tv_msg);
        TextView yes = (TextView) popView.findViewById(R.id.yes_exit);
        TextView not = (TextView) popView.findViewById(R.id.not_exit);
        title.setVisibility(View.VISIBLE);
        msg.setText(titlename);
        yes.setText("去设置");
        not.setText("取消");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSettingIntent(mContext);
                shortCutPopWindow.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCutPopWindow.dismiss();
            }
        });
        shortCutPopWindow = new PopupWindow(popView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        shortCutPopWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        shortCutPopWindow.setFocusable(true);
        shortCutPopWindow.setOutsideTouchable(true);
        shortCutPopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        shortCutPopWindow.showAtLocation(location, Gravity.CENTER, 0, 0);
    }

    public static void showShortCut2(final Activity mContext, String titlename, View location) {
        View alertDialogView = LayoutInflater.from(mContext).inflate(R.layout.pop_forgiveshop, null);
        TextView title = (TextView) alertDialogView.findViewById(R.id.tv_title);
        TextView msg = (TextView) alertDialogView.findViewById(R.id.tv_msg);
        TextView yes = (TextView) alertDialogView.findViewById(R.id.sure);
        TextView not = (TextView) alertDialogView.findViewById(R.id.cancel);
        title.setVisibility(View.VISIBLE);
        msg.setText(titlename);
        yes.setText("去设置");
        not.setText("取消");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSettingIntent(mContext);
                shortCutPopWindow2.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCutPopWindow2.dismiss();
            }
        });
        shortCutPopWindow2 = new PopupWindow(alertDialogView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        shortCutPopWindow2.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        shortCutPopWindow2.setFocusable(true);
        shortCutPopWindow2.setOutsideTouchable(true);
        shortCutPopWindow2.setAnimationStyle(R.style.AnimationPopupCenter);
        shortCutPopWindow2.showAtLocation(location, Gravity.CENTER, 0, 0);
    }

    public static void showShortCut3(final Activity mContext, String titlename, View location) {
        View alertDialogView = LayoutInflater.from(mContext).inflate(R.layout.pop_forgiveshop, null);
        TextView title = (TextView) alertDialogView.findViewById(R.id.tv_title);
        TextView msg = (TextView) alertDialogView.findViewById(R.id.tv_msg);
        TextView yes = (TextView) alertDialogView.findViewById(R.id.sure);
        TextView not = (TextView) alertDialogView.findViewById(R.id.cancel);
        title.setVisibility(View.VISIBLE);
        msg.setText(titlename);
        yes.setText("去设置");
        not.setText("取消");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSettingIntent(mContext);
                shortCutPopWindow3.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCutPopWindow3.dismiss();
            }
        });
        shortCutPopWindow3 = new PopupWindow(alertDialogView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        shortCutPopWindow3.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        shortCutPopWindow3.setFocusable(true);
        shortCutPopWindow3.setOutsideTouchable(true);
        shortCutPopWindow3.setAnimationStyle(R.style.AnimationPopupCenter);
    }

    public static void getSettingIntent(Activity activity) {
        String name = Build.MANUFACTURER;
        switch (name) {
            case "HUAWEI":
                goHuaWeiMainager(activity);
                break;
            case "vivo":
                goVivoMainager(activity);
                break;
            case "OPPO":
                goOppoMainager(activity);
                break;
            case "Coolpad":
                goCoolpadMainager(activity);
                break;
            case "Meizu":
                goMeizuMainager(activity);
                break;
            case "Xiaomi":
                goIntentSetting(activity);
//                goXiaoMiMainager(activity);
                break;
            case "samsung":
                goSangXinMainager(activity);
                break;
            case "Sony":
                goSonyMainager(activity);
                break;
            case "LG":
                goLGMainager(activity);
                break;
            default:
                goIntentSetting(activity);
                break;
        }
    }

    private static void goLGMainager(Activity mContext) {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } catch (Exception e) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goSonyMainager(Activity mContext) {
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } catch (Exception e) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goHuaWeiMainager(Activity mContext) {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } catch (Exception e) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private static void goXiaoMiMainager(Activity mContext) {
        String rom = getMiuiVersion();
        Intent intent = new Intent();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
        } else {
            goIntentSetting(mContext);
        }
        mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    private static void goMeizuMainager(Activity mContext) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            goIntentSetting(mContext);
        }
    }

    private static void goSangXinMainager(Activity mContext) {
        //三星4.3可以直接跳转
        goIntentSetting(mContext);
    }

    private static void goIntentSetting(Activity mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        try {
            mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void goOppoMainager(Activity mContext) {
        doStartApplicationWithPackageName("com.coloros.safecenter", mContext);
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private static void goCoolpadMainager(Activity mContext) {
        doStartApplicationWithPackageName("com.yulong.android.security:remote", mContext);
      /*  Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private static void goVivoMainager(Activity mContext) {
        doStartApplicationWithPackageName("com.bairenkeji.icaller", mContext);
     /*   Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
    }


    private static void doStartApplicationWithPackageName(String packagename, Activity mContext) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            goIntentSetting(mContext);
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mContext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
        for (int i = 0; i < resolveinfoList.size(); i++) {
            Log.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
        }
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packageName参数2 = 参数 packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packageName参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            try {
                mContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
            } catch (Exception e) {
                goIntentSetting(mContext);
                e.printStackTrace();
            }
        }
    }

    private static boolean checkPermissionForXiaomi(Activity activity) {
        AppOpsManager manager = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);

        try {

            Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
            int property = (Integer) method.invoke(manager, 26,
                    Binder.getCallingUid(), activity.getPackageName());

            if (AppOpsManager.MODE_ALLOWED != property) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
