package com.zhjl.qihao.abrefactor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abutil.NewStatusBarUtils;

import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zhjl.qihao.abrefactor.fragment.PropertyComplaintFragment.REQUEST_CODE;
import static com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity.RESULT_CODE;

//投诉
public class ComplaintFragment extends VolleyBaseFragment {

    @BindView(R.id.tab_property_complaint)
    TabLayout tabPropertyComplaint;
    @BindView(R.id.vp_property_complaint)
    ViewPager vpPropertyComplaint;
    Unbinder unbinder;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private List<Fragment> listFragments = new ArrayList<>();
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static ComplaintFragment getInstance(String labelTypeId) {
        ComplaintFragment complaintFragment = new ComplaintFragment();
        Bundle bundle = new Bundle();
        bundle.putString("labelTypeId", labelTypeId);
        complaintFragment.setArguments(bundle);
        return complaintFragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.complaint_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        listFragments.add(new PropertyComplaintFragment());
        listFragments.add(new PropertyComplaintRecordFragment());
        vpPropertyComplaint.setAdapter(new PropertyComplaintFragmentAdapter(getChildFragmentManager()));
        tabPropertyComplaint.setupWithViewPager(vpPropertyComplaint);
        Utils.setTabLayoutTextBold(tabPropertyComplaint, "投诉", "热点投诉");
        PropertyComplaintFragment fragment = (PropertyComplaintFragment) listFragments.get(0);
        fragment.setRefreshData(new PropertyComplaintFragment.RefreshData() {
            @Override
            public void refresh() {
                tvSubmit.setEnabled(true);
                PropertyComplaintRecordFragment fragment = (PropertyComplaintRecordFragment) listFragments.get(1);
                fragment.initData(true);
            }

            @Override
            public void fail() {
                tvSubmit.setEnabled(true);
            }
        });
        vpPropertyComplaint.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    tvSubmit.setVisibility(View.VISIBLE);
                } else {
                    Utils.hideInput(getActivity());
                    tvSubmit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            PropertyComplaintFragment fragment = (PropertyComplaintFragment) listFragments.get(0);
            if (fragment != null) {
                fragment.initSendMessage();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                tvSubmit.setEnabled(false);
                if (listFragments.size() > 0) {
                    PropertyComplaintFragment fragment = (PropertyComplaintFragment) listFragments.get(0);
                    fragment.requestNote();
                }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
//            if (vpPropertyComplaint != null && vpPropertyComplaint.getChildCount() > 1) {
//                vpPropertyComplaint.setCurrentItem(1, false);
//            }
        } else {
            if (vpPropertyComplaint != null && vpPropertyComplaint.getCurrentItem() < listFragments.size()) {
                listFragments.get(vpPropertyComplaint.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
