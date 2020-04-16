package com.zhjl.qihao.freshshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.view.CannotScrollViewpager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.ITabView.TabTitle;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ShopTypeListDetailFragment extends VolleyBaseFragment {

    @BindView(R.id.vtab_shop_type)
    VerticalTabLayout vtabShopType;
    Unbinder unbinder;
//    @BindView(R.id.vp_shop_type)
//    CannotScrollViewpager vpShopType;
    @BindView(R.id.rl_fragment)
    RelativeLayout rlFragment;
    private View view;
    private List<Fragment> list;
    private String[] title= {"蔬菜","水果","鲜花","南北干货","五谷杂粮","蔬果联营"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_type_list_detail, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
//        list = new ArrayList<>();
//        list.add(new ShopListFragment());
//        list.add(new ShopListFragment());
//        list.add(new ShopListFragment());
//        list.add(new ShopListFragment());
//        list.add(new ShopListFragment());
//        list.add(new ShopListFragment());
//        vpShopType.setPagingCount(0);
//        vpShopType.setAdapter(new ShopListAdapter(getChildFragmentManager()));
//        vtabShopType.setupWithViewPager(vpShopType);

        ShopListFragment shopListFragment = new ShopListFragment();
        if (!shopListFragment.isAdded()){
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.rl_fragment,shopListFragment).commit();
        }
        getActivity().getSupportFragmentManager().beginTransaction().show(shopListFragment).commit();
        for (int i = 0; i < title.length; i++) {
            vtabShopType.addTab(new QTabView(mContext).setTitle(new TabTitle.Builder().setContent(title[i]).build()));
        }
        vtabShopType.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {

            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
        return view;
    }
//    private class ShopListAdapter extends FragmentPagerAdapter{
//
//
//
//        public ShopListAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            return list.get(i);
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return title[position];
//        }
//
//        @Override
//        public int getCount() {
//            return title.length;
//        }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
