package com.zhjl.qihao.nearbyinteraction.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBean;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBeanTwo;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.nearbyinteraction.activity.NearByListActivity;
import com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity;
import com.zhjl.qihao.nearbyinteraction.fragment.ReportFragment;
import com.zhjl.qihao.nearbyinteraction.view.EllipsizeTextView;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhjl.qihao.myaction.adapter.MySendAdapter.REQUEST_NOTE_DATA;

public class NearByListMoreAdapter extends RecyclerView.Adapter {

    private VolleyBaseActivity mContext;
    private List<EveryWeekHotspotListBeanTwo.DataBean> data;
    private ArrayList<NearByTypeBean.DataBean> dataBeans;
    private static final int HEAD_VIEW = 0;
    //1张图片布局
    private static final int ONE_PHOTO_VIEW = 1;
    //多张图片布局
    private static final int MORE_PHOTO_VIEW = 2;
    private final Session session;

    public NearByListMoreAdapter(VolleyBaseActivity mContext, List<EveryWeekHotspotListBeanTwo.DataBean> data, ArrayList<NearByTypeBean.DataBean> dataBeans) {
        this.mContext = mContext;
        this.data = data;
        this.dataBeans = dataBeans;
        session = Session.get(mContext);
    }

//    private DeleteNote deleteNote;
//
//    public void setDeleteNote(DeleteNote deleteNote) {
//        this.deleteNote = deleteNote;
//    }
//
//    public interface DeleteNote {
//        void delete(String id, int position);
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ONE_PHOTO_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_one, viewGroup, false);
            return new OneViewHolder(view);
        } else if (i == HEAD_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_head, viewGroup, false);
            return new HeadViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_more, viewGroup, false);
            return new MoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder headHolder = (HeadViewHolder) holder;
            headHolder.vpNearbyInteraction.setPageMargin(Utils.dip2px(mContext, 20));
            headHolder.vpNearbyInteraction.setAdapter(new NearByPagerAdapter());
        } else if (holder instanceof OneViewHolder) {
            int position = i - 1; //减去头布局下标
            OneViewHolder oneHolder = (OneViewHolder) holder;
            if (data.get(position).getCreateUser().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, data.get(position).getCreateUser().getAvatar().getSmallPicturePath(), oneHolder.imgHead, R.drawable.ic_head);
            } else {
                oneHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
            oneHolder.tvTitle.setText(data.get(position).getCreateUser().getNickname());
            oneHolder.tvTime.setText(data.get(position).getHowLong());
            oneHolder.tvContent.setText(data.get(position).getContent());
            oneHolder.tvNearbySelectNum.setText(data.get(position).getBrowseNumber() + "");
            oneHolder.tvNearbyAgreeNum.setText(data.get(position).getPraiseNum() + "");
            oneHolder.tvNearbyReplyNum.setText(data.get(position).getDiscussNum() + "");
//            if (session.getUserId() != null && data.get(position).getCreateUser() != null && session.getUserId().equals(data.get(position).getCreateUser().getUserId())) {
//                oneHolder.imgComplaint.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_note_delete));
//            } else {
//                oneHolder.imgComplaint.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_complaint));
//            }
            oneHolder.tvType.setText(data.get(position).getForumLabel());
            if (data.get(position).getTopic() != null) {
                oneHolder.tvTypeTwo.setVisibility(View.VISIBLE);
                oneHolder.tvTypeTwo.setText(data.get(position).getTopic().getTopicName());
            } else {
                oneHolder.tvTypeTwo.setVisibility(View.GONE);
            }
            setTypeColor(oneHolder.tvType, data.get(position).getForumLabel());

            oneHolder.rlNoteOneItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                if (data.get(position).getForumLabel() != null && data.get(position).getForumLabel().equals("物业投诉")) {
                    intent.putExtra("isComplaint", true);
                }
                intent.putExtra("forumNoteId", data.get(position).getForumNoteId());
                intent.putExtra("position", position);
                mContext.startActivityForResult(intent, REQUEST_NOTE_DATA);
            });
            oneHolder.imgComplaint.setOnClickListener(v -> {
                if (session.getUserId() != null && data.get(position).getCreateUser() != null && session.getUserId().equals(data.get(position).getCreateUser().getUserId())) {
//                    PopWindowUtils.getInstance().show("确认删除帖子吗？", mContext);
//                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
//                        deleteNote.delete(data.get(position).getForumNoteId(), position);
//                    });
                    ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), false, true, null);
                    reportFragment.show(mContext.getSupportFragmentManager(), "7");
                    reportFragment.setSetRefershData(() -> {
                        if (data.size() > position) {
                            data.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), false, false, null);
                    reportFragment.show(mContext.getSupportFragmentManager(), "7");
                }
            });
            ViewGroup.LayoutParams params = oneHolder.imgContent.getLayoutParams();
            if (data.get(position).getForumPictureList().get(0) != null) {
                try {
                    int[] point = Utils.settingWidthHeight(mContext, (int) Float.parseFloat(data.get(position).getForumPictureList().get(0).getWidth()), (int) Float.parseFloat(data.get(position).getForumPictureList().get(0).getHeight()));
                    params.width = point[0];
                    params.height = point[1];
                    oneHolder.imgContent.setLayoutParams(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                oneHolder.imgContent.setLayoutParams(params);
            }
            if (data.get(position).getForumPictureList().size() == 0) {
                oneHolder.imgContent.setVisibility(View.GONE);
            } else {
                oneHolder.imgContent.setVisibility(View.VISIBLE);
                PictureHelper.setImageView(mContext, data.get(position).getForumPictureList().get(0).getFilename(), oneHolder.imgContent, R.drawable.img_loading);
            }
        } else {
            int position = i - 1;
            MoreViewHolder moreHolder = (MoreViewHolder) holder;
            if (data.get(position).getCreateUser().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, data.get(position).getCreateUser().getAvatar().getSmallPicturePath(), moreHolder.imgHead, R.drawable.ic_head);
            } else {
                moreHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
            moreHolder.tvTitle.setText(data.get(position).getCreateUser().getNickname());
            moreHolder.tvTime.setText(data.get(position).getHowLong());
            moreHolder.tvContent.setText(data.get(position).getContent());
            moreHolder.tvNearbyMoreSelectNum.setText(data.get(position).getBrowseNumber() + "");
            moreHolder.tvNearbyMoreAgreeNum.setText(data.get(position).getPraiseNum() + "");
            moreHolder.tvNearbyMoreReplyNum.setText(data.get(position).getDiscussNum() + "");
//            if (session.getUserId() != null && data.get(position).getCreateUser() != null && session.getUserId().equals(data.get(position).getCreateUser().getUserId())) {
//                moreHolder.imgMoreComplaint.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_note_delete));
//            } else {
//                moreHolder.imgMoreComplaint.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_complaint));
//            }
            moreHolder.tvType.setText(data.get(position).getForumLabel());
            if (data.get(position).getTopic() != null) {
                moreHolder.tvTypeTwo.setVisibility(View.VISIBLE);
                moreHolder.tvTypeTwo.setText(data.get(position).getTopic().getTopicName());
            } else {
                moreHolder.tvTypeTwo.setVisibility(View.GONE);
            }
            setTypeColor(moreHolder.tvType, data.get(position).getForumLabel());
            if (data.get(position).getForumPictureList().size() == 0) {
                moreHolder.gvImg.setVisibility(View.GONE);
            } else {
                moreHolder.gvImg.setVisibility(View.VISIBLE);
                moreHolder.gvImg.setAdapter(new PhotoAdapter(data.get(position).getForumPictureList()));
                //设置GridView不可点击
                moreHolder.gvImg.setClickable(false);
                moreHolder.gvImg.setPressed(false);
                moreHolder.gvImg.setEnabled(false);
            }
            moreHolder.rlNoteMoreItem.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, NearByNoteContentActivity.class);
                if (data.get(position).getForumLabel() != null && data.get(position).getForumLabel().equals("物业投诉")) {
                    intent.putExtra("isComplaint", true);
                }
                intent.putExtra("forumNoteId", data.get(position).getForumNoteId());
                intent.putExtra("position", position);
                mContext.startActivityForResult(intent,REQUEST_NOTE_DATA);
            });
            moreHolder.imgMoreComplaint.setOnClickListener(v -> {
                if (session.getUserId() != null && data.get(position).getCreateUser() != null && session.getUserId().equals(data.get(position).getCreateUser().getUserId())) {
//                    PopWindowUtils.getInstance().show("确认删除帖子吗？", mContext);
//                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
//                        deleteNote.delete(data.get(position).getForumNoteId(), position);
//                    });
                    ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), false, true, null);
                    reportFragment.show(mContext.getSupportFragmentManager(), "7");
                    reportFragment.setSetRefershData(() -> {
                        if (data.size() > position) {
                            data.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), false, false, null);
                    reportFragment.show(mContext.getSupportFragmentManager(), "7");
                }
            });
        }
    }


    class NearByPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public float getPageWidth(int position) {
            return 0.18f;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 64), Utils.dip2px(mContext, 64));
            view.setLayoutParams(lp);
            PictureHelper.setImageView(mContext, dataBeans.get(position).getIcon(), view, R.drawable.img_loading);
            container.addView(view);
            view.setOnClickListener(v -> {      //邻里互动分类点击
                Intent intent = new Intent(mContext, NearByListActivity.class);
                intent.putExtra("nearbyName", dataBeans.get(position).getLabelName());
                intent.putExtra("nearbyId", dataBeans.get(position).getId());
                mContext.startActivity(intent);
            });
            return view;
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
            case "物业投诉":
                tvType.setTextColor(Color.parseColor("#EAB619"));
                tvType.setBackground(ContextCompat.getDrawable(mContext, R.drawable.wyts_bg));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataBeans != null && dataBeans.size() > 0 ? data.size() + 1 : data.size();
    }

    public void addData(List<EveryWeekHotspotListBeanTwo.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && dataBeans != null && dataBeans.size() > 0) {
            return HEAD_VIEW;
        } else if (data.get(position - 1) != null && data.get(position - 1).getForumPictureList() != null && data.get(position - 1).getForumPictureList().size() == 1) {
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
        @BindView(R.id.tv_type_two)
        TextView tvTypeTwo;
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
        @BindView(R.id.tv_type_two)
        TextView tvTypeTwo;
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

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_nearby_interaction)
        ViewPager vpNearbyInteraction;
        @BindView(R.id.v_line)
        View vLine;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PhotoAdapter extends BaseAdapter {
        private List<EveryWeekHotspotListBeanTwo.DataBean.ForumPictureListBean> forumPictureList;

        public PhotoAdapter(List<EveryWeekHotspotListBeanTwo.DataBean.ForumPictureListBean> forumPictureList) {
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
