package com.zhjl.qihao.propertyservicepay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyPayOrderAdapter extends RecyclerView.Adapter<PropertyPayOrderAdapter.MyViewHolder> {


    private Context context;
    private List<PropertyPayRecordBean.DataBean> list;

    public PropertyPayOrderAdapter(Context context, List<PropertyPayRecordBean.DataBean> data) {
        this.context = context;
        list = data;
    }

    public void addData(List<PropertyPayRecordBean.DataBean> data) {
        list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.property_pay_order_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        try {
            holder.tvRoomName.setText(list.get(i).getRoomName());
            holder.tvPayTime.setText(list.get(i).getCreateTime());
            holder.tvOrderNumber.setText(list.get(i).getOrderSn());
            holder.llPayRecord.removeAllViews();
            for (int j = 0; j < list.get(i).getItems().size(); j++) {
                View view = View.inflate(context, R.layout.pay_record, null);
                TextView tvPayRecordName = view.findViewById(R.id.tv_pay_record_name);
                TextView tvPayMoney = view.findViewById(R.id.tv_pay_money);
                tvPayRecordName.setText(list.get(i).getItems().get(j).getItemName());
                tvPayMoney.setText("¥"+list.get(i).getItems().get(j).getCostMoney());
                holder.llPayRecord.addView(view);
            }
            holder.tvSumMoney.setText("¥"+list.get(i).getTotalAmount());
        } catch (Exception e) {
            Toast.makeText(context, "数据出错了！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_room_name)
        TextView tvRoomName;
        @BindView(R.id.tv_order_number)
        TextView tvOrderNumber;
        @BindView(R.id.tv_pay_time)
        TextView tvPayTime;
        @BindView(R.id.ll_pay_record)
        LinearLayout llPayRecord;
        @BindView(R.id.tv_sum_money)
        TextView tvSumMoney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
