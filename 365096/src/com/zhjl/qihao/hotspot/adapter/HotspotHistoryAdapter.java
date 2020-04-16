package com.zhjl.qihao.hotspot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.hotspot.bean.HotspotHistoryListBean;
import com.zhjl.qihao.hotspot.view.RoundTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotspotHistoryAdapter extends RecyclerView.Adapter<HotspotHistoryAdapter.MyViewHolder> {
    private Context context;
    private List<HotspotHistoryListBean.DataBean> list;
    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public HotspotHistoryAdapter(Context context, List<HotspotHistoryListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<HotspotHistoryListBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_updates_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
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
            holder.tvTitle.setText(list.get(position).getTitle());
            if (list.get(position).getCreateUser() != null) {
                holder.tvName.setText(list.get(position).getCreateUser().getNickname());
                if (list.get(position).getCreateUser().getAvatar() != null) {
                    PictureHelper.setOptionsGlide(context, 12, list.get(position).getCreateUser().getAvatar().getSmallPicturePath(), holder.headImg, R.drawable.img_head);
                }
            }
            if (list.get(position).getForumLabel() != null) {
                switch (list.get(position).getForumLabel()) {
                    case "生活杂谈":
                        holder.tvType.setBackgroundColor(Color.parseColor("#ff9482"));
                        break;
                    case "兴趣爱好":
                        holder.tvType.setBackgroundColor(Color.parseColor("#67b4f2"));
                        break;
                    case "二手闲置":
                        holder.tvType.setBackgroundColor(Color.parseColor("#f9c236"));
                        break;
                    case "心灵鸡汤":
                        holder.tvType.setBackgroundColor(Color.parseColor("#ff9cb7"));
                        break;
                    case "随手美拍":
                        holder.tvType.setBackgroundColor(Color.parseColor("#afa3f8"));
                        break;
                    case "互利互助":
                        holder.tvType.setBackgroundColor(Color.parseColor("#65d49d"));
                        break;
                    default:
                        holder.tvType.setBackgroundColor(Color.parseColor("#209b45"));
                        break;
                }
                holder.tvType.setText(list.get(position).getForumLabel());
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
            PictureHelper.setOptionsGlide(context, 12, list.get(position).getForumPictureList().get(0).getPicturePath(), holder.imgContent, R.drawable.img_err);
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
        @BindView(R.id.tv_type)
        RoundTextView tvType;
        @BindView(R.id.img_content)
        ImageView imgContent;
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
