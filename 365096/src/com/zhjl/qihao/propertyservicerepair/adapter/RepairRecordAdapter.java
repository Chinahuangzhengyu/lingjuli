package com.zhjl.qihao.propertyservicerepair.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.propertyservicerepair.bean.PropertyRepairRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepairRecordAdapter extends RecyclerView.Adapter<RepairRecordAdapter.MyViewHolder> {


    private Activity context;
    private List<PropertyRepairRecordBean.DataBean> data;

    public RepairRecordAdapter(Activity context, List<PropertyRepairRecordBean.DataBean> data) {
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
        if (data.get(position).getStatus().equals("待处理")) {
            holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_green_2));
        } else {
            holder.tvRepairRecordStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_bbb_2));
        }
        holder.tvRepairRecordStatus.setText(data.get(position).getStatus());
        holder.tvRepairRecordContent.setText(data.get(position).getDescription());
        holder.tvRepairRecordTitle.setText(data.get(position).getTypeName());
        holder.tvRepairRecordDate.setText(data.get(position).getCreateTime());
        if (data.get(position).getSmallPicturePath() == null) {
//                holder.imgContent.setImageResource(View.GONE);
            Glide.with(context).clear(holder.imgRepairRecordPic);
//                holder.imgContent.setImageDrawable(null);
            holder.imgRepairRecordPic.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.img_loading));
            holder.imgRepairRecordPic.setTag(R.id.img_repair_record_pic, position);
            return;
        }
        Object tag = holder.imgRepairRecordPic.getTag(R.id.img_repair_record_pic);
        if (tag != null && (int) tag != position) {
            //如果tag不是Null,并且同时tag不等于当前的position。
            //说明当前的viewHolder是复用来的
            Glide.with(context).clear(holder.imgRepairRecordPic);
        }
//            holder.imgContent.setImageResource(View.VISIBLE);
        PictureHelper.setImageView(context, data.get(position).getSmallPicturePath(), holder.imgRepairRecordPic, R.drawable.img_err);
        holder.imgRepairRecordPic.setTag(R.id.img_repair_record_pic, position);
        holder.rlRepairRecordItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<PropertyRepairRecordBean.DataBean> data) {
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
