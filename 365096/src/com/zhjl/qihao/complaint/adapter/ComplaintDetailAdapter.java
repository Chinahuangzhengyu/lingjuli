package com.zhjl.qihao.complaint.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.CommentHistoryRecordBean;
import com.zhjl.qihao.util.Utils;
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

public class ComplaintDetailAdapter extends RecyclerView.Adapter<ComplaintDetailAdapter.MyViewHolder> {


    private VolleyBaseActivity activity;
    private List<CommentHistoryRecordBean.DataBean> lists;
    private Session session;

    public ComplaintDetailAdapter(VolleyBaseActivity activity, List<CommentHistoryRecordBean.DataBean> lists) {
        this.lists = lists;
        this.activity = activity;
        session = Session.get(activity);
    }

    public void addData(List<CommentHistoryRecordBean.DataBean> discussList) {
        this.lists = discussList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.complaint_detail_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.rlComplaintDetailItem.setBackgroundResource(R.drawable.round_gray_bg);
            holder.imgSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
            if (lists.get(position).getCreateUserInfo() != null && lists.get(position).getCreateUserInfo().getAvatar() != null) {
                PictureHelper.setOptionsGlide(activity, 12, lists.get(position).getCreateUserInfo().getAvatar().getSamllPicturePath(), holder.imgHead, R.drawable.img_head);
                holder.tvName.setText(Html.fromHtml("<font color = \"#209b45\">" + lists.get(position).getCreateUserInfo().getNickName() + "</font>" + "<font color = \"#9a9a9a\">" + lists.get(position).getContent() + "</font>"));
            }
            holder.tvTime.setTextColor(Color.parseColor("#9a9a9a"));
            holder.tvTime.setText(lists.get(position).getHowLong());
            holder.imgSureAgree.setText(lists.get(position).getPraiseNum() + "");
            isSureAgree(holder, lists.get(position).getPraiseStatus());
            holder.tvMore.setVisibility(View.GONE);
            holder.tvReply.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imgSureAgree.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.img_sure_agree);
            params.rightMargin = Utils.px2dip(activity, 15);
            holder.imgSureAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.imgSureAgree.setEnabled(false);
                    requestAddSureAgreeData(position, holder);

                }
            });
        } catch (Exception e) {
            Toast.makeText(activity, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 请求点赞接口
     */
    private void requestAddSureAgreeData(final int position, final MyViewHolder holder) {
        if (lists != null && lists.size() != 0) {
            ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(activity).createService(ComplaintApiInterfaces.class);
            int priseStatus = lists.get(position).getPraiseStatus();
            HashMap<String, Object> map = new HashMap<>();
            if (priseStatus == 1) {
                map.put("praiseStatus", "0");    //0取消点赞
            } else {
                map.put("praiseStatus", "1");    //点赞
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
                            lists.get(position).setPraiseStatus(1);
                            lists.get(position).setPraiseNum(countNum);
                        }
                    } else if (message.equals("取消点赞成功")) {
                        isSureAgree(holder, 0);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum--;
                            holder.imgSureAgree.setText(countNum + "");
                            lists.get(position).setPraiseStatus(0);
                            lists.get(position).setPraiseNum(countNum);
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

    private void isSureAgree(MyViewHolder holder, int booleanValue) {
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

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_more)
        ImageView tvMore;
        @BindView(R.id.gv_comment_img)
        CustomGridView gvCommentImg;
        @BindView(R.id.img_sure_agree)
        TextView imgSureAgree;
        @BindView(R.id.rl_complaint_detail_item)
        RelativeLayout rlComplaintDetailItem;
        @BindView(R.id.tv_reply)
        TextView tvReply;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
