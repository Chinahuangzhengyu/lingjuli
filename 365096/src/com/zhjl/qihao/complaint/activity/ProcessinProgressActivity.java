package com.zhjl.qihao.complaint.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.complaint.adapter.ComplaintDetailAdapter;
import com.zhjl.qihao.complaint.adapter.ComplaintPropertyAdapter;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.CommentHistoryRecordBean;
import com.zhjl.qihao.complaint.bean.PropertyProgressBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

//物业处理(评论记录共用)进度activity
public class ProcessinProgressActivity extends VolleyBaseActivity {
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.xrv_processin_progress)
    XRecyclerView xrvProcessinProgress;
    private List<CommentHistoryRecordBean.DataBean> recordLists = new ArrayList<>();
    private ComplaintDetailAdapter complaintDetailAdapter;
    private ComplaintPropertyAdapter complaintPropertyAdapter;
    private String discussId;
    private String forumNoteId;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int totalPage;
    private boolean isRefresh = true;
    private boolean isLoadMore;
    private ArrayList<PropertyProgressBean.DataBean> data = new ArrayList<>();
    private ComplaintApiInterfaces complaintApiInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processin_progress);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        String title = getIntent().getStringExtra("title");
        discussId = getIntent().getStringExtra("discussId");
        forumNoteId = getIntent().getStringExtra("forumNoteId");
//        data = getIntent().getParcelableArrayListExtra("data");
        NewHeaderBar.createCommomBack(this, title, this);
        xrvProcessinProgress.setLayoutManager(new LinearLayoutManager(this));
        complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        if (!title.equals("物业处理进度")) {
            if (discussId != null && !discussId.equals("")) {
                initData();     //请求评论记录
            }
            complaintDetailAdapter = new ComplaintDetailAdapter(this, recordLists);
            xrvProcessinProgress.setAdapter(complaintDetailAdapter);
        } else {
            initPropertyData();
            complaintPropertyAdapter = new ComplaintPropertyAdapter(this, data);
            xrvProcessinProgress.setAdapter(complaintPropertyAdapter);
        }
        xrvProcessinProgress.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isLoadMore = false;
                isRefresh = true;
                initData();
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvProcessinProgress.getFootView().setPadding(0, top, 0, bottom);
                    xrvProcessinProgress.setNoMore(true);
                }
            }
        });
    }

    //物业处理进度
    private void initPropertyData() {
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
//        map.put("pageIndex", pageIndex + "");
//        map.put("pageSize", pageSize + "");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> commentHistoryRecord = complaintApiInterfaces.getPropertyProgressList(body);
        activityRequestData(commentHistoryRecord, PropertyProgressBean.class, new RequestResult<PropertyProgressBean>() {
            @Override
            public void success(PropertyProgressBean result, String message) {
                List<PropertyProgressBean.DataBean> list = result.getData();
                if (list.size() > 0) {
//                                    totalPage = list.get();
                    if (isRefresh) {
                        data.clear();
                    }
                    data.addAll(list);
                    complaintPropertyAdapter.addData(list);
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                xrvProcessinProgress.refreshComplete();
                xrvProcessinProgress.loadMoreComplete();
            }

            @Override
            public void fail() {
                xrvProcessinProgress.refreshComplete();
                xrvProcessinProgress.loadMoreComplete();
            }
        });
    }


    //回复历史记录
    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("discussId", discussId);
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> commentHistoryRecord = complaintApiInterfaces.getCommentHistoryRecord(body);
        activityRequestData(commentHistoryRecord, CommentHistoryRecordBean.class, new RequestResult<CommentHistoryRecordBean>() {
            @Override
            public void success(CommentHistoryRecordBean result, String message) {
                List<CommentHistoryRecordBean.DataBean> list = result.getData();
                if (list.size() > 0) {
                    totalPage = result.getTotalPage();
                    if (totalPage>1){
                        xrvProcessinProgress.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        recordLists.clear();
                        recordLists.addAll(list);
                        complaintDetailAdapter.addData(recordLists);
                    }
                    if (isLoadMore) {
                        list.addAll(recordLists);
                        complaintDetailAdapter.addData(recordLists);
                    }
                    complaintDetailAdapter.addData(list);
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (isRefresh){
                    xrvProcessinProgress.refreshComplete();
                }else {
                    xrvProcessinProgress.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                xrvProcessinProgress.refreshComplete();
                xrvProcessinProgress.loadMoreComplete();
            }
        });
    }

}
