package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.propertyservicepay.fragment.CarFinishPayFragment;
import com.zhjl.qihao.propertyservicepay.fragment.CarPayFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarPayDetailActivity extends VolleyBaseActivity {
    @BindView(R.id.tab_car_pay)
    TabLayout tabCarPay;
    @BindView(R.id.vp_car_pay)
    ViewPager vpCarPay;
    private List<Fragment> listFragments = new ArrayList<>();
    private int carId;
    private ArrayList<UserRoomListBean.DataBean> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pay_detail);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "停车费", this);
        boolean isPaySuccess = getIntent().getBooleanExtra("isPaySuccess", false);
        if (savedInstanceState != null) {
            carId = savedInstanceState.getInt("carId");
            roomList = savedInstanceState.getParcelableArrayList("roomList");
        }else {
            carId = getIntent().getIntExtra("carId", 0);
            roomList = getIntent().getParcelableArrayListExtra("roomList");
        }
        listFragments.add(CarPayFragment.getInstance(carId, roomList));
        listFragments.add(CarFinishPayFragment.getInstance(carId, roomList));

        vpCarPay.setAdapter(new CarFragmentAdapter(getSupportFragmentManager()));
        tabCarPay.setupWithViewPager(vpCarPay);
        Utils.setTabLayoutTextBold(tabCarPay, "缴费", "已交费");
        if (isPaySuccess) {
            vpCarPay.setCurrentItem(1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        roomList = intent.getParcelableArrayListExtra("roomList");
        carId = intent.getIntExtra("carId",0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("carId", carId);
        outState.putParcelableArrayList("roomList", roomList);
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

    private class CarFragmentAdapter extends FragmentPagerAdapter {

        public CarFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }
    }
}
