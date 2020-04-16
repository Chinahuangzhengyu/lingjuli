package com.zhjl.qihao.integration.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.integration.bean.BalanceListBean;
import com.zhjl.qihao.integration.bean.IntegralListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.ViewHolder> {

    private Context context;
    private List<?> list;

    public BalanceAdapter(Context context, List<?> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.integrallist_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        try {
            if (list.get(i) instanceof IntegralListBean.NbitListBean) {
                IntegralListBean.NbitListBean integralListBean = (IntegralListBean.NbitListBean) list.get(i);
                holder.tvNum.setText(integralListBean.getNumber() + "个");
                holder.integralName.setText(integralListBean.getMemo());
                holder.integralTime.setText(integralListBean.getCreate_date());
            } else {
                BalanceListBean.SpendListBean spendListBean = (BalanceListBean.SpendListBean) list.get(i);
                holder.tvNum.setText(spendListBean.getPrice());
                holder.integralName.setText(spendListBean.getContent());
                holder.integralTime.setText(spendListBean.getCreateTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<?> integralList) {
        this.list = integralList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.integral_name)
        TextView integralName;
        @BindView(R.id.integral_time)
        TextView integralTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
