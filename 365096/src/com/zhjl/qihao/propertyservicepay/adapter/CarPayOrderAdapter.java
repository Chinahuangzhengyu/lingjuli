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

public class CarPayOrderAdapter extends RecyclerView.Adapter<CarPayOrderAdapter.MyViewHolder> {


    private Context context;
    List<PropertyPayRecordBean.DataBean> data;

    public CarPayOrderAdapter(Context context, List<PropertyPayRecordBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public void addData(List<PropertyPayRecordBean.DataBean> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_pay_order_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        try {
            holder.tvCarRoomName.setText(data.get(i).getRoomName());
            holder.tvCarPayTime.setText(data.get(i).getCreateTime());
            holder.tvCarOrderNumber.setText(data.get(i).getOrderSn());
            holder.llPayCarRecord.removeAllViews();
            for (int j = 0; j < data.get(i).getItems().size(); j++) {
                View view = View.inflate(context, R.layout.pay_record, null);
                TextView tvPayRecordName = view.findViewById(R.id.tv_pay_record_name);
                TextView tvPayMoney = view.findViewById(R.id.tv_pay_money);
                tvPayRecordName.setText(data.get(i).getItems().get(j).getItemName());
                tvPayMoney.setText("¥"+data.get(i).getItems().get(j).getCostMoney());
                holder.llPayCarRecord.addView(view);
            }
            holder.tvSumMoney.setText("¥"+data.get(i).getTotalAmount());
        } catch (Exception e) {
            Toast.makeText(context, "数据出错了！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_car_room_name)
        TextView tvCarRoomName;
        @BindView(R.id.tv_car_order_number)
        TextView tvCarOrderNumber;
        @BindView(R.id.tv_car_pay_time)
        TextView tvCarPayTime;
        @BindView(R.id.ll_pay_car_record)
        LinearLayout llPayCarRecord;
        @BindView(R.id.tv_sum_money)
        TextView tvSumMoney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}