package com.zhjl.qihao.opendoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.bean.OpenDoorListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenDoorAddressAdapter extends RecyclerView.Adapter<OpenDoorAddressAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<OpenDoorListBean.DataBean> data;
    private int position;

    public OpenDoorAddressAdapter(Context mContext, ArrayList<OpenDoorListBean.DataBean> data,int position) {
        this.mContext = mContext;
        this.data = data;
        this.position = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.open_door_address_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.tvChooseSmallCity.setText(data.get(i).getSmallCommunityName());
        holder.tvRoomNumber.setText(data.get(i).getGroupName());
        if (i == position) {
            holder.tvStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.car_choose_click));
        } else {
            holder.tvStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.car_choose));
        }
        holder.llSmallCityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v, i, holder.tvStatus);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_choose_small_city)
        TextView tvChooseSmallCity;
        @BindView(R.id.tv_room_number)
        TextView tvRoomNumber;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.ll_small_city_item)
        RelativeLayout llSmallCityItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface SetOnItemClickListener {
        void onClick(View view, int position, TextView tv);
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
