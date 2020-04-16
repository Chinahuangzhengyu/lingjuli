package com.zhjl.qihao.abrefactor.fragment;

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

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.adapter.CommunityHotsListAdapter;
import com.zhjl.qihao.abrefactor.bean.CommunityHotsList;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;
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

import static com.zhjl.qihao.abrefactor.adapter.CommunityHotsListAdapter.COMPLAINT_REQUEST_CODE;

public class PropertyComplaintRecordFragment extends VolleyBaseFragment {
    @BindView(R.id.xrv_complaint_record)
    XRecyclerView xrvComplaintRecord;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private CommunityHotsListAdapter communityHotsListAdapter;
    private int pageSize = 10;
    private int pageIndex = 1;
    private boolean isRefresh = true;
    private int totalPage;
    private List<CommunityHotsList.DataBean> data = new ArrayList<>();
    private VolleyBaseActivity mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (VolleyBaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_property_complaint_record_main, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvComplaintRecord.setLayoutManager(new LinearLayoutManager(mContext));
        communityHotsListAdapter = new CommunityHotsListAdapter(mContext, data);
        xrvComplaintRecord.setAdapter(communityHotsListAdapter);
        xrvComplaintRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
                initData(true);
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
//        initData(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(true);
    }

    public void initData(boolean isRefresh) {
        ComplaintInterface complaintInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
//        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintInterface.getCommunityHotsList(map);
        fragmentRequestData(call, CommunityHotsList.class, new RequestResult<CommunityHotsList>() {
            @Override
            public void success(CommunityHotsList result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    xrvComplaintRecord.refreshComplete();
                } else {
                    data.addAll(result.getData());
                    xrvComplaintRecord.loadMoreComplete();
                }
                data.addAll(result.getData());
                communityHotsListAdapter.addData(data);
                if (totalPage > 1) {
                    xrvComplaintRecord.setLoadingMoreEnabled(true);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==COMPLAINT_REQUEST_CODE){
            if (data != null) {
                NearByNoteContentBean.DataBean resultData = data.getParcelableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (resultData!=null){
                    if (position>=this.data.size()){
                        return;
                    }
                    this.data.get(position).setPraiseNum(resultData.getPraiseNum());
                    this.data.get(position).setBrowseNumber(resultData.getBrowseNumber());
                    this.data.get(position).setDiscussNum(resultData.getDiscussNum());
                    communityHotsListAdapter.addData(this.data);
                }
            }
        }
    }
}
