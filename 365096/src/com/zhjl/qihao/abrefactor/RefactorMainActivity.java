package com.zhjl.qihao.abrefactor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abrefactor.bean.OpenDoorListBean;
import com.zhjl.qihao.abrefactor.bean.RoomBean;
import com.zhjl.qihao.abrefactor.fragment.ComplaintFragment;
import com.zhjl.qihao.propertyRepresentations.fragment.QHExposureFragment;
import com.zhjl.qihao.abrefactor.fragment.OpenDoorFragment;
import com.zhjl.qihao.abrefactor.fragment.QHMainFragment;
import com.zhjl.qihao.abrefactor.fragment.QHMineFragment;
import com.zhjl.qihao.abrefactor.utils.Utils;
import com.zhjl.qihao.abupapp.UpdateChecker;
import com.zhjl.qihao.abupapp.UpdateDialog;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.opendoor.api.OpenInterface;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.view.VerticalViewPager;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.abupapp.UpdateDialog.REQUEST_CODE_APP_INSTALL;
import static com.zhjl.qihao.abupapp.UpdateDialog.REQUEST_CODE_NOTIFICATION;
import static com.zhjl.qihao.abupapp.UpdateDialog.WRITE_EXTERNAL_CODE;
import static com.zhjl.qihao.abupapp.UpdateDialog.loadUrl;
import static com.zhjl.qihao.abupapp.UpdateDialog.mSavePath;
import static com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity.BINDING_ADDRESS_SUCCESS;
import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

/**
 * 标记tag v1.0.30
 */
public class RefactorMainActivity extends VolleyBaseActivity implements OnCheckedChangeListener {


    private static final int TAG_LOAD_THEFIRSTTIME = 0x002;
    private static final int TAG_LOAD_USERTYPE = 0x003;
    private static final int SYNC_STEP = 0x100;

    int[] guideopen = {R.drawable.opendoor_guide1, R.drawable.opendoor_guide2, R.drawable.opendoor_guide3, R.drawable.opendoor_guide4};

    /**
     * 网络超时
     */
    private static final int TAG_NETWORK_TIMEOUT = 0x004;// 网络超时
    /**
     * 需要更新
     */
    public static final int HAS_NEW = 0x005;
    /**
     * 不用更新
     */
    public static final int NO_NEW = 0x006;// 不用更新
    /**
     * 强制更新
     */
    public static final int FORCEDUPGRADD = 0x007;// 强制更新
    private Bundle bundle;
    private long exitTime = 0;// 记录上一次点击返回按钮的时间
    /**
     * Called when the activity is first created.
     */
    private long firstMillis = 0;// 用于保存时间戳
    private long timeLong = 600;// 时间戳
    private long onresumeMillis = 0;

    private ImageView ll_opendoor;
    //private HeaderBar mHeaderBar;
    private BluetoothAdapter mBluetoothAdapter;
    private LinearLayout parent_main;

    public PopupWindow customerPopWindow;
    private ImageView opendoor;
    private LinearLayout ll_content;

    private int first = 0;
    private ArrayList<RoomBean> roomArray;
    private boolean isRotate = false;
    private FragmentManager fManager;

    private RadioGroup rg_tab;
    private ComplaintFragment ComplaintFragment;
    private QHMainFragment mainFragment;
    private QHExposureFragment QHExposureFragment;
    private QHMineFragment mineFragment;                 // 新版我的

    private OpenDoorFragment openDoorFragment;

    private Fragment currentFragment;
    private int currentIndex = 0;
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private static final String CURRENT_ROOMLIST = "CURRENT_ROOM_DATA";
    private List<Fragment> fragments = new ArrayList<>();
    private static final int READ_SD_PERMISSION = 11111;
    private final MyHandler myHandler = new MyHandler(this);
    private TextView tvOpenDoorName;
    private ArrayList<OpenDoorListBean.DataBean> data;  //开门数据
    private String qrCode = "";
    List<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms = new ArrayList<>();

    private static class MyHandler extends Handler {
        private final WeakReference<RefactorMainActivity> mActivity;
        private RefactorMainActivity instance;

        public MyHandler(RefactorMainActivity activity) {
            mActivity = new WeakReference<RefactorMainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            instance = mActivity.get();
            if (msg.what == TAG_LOAD_THEFIRSTTIME) {
                try {
                    JSONObject jsonobject = new JSONObject(msg.obj.toString());
                    instance.mSession.setBasic(jsonobject);
                    if (jsonobject.has(Constants.RESULT)
                            && Constants.REQUEST_SUCCESS.equals(jsonobject.getString(Constants.RESULT))) {

                        if ((Constants.USER_VISITOR + "").equals(jsonobject.getString(Constants.USERTYPE))
                                && !jsonobject.has(Constants.NEWSMALLCOMMUNITYCODE)
                                && !jsonobject.has(Constants.LIST)) {

                            // 普通用户跳转到选择小区
                        } else if (jsonobject.has(Constants.LIST)) {

                            JSONArray jsonarr = jsonobject.getJSONArray(Constants.LIST);

                            for (int i = 0; i < jsonarr.length(); i++) {
                                JSONObject jsonObj = jsonarr.getJSONObject(i);
                                final Map<String, String> map = new HashMap<String, String>();
                                Iterator<String> it = jsonObj.keys();//在jsonobject中的获取所有的key值
                                while (it.hasNext()) {
                                    String key = it.next();
                                    String value = jsonObj.getString(key);
                                    map.put(key, value);
                                }
                                // houselist.add(map);
                            }


                        } else if (jsonobject.has(Constants.NEWSMALLCOMMUNITYCODE)) {

                        }

                    } else if ("3".equals(jsonobject.getString(Constants.RESULT))) {
                        ToastUtils.showToast(instance,
                                jsonobject.getString(Constants.MESSAGE));
                    } else if ("2".equals(jsonobject.getString(Constants.RESULT))) {
                        Toast.makeText(instance, jsonobject.getString(Constants.MESSAGE),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(instance,
                                jsonobject.getString(Constants.MESSAGE),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (msg.what == TAG_LOAD_USERTYPE) {
                try {
                    JSONObject jsonobject = new JSONObject(msg.obj.toString());
//					Log.i("111111", jsonobject + "");
                    if (jsonobject.has(Constants.RESULT) && jsonobject.get(Constants.RESULT).equals(Constants.REQUEST_SUCCESS)) {
                        if (jsonobject.has(Constants.USERTYPE)) { // 判断UserType是否更改
                            // 更改后才会执行下面的信息修改
                            if (jsonobject.has(Constants.NEWSMALLCOMMUNITYCODE)) {

                                instance.mSession.setSmallCommunityCode(jsonobject.getString(Constants.NEWSMALLCOMMUNITYCODE));
                            }
                            if (jsonobject.has(Constants.SMALLCOMMUNITYNAME)) {

                                instance.mSession.setSmallCommunityName(jsonobject.getString(Constants.SMALLCOMMUNITYNAME));
                            }

                            if (jsonobject.has(Constants.NEW_ROOM_CODE)) {

                                instance.mSession.setRoomCode(jsonobject.getString(Constants.NEW_ROOM_CODE));
                            }

                            if (jsonobject.has(Constants.ROOMNAME)) {
                                instance.mSession.setRoomName(jsonobject.getString(Constants.ROOMNAME));
                            }
                            if (jsonobject.has(Constants.USERTYPE)) {
                                instance.mSession.setUserType(jsonobject.getString(Constants.USERTYPE));
                            }
                        }

                    } else {
                    }
                } catch (JSONException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            } else if (msg.what == TAG_NETWORK_TIMEOUT) {
                instance.showErrortoast();
            } else if (msg.what == SYNC_STEP) {
                //   tv_health_step.setText(msg.arg1 + "");
            }
            super.handleMessage(msg);
        }
    }


    //开门回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (currentFragment != null && currentFragment != mainFragment) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
            return;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_APP_INSTALL) {
            if (loadUrl != null && !loadUrl.equals("") && mSavePath != null && !mSavePath.equals("")) {
                UpdateDialog.installAPK();
            } else {
                UpdateChecker.checkForDialog(this);
            }
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {     //从设置里打开了权限
            if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                if (loadUrl != null && !loadUrl.equals("") && mSavePath != null && !mSavePath.equals("")) {
                    UpdateDialog.installAPK();
                } else {
                    UpdateChecker.checkForDialog(this);
                }
            }
        } else if (resultCode == BINDING_ADDRESS_SUCCESS) {
            initData();
        }else if (requestCode==REQUEST_CODE_NOTIFICATION){  //通知权限开启返回
            UpdateChecker.checkForDialog(this);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
//        ZHJLApplication.getRefWatcher().watch(this);
        setContentView(R.layout.ab_activity_newmain);
        fManager = getSupportFragmentManager();
        //获取“内存重启”时保存的索引下标
        if (savedInstanceState != null) {
            fragments.clear();
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            mainFragment = (QHMainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "newMain");
            ComplaintFragment = (ComplaintFragment) getSupportFragmentManager().getFragment(savedInstanceState, "complaint");
            openDoorFragment = (OpenDoorFragment) getSupportFragmentManager().getFragment(savedInstanceState, "openDoor");
            QHExposureFragment = (QHExposureFragment) getSupportFragmentManager().getFragment(savedInstanceState, "sweetCicle");
            mineFragment = (QHMineFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mine");

            if (mainFragment == null) {
                mainFragment = new QHMainFragment();
            }
            if (ComplaintFragment == null) {
                ComplaintFragment = new ComplaintFragment();
            }
            if (QHExposureFragment == null) {
                QHExposureFragment = new QHExposureFragment();
            }
            if (mineFragment == null) {
                mineFragment = new QHMineFragment();
            }

            addToList(mainFragment);
            addToList(ComplaintFragment);
            if (openDoorFragment == null) {
                addToList(mainFragment);
            } else {
                addToList(openDoorFragment);
            }
            addToList(QHExposureFragment);
            addToList(mineFragment);
            showFragment(fragments.get(currentIndex));
            //恢复fragment页面
        } else {
            initTab();
        }
        String opendoor = getIntent().getStringExtra("shortcut");
        mSession.setServeNumb(0);//为了解决测试服务器切换到正式服务器遗留问题
        mSession.setScreenSize();//得到手机的尺寸
        long secondMillis = SystemClock.uptimeMillis();
        long value = secondMillis - firstMillis;
        //获取屏幕亮度
        int screenBrightness = Utils.getScreenBrightness(this);
        mSession.setScreenBrightness(screenBrightness);
        if (value > timeLong) {// 防止过快访问
            firstMillis = secondMillis;
            // 初始化控件
            initUI();
            if (opendoor != null && opendoor.equals("opendoor")) {
                openDoor();
            }
            initOpenDoorData();
        }
        //显示问卷调查
//        UpdateChecker.checkIsShowMainDialog(this);

        if (!RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            RequestPermissionUtils.requestPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_SD_PERMISSION);
        } else {
            UpdateChecker.checkForDialog(this);
        }
    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Call<ResponseBody> call = propertyPayInterface.userRoomList(ParamForNet.put(new HashMap<String, Object>()));
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData().size() > 0) {
                    rooms.clear();
                    for (int i = 0; i < result.getData().size(); i++) {
                        LoginBean.DataBean.UserInfoBean.UserRoomsBean roomsBean = new LoginBean.DataBean.UserInfoBean.UserRoomsBean();
                        roomsBean.setRoomName(result.getData().get(i).getRoomName());
                        roomsBean.setRoomId(result.getData().get(i).getRoomId());
                        roomsBean.setSmallCommunityName(result.getData().get(i).getSmallCommunityName());
                        rooms.add(roomsBean);
                    }
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initOpenDoorData() {
        OpenInterface openInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OpenInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = openInterface.lockGroupList(body);
        activityRequestData(call, OpenDoorListBean.class, new RequestResult<OpenDoorListBean>() {
            @Override
            public void success(OpenDoorListBean result, String message) throws Exception {
                data = result.getData();
                if (result.getData().size() > 0) {
                    requestOpenDoorCode(result.getData().get(0).getDoorAccessId(), result.getData().get(0).getAreaId());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestOpenDoorCode(String groupId, int areaId) {
        OpenInterface openInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OpenInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("doorAccessId", groupId);
        map.put("areaId", areaId + "");
        map.put("minutes", "30");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = openInterface.getOpenDoorCode(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                JSONObject data = object.optJSONObject("data");
                qrCode = data.getString("qrCode");
            }

            @Override
            public void fail() {

            }
        });
    }


    private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragments.add(fragment);
        }

//        android.util.Log.i("fragmentList数量", "//////" + fragments.size());
    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {

        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragment).commitAllowingStateLoss();
            /*添加到 fragmentList*/
            fragments.add(fragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        if (mainFragment != null && mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "newMain", mainFragment);
        }
        if (ComplaintFragment != null && ComplaintFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "complaint", ComplaintFragment);
        }
        if (openDoorFragment != null && openDoorFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "openDoor", openDoorFragment);
        }
        if (QHExposureFragment != null && QHExposureFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "sweetCicle", QHExposureFragment);
        }
        if (mineFragment != null && mineFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "mine", mineFragment);
        }
        if (roomArray != null) {
            outState.putSerializable(CURRENT_ROOMLIST, roomArray);
        }
        super.onSaveInstanceState(outState);
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {


        for (Fragment frag : fragments) {

            if (frag != fragment) {
                /*先隐藏其他fragment*/
                getSupportFragmentManager().beginTransaction().hide(frag).commitAllowingStateLoss();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
        currentFragment = fragment;
    }

    private void initUI() {
        //第一次进入开门页面的引导图
        opendoor = (ImageView) findViewById(R.id.opendoor);
        opendoor.setOnClickListener(this);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tvOpenDoorName = (TextView) findViewById(R.id.tv_open_door_name);
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        ll_opendoor = (ImageView) findViewById(R.id.ll_opendoor);
        ll_opendoor.setOnClickListener(this);
        if (currentIndex == 0) {
            rg_tab.check(R.id.tab_main);
        } else if (currentIndex == 2) {
            rg_tab.clearCheck();
        } else {
            RadioButton rb_tab = (RadioButton) rg_tab.getChildAt(currentIndex);
            rb_tab.setChecked(true);
        }
        rg_tab.setOnCheckedChangeListener(this);
        parent_main = (LinearLayout) findViewById(R.id.parent_main);
    }

    private void initTab() {
        LogUtils.e("Main", "--initFragment");
        if (mainFragment == null) {
            mainFragment = new QHMainFragment();
//            fragments.add(0,mainFragment);
            addFragment(mainFragment);
            showFragment(mainFragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.tab_main:
                RadioButton rb1 = (RadioButton) group.getChildAt(0);
                if (rb1.isChecked()) {
                    currentIndex = 0;
                    clickTab1Layout1();
                }
                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.ff999999));
                if (isRotate && rb1.isChecked()) {
                    runAnim(false);
                    isRotate = false;
                }
                break;
            case R.id.tab_arrivehome:
                if (rooms.size() == 0) {
                    RadioButton rb = (RadioButton) group.getChildAt(0);
                    rb.setChecked(true);
                    PopWindowUtils.getInstance().show("您还没有绑定住所，点击绑定吧！", this);
                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                        Intent intent = new Intent();
                        intent.setClass(mContext, AddHomeAddressBindingActivity.class);
                        intent.putExtra("isPopAddress", true);
                        startActivityForResult(intent, 0x166);
                    });
                    return;
                }
                RadioButton rb2 = (RadioButton) group.getChildAt(1);
                if (rb2.isChecked()) {
                    currentIndex = 1;
                    clickTab1Layout2();
                }
                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.ff999999));
                if (isRotate && rb2.isChecked()) {
                    runAnim(false);
                    isRotate = false;
                }
                break;
//            case R.id.tab_openDoor:
//                RadioButton rb3 = (RadioButton) group.getChildAt(2);
//                if (rb3.isChecked()) {
//                    currentIndex = 2;
//                    clickTab1Layout5();
//                }
//                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.ff999999));
//                if (isRotate && rb3.isChecked()) {
//                    runAnim(false);
//                    isRotate = false;
//                }
                /*if (islogin()) {
                } else {
                    Toast.makeText(instance, getResources().getString(R.string.tips_please_login), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserLoginActivity.class);
                    intent.putExtra("Visitor", "visitor");
                    startActivity(intent);

                }*/
//                break;
            case R.id.tab_circle:
                RadioButton rb4 = (RadioButton) group.getChildAt(4);
                if (rb4.isChecked()) {
                    currentIndex = 3;
                    clickTab1Layout3();
                }
                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.ff999999));
                if (isRotate && rb4.isChecked()) {
                    runAnim(false);
                    isRotate = false;
                }
                /*if (islogin()) {
                } else {
                    Toast.makeText(instance, getResources().getString(R.string.tips_please_login), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserLoginActivity.class);
                    intent.putExtra("Visitor", "visitor");
                    startActivity(intent);

                }*/
                break;
            case R.id.tab_mine:
                RadioButton rb5 = (RadioButton) group.getChildAt(5);
                if (rb5.isChecked()) {
                    currentIndex = 4;
                    clickTab1Layout4();
                }
                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.ff999999));
                if (isRotate && rb5.isChecked()) {
                    runAnim(false);
                    isRotate = false;
                }
                break;
        }

    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout1() {
        LogUtils.e("Main", "--fragment1");
        if (mainFragment == null) {
            mainFragment = new QHMainFragment();
        }
        addFragment(mainFragment);
        showFragment(mainFragment);
    }

    private void clickTab1Layout2() {
        /*Intent intent = new Intent(this, PayDemoActivity.class);
        startActivity(intent);*/
        LogUtils.e("Main", "--fragment2");
        if (ComplaintFragment == null) {
            ComplaintFragment = new ComplaintFragment();
        }
        addFragment(ComplaintFragment);
        showFragment(ComplaintFragment);
    }

    private void clickTab1Layout3() {
        LogUtils.e("Main", "--fragment3");
        if (QHExposureFragment == null) {
            QHExposureFragment = new QHExposureFragment();
        }
        addFragment(QHExposureFragment);
        showFragment(QHExposureFragment);
    }

    private void clickTab1Layout4() {
        LogUtils.e("Main", "--fragment4");
//        if (mineFragment == null) {
//            mineFragment = new MineFragment();
//        }
        if (mineFragment == null) {
            mineFragment = new QHMineFragment();
        }
        addFragment(mineFragment);
        showFragment(mineFragment);
    }

    private void clickTab1Layout5() {
        if (openDoorFragment == null) {
            openDoorFragment = OpenDoorFragment.getInstance(data, qrCode);
        } else {
            openDoorFragment.imgData(data, qrCode);
        }
        addFragment(openDoorFragment);
        showFragment(openDoorFragment);
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment, String strTag) {

        if (currentFragment == fragment)
            return;
        if (currentFragment == null) {
            transaction.add(R.id.content_layout, fragment, strTag).show(fragment).commitAllowingStateLoss();
        } else {
            if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
                transaction.hide(currentFragment)
                        .add(R.id.content_layout, fragment, strTag).commitAllowingStateLoss();
            } else {
                restoreFragment();
            }
        }

        currentFragment = fragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        long secondMillis = SystemClock.uptimeMillis();
        long value = secondMillis - onresumeMillis;
        if (value > timeLong) {// 防止过快访问
            onresumeMillis = secondMillis;

            //更新fragment中的数据
            if (null != mainFragment) {

            }
        }
        if (!getIntent().getBooleanExtra("Tourist", false)) {
            initData();
        }
    }

    private OrientationEventListener mOrientationHelper;
    private int mAlwaysChangingPhoneAngle = -1;

    @Override
    protected void onPause() {
        super.onPause();
//        VolleyRequestManager.cancelAll(this);//取消请求数据队列
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureHelper.clearMemoryCache();//清除缓存
//        unbindOpendoorbleService();
    }

    public Handler getHandler() {
        // TODO Auto-generated method stub
        return this.myHandler;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.opendoor:
                rg_tab.clearCheck();
                currentIndex = 2;
                first++;
                break;
            case R.id.ll_opendoor: { //点击开门
                initOpenDoorData();
                if (rooms.size() == 0) {
                    PopWindowUtils.getInstance().show("您还没有绑定住所，点击绑定吧！", this);
                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                        Intent intent = new Intent();
                        intent.setClass(mContext, AddHomeAddressBindingActivity.class);
                        intent.putExtra("isPopAddress", true);
                        startActivityForResult(intent, 0x166);
                    });
                    return;
                }
                if (!isRotate) {
                    runAnim(true);
                }
                isRotate = true;
                rg_tab.clearCheck();
                currentIndex = 2;
                tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.new_theme_color));
                openDoor();
            }
            break;
            case R.id.customer_phone:
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000365096"));//跳转到拨号界面，同时传递电话号码
                startActivity(callIntent);
                break;
            case R.id.btn_close:
                customerPopWindow.dismiss();
                break;
        }
    }

    private void runAnim(boolean isClick) {
        RotateAnimation anim = null;
        if (isClick) {
            anim = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setDuration(100);
        anim.setFillAfter(true);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setFillAfter(true);
        ll_opendoor.startAnimation(anim);
    }

    //进入开门页面的判断
    private void openDoor() {
        if (!isRotate) {
            runAnim(true);
        }
        isRotate = true;
        rg_tab.clearCheck();
        tvOpenDoorName.setTextColor(ContextCompat.getColor(mContext,R.color.new_theme_color));
        clickTab1Layout5();
    }


    public interface back {
        public boolean canback();

        public void backcab();
    }

    public interface reloadUrl {
        public void reloadUrl();

        public void loadLocalUrl(String url);
    }

    /*
    联系我们弹窗
     */
    private TextView customer_phone;
    private Button btn_close;

    private void initPopWindow() {
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        this.getWindow().setAttributes(lp);
        if (customerPopWindow == null) {
            @SuppressLint("InflateParams") View popView = LayoutInflater.from(this).inflate(R.layout.ab_select_customer, null);
            customerPopWindow = new PopupWindow(popView, mSession.getWidth(), VerticalViewPager.LayoutParams.MATCH_PARENT);
            //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
            customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
            Drawable draw = new ColorDrawable(ContextCompat.getColor(mContext,
                    android.R.color.transparent));
            customerPopWindow.setBackgroundDrawable(draw);
            customerPopWindow.setFocusable(true);
            customerPopWindow.setOutsideTouchable(true);
            customerPopWindow.setAnimationStyle(R.style.AnimationPopupPush);

            View ll_spec_bg = popView.findViewById(R.id.ll_spec_bg);

            //ll_spec_bg.getBackground().setAlpha(125);
            ll_spec_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerPopWindow.dismiss();
                }
            });
            customer_phone = (TextView) popView.findViewById(R.id.customer_phone);
            customer_phone.setOnClickListener(this);
            btn_close = (Button) popView.findViewById(R.id.btn_close);
            btn_close.setOnClickListener(this);
            customerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = RefactorMainActivity.this.getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    RefactorMainActivity.this.getWindow().setAttributes(lp);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(mContext, "再按一次退出零距离");
            exitTime = System.currentTimeMillis();
        } else {
            Tools tools = new Tools(mContext, Constants.NEARBYSETTING);
            tools.putValue(Constants.SOFTARTICLE_IS_SHOW, "true");
            ZHJLApplication.getInstance().exitAPP();
        }
    }


    @Override
    public void requestError(VolleyError e, int action) {
        super.requestError(e, action);
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment() {

        FragmentTransaction mBeginTreansaction = fManager.beginTransaction();

        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) != null) {
                if (i == currentIndex) {
                    mBeginTreansaction.show(fragments.get(i));
                } else {
                    mBeginTreansaction.hide(fragments.get(i));
                }
            }
        }

        mBeginTreansaction.commitAllowingStateLoss();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_SD_PERMISSION) {
            boolean isAllGranted = true;
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false;
                    if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            RequestPermissionUtils.showShortCut2(this, "开启app升级需要存储空间权限！", ll_content);
                        }
                    }

                }
            }
            if (isAllGranted) {
                UpdateChecker.checkForDialog(this);
            } else {
                if (!RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.READ_CONTACTS})) {
                    Toast.makeText(mContext, "开启app升级需要存储空间权限！", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == WRITE_EXTERNAL_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    RequestPermissionUtils.showShortCut3(this, "开启app升级需要存储空间权限！", ll_content);
                    return;
                }
                Toast.makeText(mContext, "开启app升级需要存储空间权限！", Toast.LENGTH_SHORT).show();
            } else {
                UpdateChecker.checkForDialog(this);
            }
        }
    }
}