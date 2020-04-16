package com.zhjl.qihao.propertyservicepay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayInfoBean;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyPayListAdapter extends RecyclerView.Adapter<PropertyPayListAdapter.ViewHolder> {

    private Context mContext;
    private List<PropertyPayInfoBean.DataBean.StandardsBean> list;

    public PropertyPayListAdapter(Context mContext, List<PropertyPayInfoBean.DataBean.StandardsBean> list) {
        this.mContext = mContext;
        this.list= list;
    }
    public void addData(List<PropertyPayInfoBean.DataBean.StandardsBean> list){
        this.list= list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pay_time_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        BigDecimal giveNb = new BigDecimal(list.get(i).getGiveNBItNumber());
        BigDecimal losePrice = new BigDecimal(100);
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onItemClick(v,i);
            }
        });
        if (i==0){
            holder.llItem.setBackground(ContextCompat.getDrawable(mContext,R.drawable.stroke_solid_circle_green_6));

        }else {
            holder.llItem.setBackground(ContextCompat.getDrawable(mContext,R.drawable.stroke_solid_circle_bbbbbb_6));
        }

        holder.tvPayTime.setText(list.get(i).getName());
        BigDecimal price = giveNb.divide(losePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        holder.tvPayContent.setText("送价值"+price+"元的N币");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_pay_time)
        TextView tvPayTime;
        @BindView(R.id.tv_pay_content)
        TextView tvPayContent;
        @BindView(R.id.tv_use_detail)
        TextView tvUseDetail;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public interface SetOnItemClickListener{
        void onItemClick(View view,int position);
    }
}
