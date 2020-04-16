package com.zhjl.qihao.myaction.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.myaction.api.MyActionInterface;
import com.zhjl.qihao.myaction.bean.MyReplyActionBean;
import com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity;
import com.zhjl.qihao.nearbyinteraction.fragment.SelectReplyPopFragment;
import com.zhjl.qihao.nearbyinteraction.view.EllipsizeTextView;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MyReplyAdapter extends RecyclerView.Adapter<MyReplyAdapter.ViewHolder> {


    private VolleyBaseActivity mContext;
    private List<MyReplyActionBean.DataBean> data;
    private String createId = "";

    public MyReplyAdapter(VolleyBaseActivity mContext, List<MyReplyActionBean.DataBean> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_reply_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (data.get(i).getCreateUserInfo() != null && data.get(i).getCreateUserInfo().getAvatar() != null) {
            PictureHelper.setHeadImageView(mContext, data.get(i).getCreateUserInfo().getAvatar().getSmallPicturePath(), holder.imgHead, R.drawable.ic_head);
        } else {
            holder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
        }
        if (data.get(i).getCreateUserInfo() != null) {
            holder.tvTitle.setText(data.get(i).getCreateUserInfo().getNickname());
        } else {
            holder.tvTitle.setText("");
        }
        holder.tvTime.setText(data.get(i).getHowLong());
        holder.tvContent.setText(data.get(i).getContent());
//        if (data.get(i).getReplyUserInfo() != null) {
//            holder.viewRed.setVisibility(View.VISIBLE);
//        } else {
//            holder.viewRed.setVisibility(View.GONE);
//        }
        if (data.get(i).getNewReply() == 0) {
            holder.viewRed.setVisibility(View.GONE);
        } else {
            holder.viewRed.setVisibility(View.VISIBLE);
        }
        if (data.get(i).getForumNoteDetailVO() != null) {
            holder.lvMyReplyContent.setVisibility(View.VISIBLE);
            holder.lvMyReplyContent.setAdapter(new MyReplayListAdapter(data.get(i).getForumNoteDetailVO(), data.get(i).getForumNoteId()));
            holder.lvMyReplyContent.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                mContext.startActivity(intent);
            });
        } else {
            holder.lvMyReplyContent.setVisibility(View.GONE);
        }
        holder.imgMessage.setOnClickListener(v -> {
//            if (data.get(i).getCreateUserInfo()!=null){
//                createId = data.get(i).getCreateUserInfo().getUserId();
//            }
            requestCancelRedView(i);
            SelectReplyPopFragment selectReplyPopFragment = SelectReplyPopFragment.getInstance(data.get(i), data.get(i).getDiscussId(), createId, data.get(i).getReplyDiscussId());
            selectReplyPopFragment.show(mContext.getSupportFragmentManager(), "4");
        });
        holder.rlItem.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
            intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
            mContext.startActivity(intent);
        });
    }

    private void requestCancelRedView(int position) {
        MyActionInterface myActionInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(MyActionInterface.class);
        Map<String,Object> map = new HashMap<>();
        map.put("discussId",data.get(position).getDiscussId());
        Call<ResponseBody> call = myActionInterface.getNoeNoteDetail(map);
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                data.get(position).setNewReply(0);
                notifyDataSetChanged();
            }

            @Override
            public void fail() {

            }
        });
    }

    class MyReplayListAdapter extends BaseAdapter {

        private MyReplyActionBean.DataBean.ForumNoteDetailVOBean bean;
        private String forumNoteId;

        public MyReplayListAdapter(MyReplyActionBean.DataBean.ForumNoteDetailVOBean bean, String forumNoteId) {
            this.bean = bean;
            this.forumNoteId = forumNoteId;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.my_reply_list_item, null);
                holder = new ListViewHolder(convertView);
            }
            try {
                if (bean.getForumPictureList().size() > 0) {
                    holder.imgHead.setVisibility(View.VISIBLE);
                    PictureHelper.setImageView(mContext, bean.getForumPictureList().get(position).getFilename(), holder.imgHead, R.drawable.img_loading);
                } else {
                    holder.imgHead.setVisibility(View.GONE);
                }
//                holder.tvContent.setText(bean.getContent());
                if (bean.getTopic() != null) {
                    SpannableStringBuilder builder = new SpannableStringBuilder("#" + bean.getTopic().getTopicName() + "#" + bean.getContent());
                    ClickableSpan span = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Toast.makeText(mContext, "点击了！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
                            ds.setUnderlineText(false);
                        }
                    };
                    builder.setSpan(span, 0, bean.getTopic().getTopicName().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ClickableSpan spanString = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                            intent.putExtra("forumNoteId", forumNoteId);
                            mContext.startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                            ds.setUnderlineText(false);
                        }
                    };
                    builder.setSpan(spanString, bean.getTopic().getTopicName().length() + 2, bean.getContent().length() + bean.getTopic().getTopicName().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.tvContent.setText(builder);
                    holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    holder.tvContent.setText(bean.getContent());
                }
//                holder.rlItem.setOnClickListener(v -> {
//
//                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        class ListViewHolder {
            @BindView(R.id.img_head)
            RoundImageView imgHead;
            @BindView(R.id.tv_content)
            EllipsizeTextView tvContent;
            @BindView(R.id.rl_item)
            RelativeLayout rlItem;

            ListViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<MyReplyActionBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_message)
        ImageView imgMessage;
        @BindView(R.id.view_red)
        View viewRed;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.lv_my_reply_content)
        ListViewForScrollView lvMyReplyContent;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
