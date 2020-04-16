package com.zhjl.qihao.myaction.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.myaction.bean.MySendActionBean;
import com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity;
import com.zhjl.qihao.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private VolleyBaseActivity mContext;
    private List<MySendActionBean.DataBean> data;
    public static final int REQUEST_NOTE_DATA = 0x885;
    //1张图片布局
    private static final int ONE_PHOTO_VIEW = 1;

    public MySendAdapter(VolleyBaseActivity mContext, List<MySendActionBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ONE_PHOTO_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.my_send_list_one, viewGroup, false);
            return new OneViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.my_send_list, viewGroup, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder moreHolder, int i) {
        if (moreHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) moreHolder;
            holder.tvContent.setText(data.get(i).getContent());
            holder.tvNearbyMoreSelectNum.setText(data.get(i).getBrowseNumber() + "");
            holder.tvNearbyMoreAgreeNum.setText(data.get(i).getPraiseNum() + "");
            holder.tvNearbyMoreReplyNum.setText(data.get(i).getDiscussNum() + "");
            holder.tvDate.setText(data.get(i).getHowLong());
            holder.tvType.setText(data.get(i).getForumLabel());
            if (data.get(i).getTopic() != null) {
                holder.tvTypeTwo.setVisibility(View.VISIBLE);
                holder.tvTypeTwo.setText(data.get(i).getTopic().getTopicName());
            } else {
                holder.tvTypeTwo.setVisibility(View.GONE);
            }
            setTypeColor(holder.tvType, data.get(i).getForumLabel());
            if (data.get(i).getForumPictureList().size() == 0) {
                holder.gvImg.setVisibility(View.GONE);
            } else {
                holder.gvImg.setVisibility(View.VISIBLE);
                //设置GridView不可点击
                holder.gvImg.setClickable(false);
                holder.gvImg.setPressed(false);
                holder.gvImg.setEnabled(false);
                holder.gvImg.setAdapter(new PhotoAdapter(data.get(i).getForumPictureList()));
            }
            holder.rlMySendItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                intent.putExtra("position", i);
                mContext.startActivityForResult(intent, REQUEST_NOTE_DATA);
            });
        }else {
            OneViewHolder holder = (OneViewHolder) moreHolder;
            holder.tvContent.setText(data.get(i).getContent());
            holder.tvNearbyMoreSelectNum.setText(data.get(i).getBrowseNumber() + "");
            holder.tvNearbyMoreAgreeNum.setText(data.get(i).getPraiseNum() + "");
            holder.tvNearbyMoreReplyNum.setText(data.get(i).getDiscussNum() + "");

            holder.tvDate.setText(data.get(i).getHowLong());
            holder.tvType.setText(data.get(i).getForumLabel());
            if (data.get(i).getTopic() != null) {
                holder.tvTypeTwo.setVisibility(View.VISIBLE);
                holder.tvTypeTwo.setText(data.get(i).getTopic().getTopicName());
            } else {
                holder.tvTypeTwo.setVisibility(View.GONE);
            }
            setTypeColor(holder.tvType, data.get(i).getForumLabel());
            ViewGroup.LayoutParams params = holder.imgContent.getLayoutParams();
            if (data.get(i).getForumPictureList().get(0) != null) {
                try {
                    int[] point = Utils.settingWidthHeight(mContext, (int) Float.parseFloat(data.get(i).getForumPictureList().get(0).getWidth()), (int) Float.parseFloat(data.get(i).getForumPictureList().get(0).getHeight()));
                    params.width = point[0];
                    params.height = point[1];
                    holder.imgContent.setLayoutParams(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.imgContent.setLayoutParams(params);
            }
            if (data.get(i).getForumPictureList().size() == 0) {
                holder.imgContent.setVisibility(View.GONE);
            } else {
                holder.imgContent.setVisibility(View.VISIBLE);
                PictureHelper.setImageView(mContext, data.get(i).getForumPictureList().get(0).getFilename(), holder.imgContent, R.drawable.img_loading);
            }
            holder.rlMySendItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                intent.putExtra("forumNoteId", data.get(i).getForumNoteId());
                intent.putExtra("position", i);
                mContext.startActivityForResult(intent, REQUEST_NOTE_DATA);
            });
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

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) != null && data.get(position).getForumPictureList() != null && data.get(position).getForumPictureList().size() == 1) {
            return ONE_PHOTO_VIEW;
        }
        return super.getItemViewType(position);
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
            case "物业投诉":
                tvType.setTextColor(Color.parseColor("#EAB619"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.wyts_bg));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<MySendActionBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class OneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.img_content)
        RoundImageView imgContent;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_type_two)
        TextView tvTypeTwo;
        @BindView(R.id.tv_nearby_more_select_num)
        TextView tvNearbyMoreSelectNum;
        @BindView(R.id.tv_nearby_more_agree_num)
        TextView tvNearbyMoreAgreeNum;
        @BindView(R.id.tv_nearby_more_reply_num)
        TextView tvNearbyMoreReplyNum;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.ll_info)
        LinearLayout llInfo;
        @BindView(R.id.rl_my_send_item)
        RelativeLayout rlMySendItem;

        public OneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gv_img)
        GridViewForScrollView gvImg;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_type_two)
        TextView tvTypeTwo;
        @BindView(R.id.tv_nearby_more_select_num)
        TextView tvNearbyMoreSelectNum;
        @BindView(R.id.tv_nearby_more_agree_num)
        TextView tvNearbyMoreAgreeNum;
        @BindView(R.id.tv_nearby_more_reply_num)
        TextView tvNearbyMoreReplyNum;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_my_send_item)
        RelativeLayout rlMySendItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
