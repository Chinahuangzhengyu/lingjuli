package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.fragment.BalanceFragment;
import com.zhjl.qihao.integration.fragment.IntegralFragment;
import com.zhjl.qihao.util.NewHeaderBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhjl.qihao.integration.activity.MembershipCardActivity.RESULT_INTEGRATION_CODE;

public class BalanceActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_integral_sum)
    TextView tvIntegralSum;
    @BindView(R.id.tv_integral_name)
    TextView tvIntegralName;
    @BindView(R.id.tab_integral)
    TabLayout tabIntegral;
    @BindView(R.id.vp_integral)
    ViewPager vpIntegral;
    private List<Fragment> list = new ArrayList<>();
    private String integralSum;
    private String balanceSum = "¥0.00";
    public static final int REQUEST_BALANCE_REFRESH = 0x6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        NewHeaderBar headerBar = NewHeaderBar.createCommomBack(this, "我的账户", "会员卡", this);
        headerBar.getRightText().setTextSize(14);
        headerBar.getRightText().setTextColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
        headerBar.getRightText().setOnClickListener(this);
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_card);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        headerBar.getRightText().setCompoundDrawables(null, null, drawable, null);
//        headerBar.getRightText().setVisibility(View.GONE);
        list.add(new IntegralFragment());
        list.add(new BalanceFragment());
        vpIntegral.setAdapter(new IntegralFragmentAdapter(getSupportFragmentManager()));
        tabIntegral.setupWithViewPager(vpIntegral);
        for (int i = 0; i < tabIntegral.getTabCount(); i++) {
            tabIntegral.getTabAt(i).setCustomView(R.layout.tab_item);
            TextView tabItemText = tabIntegral.getTabAt(i).getCustomView().findViewById(R.id.tab_item_text);
            if (i == 0) {
                tabItemText.setText("N币");
                tabItemText.setTextColor(Color.parseColor("#23AC38"));
            } else {
                tabItemText.setText("余额");
                tabItemText.setTextColor(Color.parseColor("#1F1F1F"));
            }
        }
        IntegralFragment integralFragment = (IntegralFragment) list.get(0);
        integralFragment.setGetData(sum -> {
            integralSum = sum;
            tvIntegralSum.setText(integralSum);
        });

        BalanceFragment balanceFragment = (BalanceFragment) list.get(1);
        balanceFragment.setGetBalanceData((sum, isRefresh) -> {
            balanceSum = sum;
            if (isRefresh){
                tvIntegralSum.setText(balanceSum);
            }
        });
        tabIntegral.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.parseColor("#23AC38"));
                if (textView.getText().toString().trim().equals("余额")) {
                    tvIntegralName.setText("余额剩余");
                    tvIntegralSum.setText(balanceSum);
                } else {
                    tvIntegralName.setText("N币剩余");
                    tvIntegralSum.setText(integralSum);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.parseColor("#1F1F1F"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public class IntegralFragmentAdapter extends FragmentPagerAdapter {

        public IntegralFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent();
                intent.setClass(mContext, MembershipCardActivity.class);
                startActivityForResult(intent, REQUEST_BALANCE_REFRESH);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_INTEGRATION_CODE) {
            if (list.size() >= 2 && list.get(0) != null && list.get(1) != null) {
//                IntegralFragment integralFragment = (IntegralFragment) list.get(0);
//                integralFragment.initData(1);
                BalanceFragment balanceFragment = (BalanceFragment) list.get(1);
                if (vpIntegral.getCurrentItem()==1){
                    balanceFragment.initData(1,true);
                }else {
                    balanceFragment.initData(1,false);
                }
            }
        }
    }
}
