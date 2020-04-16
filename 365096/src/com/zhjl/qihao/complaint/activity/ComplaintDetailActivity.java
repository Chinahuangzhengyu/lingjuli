package com.zhjl.qihao.complaint.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
//import com.zhjl.qihao.complaint.adapter.ComplaintDetailExAdapter;
import com.zhjl.qihao.complaint.adapter.ComplaintDetailExAdapter;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.ComplaintDetailBean;
import com.zhjl.qihao.complaint.bean.ComplaintDetailReplyBean;
import com.zhjl.qihao.complaint.view.XExpandableListView;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.ScreenUtil;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ChatEditText;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import uk.co.senab.photoview.PhotoView;

public class ComplaintDetailActivity extends VolleyBaseActivity {
    //    @BindView(R.id.rv_complaint_detail)
//    ExpandableListView rvComplaintDetail;
    @BindView(R.id.rv_complaint_detail)
    XExpandableListView rvComplaintDetail;
    @BindView(R.id.tv_sure_agree)
    TextView tvSureAgree;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.tv_Comment)
    TextView tvComment;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.reply_edt)
    ChatEditText replyEdt;
    @BindView(R.id.btn_send_reply)
    LinearLayout btnSendReply;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.ll_aboveView)
    LinearLayout llAboveView;
    private List<ComplaintDetailReplyBean.DataBean> headList = new ArrayList<>();
    private List<String> imglists;
    private GridView gvDetailImgUpload;
    private TextView tvReport;
    private TextView imgSureAgree;
    private TextView tvDetailProgress;
    private String isHotspot;
    private String forumNoteId;
    private ComplaintDetailBean.DataBean data;
    private TextView tvComplaintDetailTitle;
    private ImageView imgComplaintDetail;
    private TextView tvComplaintDetailName;
    private TextView tvComplaintDetailTime;
    private TextView tvComplaintContent;
    private MyAdapter myAdapter;
    private ComplaintDetailExAdapter complaintDetailExAdapter;
    private ComplaintApiInterfaces complaintApiInterfaces;
    private int groupPosition;  //回复下标
    private int childPosition;  //回复回复的下标
    private boolean isChild;    //是否是子类
    private boolean isReportContent;
    private PopupWindow delPopWindow;
    private ViewPager vpPhotoView;
    private PhotoViewPagerAdapter photoViewPagerAdapter;
    public List<LoginBean.DataBean.UserInfoBean.AvatarBean> photoViews = new ArrayList<>(); //查看大图的
    private PopupWindow phtotPopwindow;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLoadMore;
    private boolean isRefresh;
    private int loadingFinish = 0;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_detail_activity);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        String detailTitle = getIntent().getStringExtra("detailTitle");
        isHotspot = getIntent().getStringExtra("isHotspot");
        forumNoteId = getIntent().getStringExtra("forumNoteId");
        NewHeaderBar headerBar = NewHeaderBar.createCommomBack(this, detailTitle, this);
        rlLoading.setVisibility(View.VISIBLE);
        initView();
        initData();
        initDetailData();

    }

    private void initDetailData() {
        complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.getComplaintReplyDetail(body);
        activityRequestData(call, ComplaintDetailReplyBean.class, new RequestResult<ComplaintDetailReplyBean>() {
            @Override
            public void success(ComplaintDetailReplyBean result, String message) {
                loadingFinish++;
                if (result.getData().size() != 0) {
                    totalPage = result.getTotalPage();
                    if (isRefresh) {
                        headList.clear();
                    }
                    headList.addAll(result.getData());
                    complaintDetailExAdapter.addData(rvComplaintDetail, result.getData(), data);
                }
                isLoadingFinish(loadingFinish);
            }

            @Override
            public void fail() {
                loadingFinish++;
                isLoadingFinish(loadingFinish);
            }
        });
    }

    private void isLoadingFinish(int finish) {
        if (finish >= 2) {
            if (isRefresh) {
                rvComplaintDetail.stopRefresh();
            }
            if (isLoadMore) {
                rvComplaintDetail.stopLoadMore();
            }
        }
    }

    private void initData() {
        //头部信息
        complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.getComplaintDetail(body);
        activityRequestData(call, ComplaintDetailBean.class, new RequestResult<ComplaintDetailBean>() {
            @Override
            public void success(ComplaintDetailBean result, String message) {
                loadingFinish++;
                if (result.getData() != null) {
                    data = result.getData();
                    if (data.getCreateUser() != null && data.getCreateUser().getAvatar() != null) {
                        PictureHelper.setOptionsGlide(mContext, 12, data.getCreateUser().getAvatar().getSmallPicturePath(), imgComplaintDetail, R.drawable.img_head);
                        tvComplaintDetailName.setText(data.getCreateUser().getNickName());
                    }
                    if (data.getComplaintId() != null && !data.getComplaintId().equals("")) {
                        tvDetailProgress.setVisibility(View.VISIBLE);
                    } else {
                        tvDetailProgress.setVisibility(View.GONE);
                    }
                    tvComplaintDetailTime.setText(data.getHowLong());
                    tvComplaintContent.setText(data.getContent());
                    if (data.getForumPicture() != null) {
                        myAdapter.addData(data.getForumPicture());
                        photoViewPagerAdapter.addData(data.getForumPicture());
                    }
                    imgSureAgree.setText(data.getPraiseNum() + "");
                    if (data.getTitle() != null) {
                        tvComplaintDetailTitle.setText(data.getTitle());
                    }
                    if (data.getProcessStatus() > 0) {
//                        tvReport.setVisibility(View.GONE);
//                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgSureAgree.getLayoutParams();
//                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.img_sure_agree);
//                        params.rightMargin = Utils.px2dip(mContext, 15);
//                        tvDetailProgress.setVisibility(View.VISIBLE);
                    } else {
//                        tvReport.setVisibility(View.VISIBLE);
//                        tvDetailProgress.setVisibility(View.GONE);
//                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgSureAgree.getLayoutParams();
//                        params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    }
                    addAgree(data.getPraiseStatus());
                } else {
                    Toast.makeText(ComplaintDetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
                isLoadingFinish(loadingFinish);
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                loadingFinish++;
                isLoadingFinish(loadingFinish);
                Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                rlLoading.setVisibility(View.GONE);
            }
        });


    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.complaint_detail_head, null);
        gvDetailImgUpload = view.findViewById(R.id.gv_detail_img_upload);
        tvComplaintDetailTitle = view.findViewById(R.id.tv_complaint_detail_title);
        imgComplaintDetail = view.findViewById(R.id.img_complaint_detail);
        tvComplaintDetailName = view.findViewById(R.id.tv_complaint_detail_name);
        tvComplaintDetailTime = view.findViewById(R.id.tv_complaint_time);
        tvComplaintContent = view.findViewById(R.id.tv_complaint_content);
        tvReport = view.findViewById(R.id.tv_report);
        imgSureAgree = view.findViewById(R.id.img_sure_agree);
        tvDetailProgress = view.findViewById(R.id.tv_detail_progress);
        complaintDetailExAdapter = new ComplaintDetailExAdapter(ComplaintDetailActivity.this, headList, false, false);
        rvComplaintDetail.setAdapter(complaintDetailExAdapter);
        rvComplaintDetail.addHeaderView(view);
        myAdapter = new MyAdapter();
        gvDetailImgUpload.setAdapter(myAdapter);

        View photoView = View.inflate(mContext, R.layout.show_big_picture, null);
        vpPhotoView = photoView.findViewById(R.id.vp_photo_view);
        View viewTop = photoView.findViewById(R.id.view_top);
        View viewBottom = photoView.findViewById(R.id.view_bottom);
        viewTop.setOnClickListener(this);
        viewBottom.setOnClickListener(this);
        photoViewPagerAdapter = new PhotoViewPagerAdapter();
        vpPhotoView.setAdapter(photoViewPagerAdapter);
        vpPhotoView.setOffscreenPageLimit(3);
        phtotPopwindow = new PopupWindow(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        phtotPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, ComplaintDetailActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        phtotPopwindow.setFocusable(true);
        phtotPopwindow.setOutsideTouchable(true);


        tvDetailProgress.setOnClickListener(this);
        tvSureAgree.setOnClickListener(this);
        imgSureAgree.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        llAboveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlParent.getVisibility() == View.VISIBLE) {
                    rlParent.setVisibility(View.GONE);
                }
                Utils.hideSoftInput(ComplaintDetailActivity.this);
                isReportContent = false;
            }
        });
        btnSendReply.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        gvDetailImgUpload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.darkenBackground(0.5f, ComplaintDetailActivity.this);
                vpPhotoView.setCurrentItem(position);
                phtotPopwindow.showAsDropDown(rvComplaintDetail, 0, 0);
            }
        });
//        replyEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
//                    //处理事件
//
//                }
//                return true;
//            }
//        });
        complaintDetailExAdapter.setSetOnReportListener(new ComplaintDetailExAdapter.SetOnReportListener() {
            @Override
            public void onReport(View view, int groupPosition, int childPosition, boolean isChild) {
                ComplaintDetailActivity.this.groupPosition = groupPosition;
                ComplaintDetailActivity.this.childPosition = childPosition;
                ComplaintDetailActivity.this.isChild = isChild;
                replyEdt.requestFocus();
                rlParent.setVisibility(View.VISIBLE);
                Utils.openKeyboard(replyEdt);
            }
        });
        rvComplaintDetail.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        rvComplaintDetail.setPullLoadEnable(false);
        rvComplaintDetail.setXListViewListener(new XExpandableListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadingFinish = 0;
                isLoadMore = false;
                isRefresh = true;
                initData();
                pageIndex = 1;
                initDetailData();
            }

            @Override
            public void onLoadMore() {
                loadingFinish = 0;
                initData();
                pageIndex++;
                isLoadMore = true;
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initDetailData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    rvComplaintDetail.getFooterView().setPadding(0, top, 0, bottom);
                    rvComplaintDetail.setPullLoadEnable(false);
                }
            }
        });
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int[] screenSize = getScreenSize(mContext);
                int height = screenSize[1];
//                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
//                Log.e("++++++++++++++++++",height+"测量全面屏高度！");
                int diff = height - r.bottom;
                if (new ScreenUtil().getScreenUtilInstance(mContext).getVirtualKeyHeight()==0){
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                }else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }

            }
        };
    }

    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.tv_detail_progress:
//                Intent intent = new Intent(this, ProcessinProgressActivity.class);
//                intent.putExtra("title", "物业处理进度");
//                if (data != null) {
//                    intent.putExtra("forumNoteId", data.getForumNoteId());
//                }
//                startActivity(intent);
                try {
                    if (data.getComplaintId() != null && !data.getComplaintId().equals("")) {
                        Intent detailIntent = new Intent(mContext, RepairDetailActivity.class);
                        detailIntent.putExtra("isComplaint", true);
                        detailIntent.putExtra("id", Long.parseLong(data.getComplaintId()));
                        startActivity(detailIntent);
                    } else {
                        tvDetailProgress.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_sure_agree:        //点赞
                imgSureAgree.setEnabled(false);
                requestAddAgreeData();
                break;
            case R.id.img_sure_agree:       //点赞
                imgSureAgree.setEnabled(false);
                requestAddAgreeData();
                break;
            case R.id.tv_Comment:       //评论
                if (!islogin()) {
                    showtips();
                } else {
                    isReportContent = true;
                    replyEdt.requestFocus();
                    rlParent.setVisibility(View.VISIBLE);
                    Utils.openKeyboard(replyEdt);
                }
                break;
            case R.id.ll_aboveview:     //评论上面空白区域

                break;
            case R.id.btn_send_reply:       //发送评论
                btnSendReply.setEnabled(false);
                if (replyEdt.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "回复内容不能为空！", Toast.LENGTH_SHORT).show();
                    btnSendReply.setEnabled(true);
                    return;
                }
                if (data == null) {
                    btnSendReply.setEnabled(true);
                    return;
                }
                if (isReportContent) {
                    requestReply(isReportContent);
                } else {
                    requestReply(isReportContent);
                }
                break;
            case R.id.tv_report:        //举报
                showReportPop();
                break;
            case R.id.view_top:
                phtotPopwindow.dismiss();
                break;
            case R.id.view_bottom:
                phtotPopwindow.dismiss();
                break;

        }

    }

    private void requestReport() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("forumNoteId", data.getForumNoteId());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.reportContent(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, "举报成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 举报弹窗
     */
    private void showReportPop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.exit_popupwindow, null);
        TextView tvmsg = (TextView) popView.findViewById(R.id.tv_msg);
        tvmsg.setText("亲! 你是要举报该帖子吗? ");
        TextView yes = (TextView) popView.findViewById(R.id.yes_exit);
        TextView not = (TextView) popView.findViewById(R.id.not_exit);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestReport();
                delPopWindow.dismiss();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delPopWindow.dismiss();
            }
        });
        delPopWindow = new PopupWindow(popView, 800, ViewGroup.LayoutParams.WRAP_CONTENT);
        delPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindowbg));

        delPopWindow.setFocusable(true);
        delPopWindow.setOutsideTouchable(true);
        delPopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        delPopWindow.showAtLocation(gvDetailImgUpload, Gravity.CENTER, 0, 0);
    }

    private void requestReply(boolean isReportContent) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", replyEdt.getText().toString().trim());
        map.put("forumNoteId", data.getForumNoteId());
        try {
            if (!isReportContent) {
                if (isChild) {
                    map.put("discussId", headList.get(groupPosition).getChildren().get(childPosition).getDiscussId());
                } else {
                    map.put("discussId", headList.get(groupPosition).getDiscussId());
                }
                map.put("replyType", 1);
            } else {
                map.put("replyType", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.addReply(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Utils.hideInput(ComplaintDetailActivity.this);
                isRefresh = true;
                pageIndex = 1;
                initDetailData();
                if (rlParent.getVisibility() == View.VISIBLE) {
                    rlParent.setVisibility(View.GONE);
                }
                ComplaintDetailActivity.this.isReportContent = false;
                replyEdt.getText().clear();
                btnSendReply.setEnabled(true);
            }

            @Override
            public void fail() {
                btnSendReply.setEnabled(true);
            }
        });
    }

//    private void requestChildReply() {
//        HashMap<String, Object> map = new HashMap<>();
//        if (data != null && data.getDiscussList().size() != 0) {
//            if (isChild) {
//                map.put("mobile", mSession.getRegisterMobile());
//                map.put("discussId", data.getDiscussList().get(groupPosition).getDiscussList().get(childPosition).getDiscussId());
//                map.put("commentsReplyContent", replyEdt.getText().toString().trim());
//                map.put("commentsReplyUser", data.getDiscussList().get(groupPosition).getDiscussList().get(childPosition).getCreateUserId());
//                map.put("userId", mSession.getUserId());
//            } else {
//                map.put("mobile", mSession.getRegisterMobile());
//                map.put("discussId", data.getDiscussList().get(groupPosition).getDiscussId());
//                map.put("commentsReplyContent", replyEdt.getText().toString().trim());
//                map.put("commentsReplyUser", data.getDiscussList().get(groupPosition).getCreateUserId());
//                map.put("userId", mSession.getUserId());
//            }
//        }
//        RequestBody body = ParamForNet.put(map);
//        Call<ResponseBody> call = complaintApiInterfaces.addChildReply(body);
//        activityRequestData(call, null, new RequestResult<Object>() {
//            @Override
//            public void success(Object result, String message) {
//                initData();
//                if (rlParent.getVisibility() == View.VISIBLE) {
//                    rlParent.setVisibility(View.GONE);
//                }
//                Utils.hideInput(ComplaintDetailActivity.this);
//                isReportContent = false;
//                replyEdt.getText().clear();
//                btnSendReply.setEnabled(true);
//            }
//
//            @Override
//            public void fail() {
//                btnSendReply.setEnabled(true);
//            }
//        });
//    }

    private void requestAddAgreeData() {
        if (data != null) {
            ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
            final HashMap<String, Object> map = new HashMap<>();
            if (data.getPraiseStatus() == 1) {
                map.put("praiseStatus", 0);    //0取消点赞
            } else {
                map.put("praiseStatus", 1);    //点赞
            }
            map.put("forumNoteId", data.getForumNoteId());
            RequestBody body = ParamForNet.put(map);
            Call<ResponseBody> call = interfaces.addAgree(body);
            activityRequestData(call, null, new RequestResult<Object>() {
                @Override
                public void success(Object result, String message) {
                    String num = imgSureAgree.getText().toString();
                    if (message.equals("点赞成功")) {
                        addAgree(1);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum++;
                            imgSureAgree.setText(countNum + "");
                            data.setPraiseNum(countNum);
                            data.setPraiseStatus(1);
                        }
                    } else if (message.equals("取消点赞成功")) {
                        addAgree(0);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum--;
                            imgSureAgree.setText(countNum + "");
                            data.setPraiseNum(countNum);
                            data.setPraiseStatus(0);
                        }
                    }
                    imgSureAgree.setEnabled(true);
                }

                @Override
                public void fail() {
                    imgSureAgree.setEnabled(true);
                }
            });
        } else {
            Toast.makeText(mContext, "点赞失败！", Toast.LENGTH_SHORT).show();
        }
    }

    private void addAgree(int isAgree) {
        Drawable drawable = null;
        if (isAgree == 0) {
            drawable = getResources().getDrawable(R.drawable.sweet_cicle_great_normal);
            imgSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
            tvSureAgree.setText("点赞");
        } else {
            drawable = getResources().getDrawable(R.drawable.sweet_cicle_great);
            imgSureAgree.setTextColor(Color.parseColor("#f3705a"));
            tvSureAgree.setText("已点赞");
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        imgSureAgree.setCompoundDrawables(drawable, null, null, null);
    }

    class MyAdapter extends BaseAdapter {

        private List<LoginBean.DataBean.UserInfoBean.AvatarBean> picLists = new ArrayList<>();

        public void addData(List<LoginBean.DataBean.UserInfoBean.AvatarBean> picLists) {
            this.picLists = picLists;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return picLists.size();
        }

        @Override
        public Object getItem(int position) {
            return picLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.complaint_img_upload, null);
            }
            ImageView imgUpload = convertView.findViewById(R.id.img_upload);
            if (picLists.size() != 0) {
                PictureHelper.setOptionsGlide(mContext, 12, picLists.get(position).getPicturePath(), imgUpload, R.drawable.img_err);
            }
            return convertView;
        }
    }

    class PhotoViewPagerAdapter extends PagerAdapter {


        public void addData(List<LoginBean.DataBean.UserInfoBean.AvatarBean> list) {
            photoViews = list;
            if (photoViews.size() != 0) {
                for (int i = photoViews.size() - 1; i > 0; i--) {
                    if (photoViews.get(i).equals("")) {
                        photoViews.remove(i);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return photoViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (photoViews.size() != 0) {
                PhotoView photoView = new PhotoView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                photoView.setLayoutParams(params);
                if (photoViews.get(position).getPicturePath().endsWith("_small")) {
                    PictureHelper.setImageView(mContext, photoViews.get(position).getPicturePath(), photoView, R.drawable.img_err);
                } else {
                    String[] split = photoViews.get(position).getPicturePath().split("\\.");
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].endsWith("_small")) {
                            split[i] = split[i].substring(0, split[i].length() - 6);
                        }
                        if (i == split.length - 1) {
                            sb.append(split[i]);
                        } else {
                            sb.append(split[i] + ".");
                        }
                    }
                    PictureHelper.setImageView(mContext, sb.toString(), photoView, R.drawable.img_err);
                }
                container.addView(photoView);
                return photoView;
            } else {
                Toast.makeText(mContext, "暂无图片！", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
