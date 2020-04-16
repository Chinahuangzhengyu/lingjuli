package com.zhjl.qihao.systemsetting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAddressBindingAdapter extends RecyclerView.Adapter<HomeAddressBindingAdapter.ViewHolder> {

    private Context context;
    private List<UserRoomListBean.DataBean> data;

    public HomeAddressBindingAdapter(Context context, List<UserRoomListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_address_binding_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.tvHomeAddress.setText(data.get(i).getSmallCommunityName()+data.get(i).getRoomName());
        if (data.get(i).getResidentType().equals("1")){
            holder.tvUserType.setText("业主");
        }else if (data.get(i).getResidentType().equals("2")){
            holder.tvUserType.setText("家庭成员");
        }else {
            holder.tvUserType.setText("租户");
        }
        if (data.get(i).getStatus()==0){
            holder.tvIsBinding.setText("正在审核中");
        }else {
            holder.tvIsBinding.setText("已绑定");
        }
        holder.rlHomeAddressItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.OnItemClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<UserRoomListBean.DataBean> data) {
        this.data =data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_home_address)
        TextView tvHomeAddress;
        @BindView(R.id.tv_user_type)
        TextView tvUserType;
        @BindView(R.id.tv_is_binding)
        TextView tvIsBinding;
        @BindView(R.id.rl_home_address_item)
        RelativeLayout rlHomeAddressItem;

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
        void OnItemClick(View view,int position);
    }
}
