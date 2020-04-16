package com.zhjl.qihao.util;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/23
 * 类作用：刘海屏操作类
 */
public class SettingScreenUtils {
    private static  int screenHeight = 0;
    /**
     * 设置刘海屏
     */
//    public static int settingScreen(final Activity context) {
//        if (Build.VERSION.SDK_INT >= 28) {
//            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
////			lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
////			lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//            final View decorView = context.getWindow().getDecorView();
//
//            decorView.post(new Runnable() {
//                @Override
//                public void run() {
//                    DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
//                    Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
//                    Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
//                    Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
//                    Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
//
//                    List<Rect> rects = displayCutout.getBoundingRects();
//                    if (rects == null || rects.size() == 0) {
//                        Log.e("TAG", "不是刘海屏");
//                    } else {
//                        Log.e("TAG", "刘海屏数量:" + rects.size());
//                        for (Rect rect : rects) {
//                            Log.e("TAG", "刘海屏区域：" + rect);
//                        }
//                    }
//                    screenHeight = displayCutout.getSafeInsetTop();
////                    FrameLayout contentView =  (FrameLayout)context.findViewById(android.R.id.content);
////                    contentView.setPadding(0,displayCutout.getSafeInsetTop(),0,0);
//                }
//            });
//            context.getWindow().setAttributes(lp);
//        }else{
//                boolean hasNotchAtHuawei = StatusBarUtils.hasNotchAtHuawei(context);
//                if (hasNotchAtHuawei){
//                    int[] sizeAtHuawei = StatusBarUtils.getNotchSizeAtHuawei(context);
//                    screenHeight = sizeAtHuawei[1];
//                }
//                boolean hasNotchAtOPPO = StatusBarUtils.hasNotchAtOPPO(context);
//                if (hasNotchAtOPPO){
//                    screenHeight = 80;
//                }
//                boolean hasNotchAtVivo = StatusBarUtils.hasNotchAtVivo(context);
//                if (hasNotchAtVivo){
//                    screenHeight = Utils.dip2px(context,27);
//                }else if (Settings.Global.getInt(context.getContentResolver(), "force_black", 0) == 1){
//                    int statusBarHeight = StatusBarUtils.getStatusBarHeight(context);
//                    screenHeight = statusBarHeight;
//                }else{
//                    screenHeight = 0;
//                }
//        }
//        return screenHeight;
//    }
}
