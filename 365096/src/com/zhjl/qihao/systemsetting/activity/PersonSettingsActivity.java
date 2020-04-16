package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.ablogin.activity.UserRegisterActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.activity.userlogin.UserLoadParent;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.UrlChangeUtils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import net.tsz.afinal.FinalDb;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PersonSettingsActivity extends VolleyBaseActivity {

    @BindView(R.id.rl_update_nick)
    RelativeLayout rlUpdateNick;
    @BindView(R.id.rl_address_sure)
    RelativeLayout rlAddressSure;
    @BindView(R.id.rl_update_login_password)
    RelativeLayout rlUpdateLoginPassword;
    @BindView(R.id.rl_logout_user)
    RelativeLayout rlLogoutUser;
    @BindView(R.id.rl_exit)
    Button rlExit;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    @BindView(R.id.rl_message_send)
    RelativeLayout rlMessageSend;
    @BindView(R.id.sb_url_change)
    SwitchButton sbUrlChange;
    private RelativeLayout rl_update_nick;
    private RelativeLayout rl_update_login_password;
    private TextView rl_exit;

    private FinalDb fd;

    public static final int REQUEST_ADD_PHOTO = 1;
    private PopupWindow exitPopWindow;
    private boolean isLose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_settings);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "个人设置", this);
        isLose = mSession.isTest();
        sbUrlChange.setChecked(isLose);
        initView();

    }

    //初始化控件
    private void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //退出弹出框
    private void showExitPop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.exit_popupwindow, null);
        TextView yes = (TextView) popView.findViewById(R.id.yes_exit);
        TextView not = (TextView) popView.findViewById(R.id.not_exit);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestExit();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitPopWindow.dismiss();
            }
        });
        exitPopWindow = new PopupWindow(popView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
        exitPopWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        exitPopWindow.setFocusable(true);
        exitPopWindow.setOutsideTouchable(true);
        exitPopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        exitPopWindow.showAtLocation(llExit, Gravity.CENTER, 0, 0);
    }

    private void requestExit() {
        MainApiInterfaces mainApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = mainApiInterfaces.userExit(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                mSession.clear();
                ZHJLApplication.getInstance().finishAll();
                JPushInterface.deleteAlias(mContext, 111);
                Intent intent = new Intent(mContext, UserLoginActivity.class);
                enterActivityWithFinish(intent);
                exitPopWindow.dismiss();
            }

            @Override
            public void fail() {
                exitPopWindow.dismiss();
            }
        });
    }

    @OnClick({R.id.rl_update_nick, R.id.rl_address_sure, R.id.rl_update_login_password, R.id.rl_logout_user, R.id.rl_exit, R.id.iv_back, R.id.rl_message_send, R.id.sb_url_change})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_update_nick:
                if (TextUtils.isEmpty(mSession.getUserId())) {
                    Toast.makeText(this, "您还未登录，请登录后使用",
                            Toast.LENGTH_SHORT).show();
                    intent.setClass(this, UserLoadParent.class);
                    startActivity(intent);

                } else {
                    intent.setClass(this, MyEditorActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_address_sure:
                if (TextUtils.isEmpty(mSession.getUserId())) {
                    Toast.makeText(this, "您还未登录，请登录后使用",
                            Toast.LENGTH_SHORT).show();
                    intent.setClass(this, UserLoadParent.class);
                    startActivity(intent);

                } else {
                    intent.setClass(this, HomeAddressBindingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_update_login_password:
                if (TextUtils.isEmpty(mSession.getUserId())) {
                    Toast.makeText(this, "您还未登录，请登录后使用",
                            Toast.LENGTH_SHORT).show();
                    intent.setClass(this, UserLoadParent.class);
                    startActivity(intent);
                } else {
                    intent.setClass(mContext, UserRegisterActivity.class);
                    intent.putExtra("isUpdate", true);
                    startActivity(intent);
                }
                break;
            case R.id.rl_logout_user:

                break;
            case R.id.rl_exit:
                showExitPop();//页面
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_message_send:
                intent.setClass(mContext, MessageSendActivity.class);    //意见反馈
                startActivity(intent);
                break;
            case R.id.sb_url_change:
                isLose = !isLose;
                sbUrlChange.toggle();
                mSession.setTest(isLose);
                UrlChangeUtils.reject(new UrlChangeUtils(), isLose);
                break;
        }
    }
}
