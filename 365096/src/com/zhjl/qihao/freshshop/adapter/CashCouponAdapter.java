package com.zhjl.qihao.freshshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CashCouponAdapter extends RecyclerView.Adapter<CashCouponAdapter.ViewHolder> {


    private Context context;

    public CashCouponAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cash_coupon_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.llCashCouponItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v,i,holder.tvStatus);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cash_coupon)
        TextView tvCashCoupon;
        @BindView(R.id.tv_cash_coupon_detail)
        TextView tvCashCouponDetail;
        @BindView(R.id.tv_use_date)
        TextView tvUseDate;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.ll_cash_coupon_item)
        RelativeLayout llCashCouponItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface SetOnItemClickListener{
        void onClick(View view,int position,TextView tv);
    }
    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
