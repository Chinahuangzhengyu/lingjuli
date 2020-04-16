package com.zhjl.qihao.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class ScreenUtil {

    public ScreenUtil screenUtilInstance;
    private Context context;

    private ScreenUtil(Context context) {
        this.context = context;
    }

    public ScreenUtil() {
    }

    public synchronized ScreenUtil getScreenUtilInstance(Context context) {
        if (screenUtilInstance == null) {
            screenUtilInstance = new ScreenUtil(context);
        }
        return screenUtilInstance;
    }


    public int getVirtualKeyHeight() {
        //全面屏是否开启 0 关闭 1 开启
        if (Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0) != 0) {
            return 0;
        }
        //虚拟按键直接用原始高度和现在高度差，高度差就是虚拟按键高度
        int realHeight = getRawScreenSize(context)[1];
        int displayHeight = getScreenSize(context)[1];
        return realHeight - displayHeight;
    }


    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - display.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    //获取设备信息，来确定是否开启了全面屏
    private String getDeviceInfo() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        if (brand.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "navigation_gesture_on";
        } else {
            return "navigationbar_is_min";
        }
    }

    //获取原始的屏幕尺寸
    public static int[] getRawScreenSize(Context context) {
        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    //获取当前的屏幕尺寸
    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);

        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }
}
