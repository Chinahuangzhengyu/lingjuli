package com.zhjl.qihao.nearbyinteraction.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.nearbyinteraction.adapter.ReplyListAdapter;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NoteReplyListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ReplyListActivity extends VolleyBaseActivity {

    @BindView(R.id.tv_reply_num)
    TextView tvReplyNum;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
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
    @BindView(R.id.xrv_reply_list)
    XRecyclerView xrvReplyList;
    @BindView(R.id.ll_aboveView)
    LinearLayout llAboveView;
    @BindView(R.id.reply_edt)
    EditText replyEdt;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_edt)
    LinearLayout llEdt;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    private String discussId;
    private Session session;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int totalPage;
    private List<NoteReplyListBean.DataBean> data = new ArrayList<>();
    private ReplyListAdapter replyListAdapter;
    private NearByNoteReplyContentBean.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_reply_pop);
        Point outSize = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int height = outSize.y;
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height / 6 * 5);
        ButterKnife.bind(this);
        discussId = getIntent().getStringExtra("discussId");
        bean = getIntent().getParcelableExtra("bean");
        if (bean != null) {
            tvReplyNum.setText(bean.getDiscussNum() + "条回复");
            //设置头部数据
            if (bean.getCreateUserInfo() != null) {
                tvTitle.setText(bean.getCreateUserInfo().getNickname());
            }
            if (bean.getCreateUserInfo() != null && bean.getCreateUserInfo().getAvatar() != null) {
                PictureHelper.setHeadImageView(mContext, bean.getCreateUserInfo().getAvatar().getSmallPicturePath(), imgHead, R.drawable.ic_head);
            } else {
                imgHead.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_head));
            }
            tvTime.setText(bean.getHowLong());
            tvContent.setText(bean.getContent());

        }
        xrvReplyList.setPullRefreshEnabled(false);
        xrvReplyList.setLoadingMoreEnabled(true);
        initData();
        xrvReplyList.setLayoutManager(new LinearLayoutManager(mContext));
        replyListAdapter = new ReplyListAdapter(this, data, bean, "");
        xrvReplyList.setAdapter(replyListAdapter);
        xrvReplyList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 6);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvReplyList.getFootView().setPadding(0, top, 0, bottom);
                    xrvReplyList.setFootViewText("正在加载...", "更多评论正在赶来~");
                    xrvReplyList.setNoMore(true);
                }
            }
        });

        /**
         * 布局高度变化判断是否有软键盘
         */
        llEdt.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (oldBottom != 0 && bottom != 0) {
                if (oldBottom != bottom) {
                    // 输入布局向上移动屏幕的四分之一即视为软键盘弹出
                    if ((oldBottom - bottom) > getWindow().getDecorView().getRootView().getHeight() / 4) {
                        //弹出键盘   更改背景
                        llAboveView.setVisibility(View.VISIBLE);
                        tvSend.setVisibility(View.VISIBLE);
                    } else {
                        //未弹出  收回
                        rlParent.setVisibility(View.GONE);
                    }
                    v.invalidate();
                }
            }
        });

        replyListAdapter.setReplyOnClickListener((view, replyName, id, position) -> {
            discussId = id;
            rlParent.setVisibility(View.VISIBLE);
            replyEdt.requestFocus();
            replyEdt.setHint("@" + replyName);
            Utils.openKeyboard(replyEdt);
        });

    }

    private void initData() {
        ComplaintApiInterfaces complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("discussId", discussId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.getCommentHistoryRecord(body);
        activityRequestData(call, NoteReplyListBean.class, new VolleyBaseActivity.RequestResult<NoteReplyListBean>() {
            @Override
            public void success(NoteReplyListBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                data.addAll(result.getData());
                if (totalPage < pageIndex) {
                    replyListAdapter.addData(data, false);
                } else {
                    replyListAdapter.addData(data, true);
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    private void requestReply() {
        ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", replyEdt.getText().toString().trim());
        map.put("forumNoteId", bean.getForumNoteId());
        map.put("discussId", discussId);
        map.put("replyType", 1);    //讨论
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.addReply(body);
        activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Utils.hideInput(ReplyListActivity.this);
                pageIndex = 1;
                initData();
                if (rlParent.getVisibility() == View.VISIBLE) {
                    rlParent.setVisibility(View.GONE);
                }
                replyEdt.getText().clear();
                tvSend.setEnabled(true);
            }

            @Override
            public void fail() {
                tvSend.setEnabled(true);
            }
        });
    }

    @OnClick({R.id.img_cancel, R.id.img_complaint, R.id.ll_aboveView, R.id.tv_send, R.id.tv_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                finish();
                break;
            case R.id.tv_content:
                rlParent.setVisibility(View.VISIBLE);
                replyEdt.requestFocus();
                if (bean != null && bean.getCreateUserInfo() != null) {
                    replyEdt.setHint("@" + bean.getCreateUserInfo().getNickname());
                }
                Utils.openKeyboard(replyEdt);
                break;
            case R.id.img_complaint:
                break;
            case R.id.ll_aboveView:
                rlParent.setVisibility(View.GONE);
                Utils.hideSoftInput(this);
                break;
            case R.id.tv_send:
                requestReply();
                break;
        }
    }
}
