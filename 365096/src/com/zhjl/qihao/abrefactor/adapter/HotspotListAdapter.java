package com.zhjl.qihao.abrefactor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.zhjl.qihao.abrefactor.bean.NewHotspotHistoryBean;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.myaction.bean.MySendActionBean;
import com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity;
import com.zhjl.qihao.nearbyinteraction.view.EllipsizeTextView;
import com.zhjl.qihao.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotspotListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<NewHotspotHistoryBean.DataBean> data;
    //1张图片布局
    private static final int ONE_PHOTO_VIEW = 0;
    //多张图片布局
    private static final int MORE_PHOTO_VIEW = 1;

    public HotspotListAdapter(Context mContext, List<NewHotspotHistoryBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int viewType = getItemViewType(i);
        if (viewType == ONE_PHOTO_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_one, viewGroup, false);
            return new HotspotListAdapter.OneViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_more, viewGroup, false);
            return new HotspotListAdapter.MoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HotspotListAdapter.OneViewHolder) {
            HotspotListAdapter.OneViewHolder oneHolder = (HotspotListAdapter.OneViewHolder) holder;
            if (data.get(i).getCreateUser().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, data.get(i).getCreateUser().getAvatar().getSmallPicturePath(), oneHolder.imgHead, R.drawable.ic_head);
            } else {
                oneHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
            oneHolder.tvTitle.setText(data.get(i).getCreateUser().getNickname());
            oneHolder.tvTime.setText(data.get(i).getHowLong());
            String content = data.get(i).getTopic().getTopicName() + data.get(i).getContent();
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
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
            builder.setSpan(span, 0, data.get(i).getTopic().getTopicName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ClickableSpan spanContent = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                    intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                    ds.setUnderlineText(false);
                }
            };
            builder.setSpan(spanContent, data.get(i).getTopic().getTopicName().length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            oneHolder.tvContent.setText(builder);
            oneHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            oneHolder.tvNearbySelectNum.setText(data.get(i).getBrowseNumber() + "");
            oneHolder.tvNearbyAgreeNum.setText(data.get(i).getPraiseNum() + "");
            oneHolder.tvNearbyReplyNum.setText(data.get(i).getDiscussNum() + "");
            oneHolder.tvType.setText(data.get(i).getForumLabel());
            setTypeColor(oneHolder.tvType, data.get(i).getForumLabel());
            if (data.get(i).getForumPictureList().size() == 0) {
                oneHolder.imgContent.setVisibility(View.GONE);
            } else {
                oneHolder.imgContent.setVisibility(View.VISIBLE);
                PictureHelper.setImageView(mContext, data.get(i).getForumPictureList().get(0).getFilename(), oneHolder.imgContent, R.drawable.img_loading);
            }
            oneHolder.rlNoteOneItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                mContext.startActivity(intent);
            });
        } else {
            HotspotListAdapter.MoreViewHolder moreHolder = (HotspotListAdapter.MoreViewHolder) holder;
            if (data.get(i).getCreateUser().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, data.get(i).getCreateUser().getAvatar().getSmallPicturePath(), moreHolder.imgHead, R.drawable.ic_head);
            } else {
                moreHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
            moreHolder.tvTitle.setText(data.get(i).getCreateUser().getNickname());
            moreHolder.tvTime.setText(data.get(i).getHowLong());
            String content = data.get(i).getTopic().getTopicName() + data.get(i).getContent();
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
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
            builder.setSpan(span, 0, data.get(i).getTopic().getTopicName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ClickableSpan spanContent = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                    intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                    ds.setUnderlineText(false);
                }
            };
            builder.setSpan(spanContent, data.get(i).getTopic().getTopicName().length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            moreHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            moreHolder.tvContent.setText(builder);
//            moreHolder.tvContent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
//                    intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
//                    mContext.startActivity(intent);
//                }
//            });
            moreHolder.tvNearbyMoreSelectNum.setText(data.get(i).getBrowseNumber() + "");
            moreHolder.tvNearbyMoreAgreeNum.setText(data.get(i).getPraiseNum() + "");
            moreHolder.tvNearbyMoreReplyNum.setText(data.get(i).getDiscussNum() + "");
            moreHolder.tvType.setText(data.get(i).getForumLabel());
            setTypeColor(moreHolder.tvType, data.get(i).getForumLabel());
            if (data.get(i).getForumPictureList().size() == 0) {
                moreHolder.gvImg.setVisibility(View.GONE);
            } else {
                //设置GridView不可点击
                moreHolder.gvImg.setClickable(false);
                moreHolder.gvImg.setPressed(false);
                moreHolder.gvImg.setEnabled(false);
                moreHolder.gvImg.setVisibility(View.VISIBLE);
                moreHolder.gvImg.setAdapter(new HotspotListAdapter.PhotoAdapter(data.get(i).getForumPictureList()));
//                moreHolder.gvImg.setOnItemClickListener((parent, view, position, id) -> {
//                    Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
//                    intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
//                    mContext.startActivity(intent);
//                });
            }
            moreHolder.rlNoteMoreItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                mContext.startActivity(intent);
            });
        }
    }

    private void setTypeColor(TextView tvType, String forumLabel) {
        switch (forumLabel) {
            case "随手美拍":
                tvType.setTextColor(Color.parseColor("#17C2B1"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ssmp_bg));
                break;
            case "二手闲置":
                tvType.setTextColor(Color.parseColor("#FF9D42"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.esxz_bg));
                break;
            case "心灵鸡汤":
                tvType.setTextColor(Color.parseColor("#8042FF"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.xljt_bg));
                break;
            case "兴趣爱好":
                tvType.setTextColor(Color.parseColor("#4296FF"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.xqah_bg));
                break;
            case "互利互助":
                tvType.setTextColor(Color.parseColor("#E65C26"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.hlhz_bg));
                break;
            case "生活杂谈":
                tvType.setTextColor(Color.parseColor("#7DC217"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shzt_bg));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<NewHotspotHistoryBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getForumPictureList() != null && data.get(position).getForumPictureList().size() == 1) {
            return ONE_PHOTO_VIEW;
        } else {
            return MORE_PHOTO_VIEW;
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_content)
        EllipsizeTextView tvContent;
        @BindView(R.id.img_content)
        RoundImageView imgContent;
        @BindView(R.id.tv_nearby_select_num)
        TextView tvNearbySelectNum;
        @BindView(R.id.tv_nearby_agree_num)
        TextView tvNearbyAgreeNum;
        @BindView(R.id.tv_nearby_reply_num)
        TextView tvNearbyReplyNum;
        @BindView(R.id.img_complaint)
        ImageView imgComplaint;
        @BindView(R.id.rl_note_one_item)
        RelativeLayout rlNoteOneItem;

        public OneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_content)
        EllipsizeTextView tvContent;
        @BindView(R.id.gv_img)
        GridViewForScrollView gvImg;
        @BindView(R.id.tv_nearby_more_select_num)
        TextView tvNearbyMoreSelectNum;
        @BindView(R.id.tv_nearby_more_agree_num)
        TextView tvNearbyMoreAgreeNum;
        @BindView(R.id.tv_nearby_more_reply_num)
        TextView tvNearbyMoreReplyNum;
        @BindView(R.id.img_more_complaint)
        ImageView imgMoreComplaint;
        @BindView(R.id.rl_note_more_item)
        RelativeLayout rlNoteMoreItem;

        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PhotoAdapter extends BaseAdapter {
        private List<MySendActionBean.DataBean.ForumPictureListBean> forumPictureList;

        public PhotoAdapter(List<MySendActionBean.DataBean.ForumPictureListBean> forumPictureList) {
            this.forumPictureList = forumPictureList;
        }

        @Override
        public int getCount() {
            return forumPictureList.size();
        }

        @Override
        public Object getItem(int position) {
            return forumPictureList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.note_photo_view, null);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(holder);
            } else {
//                holder = (ViewHolder) convertView.getTag();
            }
            convertView.setClickable(false);
            RoundImageView imgPhoto = convertView.findViewById(R.id.img_photo);
            PictureHelper.setImageView(mContext, forumPictureList.get(position).getFilename(), imgPhoto, R.drawable.img_loading);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.img_photo)
            RoundImageView imgPhoto;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}