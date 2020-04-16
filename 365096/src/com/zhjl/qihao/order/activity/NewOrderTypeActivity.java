package com.zhjl.qihao.order.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.order.fragment.AllOrderFragment;
import com.zhjl.qihao.util.NewHeaderBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： 黄郑宇
 * 时间： 2018/5/28
 * 类作用：共用的订单查询类型(除售后)
 */

public class NewOrderTypeActivity extends VolleyBaseActivity {


    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.not_data)
    FrameLayout notData;
    private List<Fragment> listFragments = new ArrayList<>();
    private String orderType = "";
    private String[] typeList = {"全部", "待付款", "待发货", "待收货", "已完成"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_type);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        NewHeaderBar.createCommomBack(this, "我的订单", this);
        listFragments.add(AllOrderFragment.getInstance(-1));
        listFragments.add(AllOrderFragment.getInstance(0));
        listFragments.add(AllOrderFragment.getInstance(1));
        listFragments.add(AllOrderFragment.getInstance(2));
        listFragments.add(AllOrderFragment.getInstance(3));
//        listFragments.add(new NoPayFragment());
//        listFragments.add(new NoSendFragment());
//        listFragments.add(new NoReceiverFragment());
//        listFragments.add(new FinishOrderFragment());
        vpOrder.setAdapter(new OrderFragmentAdapter(getSupportFragmentManager()));
        tabOrder.setupWithViewPager(vpOrder);
        vpOrder.setOffscreenPageLimit(listFragments.size());
        for (int i = 0; i < tabOrder.getTabCount(); i++) {
            tabOrder.getTabAt(i).setCustomView(R.layout.tab_item);
            TextView tabItemText = tabOrder.getTabAt(i).getCustomView().findViewById(R.id.tab_item_text);
            if (i == 0) {
                tabItemText.setText(typeList[0]);
                tabItemText.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tabItemText.setText(typeList[i]);
                tabItemText.setTypeface(Typeface.DEFAULT);
            }
        }

        tabOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                vpOrder.setCurrentItem(tab.getPosition(), false);
                AllOrderFragment fragment = (AllOrderFragment) listFragments.get(tab.getPosition());
                fragment.RefreshData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTypeface(Typeface.DEFAULT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        orderType = getIntent().getStringExtra("orderType");
        switch (orderType) {
            case "待付款":
                vpOrder.setCurrentItem(1);
                break;
            case "待发货":
                vpOrder.setCurrentItem(2);
                break;
            case "待收货":
                vpOrder.setCurrentItem(3);
                break;
            case "已完成":
                vpOrder.setCurrentItem(4);
                break;
            default:
                vpOrder.setCurrentItem(0);
                break;
        }
        vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                vpOrder.setCurrentItem(i, false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class OrderFragmentAdapter extends FragmentPagerAdapter {

        public OrderFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return listFragments.get(i);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listFragments.get(vpOrder.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }
}
