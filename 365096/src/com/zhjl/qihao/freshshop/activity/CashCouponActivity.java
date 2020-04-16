package com.zhjl.qihao.freshshop.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.freshshop.adapter.CashCouponAdapter;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashCouponActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_cash_coupon)
    RecyclerView xrvCashCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_coupon);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"代金券",this);
        xrvCashCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        final CashCouponAdapter couponAdapter = new CashCouponAdapter(mContext);
        xrvCashCoupon.setAdapter(couponAdapter);
        couponAdapter.setSetOnItemClickListener(new CashCouponAdapter.SetOnItemClickListener() {
            @Override
            public void onClick(View view, int position, TextView tv) {
                for (int i = 0; i < xrvCashCoupon.getChildCount(); i++) {
                   if (i==position){
                       RelativeLayout rl = (RelativeLayout) xrvCashCoupon.getChildAt(i);
                       TextView textView = (TextView) rl.getChildAt(3);
                       textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.car_choose_click));
                   }else {
                       RelativeLayout rl = (RelativeLayout) xrvCashCoupon.getChildAt(i);
                       TextView textView = (TextView) rl.getChildAt(3);
                       textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.car_choose));
                   }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
