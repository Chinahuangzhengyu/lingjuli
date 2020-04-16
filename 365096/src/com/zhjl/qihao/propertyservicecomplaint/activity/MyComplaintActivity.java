package com.zhjl.qihao.propertyservicecomplaint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicecomplaint.adapter.MyComplaintAdapter;
import com.zhjl.qihao.propertyservicecomplaint.api.ComplaintInterface;
import com.zhjl.qihao.propertyservicecomplaint.bean.MyComplaintBean;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
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

public class MyComplaintActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_my_complaint)
    XRecyclerView xrvMyComplaint;

    private int pageIndex = 1;
    private int pageSize = 10;
    private int totalPage;
    private boolean isRefresh = true;
    private List<MyComplaintBean.DataBean> list = new ArrayList<>();
    private MyComplaintAdapter myComplaintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "我的投诉", this);
        xrvMyComplaint.setLayoutManager(new LinearLayoutManager(mContext));
        myComplaintAdapter = new MyComplaintAdapter(mContext, list);
        xrvMyComplaint.setAdapter(myComplaintAdapter);
        xrvMyComplaint.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvMyComplaint.getFootView().setPadding(0, top, 0, bottom);
                    xrvMyComplaint.setNoMore(true);
                }
            }
        });
        initData();
        myComplaintAdapter.setSetOnItemClickListener((view, position) -> {
            Intent intent = new Intent(mContext, RepairDetailActivity.class);
            intent.putExtra("isComplaint", true);
            intent.putExtra("id", list.get(position).getId());
            startActivity(intent);
        });
    }

    private void initData() {
        ComplaintInterface complaintInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintInterface.getMyComplaint(body);
        activityRequestData(call, MyComplaintBean.class, new RequestResult<MyComplaintBean>() {
            @Override
            public void success(MyComplaintBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    list.clear();
                    list = result.getData();
                    xrvMyComplaint.refreshComplete();
                } else {
                    list.addAll(result.getData());
                    xrvMyComplaint.loadMoreComplete();
                }
                myComplaintAdapter.addData(list);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvMyComplaint.refreshComplete();
                } else {
                    xrvMyComplaint.loadMoreComplete();
                }
            }
        });
    }
}
