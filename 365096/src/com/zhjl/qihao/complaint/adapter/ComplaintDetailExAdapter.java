package com.zhjl.qihao.complaint.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.activity.ProcessinProgressActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.ComplaintDetailBean;
import com.zhjl.qihao.complaint.bean.ComplaintDetailReplyBean;
import com.zhjl.qihao.view.CustomGridView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * * 评论回复列表适配器
 */


public class ComplaintDetailExAdapter extends BaseExpandableListAdapter {

    private VolleyBaseActivity activity;
    private List<ComplaintDetailReplyBean.DataBean> lists;
    //    private Map<Integer, Boolean> isGroupClick = new HashMap<>();
//    private Map<Integer, Map<Integer, Boolean>> isChildClick = new HashMap<>();
    private final Session session;

    public ComplaintDetailExAdapter(VolleyBaseActivity activity, List<ComplaintDetailReplyBean.DataBean> lists, boolean isChange, boolean isRecord) {
        this.activity = activity;
        this.lists = lists;
        session = Session.get(activity);
    }


    @Override
    public int getGroupCount() {
        return lists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (lists.get(groupPosition).getChildren()!=null){
            return lists.get(groupPosition).getChildren().size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (lists.get(groupPosition).getChildren()!=null){
            return lists.get(groupPosition).getChildren().get(childPosition);
        }else {
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.complaint_detail_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.rlComplaintDetailItem.setBackgroundResource(R.drawable.round_gray_bg);
            holder.imgSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
            holder.imgHead.setVisibility(View.VISIBLE);
            holder.tvTime.setTextColor(Color.parseColor("#9a9a9a"));
            holder.tvName.setText(Html.fromHtml("<font color = \"#209b45\">" + lists.get(groupPosition).getCreateUserInfo().getNickName() + "</font>" + "<font color = \"#9a9a9a\">" + lists.get(groupPosition).getContent() + "</font>"));
            holder.imgSureAgree.setText(lists.get(groupPosition).getPraiseNum() + "");
            holder.tvTime.setText(lists.get(groupPosition).getHowLong());   //时间
            holder.tvMore.setVisibility(View.VISIBLE);
            holder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProcessinProgressActivity.class);
                    intent.putExtra("title", "评论记录");
                    activity.startActivity(intent);
                }
            });
            isSureAgree(holder, lists.get(groupPosition).getPraiseStatus());
            final ViewHolder finalHolder = holder;
            holder.imgSureAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.imgSureAgree.setEnabled(false);
                    requestAddSureAgreeData(groupPosition, 0, finalHolder);

                }
            });
            holder.tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnReportListener.onReport(v, groupPosition, 0, false);
                }
            });
            holder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity.islogin()) {
                        Intent intent = new Intent(activity, ProcessinProgressActivity.class);
                        intent.putExtra("discussId", lists.get(groupPosition).getDiscussId());
                        intent.putExtra("title", "评论记录");
                        activity.startActivity(intent);
                    } else {
                        activity.showtips();
                    }
                }
            });

            if (lists.get(groupPosition).getCreateUserInfo().getAvatar().getSamllPicturePath() == null) {
                holder.imgHead.setImageResource(View.GONE);
                Glide.with(activity).clear(holder.imgHead);
                holder.imgHead.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_head));
                holder.imgHead.setTag(R.id.img_head, groupPosition);
                return convertView;
            }
            Object tag = holder.imgHead.getTag(R.id.img_head);
            if (tag != null && (int) tag != groupPosition) {
                //如果tag不是Null,并且同时tag不等于当前的position。
                //说明当前的viewHolder是复用来的
                Glide.with(activity).clear(holder.imgHead);
            }
            holder.imgHead.setImageResource(View.VISIBLE);
            PictureHelper.setOptionsGlide(activity, 12, lists.get(groupPosition).getCreateUserInfo().getAvatar().getSamllPicturePath(), holder.imgHead, R.drawable.img_loading);
            holder.imgHead.setTag(R.id.img_head, groupPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {
            ViewChildHolder viewChildHolder = null;
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.complaint_detail_child_item, null);
                viewChildHolder = new ViewChildHolder(convertView);
                convertView.setTag(viewChildHolder);
            } else {
                viewChildHolder = (ViewChildHolder) convertView.getTag();
            }
            isSureAgree(viewChildHolder, lists.get(groupPosition).getChildren().get(childPosition).getPraiseStatus());
            viewChildHolder.imgChildSureAgree.setText(lists.get(groupPosition).getChildren().get(childPosition).getPraiseNum() + "");
            viewChildHolder.tvChildName.setText(Html.fromHtml("<font color = \"#209b45\">" + lists.get(groupPosition).getChildren().get(childPosition).getCreateUserInfo().getNickName() + "@" + lists.get(groupPosition).getChildren().get(childPosition).getReplyUserInfo().getNickName() + "</font>" + "<font color = \"#9a9a9a\">" + lists.get(groupPosition).getChildren().get(childPosition).getContent() + "</font>"));
            viewChildHolder.tvChildTime.setText(lists.get(groupPosition).getChildren().get(childPosition).getHowLong());
            viewChildHolder.tvChildReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnReportListener.onReport(v, groupPosition, childPosition, true);
                }
            });

            final ViewChildHolder finalHolder = viewChildHolder;
            viewChildHolder.imgChildSureAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.imgChildSureAgree.setEnabled(false);
                    requestAddSureAgreeData(groupPosition, childPosition, finalHolder);
                }
            });
            viewChildHolder.tvChildMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity.islogin()) {
                        Intent intent = new Intent(activity, ProcessinProgressActivity.class);
                        intent.putExtra("discussId", lists.get(groupPosition).getChildren().get(childPosition).getDiscussId());
                        intent.putExtra("title", "评论记录");
                        activity.startActivity(intent);
                    } else {
                        activity.showtips();
                    }
                }
            });

            if (lists.get(groupPosition).getChildren() == null || lists.get(groupPosition).getChildren().size() == 0) {
                viewChildHolder.imgChildHead.setImageResource(View.GONE);
                Glide.with(activity).clear(viewChildHolder.imgChildHead);
                viewChildHolder.imgChildHead.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.ic_head));
                viewChildHolder.imgChildHead.setTag(R.id.img_child_head, groupPosition);
                return convertView;
            }
            Object tag = viewChildHolder.imgChildHead.getTag(R.id.img_child_head);
            if (tag != null && (int) tag != groupPosition) {
                //如果tag不是Null,并且同时tag不等于当前的position。
                //说明当前的viewHolder是复用来的
                Glide.with(activity).clear(viewChildHolder.imgChildHead);
            }
            viewChildHolder.imgChildHead.setImageResource(View.VISIBLE);
            PictureHelper.setOptionsGlide(activity, 12, lists.get(groupPosition).getChildren().get(childPosition).getCreateUserInfo().getAvatar().getSamllPicturePath(), viewChildHolder.imgChildHead, R.drawable.img_head);
            viewChildHolder.imgChildHead.setTag(R.id.img_child_head, groupPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private SetOnReportListener setOnReportListener;

    public void setSetOnReportListener(SetOnReportListener setOnReportListener) {
        this.setOnReportListener = setOnReportListener;
    }

    public interface SetOnReportListener {
        void onReport(View view, int groupPosition, int childPosition, boolean isChild);
    }

    /**
     * * 请求点赞接口
     */

    private void requestAddSureAgreeData(final int position, final int childPosition, final Object viewHolder) {
        if (viewHolder instanceof ViewChildHolder) {
            final ViewChildHolder holder = (ViewChildHolder) viewHolder;
            if (lists != null && lists.size() != 0 && lists.get(position).getChildren() != null && lists.get(position).getChildren().size() != 0 && lists.get(position).getChildren().get(childPosition).getChildren() != null) {
                ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(activity).createService(ComplaintApiInterfaces.class);
                int praiseStatus = lists.get(position).getChildren().get(childPosition).getPraiseStatus();
                HashMap<String, Object> map = new HashMap<>();
                if (praiseStatus == 1) {
                    map.put("praiseStatus", 0);    //0取消点赞   1点赞
                } else {
                    map.put("praiseStatus", 1);    //0取消点赞   1点赞
                }
                map.put("discussId", lists.get(position).getChildren().get(childPosition).getDiscussId());
                RequestBody body = ParamForNet.put(map);
                Call<ResponseBody> call = interfaces.replyAddAgree(body);
                activity.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
                    @Override
                    public void success(Object result, String message) {
                        String num = holder.imgChildSureAgree.getText().toString();
                        if (message.equals("点赞成功")) {
                            isSureAgree(holder, 1);
                            if (num != null) {
                                int countNum = Integer.parseInt(num);
                                countNum++;
                                holder.imgChildSureAgree.setText(countNum + "");
                                lists.get(position).getChildren().get(childPosition).setPraiseNum(countNum);
                                lists.get(position).getChildren().get(childPosition).setPraiseStatus(1);
                            }
                        } else if (message.equals("取消点赞成功")) {
                            isSureAgree(holder, 0);
                            if (num != null) {
                                int countNum = Integer.parseInt(num);
                                countNum--;
                                holder.imgChildSureAgree.setText(countNum + "");
                                lists.get(position).getChildren().get(childPosition).setPraiseNum(countNum);
                                lists.get(position).getChildren().get(childPosition).setPraiseStatus(0);
                            }
                        }
                        holder.imgChildSureAgree.setEnabled(true);
                    }

                    @Override
                    public void fail() {
                        holder.imgChildSureAgree.setEnabled(true);
                    }
                });
            }
        }

        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;
            if (lists != null && lists.size() != 0 && lists.get(position).getChildren() != null) {
                ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(activity).createService(ComplaintApiInterfaces.class);
//                boolean priseStatus = isGroupClick.get(position).booleanValue(); //取得是否点赞得值
                int praiseStatus = lists.get(position).getPraiseStatus();
                HashMap<String, Object> map = new HashMap<>();
                if (praiseStatus == 1) {
                    map.put("praiseStatus", 0);    //0取消点赞   1点赞
                } else {
                    map.put("praiseStatus", 1);    //0取消点赞   1点赞
                }
                map.put("discussId", lists.get(position).getDiscussId());
                RequestBody body = ParamForNet.put(map);
                Call<ResponseBody> call = interfaces.replyAddAgree(body);
                activity.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
                    @Override
                    public void success(Object result, String message) {
                        String num = holder.imgSureAgree.getText().toString();
                        if (message.equals("点赞成功")) {
                            isSureAgree(holder, 1);
                            if (num != null) {
                                int countNum = Integer.parseInt(num);
                                countNum++;
                                holder.imgSureAgree.setText(countNum + "");
                                lists.get(position).setPraiseNum(countNum);
                                lists.get(position).setPraiseStatus(1);
                            }
                        } else if (message.equals("取消点赞成功")) {
                            isSureAgree(holder, 0);
//                            lists.get(position).setPraiseStatus(!lists.get(position).isPraiseStatus());
                            if (num != null) {
                                int countNum = Integer.parseInt(num);
                                countNum--;
                                holder.imgSureAgree.setText(countNum + "");
                                lists.get(position).setPraiseNum(countNum);
                                lists.get(position).setPraiseStatus(0);
                            }
                        }
                        holder.imgSureAgree.setEnabled(true);
                    }

                    @Override
                    public void fail() {
                        holder.imgSureAgree.setEnabled(true);
                    }
                });
            } else {
                holder.imgSureAgree.setEnabled(true);
                Toast.makeText(activity, "点赞失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void isSureAgree(Object viewHolder, int booleanValue) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            if (booleanValue == 0) {
                Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great_normal);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.imgSureAgree.setCompoundDrawables(drawable, null, null, null);
                holder.imgSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
            } else {
                Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.imgSureAgree.setCompoundDrawables(drawable, null, null, null);
                holder.imgSureAgree.setTextColor(Color.parseColor("#f3705a"));
            }
        }
        if (viewHolder instanceof ViewChildHolder) {
            ViewChildHolder holder = (ViewChildHolder) viewHolder;
            if (booleanValue == 0) {
                Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great_normal);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.imgChildSureAgree.setCompoundDrawables(drawable, null, null, null);
                holder.imgChildSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
            } else {
                Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.imgChildSureAgree.setCompoundDrawables(drawable, null, null, null);
                holder.imgChildSureAgree.setTextColor(Color.parseColor("#f3705a"));
            }
        }

    }

    public void addData(ExpandableListView rvComplaintDetail, List<ComplaintDetailReplyBean.DataBean> discussList, ComplaintDetailBean.DataBean data) {
        this.lists = discussList;
        for (int i = 0; i < discussList.size(); i++) {
            if (discussList.get(i).getChildren()!=null){
                rvComplaintDetail.expandGroup(i);
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.gv_comment_img)
        CustomGridView gvCommentImg;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_more)
        ImageView tvMore;
        @BindView(R.id.img_sure_agree)
        TextView imgSureAgree;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.rl_complaint_detail_item)
        RelativeLayout rlComplaintDetailItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewChildHolder {
        @BindView(R.id.img_child_head)
        ImageView imgChildHead;
        @BindView(R.id.tv_child_name)
        TextView tvChildName;
        @BindView(R.id.gv_child_comment_img)
        CustomGridView gvChildCommentImg;
        @BindView(R.id.tv_child_time)
        TextView tvChildTime;
        @BindView(R.id.tv_child_more)
        ImageView tvChildMore;
        @BindView(R.id.img_child_sure_agree)
        TextView imgChildSureAgree;
        @BindView(R.id.tv_child_reply)
        TextView tvChildReply;

        ViewChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
