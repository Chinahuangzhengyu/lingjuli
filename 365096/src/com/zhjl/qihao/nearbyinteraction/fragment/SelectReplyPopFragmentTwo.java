package com.zhjl.qihao.nearbyinteraction.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.myaction.bean.MyReplyActionBean;
import com.zhjl.qihao.nearbyinteraction.adapter.ReplyListAdapter;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NoteReplyListBean;
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
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class SelectReplyPopFragmentTwo extends BottomSheetDialogFragment {


//    @BindView(R.id.xrv_reply_list)
//    XRecyclerView xrvReplyList;
//    @BindView(R.id.ll_pop_aboveView)
//    LinearLayout llPopAboveView;
//    @BindView(R.id.reply_pop_edt)
//    EditText replyPopEdt;
//    @BindView(R.id.rl_pop_parent)
//    RelativeLayout rlPopParent;
    Unbinder unbinder;
//    @BindView(R.id.tv_reply)
//    TextView tvReply;
//    @BindView(R.id.ll_edt)
//    LinearLayout llEdt;
    private VolleyBaseActivity mContext;
    private View view;
    private String discussId = "";
    private String replyId = "";
    private Session session;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh = true;
    private int totalPage;
    private List<NoteReplyListBean.DataBean> data = new ArrayList<>();
    private ReplyListAdapter replyListAdapter;
    private Object bean;
    private int discussNum;
    private boolean isReply;
    private String createId;
    private float currentY;    //起始位置
    private String replyDiscussId;
    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private BottomSheetBehavior<FrameLayout> behavior;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.BottomSheetEdit);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (VolleyBaseActivity) context;
        session = Session.get(context);
    }

    public static SelectReplyPopFragmentTwo getInstance(Object bean, String discussId, String createId, String replyDiscussId) {
        SelectReplyPopFragmentTwo replyPopFragment = new SelectReplyPopFragmentTwo();
        Bundle bundle = new Bundle();
        bundle.putString("discussId", discussId);
        bundle.putParcelable("bean", (Parcelable) bean);
        bundle.putString("createId", createId);
        bundle.putString("replyDiscussId", replyDiscussId);
        replyPopFragment.setArguments(bundle);
        return replyPopFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

    }

    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_select_reply_pop_two, container, false);
        }
//        setTopOffset(Utils.dip2px(mContext, 134));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        unbinder = ButterKnife.bind(this, view);
        discussId = getArguments().getString("discussId");
        createId = getArguments().getString("createId");
        replyDiscussId = getArguments().getString("replyDiscussId");
        replyId = discussId;
        bean = getArguments().getParcelable("bean");

        initData();
//        xrvReplyList.setLayoutManager(new LinearLayoutManager(mContext));
//        replyListAdapter = new ReplyListAdapter(mContext, data, bean, createId);
//        xrvReplyList.setAdapter(replyListAdapter);
//        xrvReplyList.setPullRefreshEnabled(false);
//        xrvReplyList.setLoadingMoreEnabled(true);
//        xrvReplyList.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                isRefresh = false;
//                pageIndex++;
//                if (pageIndex <= totalPage) {
//                    initData();
//                } else {
//                    int top = Utils.dip2px(mContext, 6);
//                    int bottom = Utils.dip2px(mContext, 30);
//                    xrvReplyList.getFootView().setPadding(0, top, 0, bottom);
//                    xrvReplyList.setFootViewText("正在加载...", "更多评论正在赶来~");
//                    xrvReplyList.setNoMore(true);
//                }
//            }
//        });


//        replyListAdapter.setReplyOnClickListener((view, replyName, id) -> {
//            replyId = id;
//            isReply = false;
//            replyPopEdt.requestFocus();
//            replyPopEdt.setHint("@" + replyName);
//            Utils.openKeyboard(replyPopEdt);
//        });
//        replyListAdapter.setSetRefreshData(() -> {
//            setRefreshData.refresh();
//            dismiss();
//        });
//        SlideDrawerHelper build = new SlideDrawerHelper.Builder(rlPopParent, rlPopParent)
//                // 设置滑动总布局初始化高度状态为最大高度
//                .initHeightState(SlideDrawerHelper.SlideParentHeight.MAX_HEIGHT)
//                .animDuration(200)
//                .build();

        return view;
    }

    @Override
    public void dismiss() {
        if (getReplyNum != null) {
            getReplyNum.replyNum(discussNum);
        }
//        Utils.hideSoftInput(getActivity());
        super.dismiss();
    }

    private void initData() {
        ComplaintApiInterfaces complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("discussId", discussId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("replyDiscussId", replyDiscussId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintApiInterfaces.noteListDiscuss(body);
        mContext.activityRequestData(call, NoteReplyListBean.class, new VolleyBaseActivity.RequestResult<NoteReplyListBean>() {
            @Override
            public void success(NoteReplyListBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                }
                data.addAll(result.getData());
//                replyListAdapter.addData(data);
            }

            @Override
            public void fail() {

            }
        });
    }


    private void requestReply() {
        ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", "");
        if (bean != null) {
            if (bean instanceof NearByNoteReplyContentBean.DataBean) {
                NearByNoteReplyContentBean.DataBean nearByBean = (NearByNoteReplyContentBean.DataBean) bean;
                map.put("forumNoteId", nearByBean.getForumNoteId());
            } else if (bean instanceof MyReplyActionBean.DataBean) {
                MyReplyActionBean.DataBean actionBean = (MyReplyActionBean.DataBean) bean;
                map.put("forumNoteId", actionBean.getForumNoteId());
            }
        }
        map.put("discussId", replyId);
        map.put("replyType", 1);    //讨论
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.addReply(body);
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Utils.hideSoftInput(getActivity());
                discussNum++;
                pageIndex = 1;
//                data.clear();
                isRefresh = true;
                initData();
                isReply = true;
//                if (llPopAboveView.getVisibility() == View.VISIBLE) {
//                    llPopAboveView.setVisibility(View.GONE);
//                }
//                replyPopEdt.getText().clear();
            }

            @Override
            public void fail() {
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



//    private boolean isSoftShowing() {
//        //获取当屏幕内容的高度
//        int screenHeight = getDialog().getWindow().getDecorView().getHeight();
//        //获取View可见区域的bottom
//        Rect rect = new Rect();
//        //DecorView即为activity的顶级view
//        getDialog().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
//        //选取screenHeight*2/3进行判断
//        return screenHeight * 2 / 3 > rect.bottom;
//    }

    public interface SetRefreshData {
        void refresh();
    }

    private SetRefreshData setRefreshData;

    public void setSetRefreshData(SetRefreshData setRefreshData) {
        this.setRefreshData = setRefreshData;
    }

    public interface GetReplyNum {
        void replyNum(int num);
    }

    private GetReplyNum getReplyNum;

    public void setGetReplyNum(GetReplyNum getReplyNum) {
        this.getReplyNum = getReplyNum;
    }
}
