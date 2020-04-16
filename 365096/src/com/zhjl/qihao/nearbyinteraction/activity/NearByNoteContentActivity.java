package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.nearbyinteraction.adapter.NearByNoteContentAdapter;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.fragment.ReportFragment;
import com.zhjl.qihao.util.Utils;
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

public class NearByNoteContentActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_aboveView)
    LinearLayout llAboveView;
    @BindView(R.id.reply_edt)
    EditText replyEdt;
    @BindView(R.id.ll_edt)
    LinearLayout llEdt;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.xrv_note_content_list)
    XRecyclerView xrvNoteContentList;
    @BindView(R.id.tv_agree_num)
    TextView tvAgreeNum;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.img_report)
    ImageView imgReport;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_line_two)
    View viewLineTwo;
    private String forumNoteId = "";
    private List<NearByNoteContentBean.DataBean.ForumPictureListBean> forumPictureList = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private int replyType;
    private boolean isRefresh = true;
    private int totalPage;
    private NearByNoteContentAdapter nearByNoteContentAdapter;
    private List<NearByNoteReplyContentBean.DataBean> listData = new ArrayList<>();
    private NearByNoteContentBean.DataBean resultData;
    private ComplaintApiInterfaces interfaces;
    private boolean isReportContent;    //是否是回复
    private String replyName = "";
    private String discussId;
    private List<NearByNoteContentBean.DataBean.AppendListBean> appendList = new ArrayList<>();
    public static final int RESULT_NOTE_DATA = 0x886;
    private boolean isComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_note_content);
        ButterKnife.bind(this);
        forumNoteId = getIntent().getStringExtra("forumNoteId");
        isComplaint = getIntent().getBooleanExtra("isComplaint", false);
        interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        initData();
        xrvNoteContentList.setLayoutManager(new LinearLayoutManager(mContext));
        nearByNoteContentAdapter = new NearByNoteContentAdapter(this, listData, resultData);
        xrvNoteContentList.setAdapter(nearByNoteContentAdapter);
        xrvNoteContentList.setLoadingMoreEnabled(true);
        xrvNoteContentList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
                initData();
//                initReplyData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initReplyData();
                } else {
//                    int top = Utils.dip2px(mContext, 10);
//                    int bottom = Utils.dip2px(mContext, 30);
//                    xrvNoteContentList.setFootViewText("正在加载...", "更多评论正在赶来~");
                    xrvNoteContentList.setNoMore(false);
                }
            }
        });

        nearByNoteContentAdapter.setReplyOnClickLintener((view, isReply, id) -> {
            isReportContent = true;
            replyName = isReply;
            discussId = id;
            replyType = 1;
            replyEdt.getText().clear();
            llAboveView.setVisibility(View.VISIBLE);
            replyEdt.requestFocus();
            replyEdt.setHint("@" + replyName);
            Utils.openKeyboard(replyEdt);
        });
        //删除帖子后的回调
        nearByNoteContentAdapter.setSetRefreshData(() -> {
            initReplyData();
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
                        llReply.setVisibility(View.VISIBLE);
                        viewLineTwo.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.GONE);
                        tvSend.setVisibility(View.VISIBLE);
                    } else {
                        //未弹出  收回
                        isReportContent = false;
                        llAboveView.setVisibility(View.GONE);
                        viewLineTwo.setVisibility(View.GONE);
                        viewLine.setVisibility(View.VISIBLE);
                        llReply.setVisibility(View.GONE);
                        tvSend.setVisibility(View.GONE);
                    }
                    v.invalidate();
                }
            }
        });
    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        Call<ResponseBody> call = interfaces.getComplaintDetail(ParamForNet.put(map));
        activityRequestData(call, NearByNoteContentBean.class, new RequestResult<NearByNoteContentBean>() {
            @Override
            public void success(NearByNoteContentBean result, String message) throws Exception {
                resultData = result.getData();
                if (resultData.getCreateUser() != null && mSession.getUserId().equals(resultData.getCreateUser().getUserId())) {
                    imgReport.setVisibility(View.GONE);
                } else {
                    imgReport.setVisibility(View.VISIBLE);
                }
                nearByNoteContentAdapter.addHeadData(resultData,isComplaint);
                appendList = resultData.getAppendList();
                tvAgreeNum.setText(result.getData().getPraiseNum() + "");
                addAgree(result.getData().getPraiseStatus());
                initReplyData();
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvNoteContentList.refreshComplete();
                } else {
                    xrvNoteContentList.loadMoreComplete();
                }
            }
        });
    }

    //    private void initReplyData() {
//        ComplaintApiInterfaces complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
//        Map<String, Object> map = new HashMap<>();
//        map.put("forumNoteId", forumNoteId);
//        map.put("pageIndex", pageIndex);
//        map.put("pageSize", pageSize);
//        Call<ResponseBody> call = complaintApiInterfaces.getComplaintReplyDetail(ParamForNet.put(map));
//        activityRequestData(call, NearByNoteReplyListBean.class, new RequestResult<NearByNoteReplyListBean>() {
//            @Override
//            public void success(NearByNoteReplyListBean result, String message) throws Exception {
//                totalPage = result.getTotalPage();
//                if (isRefersh) {
//                    listData.clear();
//                    listData.addAll(result.getData());
//                    xrvNoteContentList.refreshComplete();
//                } else {
//                    listData.addAll(result.getData());
//                    xrvNoteContentList.loadMoreComplete();
//                }
//                nearByNoteContentAdapter.addData(listData);
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
//    }

    @OnClick({R.id.img_back, R.id.ll_aboveView, R.id.tv_reply, R.id.tv_agree_num, R.id.tv_send, R.id.img_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = getIntent().putExtra("data", resultData);
                getIntent().putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(RESULT_NOTE_DATA, intent);
                finish();
                break;
            case R.id.ll_aboveView:
                isReportContent = false;
                llAboveView.setVisibility(View.GONE);
                Utils.hideSoftInput(this);
                tvSend.setVisibility(View.GONE);
                break;
            case R.id.tv_reply:
                isReportContent = false;
//                replyEdt.getText().clear();
//                replyEdt.setHint("我也来说两句~");
                llAboveView.setVisibility(View.VISIBLE);
                llReply.setVisibility(View.VISIBLE);
                viewLineTwo.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.GONE);
                tvSend.setVisibility(View.VISIBLE);
                replyEdt.requestFocus();
                Utils.openKeyboard(replyEdt);
                break;
            case R.id.tv_agree_num:
                requestAddAgreeData();
                break;
            case R.id.tv_send:
                if (resultData != null) {
                    tvSend.setEnabled(false);
                    requestReply(isReportContent);
                }
                break;
            case R.id.img_report:
                ReportFragment reportFragment = ReportFragment.getInstance(forumNoteId, false, false, null);
                reportFragment.show(getSupportFragmentManager(), "7");
                break;
        }
    }

    private void initReplyData() {
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        Call<ResponseBody> call = interfaces.getComplaintReplyDetail(ParamForNet.put(map));
        activityRequestData(call, NearByNoteReplyContentBean.class, new RequestResult<NearByNoteReplyContentBean>() {
            @Override
            public void success(NearByNoteReplyContentBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    listData.clear();
                    for (int i = 0; i < appendList.size(); i++) {
                        NearByNoteContentBean.DataBean.AppendListBean appendListBean = appendList.get(i);
                        NearByNoteReplyContentBean.DataBean dataBean = new NearByNoteReplyContentBean.DataBean();
                        dataBean.setContent(appendListBean.getContent());
                        dataBean.setCreateUserInfo(appendListBean.getCreateUserInfo());
                        dataBean.setDiscussId(appendListBean.getDiscussId());
                        dataBean.setDiscussNum(appendListBean.getDiscussNum());
                        dataBean.setForumNoteId(appendListBean.getForumNoteId());
                        dataBean.setHowLong(appendListBean.getHowLong());
                        dataBean.setPraiseNum(appendListBean.getPraiseNum());
                        dataBean.setPraiseStatus(appendListBean.getPraiseStatus());
                        dataBean.setReplyUserInfo(appendListBean.getReplyUserInfo());
                        listData.add(dataBean);
                    }
                    listData.addAll(result.getData());
                    xrvNoteContentList.refreshComplete();
                } else {
                    listData.addAll(result.getData());
                    xrvNoteContentList.loadMoreComplete();
                }
                nearByNoteContentAdapter.addData(listData);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvNoteContentList.refreshComplete();
                } else {
                    xrvNoteContentList.loadMoreComplete();
                }
            }
        });
        replyEdt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llAboveView.setVisibility(View.VISIBLE);
                llReply.setVisibility(View.VISIBLE);
                viewLineTwo.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.GONE);
                tvSend.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * flag  0:追加帖子，1：回复帖子2：回复讨论
     * 回复帖子
     *
     * @param isReportContent
     */
    private void requestReply(boolean isReportContent) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", replyEdt.getText().toString().trim());
        map.put("forumNoteId", resultData.getForumNoteId());
        try {
            if (isReportContent) {
                map.put("discussId", discussId);
                map.put("replyType", 1);    //讨论
            } else {
                map.put("replyType", 0);    //帖子
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.addReply(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Utils.hideSoftInput(NearByNoteContentActivity.this);
                isRefresh = true;
                pageIndex = 1;
                NearByNoteContentActivity.this.isReportContent = false;
                initData();
                if (llAboveView.getVisibility() == View.VISIBLE) {
                    llAboveView.setVisibility(View.GONE);
                    llReply.setVisibility(View.GONE);
                    viewLine.setVisibility(View.VISIBLE);
                    viewLineTwo.setVisibility(View.GONE);
                }
                replyEdt.getText().clear();
                replyEdt.setHint("我也来说两句~");
                tvSend.setVisibility(View.GONE);
                tvSend.setEnabled(true);
            }

            @Override
            public void fail() {
                tvSend.setEnabled(true);
            }
        });
    }

    /**
     * 点赞状态
     *
     * @param isAgree
     */
    private void addAgree(int isAgree) {
        Drawable drawable = null;
        if (isAgree == 0) {
            drawable = getResources().getDrawable(R.drawable.img_nearby_detail_no_agree);
            tvAgreeNum.setTextColor(Color.parseColor("#1F1F1F"));
        } else {
            drawable = getResources().getDrawable(R.drawable.img_nearby_detail_agree);
            tvAgreeNum.setTextColor(Color.parseColor("#F32222"));
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvAgreeNum.setCompoundDrawables(drawable, null, null, null);
    }

    private void requestAddAgreeData() {
        if (resultData != null) {
            final HashMap<String, Object> map = new HashMap<>();
            if (resultData.getPraiseStatus() == 1) {
                map.put("praiseStatus", 0);    //0取消点赞
            } else {
                map.put("praiseStatus", 1);    //点赞
            }
            map.put("forumNoteId", resultData.getForumNoteId());
            RequestBody body = ParamForNet.put(map);
            Call<ResponseBody> call = interfaces.addAgree(body);
            activityRequestData(call, null, new RequestResult<Object>() {
                @Override
                public void success(Object result, String message) {
                    String num = tvAgreeNum.getText().toString();
                    if (message.equals("点赞成功")) {
                        addAgree(1);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum++;
                            tvAgreeNum.setText(countNum + "");
                            resultData.setPraiseNum(countNum);
                            resultData.setPraiseStatus(1);
                        }
                    } else if (message.equals("取消点赞成功")) {
                        addAgree(0);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum--;
                            tvAgreeNum.setText(countNum + "");
                            resultData.setPraiseNum(countNum);
                            resultData.setPraiseStatus(0);
                        }
                    }
                    nearByNoteContentAdapter.addHeadData(resultData, isComplaint);
                    tvAgreeNum.setEnabled(true);
                }

                @Override
                public void fail() {
                    tvAgreeNum.setEnabled(true);
                }
            });
        } else {
            Toast.makeText(mContext, "点赞失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent().putExtra("data", resultData);
            getIntent().putExtra("position", getIntent().getIntExtra("position", 0));
            setResult(RESULT_NOTE_DATA, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isSoftShowing() {
        //获取当屏幕内容的高度
        int screenHeight = this.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }
}
