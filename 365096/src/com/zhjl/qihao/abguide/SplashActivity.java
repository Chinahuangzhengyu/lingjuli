package com.zhjl.qihao.abguide;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhjl.qihao.R;

import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abutil.NetWorkUtils;
import com.zhjl.qihao.abutil.SpUtils;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.chooseCity.activty.ChoosePropertyActivity;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.abutil.LogUtils;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public class SplashActivity extends VolleyBaseActivity {

    private final static int TRANSLATE_NOW = 0x001;
    private Timer timer_Reciprocal;
    private AlertDialog alertDialog;
    private TextView protocolAgree;
    private TextView protocolCancel;
    private View view;
    private SharedPreferences sharePreferences;
    private int mWidth;
    private int mHeight;
    private boolean isFirstOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFlag(false);
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//满屏
        /*if (Build.VERSION.SDK_INT >= 14) {//4.0以上隐藏菜单导航
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(params);
        }*/
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mWidth = point.x;
        mHeight = point.y;
        sharePreferences = getSharedPreferences("userProtocol",
                MODE_PRIVATE);
        // 判断是否是第一次开启应用
        isFirstOpen = SpUtils.getBoolean(this, Constants.FIRST_OPEN);
        LogUtils.e("splash", "onCreate()");
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            if (sharePreferences.getBoolean("isAgree",false)){
                Intent intent = new Intent(this, WelcomeGuideActivity.class);
                startActivity(intent);
                finish();
            }else {
                initPop();
            }
            return;
        }
    }

    private void initPop() {
        view = View.inflate(mContext, R.layout.user_protocol, null);
        TextView tvContent = view.findViewById(R.id.tv_content);
        SpannableStringBuilder builder = new SpannableStringBuilder(tvContent.getText().toString());
        ClickableSpan clickablePrivate = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //跳转隐私政策
                Intent intent = new Intent(SplashActivity.this, UserAgreementActivity.class);
                intent.putExtra("name","隐私政策");
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/private_prot.html");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(mContext,R.color.new_theme_color));
                ds.setUnderlineText(false);
            }
        };
        builder.setSpan(clickablePrivate,108,114, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableProtocol = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //跳转服务协议
                Intent intent = new Intent(SplashActivity.this, UserAgreementActivity.class);
                intent.putExtra("name","服务协议");
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/service_prot.html");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(mContext,R.color.new_theme_color));
                ds.setUnderlineText(false);
            }
        };
        builder.setSpan(clickableProtocol,115,121, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvContent.setText(builder);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());

        protocolAgree = view.findViewById(R.id.protocol_agree);
        protocolCancel = view.findViewById(R.id.protocol_cancel);
        protocolAgree.setOnClickListener(view -> {
                sharePreferences.edit().putBoolean("isAgree", true).commit();
                if (isFirstOpen){
                    loginType();
                }else {
                    Intent intent = new Intent(this, WelcomeGuideActivity.class);
                    startActivity(intent);
                    finish();
                }
                alertDialog.dismiss();
        });
        protocolCancel.setOnClickListener(view -> {
            alertDialog.dismiss();
            ZHJLApplication.getInstance().exitAPP();
        });
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setView(view);
        dialog.setCancelable(false);
        alertDialog = dialog.create();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtils.e("splash", "onStart()");
        if (NetWorkUtils.isConnect(mContext)) {
            //initSplashPicData();
            StartTimer();
        } else {
            //没有网络的时候的处理方式，暂时未处理
            StartTimer();
            //ToastUtils.showToast();
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtils.e("splash", "onStop()");
        if (null != timer_Reciprocal) {
            timer_Reciprocal.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("splash", "onDestroy()");
    }

    /**
     * 计算页面延时
     */
    private void StartTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TRANSLATE_NOW);
            }
        };
        timer_Reciprocal = new Timer(true);//定时器
        // timer_Reciprocal.schedule(task, 1000, 1000);//1s之后开始执行，然后每1s执行一次，
        timer_Reciprocal.schedule(task, 1000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    boolean isAgree = sharePreferences.getBoolean("isAgree", false);
                    if (isAgree) {
                        return;
                    }
                    if (alertDialog!=null&&!alertDialog.isShowing()) {
                        alertDialog.show();
                        if (alertDialog != null && alertDialog.getWindow() != null) {
                            Window dialogWindow = alertDialog.getWindow();
                            dialogWindow.setLayout(mWidth / 4 * 3, mHeight / 4 * 3);
                            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                        }
                    }
                    break;
                default:
                    if (sharePreferences.getBoolean("isAgree",false)){
                        loginType();
                    }else {
                        if (alertDialog!=null&&alertDialog.isShowing()) {
                           return;
                        }
                        initPop();
                    }
                    break;
            }

        }
    };

    private void loginType() {
        boolean login = mSession.isLogin();
        String smallCommunityName = mSession.getSmallCommunityName();
        if (login && smallCommunityName != null && !smallCommunityName.equals("")) {
            Intent intent = new Intent(mContext, RefactorMainActivity.class);
            startActivity(intent);
        } else if (login && smallCommunityName != null && smallCommunityName.equals("")) {
            Intent intent = new Intent(mContext, ChoosePropertyActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, UserLoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

}
