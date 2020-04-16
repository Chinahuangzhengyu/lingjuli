package com.zhjl.qihao.nearbyinteraction.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
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
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.activity.ComplaintDetailActivity;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.nearbyinteraction.activity.SearchNoteActivity;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.fragment.ReportFragment;
import com.zhjl.qihao.nearbyinteraction.fragment.SelectReplyPopFragment;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class NearByNoteContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private VolleyBaseActivity mContext;
    private List<NearByNoteReplyContentBean.DataBean> listData;
    private NearByNoteContentBean.DataBean resultData;
    private static final int HEAD_VIEW = 0;
    private static final int CONTENT_VIEW = 1;
    private static final int EMPTY_VIEW = 2;
    public static final int RESULT_DELETE_NOTE = 0x668;
    private boolean isMySelf;
    private String createId = "";
    private Session session;
    private boolean isComplaint;

    public NearByNoteContentAdapter(VolleyBaseActivity mContext, List<NearByNoteReplyContentBean.DataBean> listData, NearByNoteContentBean.DataBean resultData) {
        this.mContext = mContext;
        this.listData = listData;
        session = Session.get(mContext);
        this.resultData = resultData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == HEAD_VIEW) {
            if (!isComplaint) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_note_head_view, viewGroup, false);
                return new HeadViewHolder(view);
            } else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.complaint_note_head_view, viewGroup, false);
                return new ComplaintHeadViewHolder(view);
            }
        } else if (i == CONTENT_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.nearby_note_content, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, viewGroup, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        try {
            if (holder instanceof HeadViewHolder) {
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (resultData.getForumPictureList() == null || resultData.getForumPictureList().size() == 0) {
                    headViewHolder.llNoteImg.removeAllViews();
                    headViewHolder.llNoteImg.setVisibility(View.GONE);
                } else {
                    headViewHolder.llNoteImg.setVisibility(View.VISIBLE);
                    addView(headViewHolder.llNoteImg);
                }
                if (resultData.getCreateUser() != null) {
                    createId = resultData.getCreateUser().getUserId();
                }
                if (resultData.getCreateUser() != null && resultData.getCreateUser().getUserId().equals(session.getUserId())) {
                    headViewHolder.imgDelete.setVisibility(View.VISIBLE);
                    isMySelf = true;
                } else {
                    isMySelf = false;
                    headViewHolder.imgDelete.setVisibility(View.GONE);
                }
                if (resultData.getCreateUser() != null && resultData.getCreateUser().getAvatar() != null) {
                    PictureHelper.setHeadImageView(mContext, resultData.getCreateUser().getAvatar().getSmallPicturePath(), headViewHolder.imgHead, R.drawable.ic_head);
                } else {
                    headViewHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                }
                headViewHolder.tvTitle.setText(resultData.getCreateUser().getNickname());
                headViewHolder.tvTime.setText(resultData.getHowLong());
                headViewHolder.tvNearbySelectNum.setText(resultData.getBrowseNumber() + "");
                headViewHolder.tvNearbyAgreeNum.setText(resultData.getPraiseNum() + "");
                headViewHolder.tvNearbyReplyNum.setText(resultData.getDiscussNum() + "");
                String topicName = "#" + resultData.getTopic().getTopicName() + "#";
                SpannableStringBuilder builder = new SpannableStringBuilder(topicName + resultData.getContent());
                ClickableSpan span = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Intent intent = new Intent(mContext, SearchNoteActivity.class);
                        intent.putExtra("topicName", topicName);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
                        ds.setUnderlineText(false);
                    }
                };
                builder.setSpan(span, 0, topicName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                headViewHolder.tvContent.setText(builder);
                headViewHolder.tvReplyNum.setText("全部评论(" + resultData.getDiscussNum() + ")");
                headViewHolder.imgDelete.setOnClickListener(v -> {
                    PopWindowUtils.getInstance().show("确认删除帖子吗？", mContext);
                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
                        deleteNote();
                    });
                });

            } else if (holder instanceof ComplaintHeadViewHolder) {
                ComplaintHeadViewHolder complaintHeadViewHolder = (ComplaintHeadViewHolder) holder;
                if (resultData.getForumPictureList() == null || resultData.getForumPictureList().size() == 0) {
                    complaintHeadViewHolder.llNoteImg.removeAllViews();
                    complaintHeadViewHolder.llNoteImg.setVisibility(View.GONE);
                } else {
                    complaintHeadViewHolder.llNoteImg.setVisibility(View.VISIBLE);
                    addView(complaintHeadViewHolder.llNoteImg);
                }
                if (resultData.getCreateUser() != null && resultData.getCreateUser().getAvatar() != null) {
                    PictureHelper.setHeadImageView(mContext, resultData.getCreateUser().getAvatar().getSmallPicturePath(), complaintHeadViewHolder.imgHead, R.drawable.ic_head);
                } else {
                    complaintHeadViewHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                }
                complaintHeadViewHolder.tvName.setText(resultData.getCreateUser().getNickname());
                complaintHeadViewHolder.tvDate.setText(resultData.getHowLong());
                complaintHeadViewHolder.tvContent.setText(resultData.getContent());
                complaintHeadViewHolder.tvNearbySelectNum.setText(resultData.getBrowseNumber() + "");
                complaintHeadViewHolder.tvNearbyAgreeNum.setText(resultData.getPraiseNum() + "");
                complaintHeadViewHolder.tvNearbyReplyNum.setText(resultData.getDiscussNum() + "");
                complaintHeadViewHolder.tvReplyNum.setText("全部评论(" + resultData.getDiscussNum() + ")");
                complaintHeadViewHolder.tvTitle.setText(resultData.getTitle());
                complaintHeadViewHolder.tvProgress.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, RepairDetailActivity.class);
                    intent.putExtra("isComplaint",true);
                    intent.putExtra("id",resultData.getComplaintId());
                    mContext.startActivity(intent);
                });
            } else if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                if (listData.size() > 0) {
                    int position = i - 1;  //减去头布局下标
                    if (listData.get(position).getCreateUserInfo().getAvatar() != null) {
                        PictureHelper.setHeadImageView(mContext, listData.get(position).getCreateUserInfo().getAvatar().getSmallPicturePath(), viewHolder.imgHead, R.drawable.ic_head);
                    } else {
                        viewHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                    }
                    viewHolder.tvTitle.setText(listData.get(position).getCreateUserInfo().getNickname());
                    viewHolder.tvTime.setText(listData.get(position).getHowLong());
                    viewHolder.tvContent.setText(listData.get(position).getContent());
                    viewHolder.rlItem.setOnClickListener(v -> {
                        if (listData.get(position).getCreateUserInfo() != null) {
                            setReplyOnClickListener.onClick(v, listData.get(position).getCreateUserInfo().getNickname(), listData.get(position).getDiscussId());
                        } else {
                            setReplyOnClickListener.onClick(v, "", listData.get(position).getDiscussId());
                        }
                    });
                    if (listData.get(position).getDiscussNum() == 0) {
                        viewHolder.tvSelectReply.setText("回复");
                    } else {
                        viewHolder.tvSelectReply.setText("查看" + listData.get(position).getDiscussNum() + "回复");
                    }
                    if (listData.get(position).getCreateUserInfo() != null && listData.get(position).getReplyUserInfo() != null && listData.get(position).getReplyUserInfo().getUserId().equals(listData.get(position).getCreateUserInfo().getUserId())) {
                        viewHolder.tvTag.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.tvTag.setVisibility(View.GONE);
                    }
                    viewHolder.tvSelectReply.setOnClickListener(v -> {
                        if (viewHolder.tvSelectReply.getText().toString().equals("回复")) {
                            if (listData.get(position).getCreateUserInfo() != null) {
                                setReplyOnClickListener.onClick(v, listData.get(position).getCreateUserInfo().getNickname(), listData.get(position).getDiscussId());
                            } else {
                                setReplyOnClickListener.onClick(v, "", listData.get(position).getDiscussId());
                            }
                        } else {
                            SelectReplyPopFragment selectReplyPopFragment = SelectReplyPopFragment.getInstance(listData.get(position), listData.get(position).getDiscussId(), createId, "");
                            selectReplyPopFragment.show(mContext.getSupportFragmentManager(), "4");
                            selectReplyPopFragment.setGetReplyNum(num -> {
                                viewHolder.tvSelectReply.setText("查看" + num + "回复");
//                                resultData.setDiscussNum(num);
//                                addHeadData(resultData);
                            });
                            selectReplyPopFragment.setSetRefreshData(() -> {
                                setRefreshData.refresh();
                            });
//                            Intent intent = new Intent(mContext, ReplyListActivity.class);
//                            intent.putExtra("discussId",listData.get(position).getDiscussId());
//                            intent.putExtra("bean",listData.get(position));
//                            mContext.startActivity(intent);
//                            mContext.overridePendingTransition(0,0);
                        }
                    });
                    viewHolder.imgComplaint.setOnClickListener(v -> {
                        if (listData.get(position).getCreateUserInfo() != null) {
                            if (listData.get(position).getCreateUserInfo().getUserId().equals(session.getUserId())) {
                                ReportFragment reportFragment = ReportFragment.getInstance(listData.get(position).getForumNoteId(), true, true, listData.get(position).getDiscussId());
                                reportFragment.show(mContext.getSupportFragmentManager(), "7");
                                reportFragment.setSetRefershData(() -> {
                                    //删除评论
                                    if (resultData != null) {
                                        int discussNum = resultData.getDiscussNum();
                                        if (discussNum == 0) {
                                            return;
                                        } else {
                                            discussNum = discussNum - 1;
                                        }
                                        resultData.setDiscussNum(discussNum);
                                    }
                                    listData.remove(position);
                                    notifyDataSetChanged();
                                });
                            } else {
                                ReportFragment reportFragment = ReportFragment.getInstance(listData.get(position).getForumNoteId(), true, false, listData.get(position).getDiscussId());
                                reportFragment.show(mContext.getSupportFragmentManager(), "7");
                            }
                        }
                    });
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 添加图片
     *
     * @param llNoteImg
     */
    private void addView(LinearLayout llNoteImg) {
        llNoteImg.removeAllViews();
        if (resultData != null && resultData.getForumPictureList() != null && resultData.getForumPictureList().size() != 0) {
            for (int i = 0; i < resultData.getForumPictureList().size(); i++) {
                RoundImageView imgPhoto = new RoundImageView(mContext);
                try {
                    int[] pictureWidthHeight = Utils.pictureWidthHeight(mContext, (int) Float.parseFloat(resultData.getForumPictureList().get(i).getWidth()), (int) Float.parseFloat(resultData.getForumPictureList().get(i).getHeight()), new int[]{Utils.dip2px(mContext, 14), Utils.dip2px(mContext, 14)});
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pictureWidthHeight[0], pictureWidthHeight[1]);
                    params.bottomMargin = Utils.dip2px(mContext, 10);
                    imgPhoto.setLayoutParams(params);
                    imgPhoto.setRoundImg(mContext, 6);
                    PictureHelper.setImageView(mContext, resultData.getForumPictureList().get(i).getFilename(), imgPhoto, R.drawable.img_loading);
                    llNoteImg.addView(imgPhoto);
                    imgPhoto.setOnClickListener(v -> {
                        for (int j = 0; j < llNoteImg.getChildCount(); j++) {
                            if (v.getTag() == llNoteImg.getChildAt(j).getTag()) {
                                showImage(j);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showImage(int index) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        if (resultData != null && resultData.getForumPictureList() != null) {
            String[] strings = new String[resultData.getForumPictureList().size()];
            for (int i = 0; i < resultData.getForumPictureList().size(); i++) {
                strings[i] = resultData.getForumPictureList().get(i).getFilename();
            }
            if (strings.length > index) {
                it.putExtra("urls", strings);
                it.putExtra("nowImage", strings[index]);
                it.putExtra("index", index);
                mContext.startActivity(it);
            }
        }
    }


    private void deleteNote() {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", resultData.getForumNoteId());
        Call<ResponseBody> call = nearByInterfaces.noteDelete(ParamForNet.put(map));
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                mContext.getIntent().putExtra("position", mContext.getIntent().getIntExtra("position", -1));
                mContext.setResult(RESULT_DELETE_NOTE, mContext.getIntent());
                mContext.finish();
            }

            @Override
            public void fail() {

            }
        });
    }

    private SetRefreshData setRefreshData;

    public void setSetRefreshData(SetRefreshData setRefreshData) {
        this.setRefreshData = setRefreshData;
    }

    public interface SetRefreshData {
        void refresh();
    }


    private SetReplyOnClickListener setReplyOnClickListener;

    public void setReplyOnClickLintener(SetReplyOnClickListener setReplyOnClickLintener) {
        this.setReplyOnClickListener = setReplyOnClickLintener;
    }

    public interface SetReplyOnClickListener {
        void onClick(View view, String replyName, String id);
    }

    @Override
    public int getItemCount() {
        return resultData == null ? listData.size() : listData.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 && resultData != null) {
            return HEAD_VIEW;
        } else if (position + 1 == listData.size() + 2) {
            return EMPTY_VIEW;
        }
        return CONTENT_VIEW;
    }

    public void addData(List<NearByNoteReplyContentBean.DataBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void addHeadData(NearByNoteContentBean.DataBean resultData, boolean isComplaint) {
        this.resultData = resultData;
        this.isComplaint = isComplaint;
        notifyDataSetChanged();
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_complaint)
        ImageView imgComplaint;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_select_reply)
        TextView tvSelectReply;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.tv_nearby_select_num)
        TextView tvNearbySelectNum;
        @BindView(R.id.tv_nearby_agree_num)
        TextView tvNearbyAgreeNum;
        @BindView(R.id.tv_nearby_reply_num)
        TextView tvNearbyReplyNum;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.ll_note_img)
        LinearLayout llNoteImg;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;


        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ComplaintHeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_progress)
        TextView tvProgress;
        @BindView(R.id.rl_head)
        RelativeLayout rlHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_nearby_select_num)
        TextView tvNearbySelectNum;
        @BindView(R.id.tv_nearby_agree_num)
        TextView tvNearbyAgreeNum;
        @BindView(R.id.tv_nearby_reply_num)
        TextView tvNearbyReplyNum;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.ll_info)
        LinearLayout llInfo;
        @BindView(R.id.v_line)
        View vLine;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.ll_note_img)
        LinearLayout llNoteImg;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;

        public ComplaintHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PhotoAdapter extends BaseAdapter {
        private List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList;

        public PhotoAdapter(List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList) {
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
                convertView = View.inflate(mContext, R.layout.note_detail_photo_view, null);
//                holder = new ViewHolder(convertView);
//                convertView.setTag(holder);
            } else {
//                holder = (ViewHolder) convertView.getTag();
            }
            RoundImageView imgPhoto = convertView.findViewById(R.id.img_photo);
            ViewGroup.LayoutParams params = imgPhoto.getLayoutParams();
            if (forumPictureList.get(position).getWidth() != null && forumPictureList.get(position).getHeight() != null) {
                try {
                    params.height = (int) Float.parseFloat(forumPictureList.get(position).getHeight());
                    imgPhoto.setLayoutParams(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                imgPhoto.setLayoutParams(params);
            }
            PictureHelper.setImageView(mContext, forumPictureList.get(position).getFilename(), imgPhoto, R.drawable.img_loading);
            return convertView;
        }

        public void addData(List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList) {
            this.forumPictureList = forumPictureList;
            notifyDataSetChanged();
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
