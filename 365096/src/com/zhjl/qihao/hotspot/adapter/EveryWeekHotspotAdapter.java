package com.zhjl.qihao.hotspot.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EveryWeekHotspotAdapter extends RecyclerView.Adapter<EveryWeekHotspotAdapter.MyViewHolder> {

    private Context context;
    private List<EveryWeekHotspotListBean.DataBean> list;
    private SetOnItemClickListener setOnItemClickListener;
    private boolean isGoneNum;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public EveryWeekHotspotAdapter(Context context, List<EveryWeekHotspotListBean.DataBean> list, boolean isGoneNum) {
//        setHasStableIds(true);
        this.context = context;
        this.list = list;
        this.isGoneNum = isGoneNum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.every_week_hotspot_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            if (isGoneNum) {
                holder.tvSort.setVisibility(View.GONE);
            } else {
                if (position > 2) {
                    holder.tvSort.setVisibility(View.GONE);
                } else {
                    holder.tvSort.setVisibility(View.VISIBLE);
                    holder.tvSort.setText(position+1 + "");
                }
            }
            holder.tvTitle.setText(list.get(position).getTitle());
            if (list.get(position).getCreateUser()!=null){
                holder.tvName.setText(list.get(position).getCreateUser().getNickname());
            }
            holder.tvTime.setText(list.get(position).getHowLong());
            holder.tvContent.setText(list.get(position).getContent());
            holder.tvCommentNum.setText("已有" + list.get(position).getDiscussNum() + "人参与评论");
            holder.tvHot.setText(list.get(position).getPraiseNum() + "热度");
            holder.hotItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener.setOnItem(v, position);
                }
            });
            if (list.get(position).getCreateUser()!=null){
                PictureHelper.setOptionsGlide(context, 12, list.get(position).getCreateUser().getAvatar().getPicturePath(), holder.headImg, R.drawable.img_head);
            }
            if (list.get(position).getForumPictureList() == null || list.get(position).getForumPictureList().size() == 0) {
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
            PictureHelper.setOptionsGlide(context, 12, list.get(position).getForumPictureList().get(0).getSmallPicturePath(), holder.imgContent, R.drawable.img_err);
            holder.imgContent.setTag(R.id.img_content, position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public void addData(List<EveryWeekHotspotListBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_item)
        RelativeLayout hotItem;
        @BindView(R.id.head_img)
        ImageView headImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.img_content)
        ImageView imgContent;
        @BindView(R.id.tv_sort)
        TextView tvSort;
        @BindView(R.id.tv_hot)
        TextView tvHot;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SetOnItemClickListener {
        void setOnItem(View view, int position);
    }
}
