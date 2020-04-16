package com.zhjl.qihao.abguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abutil.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎页
 *
 * @author wwj_748
 */
public class WelcomeGuideActivity extends VolleyBaseActivity implements OnClickListener {

    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private TextView startBtn;

    // 引导页图片资源
    private static final int[] pics = {R.layout.guid_view1,
            R.layout.guid_view2, R.layout.guid_view3, R.layout.guid_view4};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab_activity_guide);

        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);

            if (i == pics.length - 1) {
                startBtn = (TextView) view.findViewById(R.id.btn_login);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }

            views.add(view);

        }

        vp = (ViewPager) findViewById(R.id.vp_guide);
        // 初始化adapter
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new PageChangeListener());

        initDots();

    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_dot);
        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            if (i == 0) {
                dots[i].setImageResource(R.drawable.splash_checked);
            } else {
                dots[i].setImageResource(R.drawable.splash_check);
            }
        }
        currentIndex = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 第一次引导图的判断，如果切换到后台，就设置下次不进入功能引导页，
        SpUtils.putBoolean(WelcomeGuideActivity.this, Constants.FIRST_OPEN, true);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }


    private void enterMainActivity() {

        Intent intent = new Intent(WelcomeGuideActivity.this,
                UserLoginActivity.class);
        startActivity(intent);

        SpUtils.putBoolean(WelcomeGuideActivity.this, Constants.FIRST_OPEN, true);
        finish();
    }

    private class PageChangeListener implements OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            // 循环取得小点图片
            for (int i = 0; i < pics.length; i++) {
                if (i == position) {
                    dots[i].setImageResource(R.drawable.splash_checked);
                } else {
                    dots[i].setImageResource(R.drawable.splash_check);
                }
            }
        }

    }
}
