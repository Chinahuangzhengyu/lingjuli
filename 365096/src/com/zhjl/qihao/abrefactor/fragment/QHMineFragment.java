package com.zhjl.qihao.abrefactor.fragment;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abmine.AboutUSActivity;
import com.zhjl.qihao.abmine.MyCollectionActivity;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.freshshop.activity.ShopCarActivity;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.homemanage.activity.HomeManageActivity;
import com.zhjl.qihao.integration.activity.BalanceActivity;
import com.zhjl.qihao.myaction.activity.MyActionActivity;
import com.zhjl.qihao.order.activity.NewOrderServiceActivity;
import com.zhjl.qihao.propertyservicecomplaint.activity.MyComplaintActivity;
import com.zhjl.qihao.systemsetting.activity.PersonSettingsActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.order.activity.NewOrderTypeActivity;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class QHMineFragment extends VolleyBaseFragment {

    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.ll_mine_head)
    LinearLayout llMineHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_mine_confirm)
    LinearLayout llMineConfirm;
    @BindView(R.id.img_mine_head)
    CircleImageView imgMineHead;
    @BindView(R.id.tv_mine_sign)
    TextView tvMineSign;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_no_login)
    RelativeLayout rlNoLogin;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_all_order)
    TextView tvAllOrder;
    @BindView(R.id.rb_no_pay)
    RadioButton rbNoPay;
    @BindView(R.id.rb_no_send)
    RadioButton rbNoSend;
    @BindView(R.id.rb_no_receive)
    RadioButton rbNoReceive;
    @BindView(R.id.rb_no_evaluate)
    RadioButton rbNoEvaluate;
    @BindView(R.id.rb_after_sale)
    RadioButton rbAfterSale;
    @BindView(R.id.rb_no_pay_number)
    RadioButton rbNoPayNumber;
    @BindView(R.id.rb_no_send_number)
    RadioButton rbNoSendNumber;
    @BindView(R.id.rb_no_receive_number)
    RadioButton rbNoReceiveNumber;
    @BindView(R.id.rb_no_evaluate_number)
    RadioButton rbNoEvaluateNumber;
    @BindView(R.id.rb_after_sale_number)
    RadioButton rbAfterSaleNumber;
    @BindView(R.id.rb_mine_account)
    RadioButton rbMineAccount;
    @BindView(R.id.rb_cattle_selection)
    RadioButton rbCattleSelection;
    @BindView(R.id.rb_mine_complaint)
    RadioButton rbMineComplaint;
    @BindView(R.id.rb_mine_collection)
    RadioButton rbMineCollection;
    @BindView(R.id.rb_buy_car)
    RadioButton rbBuyCar;
    @BindView(R.id.rb_receive_address)
    RadioButton rbReceiveAddress;
    @BindView(R.id.rb_invitation)
    RadioButton rbInvitation;
    @BindView(R.id.rb_mine_person)
    RadioButton rbMinePerson;
    @BindView(R.id.rb_about_mine)
    RadioButton rbAboutMine;
    Unbinder unbinder;
    @BindView(R.id.sv_mine)
    ScrollView svMine;
    private View view;
    private PopupWindow shortCutPopWindow;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mSession = Session.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        NewStatusBarUtils.fullScreen(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            NewStatusBarUtils.fullScreen(getActivity());
        }
    }

    private void initData() {
        try {
            if (Integer.parseInt(mSession.getUserType()) == -1) {
                rlLogin.setVisibility(View.GONE);
                imgSetting.setVisibility(View.GONE);
                rlNoLogin.setVisibility(View.VISIBLE);
            } else {
                rlNoLogin.setVisibility(View.GONE);
                imgSetting.setVisibility(View.VISIBLE);
                rlLogin.setVisibility(View.VISIBLE);
                tvName.setText(mSession.getNick());
                tvMineSign.setText(mSession.getSign());
                PictureHelper.setHeadImageView(mContext, mSession.getSmallHeadPicPath(), imgMineHead, R.drawable.ic_head);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rlLogin.setVisibility(View.GONE);
            rlNoLogin.setVisibility(View.VISIBLE);
        }
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token",mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.allOrderStatusNumber(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        int waitPay = object.optInt("wait_pay");
                        int waitSend = object.optInt("wait_send");
                        int waitConfirm = object.optInt("wait_confirm");
                        int waitComment = object.optInt("wait_comment");
                        int afterSale = object.optInt("after_sale");
                        if (waitPay == 0) {    //待支付
                            rbNoPayNumber.setVisibility(View.INVISIBLE);
                        } else {
                            rbNoPayNumber.setVisibility(View.VISIBLE);
                        }
                        if (waitSend == 0) {   //待发货
                            rbNoSendNumber.setVisibility(View.INVISIBLE);
                        } else {
                            rbNoSendNumber.setVisibility(View.VISIBLE);
                        }
                        if (waitConfirm == 0) {    //待收货
                            rbNoReceiveNumber.setVisibility(View.INVISIBLE);
                        } else {
                            rbNoReceiveNumber.setVisibility(View.VISIBLE);
                        }
                        if (waitComment == 0) {    //待评价
                            rbNoEvaluateNumber.setVisibility(View.INVISIBLE);
                        } else {
                            rbNoEvaluateNumber.setVisibility(View.VISIBLE);
                        }
                        if (afterSale == 0) {    //售后
                            rbAfterSaleNumber.setVisibility(View.INVISIBLE);
                        } else {
                            rbAfterSaleNumber.setVisibility(View.VISIBLE);
                        }
                        rbNoPayNumber.setText(waitPay + "");
                        rbNoSendNumber.setText(waitSend + "");
                        rbNoReceiveNumber.setText(waitConfirm + "");
                        rbNoEvaluateNumber.setText(waitComment + "");
                        rbAfterSaleNumber.setText(afterSale + "");
                    } else {
                        int type = object.optInt("type");
                        if (type == 0) {
                            Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                        } else if (type == 1) {
                            Utils.loginPopWindow(getActivity());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_setting, R.id.ll_mine_confirm, R.id.img_mine_head, R.id.tv_login, R.id.tv_all_order, R.id.rb_no_pay, R.id.rb_no_send, R.id.rb_no_receive, R.id.rb_no_evaluate, R.id.rb_after_sale, R.id.rb_mine_account, R.id.rb_cattle_selection, R.id.rb_mine_complaint, R.id.rb_mine_collection, R.id.rb_buy_car, R.id.rb_receive_address, R.id.rb_invitation, R.id.rb_mine_person, R.id.rb_about_mine})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_setting:      //设置
                if (islogined()) {
                    intent.setClass(getActivity(), PersonSettingsActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    showtips();
                }
                break;
            case R.id.ll_mine_confirm:      //去认证
                break;
            case R.id.img_mine_head:        //头像
                if (islogined()) {
                    intent.setClass(getActivity(), PersonSettingsActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    showtips();
                }
                break;
            case R.id.tv_login:         //立即登录
                intent.setClass(mContext, UserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_all_order:     //全部订单
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderTypeActivity.class);
                    intent.putExtra("orderType", "查看所有订单");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_no_pay:        //待付款
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderTypeActivity.class);
                    intent.putExtra("orderType", "待付款");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_no_send:       //待发货
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderTypeActivity.class);
                    intent.putExtra("orderType", "待发货");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_no_receive:        //待收货
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderTypeActivity.class);
                    intent.putExtra("orderType", "待收货");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_no_evaluate:       //待评价
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderTypeActivity.class);
                    intent.putExtra("orderType", "已完成");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_after_sale:        //售后
                if (islogined()) {
                    intent.setClass(getActivity(), NewOrderServiceActivity.class);
                    intent.putExtra("orderType", "售后");
                    intent.putExtra("orderStatus", "all");
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_mine_account:      //我的账户
                if (islogined()) {
                    intent.setClass(getActivity(), BalanceActivity.class);
                    startActivity(intent);
                } else {
                    showtips();
                }
                break;
            case R.id.rb_cattle_selection:      //牛牛优选
                intent.setClass(mContext, MyCollectionActivity.class);
                intent.putExtra("name","牛牛优选");
                startActivity(intent);
                break;
            case R.id.rb_mine_complaint:        //我的投诉
                intent.setClass(mContext, MyComplaintActivity.class);
//                intent.putExtra("name","我的投诉");
                startActivity(intent);
                break;
            case R.id.rb_mine_collection:       //我的动态
                intent.setClass(mContext, MyActionActivity.class);
//                intent.setClass(mContext, MyCollectionActivity.class);
//                intent.putExtra("name","我的动态");
                startActivity(intent);
                break;
            case R.id.rb_buy_car:          //购物车
                intent.setClass(mContext, ShopCarActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_receive_address:       //收货地址
                intent.setClass(mContext, MyCollectionActivity.class);
                intent.putExtra("name","收货地址");
                startActivity(intent);
                break;
            case R.id.rb_invitation:        //邀请
                intent.setClass(mContext, HomeManageActivity.class);
//                intent.putExtra("name","邀请");
                startActivity(intent);
                break;
            case R.id.rb_mine_person:       //在线客服
                intent.setClass(mContext, UserAgreementActivity.class);
                intent.putExtra("name","在线客服");
                intent.putExtra("webContent","http://p.qiao.baidu.com/cps/chat?siteId=14492024&userId=29966678&cp=&cr=APP&cw=");
                startActivity(intent);
                break;
            case R.id.rb_about_mine:        //关于我们
                intent.setClass(mContext, AboutUSActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void showShortCut() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.exit_popupwindow, null);
        TextView title = (TextView) popView.findViewById(R.id.tv_title);
        TextView msg = (TextView) popView.findViewById(R.id.tv_msg);
        TextView yes = (TextView) popView.findViewById(R.id.yes_exit);
        TextView not = (TextView) popView.findViewById(R.id.not_exit);
        title.setVisibility(View.VISIBLE);
        msg.setText("创建开门快捷方式，需要开启应用的桌面快捷方式权限");
        yes.setText("去设置");
        not.setText("创建");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppDetailSettingIntent(mContext);
                shortCutPopWindow.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOpenDoor();
                shortCutPopWindow.dismiss();
            }
        });
        shortCutPopWindow = new PopupWindow(popView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        shortCutPopWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        shortCutPopWindow.setFocusable(true);
        shortCutPopWindow.setOutsideTouchable(true);
        shortCutPopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        shortCutPopWindow.showAtLocation(svMine, Gravity.CENTER, 0, 0);

    }

    private void getAppDetailSettingIntent(Context context) {
      /*  Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);*/
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    private void createOpenDoor() {
        Intent shortcutIntent = new Intent(getActivity(), RefactorMainActivity.class);//快捷方式跳转的Intent
        shortcutIntent.setAction(Intent.ACTION_VIEW);
        shortcutIntent.putExtra("shortcut", "opendoor");
        ShortcutManager shortcutManager = getContext().getSystemService(ShortcutManager.class);
        if (!shortcutManager.isRequestPinShortcutSupported()) {
            return; //不支持创建快捷方式   PinShortcut 为我们常见的桌面快捷方式
        }

        String action = "com.deniu.shortcut.create";
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //快捷方式创建完成的广播
                Toast.makeText(context, "创建成功！", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter = new IntentFilter(action);
        //注册接受结果的广播
        getContext().registerReceiver(receiver, filter);
        //快捷方式对象
        ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(getContext(), "qihao") //id
                .setShortLabel("开门")
                .setIcon(Icon.createWithResource(getContext(), R.drawable.icon_shortcut))
                .setIntent(shortcutIntent)
                .build();
        //PendingIntent
        Intent pinnedShortcutCallbackIntent = new Intent(action);
        PendingIntent successCallback = PendingIntent.getBroadcast(getContext(), 0,
                pinnedShortcutCallbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建快捷方式
        shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
    }
}
