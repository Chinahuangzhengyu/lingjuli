package com.zhjl.qihao.propertyservicerepair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicerepair.fragment.PropertyRepairFragment;
import com.zhjl.qihao.propertyservicerepair.fragment.PropertyRepairRecordFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity.RESULT_CODE;
import static com.zhjl.qihao.propertyservicerepair.fragment.PropertyRepairFragment.REQUEST_CODE;

public class PropertyRepairActivity extends VolleyBaseActivity {
    @BindView(R.id.tab_property_repair)
    TabLayout tabPropertyRepair;
    @BindView(R.id.vp_property_repair)
    ViewPager vpPropertyRepair;
    private List<Fragment> listFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_repair);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "物业报修", this);
        listFragments.add(new PropertyRepairFragment());
        listFragments.add(new PropertyRepairRecordFragment());
        vpPropertyRepair.setAdapter(new PropertyRepairFragmentAdapter(getSupportFragmentManager()));
        tabPropertyRepair.setupWithViewPager(vpPropertyRepair);
        Utils.setTabLayoutTextBold(tabPropertyRepair, "物业报修", "报修记录");
        PropertyRepairFragment fragment = (PropertyRepairFragment) listFragments.get(0);
        fragment.setRefreshData(new PropertyRepairFragment.RefreshData() {
            @Override
            public void refresh() {
                PropertyRepairRecordFragment fragment = (PropertyRepairRecordFragment) listFragments.get(1);
                fragment.initData();
            }
        });
    }

    private class PropertyRepairFragmentAdapter extends FragmentPagerAdapter {

        public PropertyRepairFragmentAdapter(FragmentManager fm) {
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
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            if (vpPropertyRepair != null) {
                vpPropertyRepair.setCurrentItem(1,false);
            }
        }
    }
}
