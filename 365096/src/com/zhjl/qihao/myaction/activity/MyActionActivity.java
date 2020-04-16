package com.zhjl.qihao.myaction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.myaction.fragment.MyReplyActionFragment;
import com.zhjl.qihao.myaction.fragment.MySendActionFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyActionActivity extends VolleyBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tab_my_action)
    TabLayout tabMyAction;
    @BindView(R.id.vp_my_action)
    ViewPager vpMyAction;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_action);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "我的动态", this);
        fragmentList.add(new MySendActionFragment());
        fragmentList.add(new MyReplyActionFragment());
        vpMyAction.setAdapter(new MyActionViewPagerAdapter(getSupportFragmentManager()));
        tabMyAction.setupWithViewPager(vpMyAction);
        Utils.setTabLayoutTextBold(tabMyAction, "我发布的", "我评论的");

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        tabMyAction.post(() -> Utils.setIndicator(tabMyAction, 50, 50));
//    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private class MyActionViewPagerAdapter extends FragmentPagerAdapter {

        public MyActionViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragmentList.get(vpMyAction.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }
}
