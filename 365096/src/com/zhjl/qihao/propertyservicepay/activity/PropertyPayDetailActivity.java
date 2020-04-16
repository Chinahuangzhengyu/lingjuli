package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.propertyservicepay.fragment.PropertyFinishPayFragment;
import com.zhjl.qihao.propertyservicepay.fragment.PropertyPayFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyPayDetailActivity extends VolleyBaseActivity {
    @BindView(R.id.tab_property_pay)
    TabLayout tabPropertyPay;
    @BindView(R.id.vp_property_pay)
    ViewPager vpPropertyPay;
    private List<Fragment> listFragments = new ArrayList<>();
    private int propertyId;
    private ArrayList<UserRoomListBean.DataBean> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_pay_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "物业费", this);
        boolean isPaySuccess = getIntent().getBooleanExtra("isPaySuccess", false);
        if (savedInstanceState != null) {
            propertyId = savedInstanceState.getInt("propertyId");
            roomList = savedInstanceState.getParcelableArrayList("roomList");
        }else {
            propertyId = getIntent().getIntExtra("propertyId", 0);
            roomList = getIntent().getParcelableArrayListExtra("roomList");
        }
        listFragments.add(PropertyPayFragment.newInstance(propertyId, roomList));
        listFragments.add(PropertyFinishPayFragment.newInstance(propertyId, roomList));
        vpPropertyPay.setAdapter(new PropertyFragmentAdapter(getSupportFragmentManager()));
        tabPropertyPay.setupWithViewPager(vpPropertyPay);
        Utils.setTabLayoutTextBold(tabPropertyPay, "缴费", "已交费");
        if (isPaySuccess){
            vpPropertyPay.setCurrentItem(1);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        roomList = intent.getParcelableArrayListExtra("roomList");
        propertyId = intent.getIntExtra("propertyId",0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("propertyId", propertyId);
        outState.putParcelableArrayList("roomList", roomList);
    }

    private class PropertyFragmentAdapter extends FragmentPagerAdapter {
        private String[] strings = {"缴费", "已交费"};

        public PropertyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }
    }
}
