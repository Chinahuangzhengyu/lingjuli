package com.zhjl.qihao.homemanage.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.homemanage.activity.HomeManageDetailActivity;
import com.zhjl.qihao.homemanage.bean.HomeManageListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeManageAdapter extends RecyclerView.Adapter<HomeManageAdapter.ViewHolder> {

    private VolleyBaseActivity mContext;
    private List<HomeManageListBean.DataBean> data;
    public static final int REQUEST_HOME_AGREE_CODE = 0x21;


    public HomeManageAdapter(VolleyBaseActivity mContext, List<HomeManageListBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_manage_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvCityName.setText(data.get(i).getSmallCommunityName() + data.get(i).getRoomName());
        if (data.get(i).getResidentType().equals("1")) {
            holder.tvType.setText("业主");
        } else if (data.get(i).getResidentType().equals("2")) {
            holder.tvType.setText("家庭成员");
        } else {
            holder.tvType.setText("租户");
        }
        if (data.get(i).getStatus() == 0) {
            holder.tvStatus.setText("待审核");
            holder.imgDelete.setVisibility(View.GONE);
        } else if (data.get(i).getStatus() == 1) {
            holder.tvStatus.setText("通过");
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setText("未通过");
            holder.imgDelete.setVisibility(View.VISIBLE);
        }
        holder.clItem.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, HomeManageDetailActivity.class);
            intent.putExtra("data",data.get(i));
            intent.putExtra("position",i);
            mContext.startActivityForResult(intent,REQUEST_HOME_AGREE_CODE);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<HomeManageListBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_city_name)
        TextView tvCityName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.cl_item)
        ConstraintLayout clItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
