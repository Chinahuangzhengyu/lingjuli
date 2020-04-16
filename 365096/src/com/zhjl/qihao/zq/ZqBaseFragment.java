package com.zhjl.qihao.zq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * time   :  2018/9/22
 * author :  Z
 * des    :   所有 Fragment 的base
 */
public abstract class ZqBaseFragment extends Fragment {

    private static String TAG = "== BaseFragment";
    public Unbinder bind;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Unbinder unbinder;
    @BindView(R.id.fake_status_bar)
    View statusBar;
    @BindView(R.id.t_right)
    TextView tRight;
    @BindView(R.id.i)
    ImageView i;
    @BindView(R.id.i_right)
    LinearLayout iRight;

    private FrameLayout containers;
    protected Activity activity;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;


    public ZqBaseFragment() {
    }


    public TextView gettRight() {
        return tRight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.zq_fragment_base, container, false);
        containers = view.findViewById(R.id.container);
        if (getContentView(getResId()) != null) {
            containers.addView(getContentView(getResId()));
            bind = ButterKnife.bind(this, view);
            initView(savedInstanceState);
        } else {
            view = initView(savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    private View getContentView(int ResId) {

        return LayoutInflater.from(activity).inflate(ResId, containers, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();

        initToolBar();

    }

    /**
     * 表示 Fragment 的 UI 对于用户是否可见   ----- 懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;

            lazyLoad();


        } else {
            isUIVisible = false;
        }
    }

    /**
     * 设置懒加载    就是在判断界面用户可见的时候才去加载数据;
     */
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {

            initDatas();

            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

//            Log.e(TAG, " 界面可见 ，可以加载数据 ");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    protected void setStatusBar(int color, boolean darkFont) {

        statusBar.setBackgroundColor(getResources().getColor(color));

        // 状态栏的高度
        int statusBarHeight = getStatusBarHeight(activity);
        statusBar.getLayoutParams().height = statusBarHeight;


        setImmerse(darkFont);
    }

    /**
     * 动态设置 沉浸式状态栏   默认设置为 toolbar  一样的颜色
     * 也是需要 有  View = statusBar ; 和 toolBar;
     */
    protected void setStatusBar(boolean darkFont) {

        if (isShowToolBar()) {
            int color = ((ColorDrawable) toolbar.getBackground().mutate()).getColor();
            setStatusBar(color, darkFont);
        }

    }


    /**
     * 仅仅设置为 全屏显示
     */
    protected void setImmerse(boolean darkFont) {
        // 设置statusBar 的字体颜色  黑或者白   只有这两种颜色
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (darkFont) {     //  黑色

            option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;


        } else {           // 白色

            option = option | 0;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(option);


    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    protected static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    // 子类可以通过重写 这个方法来实现是否显示 ToolBar;
    protected boolean isShowToolBar() {
        return true;
    }


    private void initToolBar() {

        // 一般是把back 隐藏掉的吧
        back.setVisibility(View.GONE);

        if (!isShowToolBar() && toolbar != null) {
            // 好像没有效果
//            containers.removeView(toolbar);
            // 先这么隐藏着  ...趴
            toolbar.setVisibility(View.GONE);
        }

//        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("");
        back();
    }


    protected LinearLayout getBack() {
        return back;
    }

    /**
     * 仅仅显示中间的 标题
     *
     * @param character
     */
    protected void setTitle(@NonNull String character) {
        if (title != null) {
            title.setText(character);
        }
        tRight.setVisibility(View.GONE);
        iRight.setVisibility(View.GONE);
    }

    /**
     * 设置标题 和右边文字
     *
     * @param st
     * @param rt
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
        iRight.setOnClickListener(listener);
    }


    // Fragment  用到 back  极少;
    private void back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
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
        Intent intent = new Intent(activity, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        bind.unbind();
    }


    protected abstract void initDatas();


    /**
     * 可以做一些保存状态的 操作
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract View initView(Bundle savedInstanceState);

    protected abstract int getResId();


}
