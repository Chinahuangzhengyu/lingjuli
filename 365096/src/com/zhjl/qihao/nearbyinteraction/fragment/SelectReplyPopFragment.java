package com.zhjl.qihao.nearbyinteraction.fragment;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.myaction.bean.MyReplyActionBean;
import com.zhjl.qihao.nearbyinteraction.adapter.ReplyListAdapter;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteReplyContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NoteReplyListBean;
import com.zhjl.qihao.nearbyinteraction.view.KeyboardHeightObserver;
import com.zhjl.qihao.nearbyinteraction.view.KeyboardHeightProvider;
import com.zhjl.qihao.util.ScreenUtil;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.lang.reflect.Field;
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

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static com.zhjl.qihao.util.ScreenUtil.getRawScreenSize;
import static com.zhjl.qihao.util.ScreenUtil.getScreenSize;

public class SelectReplyPopFragment extends BottomSheetDialogFragment implements KeyboardHeightObserver {


    @BindView(R.id.xrv_reply_list)
    RecyclerView xrvReplyList;
    @BindView(R.id.ll_pop_aboveView)
    LinearLayout llPopAboveView;
    @BindView(R.id.reply_pop_edt)
    EditText replyPopEdt;
    @BindView(R.id.tv_pop_send)
    TextView tvPopSend;
    @BindView(R.id.ll_pop_edt)
    LinearLayout llPopEdt;
    //    @BindView(R.id.rl_pop_parent)
//    RelativeLayout rlPopParent;
    Unbinder unbinder;
    @BindView(R.id.tv_reply_num)
    TextView tvReplyNum;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.ll_edt)
    LinearLayout llEdt;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_line_two)
    View viewLineTwo;
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
    private LinearLayout loadingView;
    private TextView tvNotData;
    private boolean isFinish;
    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private BottomSheetBehavior<FrameLayout> behavior;
    private int statusBarHeight;
    private ScreenUtil instance;
    private KeyboardHeightProvider keyboardHeightProvider;
    private int oriBottomMargin;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (VolleyBaseActivity) context;
        session = Session.get(context);
    }

    public static SelectReplyPopFragment getInstance(Object bean, String discussId, String createId, String replyDiscussId) {
        SelectReplyPopFragment replyPopFragment = new SelectReplyPopFragment();
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
        instance = new ScreenUtil().getScreenUtilInstance(mContext);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
//            if (instance.getVirtualKeyHeight() == 0) {
//                Toast.makeText(mContext, "高度关闭" + (ScreenUtil.getRawScreenSize(mContext)[1] - NewStatusBarUtils.getStatusBarHeight(mContext)), Toast.LENGTH_SHORT).show();
//                layoutParams.height = ScreenUtil.getRawScreenSize(mContext)[1] - NewStatusBarUtils.getStatusBarHeight(mContext);
//            } else {
//                Toast.makeText(mContext, "高度开启" + (ScreenUtil.getRawScreenSize(mContext)[1] - NewStatusBarUtils.getStatusBarHeight(mContext) - instance.getVirtualKeyHeight()), Toast.LENGTH_SHORT).show();
//                layoutParams.height = ScreenUtil.getRawScreenSize(mContext)[1] - NewStatusBarUtils.getStatusBarHeight(mContext) - instance.getVirtualKeyHeight();
//            }
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //跳过折叠状态
            behavior.setSkipCollapsed(true);
        }
        final View view = getView();
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
        });
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
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
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_select_reply_pop, container, false);
        }
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | SOFT_INPUT_ADJUST_NOTHING);
        unbinder = ButterKnife.bind(this, view);


        View decorView = getDialog().getWindow().getDecorView();
        View contentView = getDialog().getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        setEdit();
        discussId = getArguments().getString("discussId");
        createId = getArguments().getString("createId");
        replyDiscussId = getArguments().getString("replyDiscussId");
        replyId = discussId;
        bean = getArguments().getParcelable("bean");
        if (bean != null) {
            if (bean instanceof MyReplyActionBean.DataBean) {
                MyReplyActionBean.DataBean beanReply = (MyReplyActionBean.DataBean) bean;
                tvReplyNum.setText(beanReply.getDiscussNum() + "条回复");
                discussNum = beanReply.getDiscussNum();
            } else if (bean instanceof NearByNoteReplyContentBean.DataBean) {
                NearByNoteReplyContentBean.DataBean beanReply = (NearByNoteReplyContentBean.DataBean) bean;
                tvReplyNum.setText(beanReply.getDiscussNum() + "条回复");
                discussNum = beanReply.getDiscussNum();
            }
        }
        initData();
        xrvReplyList.setLayoutManager(new LinearLayoutManager(mContext));
        replyListAdapter = new ReplyListAdapter(mContext, data, bean, createId);
        xrvReplyList.setAdapter(replyListAdapter);

        /**
         * 布局高度变化判断是否有软键盘
         */
        llPopEdt.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (oldBottom != 0 && bottom != 0) {
                if (oldBottom != bottom) {
                    if (isReply) {
                        return;
                    }
                    // 输入布局向上移动屏幕的四分之一即视为软键盘弹出
                    if ((oldBottom - bottom) > getDialog().getWindow().getDecorView().getRootView().getHeight() / 4) {
                        //弹出键盘   更改背景
                        tvPopSend.setVisibility(View.VISIBLE);
                        llPopAboveView.setVisibility(View.VISIBLE);
                        llEdt.setVisibility(View.GONE);
                        viewLineTwo.setVisibility(View.GONE);
                        llPopEdt.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                    } else if ((bottom - oldBottom) > getDialog().getWindow().getDecorView().getRootView().getHeight() / 4) {
                        //弹出键盘   更改背景
                        tvPopSend.setVisibility(View.VISIBLE);
                        llPopAboveView.setVisibility(View.VISIBLE);
                        llPopEdt.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                        llEdt.setVisibility(View.GONE);
                        viewLineTwo.setVisibility(View.GONE);
                    } else {
                        //未弹出  收回
                        llPopAboveView.setVisibility(View.GONE);
                        llEdt.setVisibility(View.VISIBLE);
                        viewLineTwo.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.GONE);
                        llPopEdt.setVisibility(View.GONE);
                    }
                    v.invalidate();
                }
            }
        });

        replyListAdapter.setReplyOnClickListener((view, replyName, id, position) -> {
            replyId = id;
            isReply = false;
            tvPopSend.setVisibility(View.VISIBLE);
            llPopAboveView.setVisibility(View.VISIBLE);
            llPopEdt.setVisibility(View.VISIBLE);
            replyPopEdt.getText().clear();
            replyPopEdt.requestFocus();
//            xrvReplyList.scrollToPosition(position);
            replyPopEdt.setHint("@" + replyName);
            Utils.openKeyboard(replyPopEdt);
        });
        replyListAdapter.setGetFootView((textView, loadingView) -> {
            pageIndex++;
            initData();
        });

        replyListAdapter.setSetRefreshData(() -> {
            setRefreshData.refresh();
            dismiss();
        });

        xrvReplyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                    int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if (dy < 0) {
                        //上滑到顶了
//                        if (firstVisibleItemPosition==0){
//
//                        }
                    } else {
                        //下滑到底部
                        int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                        if (lastCompletelyVisibleItemPosition == layoutManager.getItemCount() - 1) {
                            isRefresh = false;
                            if (isFinish) {
                                return;
                            }
                            pageIndex++;
                            initData();
                        }
                    }
                }
            }
        });

        return view;
    }


    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int[] screenSize = getRawScreenSize(mContext);
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

    private void setEdit() {
        keyboardHeightProvider = new KeyboardHeightProvider(getActivity());
        llPopEdt.post((Runnable) () -> {
            keyboardHeightProvider.start();
            //  获取元素的初始位置
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llPopEdt.getLayoutParams();
            oriBottomMargin = layoutParams.bottomMargin;

        });
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
//        map.put("replyDiscussId", replyDiscussId);
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
//                if (result.getData().size() < 10) {
//                    tvNotData.setVisibility(View.VISIBLE);
//                } else {
//                    tvNotData.setVisibility(View.GONE);
//                }
                if (totalPage >= pageIndex) {
                    isFinish = false;
                    replyListAdapter.addData(data, false);
                } else {
                    isFinish = true;
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
        map.put("content", replyPopEdt.getText().toString().trim());
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
                llPopEdt.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                llEdt.setVisibility(View.VISIBLE);
                viewLineTwo.setVisibility(View.VISIBLE);
                if (llPopAboveView.getVisibility() == View.VISIBLE) {
                    llPopAboveView.setVisibility(View.GONE);
                }
                tvReplyNum.setText(discussNum + "条回复");
                replyPopEdt.setHint("我也来说两句~");
                replyPopEdt.getText().clear();
                tvPopSend.setEnabled(true);
            }

            @Override
            public void fail() {
                tvPopSend.setEnabled(true);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        keyboardHeightProvider.close();
        unbinder.unbind();
    }

    @OnClick({R.id.img_cancel, R.id.tv_pop_send, R.id.ll_pop_aboveView, R.id.tv_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.ll_pop_aboveView:
                llPopEdt.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                llEdt.setVisibility(View.VISIBLE);
                viewLineTwo.setVisibility(View.VISIBLE);
                llPopAboveView.setVisibility(View.GONE);
                replyPopEdt.getText().clear();
                replyPopEdt.setHint("我也来说两句~");
                Utils.hideSoftKeyboard(mContext, replyPopEdt);
                break;
            case R.id.tv_pop_send:
                if (replyPopEdt.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "请输入内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestReply();
                break;
            case R.id.tv_reply:
                replyId = discussId;
                tvPopSend.setVisibility(View.VISIBLE);
                llPopAboveView.setVisibility(View.VISIBLE);
                llPopEdt.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                llEdt.setVisibility(View.GONE);
                viewLineTwo.setVisibility(View.GONE);
                replyPopEdt.requestFocus();
                Utils.openKeyboard(replyPopEdt);
                break;

        }
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
//        Toast.makeText(mContext, "弹窗高度"+height, Toast.LENGTH_SHORT).show();
        if (height > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llPopEdt.getLayoutParams();
            layoutParams.bottomMargin = height;
            llPopEdt.setLayoutParams(layoutParams);
        } else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llPopEdt.getLayoutParams();
            layoutParams.bottomMargin = oriBottomMargin;
            llPopEdt.setLayoutParams(layoutParams);
        }
    }

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
