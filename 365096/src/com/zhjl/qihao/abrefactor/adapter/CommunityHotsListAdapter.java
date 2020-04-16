package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.bean.CommunityHotsList;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity;
import com.zhjl.qihao.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityHotsListAdapter extends RecyclerView.Adapter<CommunityHotsListAdapter.ViewHolder> {

    private VolleyBaseActivity mContext;
    private List<CommunityHotsList.DataBean> data;
    public static final int COMPLAINT_REQUEST_CODE = 0x669;


    public CommunityHotsListAdapter(VolleyBaseActivity mContext, List<CommunityHotsList.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_hots_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvTitle.setText(data.get(i).getTitle());
        holder.tvContent.setText(data.get(i).getContent());
        if (data.get(i).getCreateUser() != null) {
            holder.tvName.setText(data.get(i).getCreateUser().getNickname());
            if (data.get(i).getCreateUser().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, data.get(i).getCreateUser().getAvatar().getSmallPicturePath(), holder.imgHead, R.drawable.ic_head);
            } else {
                holder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
        } else {
            holder.tvName.setText("");
            holder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
        }
        if (data.get(i).getForumPictureList() != null && data.get(i).getForumPictureList().size() > 0) {
            PictureHelper.setImageView(mContext, data.get(i).getForumPictureList().get(0).getFilename(), holder.imgCommunityHots, R.drawable.img_loading);
        } else {
            holder.imgCommunityHots.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_loading));
        }
        holder.tvAgreeNum.setText(data.get(i).getPraiseNum() + "");
        holder.tvReplyNum.setText(data.get(i).getDiscussNum() + "");
        holder.tvSelectNum.setText(data.get(i).getBrowseNumber() + "");
        holder.tvDate.setText(data.get(i).getHowLong());
        holder.rlItem.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
            intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
            intent.putExtra("isComplaint", true);
            intent.putExtra("position", i);
            mContext.startActivityForResult(intent, COMPLAINT_REQUEST_CODE);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<CommunityHotsList.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_community_hots)
        RoundImageView imgCommunityHots;
        @BindView(R.id.view_red)
        View viewRed;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_select_num)
        TextView tvSelectNum;
        @BindView(R.id.tv_agree_num)
        TextView tvAgreeNum;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.ll_info)
        LinearLayout llInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
