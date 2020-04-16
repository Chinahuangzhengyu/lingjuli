package com.zhjl.qihao.zq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * time   :  2018/11/7
 * author :  Z
 * des    :   单独封装 BaseActivity;
 */
public abstract class ZqBaseActivity extends AppCompatActivity {

    private static String TAG = "== BaseActivity =";
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.t_right)
    TextView tRight;

    @BindView(R.id.fake_status_bar)
    View statusBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.i_right)
    LinearLayout iRight;
    @BindView(R.id.i)
    ImageView i;

    private FrameLayout container;
    Unbinder bind;
    protected Activity mCurrentActivity;

    private ViewGroup viewGroup;

    // Activity 管理栈
    private long mPreTime;                                                               // 用来记录首页返回键时间;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //  添加快速更换颜色
//        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        viewGroup = findViewById(android.R.id.content);  // (ViewGroup) getWindow().getDecorView().getRootView()

        View view = LayoutInflater.from(this).inflate(R.layout.zq_activity_base, viewGroup, false);
        container = view.findViewById(R.id.container);


        // 这里是动态添加 toolbar   ------ 但是这里不科学  ，如果没有引用 toolbar .butterknife 就会报错;
//        if (isShowToolBar()) {
//            View actionBar = LayoutInflater.from(this).inflate(R.layout.toolbar_base, viewGroup, false);
//            viewGroup.addView(actionBar);
//        }


        if (getContentView(getResId()) != null) {
            container.addView(getContentView(getResId()));
        }


        setContentView(view);
        ButterKnife.bind(this);

        //////////////////////////////////   所有相关操作 从这里开始吧  ///////////////////////////////////////

        // 设置沉浸式状态栏; 目前先设置所有的状态栏颜色都为黑色
//        setStatusBar(true);
        initToolbar();


//        // 绑定EventBus
//        if (isRegisterEventBus()) {
//            EventBusUtil.register(this);
//        }

        initDatas();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 接收事件分发
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventBusCome(Event event) {
//        if (event != null) {
//            receiveEvent(event);
//        }
//    }

//    /**
//     * 接收到分发到事件
//     *
//     * @param event 事件
//     */
//    protected void receiveEvent(Event event) {
//
//    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        bind = ButterKnife.bind(this);

    }


    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public int getVirtualBarHeigh(Context context) {
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
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    /**
     * 计算状态栏高度;
     *
     * @return
     */
    private int getStatusBarHeight() {
        // 获得状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 动态设置 沉浸式状态栏
     * LayoutParams  是根据父布局来 定义的 ，现在先使用LinearLayout.Params. 就要求使用这个方法的跟布局使用LinearLayout ;
     * true ：  黑色吧
     * false：  白色
     */
    protected void setStatusBar(boolean darkFont) {


        if (isShowToolBar()) {
            int color = ((ColorDrawable) toolbar.getBackground().mutate()).getColor();
            statusBar.setBackgroundColor(color);
        }

        // 状态栏的高度
        int statusBarHeight = getStatusBarHeight();
        statusBar.getLayoutParams().height = statusBarHeight;


//        if (checkDeviceHasNavigationBar(mCurrentActivity)) {    // 开启了底部导航栏
//            Log.e(TAG, "判断如果开启了底部导航栏，设置一个 导航");
//        } else {
//        }


        // 设置statusBar 的字体颜色  黑或者白   只有这两种颜色
        int option = /*View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | */View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        // 修改状态栏字体颜色
        if (darkFont) {
            option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;


        } else {

            option = option | 0;
        }

        getWindow().getDecorView().setSystemUiVisibility(option);
    }


    //  初始化 ToolBar;
    protected void initToolbar() {
        if (!isShowToolBar()) {
            // 好像没有效果
            viewGroup.removeView(toolbar);
            // 先这么隐藏着  ...趴
            if (toolbar != null) {
                toolbar.setVisibility(View.GONE);
            }
        }

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        back();
    }

    /**
     * 默认 显示 ToolBar ;
     *
     * @return
     */
    protected boolean isShowToolBar() {
        return true;
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 仅仅显示中间的 标题
     *
     * @param character
     */
    protected void setTitle(@NonNull String character) {
        if (!isShowToolBar()) return;

        if (title != null) {
            title.setText(character);
        }

        tRight.setVisibility(View.GONE);
        iRight.setVisibility(View.GONE);

        int toolBarColor = ((ColorDrawable) toolbar.getBackground().mutate()).getColor();
        back.setBackground(setPressedDrawable(toolBarColor, R.color.ZqGrey));
    }

    /**
     * 设置标题 和右边文字
     *
     * @param st       标题
     * @param rt       右边文字
     * @param listener
     */
    protected void setRightText(String st, String rt, View.OnClickListener listener) {
        tRight.setVisibility(View.VISIBLE);
        iRight.setVisibility(View.GONE);

        if (title != null) {
            title.setText(st);
        }
        if (tRight != null) {
            tRight.setText(rt);
            tRight.setOnClickListener(listener);
        }

    }


    /**
     * 设置右边图片
     *
     * @param resId
     * @param listener
     */
    protected void setRightImage(@NonNull String titles, int resId, View.OnClickListener listener) {
        if (title != null) {
            title.setText(titles);
        }
        tRight.setVisibility(View.GONE);
        iRight.setVisibility(View.VISIBLE);
        i.setImageResource(resId);
        i.setOnClickListener(listener);
    }


    /**
     * 控件可以直接 view.setBackground();
     *
     * @param normalColor
     * @param pressedColor
     * @return
     */
    private Drawable setPressedDrawable(int normalColor, int pressedColor) {
        // 动态设置 toolbar的 颜色
        ColorDrawable normalDrawable = new ColorDrawable(normalColor);
        ColorDrawable pressDrawable = new ColorDrawable(getResources().getColor(pressedColor));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);

        return stateListDrawable;
    }


    /**
     * 返回键
     */
    private void back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public LinearLayout getBack() {
        return back;
    }


    // 获取到标题 ;
    public TextView getCenterTitle() {
        return title;
    }

    public TextView gettRight() {
        return tRight;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        bind.unbind();

//        activityManager.finishAllActivity();

        // 解绑EventBus
//        if (isRegisterEventBus()) {
//            EventBusUtil.unregister(this);
//        }

    }


    protected abstract void initDatas();

    protected abstract int getResId();

    private View getContentView(int ResId) {

        return LayoutInflater.from(this).inflate(ResId, container, false);
    }


    /**
     * 界面跳转
     */
    protected void toGo(Class<?> clazz) {

        toGo(clazz, null);
    }


    /**
     * 界面跳转 传参
     *
     * @param clazz
     * @param bundle
     */
    protected void toGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 界面 跳转 然后kill
     *
     * @param clazz
     */
    protected void toGoThenKill(Class<?> clazz) {
        toGo(clazz, null);
        finish();
    }

    /**
     * 界面 跳转 传参，然后 kill;
     *
     * @param clazz
     * @param bundle
     */
    protected void toGoThenKill(Class<?> clazz, Bundle bundle) {
        toGo(clazz, bundle);
        finish();
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }


}
