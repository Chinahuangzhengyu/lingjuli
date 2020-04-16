package com.zhjl.qihao.propertyservicecomplaint.activity;

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
import com.zhjl.qihao.propertyservicecomplaint.fragment.PropertyComplaintFragment;
import com.zhjl.qihao.propertyservicecomplaint.fragment.PropertyComplaintRecordFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhjl.qihao.propertyservicecomplaint.fragment.PropertyComplaintFragment.REQUEST_CODE;
import static com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity.RESULT_CODE;

public class PropertyComplaintActivity extends VolleyBaseActivity {

    @BindView(R.id.tab_property_complaint)
    TabLayout tabPropertyComplaint;
    @BindView(R.id.vp_property_complaint)
    ViewPager vpPropertyComplaint;
    private List<Fragment> listFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_complaint);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "投诉", this);
        listFragments.add(new PropertyComplaintFragment());
        listFragments.add(new PropertyComplaintRecordFragment());
        vpPropertyComplaint.setAdapter(new PropertyComplaintFragmentAdapter(getSupportFragmentManager()));
        tabPropertyComplaint.setupWithViewPager(vpPropertyComplaint);
        Utils.setTabLayoutTextBold(tabPropertyComplaint, "投诉", "历史记录");
        PropertyComplaintFragment fragment = (PropertyComplaintFragment) listFragments.get(0);
        fragment.setRefreshData(new PropertyComplaintFragment.RefreshData() {
            @Override
            public void refresh() {
                PropertyComplaintRecordFragment fragment = (PropertyComplaintRecordFragment) listFragments.get(1);
                fragment.initData(true);
            }
        });
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

    private class PropertyComplaintFragmentAdapter extends FragmentPagerAdapter {

        public PropertyComplaintFragmentAdapter(FragmentManager fm) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            if (vpPropertyComplaint != null) {
                vpPropertyComplaint.setCurrentItem(1,false);
            }
        }
    }
}
