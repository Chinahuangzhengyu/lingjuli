package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.bean.ComplaintListBean;
import com.zhjl.qihao.abutil.PictureHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    private Context context;
    private List<ComplaintListBean.DataBean> list;
    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public ComplaintAdapter(Context context, List<ComplaintListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<ComplaintListBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_complaint_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
//            PictureHelper.setOptionsGlide(context, 12, list.get(position).getPhoneUser().getAvatar().getPicturePath(), holder.headImg, R.drawable.img_head);
            holder.tvTitle.setText(list.get(position).getTitle());
            holder.tvTime.setText(list.get(position).getHowLong());
            if (list.get(position).getProcessStatus() == 2) {
                holder.tvStatus.setText("已处理");
                holder.tvStatus.setTextColor(Color.parseColor("#008000"));
                Drawable drawable = context.getResources().getDrawable(R.drawable.sure_img);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.tvStatus.setCompoundDrawables(null, drawable, null, null);
            } else if (list.get(position).getProcessStatus() == 1) {
                holder.tvStatus.setTextColor(Color.parseColor("#fffd9e44"));
                holder.tvStatus.setText("处理中");
                Drawable drawable = context.getResources().getDrawable(R.drawable.not_sure_img);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.tvStatus.setCompoundDrawables(null, drawable, null, null);
            } else {
                holder.tvStatus.setTextColor(Color.parseColor("#fffd9e44"));
                holder.tvStatus.setText("未处理");
                Drawable drawable = context.getResources().getDrawable(R.drawable.not_err_img);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.tvStatus.setCompoundDrawables(null, drawable, null, null);
            }
            holder.tvContent.setText(list.get(position).getContent());
            holder.tvCommentNum.setText("已有" + list.get(position).getDiscussNum() + "人参与评论");
            holder.complaintItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener.setOnItem(v, position);
                }
            });
            if (list.get(position).getPhoneUser()!=null){
                holder.tvName.setText(list.get(position).getPhoneUser().getNickName());
                if (list.get(position).getPhoneUser().getAvatar()!=null){
                    PictureHelper.setOptionsGlide(context, 12, list.get(position).getPhoneUser().getAvatar().getPicturePath(), holder.headImg, R.drawable.img_err);
                }
            }
            if (list.get(position).getForumPicture() == null || list.get(position).getForumPicture().size() == 0) {
                holder.imgContent.setImageResource(View.GONE);
                Glide.with(context).clear(holder.imgContent);
                holder.imgContent.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.img_loading));
                holder.imgContent.setTag(R.id.img_content, position);
                return;
            }
            Object tag = holder.imgContent.getTag(R.id.img_content);
            if (tag != null && (int) tag != position) {
                //如果tag不是Null,并且同时tag不等于当前的position。
                //说明当前的viewHolder是复用来的
                Glide.with(context).clear(holder.imgContent);
            }
            holder.imgContent.setImageResource(View.VISIBLE);
            if (list.get(position).getForumPicture()!=null){
                PictureHelper.setOptionsGlide(context, 12, list.get(position).getForumPicture().get(0).getPicturePath(), holder.imgContent, R.drawable.img_err);
            }
            holder.imgContent.setTag(R.id.img_content, position);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "数据错误，请稍后再试！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_img)
        ImageView headImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.img_content)
        ImageView imgContent;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.complaint_item)
        RelativeLayout complaintItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SetOnItemClickListener {
        void setOnItem(View view, int position);
    }
}
