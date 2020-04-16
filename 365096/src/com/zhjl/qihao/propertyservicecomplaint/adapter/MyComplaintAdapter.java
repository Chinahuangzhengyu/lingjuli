package com.zhjl.qihao.propertyservicecomplaint.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.propertyservicecomplaint.bean.MyComplaintBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyComplaintAdapter extends RecyclerView.Adapter<MyComplaintAdapter.ViewHolder> {


    private Context mContext;
    private List<MyComplaintBean.DataBean> list;

    public MyComplaintAdapter(Context mContext, List<MyComplaintBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_complaint_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        try {
            if (list.get(i).getStatus() == 0) {
                holder.tvRepairRecordStatus.setText("待处理");
                holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_2));
            } else {
                holder.tvRepairRecordStatus.setText("已处理");
                holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_bbb_2));
            }
            holder.tvRepairRecordContent.setText(list.get(i).getDescription());
            holder.tvRepairRecordTitle.setText(list.get(i).getTitle());
            holder.tvRepairRecordDate.setText(list.get(i).getCreateTime());
            if (list.get(i).getPicture() != null) {
                PictureHelper.setImageView(mContext, list.get(i).getPicture().getFilename(), holder.imgRepairRecordPic, R.drawable.img_loading);
            } else {
                holder.imgRepairRecordPic.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_loading));
            }
            holder.rlRepairRecordItem.setOnClickListener(v -> setOnItemClickListener.onClick(v, i));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "请求出错,请稍后再试！", Toast.LENGTH_SHORT).show();
        }
    }

    public void addData(List<MyComplaintBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_repair_record_pic)
        RoundImageView imgRepairRecordPic;
        @BindView(R.id.tv_repair_record_status)
        TextView tvRepairRecordStatus;
        @BindView(R.id.tv_repair_record_title)
        TextView tvRepairRecordTitle;
        @BindView(R.id.tv_repair_record_content)
        TextView tvRepairRecordContent;
        @BindView(R.id.tv_repair_record_date)
        TextView tvRepairRecordDate;
        @BindView(R.id.rl_repair_record_item)
        RelativeLayout rlRepairRecordItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public interface SetOnItemClickListener {
        void onClick(View view, int position);
    }
}
