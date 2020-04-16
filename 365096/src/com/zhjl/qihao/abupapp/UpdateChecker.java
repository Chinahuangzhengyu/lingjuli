package com.zhjl.qihao.abupapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.UploadAppBean;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UpdateChecker {
    private static UpdateDialog updateDialog = new UpdateDialog();
    private static MainDialog mainDialog = new MainDialog();

    public static void checkForDialog(final VolleyBaseActivity context) {
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                MainApiInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(context).createService(MainApiInterfaces.class);
                Map<String, Object> map = new HashMap<>();
                map.put("appType", "0");
                map.put("appVersion", packageInfo.versionName);
                RequestBody body = ParamForNet.put(map);
                Call<ResponseBody> call = apiInterfaces.checkApp(body);
                context.activityRequestData(call, UploadAppBean.class, new VolleyBaseActivity.RequestResult<UploadAppBean>() {
                    @Override
                    public void success(UploadAppBean result, String message) throws Exception {
                        String appVersion = result.getData().getAppVersion();
                        String downloadUrl = result.getData().getDownloadUrl();
                        int forceUpdate = result.getData().getForceUpdate();
                        boolean updateApp = updateApp(packageInfo.versionName, appVersion);
                        if (updateApp) {
                            if (forceUpdate == 1) {
                                updateDialog.show(context, result.getData().getUpdateLog(), downloadUrl, appVersion, true);
                            } else {
                                updateDialog.show(context, result.getData().getUpdateLog(), downloadUrl, appVersion, false);
                            }
                        } else {
                            if (!context.getIntent().getBooleanExtra("Tourist", false)){
                                checkIsShowMainDialog(context);
                            }
                        }
                    }

                    @Override
                    public void fail() {

                    }
                });
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    /**
     * 判断版本更新
     *
     * @param localVersion 本地app 版本号
     * @param newVersion   最新版本号
     * @return true 需要更新 false 不用
     */
    public static boolean updateApp(String localVersion, String newVersion) {
        String[] localVersionArray = localVersion.split("\\.");
        String[] newVersionArray = newVersion.split("\\.");

//        int code = 0;
//        int newCode = 0;
//        try {
//            for (int i = 0; i < localVersionArray.length; i++) {
//                int localCode = Integer.parseInt(localVersionArray[i]);
//                code += localCode;
//            }
//            for (int i = 0; i < newVersionArray.length; i++) {
//                int localCode = Integer.parseInt(newVersionArray[i]);
//                newCode += localCode;
//            }
//            if (newCode > code) {
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        int idx = 0;
        // 取数组最小长度值
        int minLength = Math.min(localVersionArray.length, newVersionArray.length);
        int diff = 0;
        // 先比较长度，再比较字符
        while (idx < minLength
                && (diff = localVersionArray[idx].length() - newVersionArray[idx].length()) == 0
                && (diff = localVersionArray[idx].compareTo(newVersionArray[idx])) == 0) {
            ++idx;
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大
        diff = (diff != 0) ? diff : localVersionArray.length - newVersionArray.length;
        if (diff < 0) {
            return true;
        }
        return false;
    }


    /**
     * 首页推广信息接口，游客模式不请求
     * @param context
     */
    public static void checkIsShowMainDialog(final VolleyBaseActivity context) {
        if (context != null) {
            try {
                MainApiInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(context).createService(MainApiInterfaces.class);
                Map<String, Object> map = new HashMap<>();
                RequestBody body = ParamForNet.put(map);
                Call<ResponseBody> call = apiInterfaces.getMainMessage(body);
                context.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
                    @Override
                    public void success(Object result, String message) throws Exception {
                        JSONObject object = new JSONObject((String) result);
                        JSONArray data = object.optJSONArray("data");
                        if (data.length() > 0) {
                            JSONObject dataObj = data.getJSONObject(0);
                            String pic = dataObj.optString("pic");
                            String url = dataObj.optString("url");
                            if (updateDialog.dialog == null || !updateDialog.dialog.isShowing()) {
                                mainDialog.show(context, pic, url);
                            }
                        }
                    }

                    @Override
                    public void fail() {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }
}
