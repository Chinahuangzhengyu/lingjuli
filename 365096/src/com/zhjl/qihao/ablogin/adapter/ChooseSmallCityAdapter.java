package com.zhjl.qihao.ablogin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.freshshop.adapter.CashCouponAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseSmallCityAdapter extends RecyclerView.Adapter<ChooseSmallCityAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms;

    public ChooseSmallCityAdapter(Context mContext, ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms) {
        this.mContext = mContext;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_small_city, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.llSmallCityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v,i,holder.tvStatus);
            }
        });
        holder.tvChooseSmallCity.setText(rooms.get(i).getSmallCommunityName());
        holder.tvRoomNumber.setText(rooms.get(i).getRoomName());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void addData(ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
            ButterKnife.bind(this,itemView);
        }
    }

    public interface SetOnItemClickListener {
        void onClick(View view, int position, TextView tv);
    }

    private CashCouponAdapter.SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(CashCouponAdapter.SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
