package com.zhjl.qihao.mymessage.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.propertyservicecomplaint.bean.MessageListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private Activity context;
    private List<MessageListBean.DataBean.ListBean> list;

    public MessageAdapter(Activity context, List<MessageListBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<MessageListBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.tvCreateTime.setText(list.get(i).getCreateTime());
        holder.tvMessageTitle.setText(list.get(i).getTitle());
        holder.tvMessageContent.setText(list.get(i).getSummary());
        if (list.get(i).getNotifyPic().equals("")) {
            holder.imgMessage.setVisibility(View.GONE);
        } else {
            holder.imgMessage.setVisibility(View.VISIBLE);
            PictureHelper.setImageView(context, list.get(i).getNotifyPic(), holder.imgMessage, R.drawable.img_err);
        }
        if (list.get(i).getStatus() == 0) {
            holder.vCircle.setVisibility(View.VISIBLE);
        } else {
            holder.vCircle.setVisibility(View.GONE);
        }
        holder.llMessageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onClick(v, i);
            }
        });
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public interface SetOnItemClickListener {
        void onClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.v_circle)
        View vCircle;
        @BindView(R.id.tv_message_title)
        TextView tvMessageTitle;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.img_message)
        ImageView imgMessage;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.ll_message_item)
        LinearLayout llMessageItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
