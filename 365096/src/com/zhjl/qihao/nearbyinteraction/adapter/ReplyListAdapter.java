package com.zhjl.qihao.nearbyinteraction.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.myaction.bean.MyReplyActionBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NoteReplyListBean;
import com.zhjl.qihao.nearbyinteraction.fragment.ReportFragment;
import com.zhjl.qihao.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private VolleyBaseActivity mContext;
    private List<NoteReplyListBean.DataBean> data;
    private List<NoteReplyListBean.DataBean> newData;
    private Object bean;
    private static final int HEAD_VIEW = 0;
    private static final int CONTENT_VIEW = 1;
    private static final int FOOT_VIEW = 2;
    private String useId = "";
    private String createId = "";
    private boolean isFinish;

    private SetReplyOnClickListener setReplyOnClickListener;
    private final Session session;

    public void setReplyOnClickListener(SetReplyOnClickListener setReplyOnClickListener) {
        this.setReplyOnClickListener = setReplyOnClickListener;
    }

    public interface SetReplyOnClickListener {
        void onClick(View view, String replyName, String id, int position);
    }

    public ReplyListAdapter(VolleyBaseActivity mContext, List<NoteReplyListBean.DataBean> data, Object bean, String createId) {
        this.mContext = mContext;
        session = Session.get(mContext);
        this.data = data;
        this.bean = bean;
        this.createId = createId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == HEAD_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.reply_list_head, viewGroup, false);
            return new HeadViewHolder(view);
        } else if (i == FOOT_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.reply_list_foot, viewGroup, false);
            return new FootViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.reply_list, viewGroup, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        try {
            if (holder instanceof ViewHolder) {
                int position = i - 1;
                ViewHolder holderContent = (ViewHolder) holder;
                if (data.get(position).getCreateUserInfo() != null) {
                    holderContent.tvTitle.setText(data.get(position).getCreateUserInfo().getNickname());
                } else {
                    holderContent.tvTitle.setText("");
                }
                holderContent.tvTime.setText(data.get(position).getHowLong());
                holderContent.rlItem.setOnClickListener(v -> {
                    if (data.get(position).getCreateUserInfo() != null) {
                        setReplyOnClickListener.onClick(v, data.get(position).getCreateUserInfo().getNickname(), data.get(position).getDiscussId(), position);
                    } else {
                        setReplyOnClickListener.onClick(v, "", data.get(position).getDiscussId(), position);
                    }
                });
                if (data.get(position).getCreateUserInfo() != null && data.get(position).getCreateUserInfo().getAvatar() != null) {
                    PictureHelper.setHeadImageView(mContext, data.get(position).getCreateUserInfo().getAvatar().getSmallPicturePath(), holderContent.imgHead, R.drawable.ic_head);
                } else {
                    holderContent.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                }
                if (data.get(position).getCreateUserInfo() != null && data.get(position).getReplyUserInfo() != null) {
                    if (data.get(position).getCreateUserInfo().getUserId().equals(createId)) {
                        holderContent.tvTag.setVisibility(View.VISIBLE);
                    } else {
                        holderContent.tvTag.setVisibility(View.GONE);
                    }
                    if (!useId.equals(data.get(position).getReplyUserInfo().getUserId())) {
                        holderContent.tvContent.setText(Html.fromHtml("<font color = \"#1f1f1f\">回复</font>" + "<font color = \"#23AC38\">" + data.get(position).getReplyUserInfo().getNickname() + "</font>" + "<font color = \"#1f1f1f\">：</font>" + "<font color = \"#1f1f1f\">" + data.get(position).getContent() + "</font>"));
                    } else {
                        holderContent.tvContent.setText(data.get(position).getContent());
                    }
                } else {
                    holderContent.tvTag.setVisibility(View.GONE);
                    holderContent.tvContent.setText(data.get(position).getContent());
                }
                holderContent.imgComplaint.setOnClickListener(v -> {
                    if (data.get(position).getCreateUserInfo() != null) {
                        if (data.get(position).getCreateUserInfo().getUserId().equals(session.getUserId())) {
                            ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), true, true, data.get(position).getDiscussId());
                            reportFragment.show(mContext.getSupportFragmentManager(), "7");
                            reportFragment.setSetRefershData(() -> {
                                setRefreshData.refresh();
                            });
                        } else {
                            ReportFragment reportFragment = ReportFragment.getInstance(data.get(position).getForumNoteId(), true, false, data.get(position).getDiscussId());
                            reportFragment.show(mContext.getSupportFragmentManager(), "7");
                        }
                    }

                });
            } else if (holder instanceof FootViewHolder) {
                FootViewHolder footViewHolder = (FootViewHolder) holder;
                if (isFinish){
                    footViewHolder.tvNotData.setVisibility(View.VISIBLE);
                    footViewHolder.llLoading.setVisibility(View.GONE);
                }else {
                    footViewHolder.llLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvNotData.setVisibility(View.GONE);
                }
                footViewHolder.tvNotData.setOnClickListener(v -> {
                    getFootView.footView(footViewHolder.tvNotData,footViewHolder.llLoading);
                });
            } else {
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (bean != null) {
                    if (bean instanceof NearByNoteReplyContentBean.DataBean) {
                        NearByNoteReplyContentBean.DataBean beanReply = (NearByNoteReplyContentBean.DataBean) bean;
                        //设置头部数据
                        if (beanReply.getCreateUserInfo() != null) {
                            useId = beanReply.getCreateUserInfo().getUserId();
                            headViewHolder.tvTitle.setText(beanReply.getCreateUserInfo().getNickname());
                            if (beanReply.getCreateUserInfo().getUserId().equals(createId)) {
                                headViewHolder.tvTag.setVisibility(View.VISIBLE);
                            } else {
                                headViewHolder.tvTag.setVisibility(View.GONE);
                            }
                        }
                        if (beanReply.getCreateUserInfo() != null && beanReply.getCreateUserInfo().getAvatar() != null) {
                            PictureHelper.setHeadImageView(mContext, beanReply.getCreateUserInfo().getAvatar().getSmallPicturePath(), headViewHolder.imgHead, R.drawable.ic_head);
                        } else {
                            headViewHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                        }
                        headViewHolder.tvTime.setText(beanReply.getHowLong());
                        headViewHolder.tvContent.setText(beanReply.getContent());
                        headViewHolder.rlItem.setOnClickListener(v -> {
                            if (beanReply.getCreateUserInfo() != null) {
                                setReplyOnClickListener.onClick(v, beanReply.getCreateUserInfo().getNickname(), beanReply.getDiscussId(), 0);
                            } else {
                                setReplyOnClickListener.onClick(v, "", beanReply.getDiscussId(), 0);
                            }
                        });
                    } else if (bean instanceof MyReplyActionBean.DataBean) {
                        MyReplyActionBean.DataBean beanReply = (MyReplyActionBean.DataBean) bean;
                        //设置头部数据
                        if (beanReply.getCreateUserInfo() != null) {
                            useId = beanReply.getCreateUserInfo().getUserId();
                            headViewHolder.tvTitle.setText(beanReply.getCreateUserInfo().getNickname());
                            headViewHolder.tvTag.setVisibility(View.GONE);
                        }
                        if (beanReply.getCreateUserInfo() != null && beanReply.getCreateUserInfo().getAvatar() != null) {
                            PictureHelper.setHeadImageView(mContext, beanReply.getCreateUserInfo().getAvatar().getSmallPicturePath(), headViewHolder.imgHead, R.drawable.ic_head);
                        } else {
                            headViewHolder.imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
                        }
                        headViewHolder.tvTime.setText(beanReply.getHowLong());
                        headViewHolder.tvContent.setText(beanReply.getContent());
                        headViewHolder.rlItem.setOnClickListener(v -> {
                            if (beanReply.getCreateUserInfo() != null) {
                                setReplyOnClickListener.onClick(v, beanReply.getCreateUserInfo().getNickname(), beanReply.getDiscussId(), 0);
                            } else {
                                setReplyOnClickListener.onClick(v, "", beanReply.getDiscussId(), 0);
                            }
                        });
                    }

                    headViewHolder.imgComplaint.setOnClickListener(v -> {
                        if (bean != null) {
                            if (bean instanceof NearByNoteReplyContentBean.DataBean) {
                                NearByNoteReplyContentBean.DataBean nearByBean = (NearByNoteReplyContentBean.DataBean) bean;
                                if (nearByBean.getCreateUserInfo() != null) {
                                    if (nearByBean.getCreateUserInfo().getUserId().equals(session.getUserId())) {
                                        ReportFragment reportFragment = ReportFragment.getInstance(nearByBean.getForumNoteId(), true, true, nearByBean.getDiscussId());
                                        reportFragment.show(mContext.getSupportFragmentManager(), "7");
                                        reportFragment.setSetRefershData(() -> {
                                            setRefreshData.refresh();
                                        });
                                    } else {
                                        ReportFragment reportFragment = ReportFragment.getInstance(nearByBean.getForumNoteId(), true, false, nearByBean.getDiscussId());
                                        reportFragment.show(mContext.getSupportFragmentManager(), "7");
                                    }
                                }
                            } else if (bean instanceof MyReplyActionBean.DataBean) {
                                MyReplyActionBean.DataBean actionBean = (MyReplyActionBean.DataBean) bean;
                                if (actionBean.getCreateUserInfo() != null) {
                                    if (actionBean.getCreateUserInfo().getUserId().equals(session.getUserId())) {
                                        ReportFragment reportFragment = ReportFragment.getInstance(actionBean.getForumNoteId(), true, true, actionBean.getDiscussId());
                                        reportFragment.show(mContext.getSupportFragmentManager(), "7");
                                        reportFragment.setSetRefershData(() -> {
                                            setRefreshData.refresh();
                                        });
                                    } else {
                                        ReportFragment reportFragment = ReportFragment.getInstance(actionBean.getForumNoteId(), true, false, actionBean.getDiscussId());
                                        reportFragment.show(mContext.getSupportFragmentManager(), "7");
                                    }
                                }
                            }
                        }
                    });

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface GetFootView{
        void footView(TextView view,LinearLayout loadingView);
    }

    private GetFootView getFootView;

    public void setGetFootView(GetFootView getFootView) {
        this.getFootView = getFootView;
    }

    public interface SetRefreshData {
        void refresh();
    }

    private SetRefreshData setRefreshData;

    public void setSetRefreshData(SetRefreshData setRefershData) {
        this.setRefreshData = setRefershData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && bean != null) {
            return HEAD_VIEW;
        } else if (position + 1 == data.size() + 2) {
            return FOOT_VIEW;
        }
        return CONTENT_VIEW;
    }

    @Override
    public int getItemCount() {
        return bean != null && data.size() >= 0 ? data.size() + 2 : data.size();
    }

    public void addData(List<NoteReplyListBean.DataBean> data, boolean isFinish) {
        this.data = data;
        this.isFinish = isFinish;
        notifyDataSetChanged();
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_complaint)
        ImageView imgComplaint;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_not_data)
        TextView tvNotData;
        @BindView(R.id.ll_loading)
        LinearLayout llLoading;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
