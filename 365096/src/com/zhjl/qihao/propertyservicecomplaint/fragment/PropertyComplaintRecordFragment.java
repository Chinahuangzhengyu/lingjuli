package com.zhjl.qihao.propertyservicecomplaint.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.propertyservicecomplaint.adapter.ComplaintRecordAdapter;
import com.zhjl.qihao.propertyservicecomplaint.api.ComplaintInterface;
import com.zhjl.qihao.propertyservicecomplaint.bean.PropertyListBean;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PropertyComplaintRecordFragment extends VolleyBaseFragment {
    @BindView(R.id.xrv_complaint_record)
    XRecyclerView xrvComplaintRecord;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private ComplaintRecordAdapter complaintRecordAdapter;
    private int pageSize = 10;
    private int pageIndex = 1;
    private boolean isRefresh;
    private int totalPage;
    private List<PropertyListBean.DataBean> data = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_property_complaint_record, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvComplaintRecord.setLayoutManager(new LinearLayoutManager(mContext));
        complaintRecordAdapter = new ComplaintRecordAdapter(getActivity(), data);
        xrvComplaintRecord.setAdapter(complaintRecordAdapter);
        xrvComplaintRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData(true);
                isRefresh = true;
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData(false);
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvComplaintRecord.getFootView().setPadding(0, top, 0, bottom);
                    xrvComplaintRecord.setNoMore(true);
                }
            }
        });
        rlLoading.setVisibility(View.VISIBLE);
        complaintRecordAdapter.setSetOnItemClickListener((view, position) -> {
            if (data.size() > 0) {
                Intent intent = new Intent(mContext, RepairDetailActivity.class);
                intent.putExtra("isComplaint", true);
                intent.putExtra("id", data.get(position).getId());
                startActivity(intent);
            }

        });
        initData(true);
        return view;
    }

    public void initData(final boolean isRefresh) {
        ComplaintInterface complaintInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintInterface.getComplaintList(body);
        fragmentRequestData(call, PropertyListBean.class, new RequestResult<PropertyListBean>() {
            @Override
            public void success(PropertyListBean result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    data.addAll(result.getData());
                    complaintRecordAdapter.addData(data);
                    xrvComplaintRecord.refreshComplete();
                } else {
                    data.addAll(result.getData());
                    complaintRecordAdapter.addData(data);
                    xrvComplaintRecord.loadMoreComplete();
                }
                if (totalPage > 1) {
                    xrvComplaintRecord.setLoadingMoreEnabled(true);
                }
                if (totalPage == 0) {

                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvComplaintRecord.refreshComplete();
                } else {
                    xrvComplaintRecord.loadMoreComplete();
                }
                rlLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
