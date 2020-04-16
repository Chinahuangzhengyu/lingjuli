package com.zhjl.qihao.freshshop.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.freshshop.bean.ShopMoreTypeBean;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopContentFragment extends VolleyBaseFragment {

    @BindView(R.id.tab_shop_type)
    TabLayout tabShopType;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.rl_tab)
    RelativeLayout rlTab;
    @BindView(R.id.ll_background)
    LinearLayout llBackground;
    @BindView(R.id.rg_shop_type)
    CustomRadioGroup rgShopType;
    Unbinder unbinder;
    private View view;
    private boolean isClick = false;
    private ArrayList<ShopMoreTypeBean.ChildrenBean> children = new ArrayList<>();
    private String[] title;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static ShopContentFragment getInstance(ArrayList<ShopMoreTypeBean.ChildrenBean> children) {
        ShopContentFragment fragment = new ShopContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("children", children);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void goneView(){
        if (rgShopType!=null&&llBackground!=null){
            if (rgShopType.getVisibility()==View.VISIBLE&&llBackground.getVisibility()==View.VISIBLE){
                isClick =  false;
                rotateAnim(isClick);
                rgShopType.setVisibility(View.GONE);
                llBackground.setVisibility(View.GONE);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_content, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        children = getArguments().getParcelableArrayList("children");
        title = new String[children.size()];
        for (int i = 0; i < children.size(); i++) {
            title[i] = children.get(i).getName();
            if (children.get(i).getId() == 0) {
                fragments.add(ShopListFragment.getInstance(children.get(i).getParent_id()));
            } else {
                fragments.add(ShopListFragment.getInstance(children.get(i).getId()));
            }
        }
        addMoreView(title);
        vpContent.setAdapter(new ShopContentChildAdapter(getChildFragmentManager()));
//        vpContent.setOffscreenPageLimit(2);
        tabShopType.setupWithViewPager(vpContent);
        for (int i = 0; i < tabShopType.getTabCount(); i++) {
            tabShopType.getTabAt(i).setCustomView(R.layout.tab_item);
            TextView tabItemText = tabShopType.getTabAt(i).getCustomView().findViewById(R.id.tab_item_text);
            if (i == 0) {
                tabItemText.setText(title[0]);
                tabItemText.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tabItemText.setText(title[i]);
                tabItemText.setTypeface(Typeface.DEFAULT);
            }
        }

        tabShopType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView()!=null){
//                    if (fragments.size()> tab.getPosition()){
//                       ShopListFragment shopListFragment = (ShopListFragment) fragments.get(tab.getPosition());
//                        shopListFragment.initData();
//                    }
                    TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    RadioButton rb = (RadioButton) rgShopType.getChildAt(tab.getPosition());
                    rb.setChecked(true);
                    rb.setTextColor(Color.parseColor("#FFFFFF"));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView()!=null){
                    TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                    textView.setTypeface(Typeface.DEFAULT);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rgShopType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < children.size(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (checkedId == radioButton.getId()) {
                        vpContent.setCurrentItem(i);
                        if (rgShopType.getVisibility()==View.VISIBLE&&llBackground.getVisibility()==View.VISIBLE){
                            isClick = !isClick;
                            rotateAnim(isClick);
                            rgShopType.setVisibility(View.GONE);
                            llBackground.setVisibility(View.GONE);
                        }
                        radioButton.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        radioButton.setTextColor(Color.parseColor("#999999"));
                    }
                }
            }
        });
        return view;
    }

    class ShopContentChildAdapter extends FragmentStatePagerAdapter {

        public ShopContentChildAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_more, R.id.ll_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_more:
                isClick = !isClick;
                rotateAnim(isClick);
                if (isClick) {
                    rgShopType.setVisibility(View.VISIBLE);
                    llBackground.setVisibility(View.VISIBLE);
                } else {
                    rgShopType.setVisibility(View.GONE);
                    llBackground.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_background:
                isClick = !isClick;
                rotateAnim(isClick);
                rgShopType.setVisibility(View.GONE);
                llBackground.setVisibility(View.GONE);
                break;
        }
    }

    private RotateAnimation rotateAnim(boolean isClick) {
        RotateAnimation anim = null;
        if (isClick) {
            anim = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setDuration(100);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setFillAfter(true);
        imgMore.startAnimation(anim);
        return anim;
    }


    private void addMoreView(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            final RadioButton button = new RadioButton(mContext);
            button.setButtonDrawable(new StateListDrawable());
            button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rg_status_14));
            button.setTextSize(14);
            button.setId(i);
            button.setText(strings[i]);
            if (i == 0) {
                button.setTextColor(Color.parseColor("#FFFFFF"));
                button.setChecked(true);
            } else {
                button.setTextColor(Color.parseColor("#999999"));
            }
            int left = Utils.dip2px(mContext, 12);
            int top = Utils.dip2px(mContext, 4);
            button.setPadding(left, top, left, top);
            rgShopType.addView(button);
        }
    }



}
