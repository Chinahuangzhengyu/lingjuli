/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.zhjl.qihao.abcommon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.ablogin.api.LoginInterface;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abrefactor.utils.NetBroadcastReceiver;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.activity.userlogin.UserLoadParent;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.util.AbStrUtil;
import com.zhjl.qihao.util.HeaderBar;
import com.zhjl.qihao.util.JSONObjectUtil;
import com.zhjl.qihao.util.SsX509TrustManager;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.LoadingAlertDialog;
import com.zhjl.qihao.volley.VolleyRequestManager;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/***
 * @company 北京智慧精灵有限公司
 * @author 黄南榆
 * @date 2014年8月31日
 * @since Activity基类
 */
@SuppressLint("Registered")
public class VolleyBaseActivity extends FragmentActivity implements OnClickListener, RequestListener<JSONObject> {
    protected Context mContext;
    private LoadingAlertDialog dialog;

    TextView textView;

    protected Session mSession;
    private List<Call> calls;   //网络请求

    private boolean flag = true;
    private boolean isFullScreen = false;
    NetBroadcastReceiver receiver = new NetBroadcastReceiver();


    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();

        }
//        SetSpWayUtils.setDefault(this);
        //设置沉浸式
        if (isFullScreen) {
            NewStatusBarUtils.fullScreen(this);
        }
        ZHJLApplication.getInstance().addActivity(this);
        mContext = this;
        mSession = Session.get(mContext);
        JPushInterface.setAlias(getApplicationContext(), 111, mSession.getUserId());
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        receiver.setNetConnectedListener(isConnected ->
        {
            if (!isConnected) {
                if (mContext != null) {
                    Toast.makeText(mContext, "网络断开！", Toast.LENGTH_SHORT).show();
                }
//                if (!dialog.isShowing()){
//                    dialog.show();
//                }
            } else {
//                downloadAPK();
            }
        });
    }
    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    public void setActivityHeaderStyle(HeaderBar mHeaderBar) {
        findViewById(R.id.rl_header_bar).setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        mHeaderBar.getTextViewTitle().setTextColor(Color.parseColor("#262626"));
        mHeaderBar.getImageViewBack().setImageResource(R.drawable.ic_back);
        mHeaderBar.getBackLeftDes().setText("");
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            java.lang.reflect.Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSession = Session.get(mContext);
        /*
		友盟数据
		 */
        MobclickAgent.onPageStart(this.getClass().getName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
        友盟数据
		 */
        MobclickAgent.onPageEnd(this.getClass().getName());
        MobclickAgent.onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();//网络请求停止
//        VolleyRequestManager.cancelAll(this);
    }

    protected void executeRequest(Request<?> request) {
		/*
        超时时间10000，最大重试次数0
		 */
        SsX509TrustManager.allowAllSSL();
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyRequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrortoast();
                dismissdialog();
            }
        };
    }

    /*
    网络错误的结果处理
     */
    protected void errorListener2() {
        showErrortoast();
        dismissdialog();
    }


    protected void showErrortoast() {
        ToastUtils.showToast(mContext, "网络不给力，请稍后再试！");
    }

    protected void showErrortoast(String errorInfo) {
        ToastUtils.showToast(mContext, errorInfo);
        // ToastUtils.showToast(mContext, "网络不给力，请稍后再试！");
    }

    protected static void showErrortoast(Context mContext, String errorInfo) {
        ToastUtils.showToast(mContext, errorInfo);
        // ToastUtils.showToast(mContext, "网络不给力，请稍后再试！");
    }

    /**
     * 显示请求数据的processdialog
     */
    protected void
    showprocessdialog() {
        if (null == dialog) {
            dialog = new LoadingAlertDialog(mContext);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        } else if (!dialog.isShowing()) {
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
    }

    /**
     * 显示请求数据的processdialog
     */
    protected void showprocessdialog(boolean isCanceled) {

        if (null == dialog) {
            dialog = new LoadingAlertDialog(mContext);
            dialog.setCanceledOnTouchOutside(isCanceled);
            dialog.show();
        } else if (!dialog.isShowing()) {
            dialog.setCanceledOnTouchOutside(isCanceled);
            dialog.show();
        }
    }

    /**
     * 取消请求数据的processdialog
     */
    protected void dismissdialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * @return void
     * @throws
     * @description 退出Activity的动画
     * @version 1.0
     */
    public void exitActivity() {
        finish();
    }

    /**
     * 跳转Activity并关闭当前的Activity
     *
     * @return void
     * @throws
     * @description
     * @version 1.0
     */
    public void enterActivityWithFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    /**
     * @param intent
     * @return void
     * @throws
     * @description 跳转Activity不finish当前的activity
     * @version 1.0
     */
    public void enterAtivityNotFinish(Intent intent) {
        startActivity(intent);
    }


    private JSONObject getreadedinfo(String msgid) {
        Tools tools = new Tools(mContext, Constants.NEARBYSETTING);
        JSONObjectUtil jsonObject = new JSONObjectUtil();
        try {
            jsonObject.put("userId",
                    String.valueOf(tools.getValue(Constants.USERID)));
            jsonObject.put("messageId", msgid);
            return jsonObject;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Response.Listener<JSONObject> readedResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonobject) {
                try {
                    if (jsonobject.getString("result").equals("0")) {
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    // getTools 用来获取静态变量里面的参数
    public Tools getTools(Context mContext) {
        Tools tools = new Tools(mContext, Constants.NEARBYSETTING);
        return tools;
    }

    public String mAudioId;

    public String getmAudioId() {
        return mAudioId;
    }

    public void setmAudioId(String mAudioId) {
        this.mAudioId = mAudioId;
    }

    public String getmAudioPath() {
        return mAudioPath;
    }

    public void setmAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath;
    }

    public String mAudioPath;
    protected AlertDialog mAlertDialog;
    protected ProgressDialog mProgressDialog;
    public String audioPath = "";


    public static final int TAG_UPLOAD_SUCCESS = 0x011;
    public static final int TAG_UPLOAD_FAILD = 0x02;
    private boolean isDestroy;
    public MyHandler myHandler = new MyHandler(VolleyBaseActivity.this);

    private static class MyHandler extends Handler {
        private final WeakReference<VolleyBaseActivity> mActivity;
        private VolleyBaseActivity instance;

        public MyHandler(VolleyBaseActivity activity) {
            mActivity = new WeakReference<VolleyBaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            instance = mActivity.get();
            if (instance.isDestroy) {
                return;
            }
            instance.mProgressDialog.dismiss();
            if (msg.what == TAG_UPLOAD_SUCCESS) {
                instance.setmAudioId(instance.mAudioId);
                instance.setmAudioPath(instance.mAudioPath);
                AudioPath audioPath = AudioPath.getAudioPath();
                if (null == audioPath.getList_AudioPath()) {
                    List<String> path = new ArrayList<String>();
                    path.add(instance.mAudioPath);
                } else {
                    audioPath.getList_AudioPath().add(instance.mAudioPath);
                }
            } else if (msg.what == TAG_UPLOAD_FAILD) {
                instance.showUploadFaildDialog();
            }
        }
    }

    public static class AudioPath {
        public static AudioPath audioPath;
        private List<String> list_AudioPath;

        public static AudioPath getAudioPath() {
            if (audioPath == null) {
                audioPath = new AudioPath();
            }
            return audioPath;
        }

        public List<String> getList_AudioPath() {
            return list_AudioPath;
        }

        public void setList_AudioPath(List<String> list_AudioPath) {
            this.list_AudioPath = list_AudioPath;
        }
    }

    /**
     * 上传图片失败
     */
    private void showUploadFaildDialog() {
        mAlertDialog.show();
    }


    /**
     * =============================================================
     * ======================== 分享代码开始 =========================
     * =============================================================
     */


    public void showSharedDialog() {
        new ShareAction(VolleyBaseActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        new ShareAction(VolleyBaseActivity.this).withText("xxx")
                                .setPlatform(share_media).setCallback(umShareListener)
                                .share();
                    }
                }).open();
    }

    public void showSharedDialog(String text, final String file) {
        new ShareAction(VolleyBaseActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        ShareAction shareAction = new ShareAction(VolleyBaseActivity.this);
                        UMImage image = new UMImage(VolleyBaseActivity.this.getApplicationContext(), new File(file));
                        shareAction.withMedia(image);

                        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
                    }
                }).open();
    }

    //以链接的方式分享
    public void showSharedDialog2(final String text, final String url, final String photo, final String note) {
        new ShareAction(VolleyBaseActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        ShareAction shareAction = new ShareAction(VolleyBaseActivity.this);
                        UMImage image = new UMImage(VolleyBaseActivity.this.getApplicationContext(), photo);
                        UMWeb web = new UMWeb(url);
                        web.setTitle(text);//标题
                        web.setThumb(image);  //缩略图
                        if (note != null && !note.equals("")) {
                            web.setDescription(note);//描述
                        } else {
                            web.setDescription(text);
                        }

                        shareAction.withMedia(web);
                        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
                    }
                }).open();
    }

    public void showShareQQ(final Bitmap codes) {
        new ShareAction(this).withText("hello").withMedia(new UMImage(mContext, codes)).setPlatform(SHARE_MEDIA.QQ)
                .setCallback(umShareListener).share();
    }

    public void showShareWX(final Bitmap codes) {
        new ShareAction(this).withText("hello").withMedia(new UMImage(mContext, codes)).setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(VolleyBaseActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(VolleyBaseActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
//            Toast.makeText(VolleyBaseActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (throwable != null) {
                Log.d("throw", "throw:" + throwable.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(VolleyBaseActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * =============================================================
     * ======================== 分享代码结束 =========================
     * =============================================================
     */

    @Override
    protected void onDestroy() {
        callCancel();
        super.onDestroy();
        isDestroy = true;
        PopWindowUtils.getInstance().dismiss();
        if (Utils.loginPopWindow!=null&&Utils.loginPopWindow.isShowing()){
            Utils.loginPopWindow.dismiss();
        }
        if (Utils.callPopWindow!=null&&Utils.callPopWindow.isShowing()){
            Utils.callPopWindow.dismiss();
        }
        ZHJLApplication.getInstance().removeActivity(this);
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.ll_head_left) {
            finish();
        }

    }

    @Override
    public void requestSuccess(JSONObject result, int action) throws JSONException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestError(VolleyError e, int action) {
        // TODO Auto-generated method stub
        errorListener2();

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public void showtips() {
        Toast.makeText(mContext, getResources().getString(R.string.tips_please_login),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, UserLoginActivity.class);
        startActivity(intent);

    }

    public void showTips() {
        Toast.makeText(mContext, getResources().getString(R.string.tips_please_login),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, UserLoginActivity.class);
        intent.putExtra("Visitor", "visitor");
        startActivity(intent);
    }

    public boolean isLogin() {
        if (AbStrUtil.isEmpty(mSession.getRegisterMobile())) {
            Toast.makeText(this,
                    getResources().getString(R.string.tips_please_login),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,
                    UserLoadParent.class);
            startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return boolean
     * @throws
     * @description 判断是否登录
     * @version 1.0
     */
    public boolean islogin() {
        String userId = mSession.getUserId();
        String toneKey = mSession.getToneKey();
        if (!TextUtils.isEmpty(userId)) {
            return true;
        } else if (!TextUtils.isEmpty(toneKey)) {
            return true;
        } else {
            return false;
        }

    }

    protected void requestCheckLogin(final VolleyBaseActivity mContext) {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.checkLogin(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Intent intent = new Intent(mContext, RefactorMainActivity.class);
                startActivity(intent);
                mContext.finish();
            }

            @Override
            public void fail() {
                Intent intent = new Intent(mContext, UserLoginActivity.class);
                startActivity(intent);
                mContext.finish();
            }
        });
    }

    public <T> void activityRequestData(Call<ResponseBody> call, final Class<T> bean, final RequestResult<T> requestResult) {
        addCalls(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        string = string.replaceAll("\r|\n", "");
                        if (!string.equals("")) {
                            JSONObject parse = new JSONObject(string);
                            String message = parse.getString("message");
                            String result = parse.getString("code");
                            if (result.equals("200")) {
                                if (bean == null) {
                                    requestResult.success((T) string, message);
//									Toast.makeText(VolleyBaseActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    T t = gson.fromJson(string, bean);
                                    requestResult.success(t, message);
                                }
                            } else if (result.equals("403")) {
                                call.cancel();
                                mSession.clear();
                                JPushInterface.deleteAlias(mContext, 111);
                                ZHJLApplication.getInstance().finishAll();
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, UserLoginActivity.class);
                                startActivity(intent);
                            } else {
                                requestResult.fail();
                                Toast.makeText(VolleyBaseActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            requestResult.fail();
                            Toast.makeText(VolleyBaseActivity.this, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        requestResult.fail();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()){
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }
        });
    }

    public <T> void activityRequestPhpData(Call<ResponseBody> call, final RequestResult<T> requestResult) {
        addCalls(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        string = string.replaceAll("\r|\n", "");
                        if (!string.equals("")) {
                            requestResult.success((T) string, "");
                        } else {
                            requestResult.fail();
                            Toast.makeText(VolleyBaseActivity.this, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                        requestResult.fail();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()){
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }
        });
    }

    public interface RequestResult<T> {
        void success(T result, String message) throws Exception;

        void fail();
    }
}
