package com.zhjl.qihao.propertyservicecomplaint.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.propertyservicecomplaint.bean.PropertyComplaintRecordBean;
import com.zhjl.qihao.propertyservicecomplaint.bean.PropertyListBean;
import com.zhjl.qihao.propertyservicerepair.bean.PropertyRepairRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplaintRecordAdapter extends RecyclerView.Adapter<ComplaintRecordAdapter.MyViewHolder> {


    private Activity context;
    private List<PropertyListBean.DataBean> data;


    public ComplaintRecordAdapter(FragmentActivity context, List<PropertyListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.repair_record_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            if (data.get(position).getStatus() == 0) {
                holder.tvRepairRecordStatus.setText("待处理");
                holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_green_2));
            } else {
                holder.tvRepairRecordStatus.setText("已处理");
                holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bbb_2));
            }
            holder.tvRepairRecordContent.setText(data.get(position).getDescription());
            holder.tvRepairRecordTitle.setText(data.get(position).getTitle());
            holder.tvRepairRecordDate.setText(data.get(position).getCreateTime());
            if (data.get(position).getPicture() != null) {
                PictureHelper.setImageView(context, data.get(position).getPicture().getFilename(), holder.imgRepairRecordPic, R.drawable.img_loading);
            } else {
                holder.imgRepairRecordPic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.img_loading));
            }
            holder.rlRepairRecordItem.setOnClickListener(v -> setOnItemClickListener.onClick(v, position));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "请求出错,请稍后再试！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<PropertyListBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
