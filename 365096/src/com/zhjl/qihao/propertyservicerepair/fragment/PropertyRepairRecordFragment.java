package com.zhjl.qihao.propertyservicerepair.fragment;

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
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
import com.zhjl.qihao.propertyservicerepair.adapter.RepairRecordAdapter;
import com.zhjl.qihao.propertyservicerepair.api.PropertyRepairInterface;
import com.zhjl.qihao.propertyservicerepair.bean.PropertyRepairRecordBean;
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

public class PropertyRepairRecordFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_repair_record)
    XRecyclerView xrvRepairRecord;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private RepairRecordAdapter repairRecordAdapter;
    private int pageSize = 10;
    private int pageIndex = 1;
    private boolean isRefresh;
    private List<PropertyRepairRecordBean.DataBean> data = new ArrayList<>();
    private int totalPage;
    private boolean isPause;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_property_repair_record, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvRepairRecord.setLayoutManager(new LinearLayoutManager(mContext));
        repairRecordAdapter = new RepairRecordAdapter(getActivity(), data);
        xrvRepairRecord.setAdapter(repairRecordAdapter);
        xrvRepairRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
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
                    xrvRepairRecord.getFootView().setPadding(0, top, 0, bottom);
                    xrvRepairRecord.setNoMore(true);
                }
            }
        });
        rlLoading.setVisibility(View.VISIBLE);

        repairRecordAdapter.setSetOnItemClickListener(new RepairRecordAdapter.SetOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (data.size() > 0 && data.get(position) != null) {
                    Intent intent = new Intent(mContext, RepairDetailActivity.class);
                    intent.putExtra("repairId", data.get(position).getId());
                    startActivity(intent);
                }
            }
        });

        initData();
        return view;
    }

    public void initData() {
        PropertyRepairInterface repairInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyRepairInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = repairInterface.propertyRepairRecordList(body);
        fragmentRequestData(call, PropertyRepairRecordBean.class, new RequestResult<PropertyRepairRecordBean>() {
            @Override
            public void success(PropertyRepairRecordBean result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    data.addAll(result.getData());
                    repairRecordAdapter.addData(data);
                    xrvRepairRecord.refreshComplete();
                } else {
                    data.addAll(result.getData());
                    repairRecordAdapter.addData(data);
                    xrvRepairRecord.loadMoreComplete();
                }
                if (totalPage > 1) {
                    xrvRepairRecord.setLoadingMoreEnabled(true);
                }
                if (totalPage == 0) {
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvRepairRecord.refreshComplete();
                } else {
                    xrvRepairRecord.loadMoreComplete();
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
