package com.zhjl.qihao.propertyRepresentations.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.propertyRepresentations.api.ExposureInterface;
import com.zhjl.qihao.propertyRepresentations.bean.ExposureTypeBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 曝光页面
 */
public class QHExposureFragment extends VolleyBaseFragment {

    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.tab_property_exposure)
    TabLayout tabPropertyExposure;
    @BindView(R.id.vp_property_exposure)
    ViewPager vpPropertyExposure;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private String[] str;
    private List<Fragment> listFragments = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_exposure, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        rlLoading.setVisibility(View.VISIBLE);
        initData();
//        listFragments.add(new EventNotificationFragment());
//        listFragments.add(new PropertyRepresentations());

        return view;
    }

    private void initData() {
        ExposureInterface exposureInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ExposureInterface.class);
        Call<ResponseBody> call = exposureInterface.exposureType(new HashMap<>());
        fragmentRequestData(call, ExposureTypeBean.class, new RequestResult<ExposureTypeBean>() {
            @Override
            public void success(ExposureTypeBean result, String message) throws Exception {
                listFragments.clear();
                List<ExposureTypeBean.DataBean> data = result.getData();
                str = new String[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    str[i] = data.get(i).getName();
                    listFragments.add(EventNotificationFragment.getInstance(data.get(i).getId()));
                }
                vpPropertyExposure.setAdapter(new PropertyExposureFragmentAdapter(getChildFragmentManager()));
                tabPropertyExposure.setupWithViewPager(vpPropertyExposure);
                if (str.length > 3) {
                    tabPropertyExposure.setTabMode(TabLayout.MODE_SCROLLABLE);
                } else {
                    tabPropertyExposure.setTabMode(TabLayout.MODE_FIXED);
                }
                Utils.setTabLayoutTextBold(tabPropertyExposure, str);
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
            }
        });
    }

    private class PropertyExposureFragmentAdapter extends FragmentPagerAdapter {

        public PropertyExposureFragmentAdapter(FragmentManager fm) {
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
