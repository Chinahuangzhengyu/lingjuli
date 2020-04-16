package com.zhjl.qihao.propertyRepresentations.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abmine.UserAgreementActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.propertyRepresentations.bean.ExposureListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventNotificationAdapter extends RecyclerView.Adapter<EventNotificationAdapter.ViewHolder> {


    private Context mContext;
    private List<ExposureListBean.DataBean> data;

    public EventNotificationAdapter(Context mContext, List<ExposureListBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_notification_new, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvTitle.setText(data.get(i).getTitle());
        holder.tvSelectNum.setText(data.get(i).getViews() + "");
        PictureHelper.setImageView(mContext, data.get(i).getImage(), holder.imgContent, R.drawable.img_loading);
        holder.llItem.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, UserAgreementActivity.class);
            intent.putExtra("name", data.get(i).getTitle());
            intent.putExtra("webContent", data.get(i).getLink());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<ExposureListBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_content)
        RoundImageView imgContent;
        @BindView(R.id.tv_select_num)
        TextView tvSelectNum;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
