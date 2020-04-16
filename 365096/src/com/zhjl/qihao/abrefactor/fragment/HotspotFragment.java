package com.zhjl.qihao.abrefactor.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.hotspot.fragment.EveryWeekHotspotFragment;
import com.zhjl.qihao.hotspot.fragment.HotspotHistoryFragment;
import com.zhjl.qihao.searchcontent.SearchContentActivity;
import com.zhjl.qihao.util.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//热点
public class HotspotFragment extends VolleyBaseFragment {

    @BindView(R.id.tab_hotspot)
    TabLayout tabHotspot;
    @BindView(R.id.vp_hotspot)
    ViewPager vpHotspot;
    Unbinder unbinder;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        tabHotspot.post(new Runnable() {
//            @Override
//            public void run() {
//                Utils.setIndicator(tabHotspot, 10, 10);
//            }
//        });
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_hotspot, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        NewStatusBarUtils.fullScreen(getActivity());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new EveryWeekHotspotFragment());
        fragments.add(new HotspotHistoryFragment());
        vpHotspot.setAdapter(new HotspotAdapter(getChildFragmentManager(), fragments));
        tabHotspot.setupWithViewPager(vpHotspot);
        tabHotspot.setTabTextColors(Color.parseColor("#c8c8c8"), Color.parseColor("#3B3B3B"));
        tabHotspot.setSelectedTabIndicatorColor(Color.parseColor("#3B3B3B"));
        vpHotspot.setOffscreenPageLimit(1);
        return view;
    }

    class HotspotAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"每周热点", "投诉热点"};
        private List<Fragment> fragments;

        public HotspotAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                Intent intent = new Intent(getActivity(), SearchContentActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        } else {
            NewStatusBarUtils.fullScreen(getActivity());
        }
    }
}
