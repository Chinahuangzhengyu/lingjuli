package com.zhjl.qihao.hotspot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.complaint.activity.ComplaintDetailActivity;
import com.zhjl.qihao.hotspot.adapter.HotspotHistoryAdapter;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.hotspot.bean.HotspotHistoryListBean;
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

/**
 * 历史热点
 */
public class HotspotHistoryFragment extends VolleyBaseFragment {
    @BindView(R.id.xrv_recent_updates)
    XRecyclerView xrvRecentUpdates;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    private View view;
    private HotspotHistoryAdapter hotspotHistoryAdapter;
    private List<HotspotHistoryListBean.DataBean> complaintBeanList = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private int totalPage;
    private boolean isRefresh = true;
    private boolean isLoadMore;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recent_updates, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        complaintBeanList = new ArrayList<>();
        rlLoading.setVisibility(View.VISIBLE);
        initData();
        xrvRecentUpdates.setLayoutManager(new LinearLayoutManager(mContext));
        hotspotHistoryAdapter = new HotspotHistoryAdapter(mContext, complaintBeanList);
        xrvRecentUpdates.setAdapter(hotspotHistoryAdapter);
        hotspotHistoryAdapter.setSetOnItemClickListener(new HotspotHistoryAdapter.SetOnItemClickListener() {
            @Override
            public void setOnItem(View view, int position) {
                if (islogined()) {
                    Intent intent = new Intent(getActivity(), ComplaintDetailActivity.class);
                    if (complaintBeanList.get(position).getForumLabel() != null) {
                        intent.putExtra("detailTitle", complaintBeanList.get(position).getForumLabel());
                    }
                    intent.putExtra("forumNoteId", complaintBeanList.get(position).getForumNoteId());
                    intent.putExtra("noteSource", "0");
                    intent.putExtra("isHotspot", "2");
                    startActivity(intent);
                } else {
                    showtips();
                }

            }
        });
        xrvRecentUpdates.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    xrvRecentUpdates.getFootView().setPadding(0, top, 0, bottom);
                    xrvRecentUpdates.setNoMore(true);
                }
            }
        });
        return view;
    }

    private void initData() {
        HotspotApiInterfaces hotspotApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("labelTypeId", "150123148717137221cb421a461891e6");
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = hotspotApiInterfaces.historyList(body);
        fragmentRequestData(call, HotspotHistoryListBean.class, new RequestResult<HotspotHistoryListBean>() {
            @Override
            public void success(HotspotHistoryListBean result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    complaintBeanList.clear();
                    complaintBeanList.addAll(result.getData());
                    hotspotHistoryAdapter.addData(complaintBeanList);
                    xrvRecentUpdates.refreshComplete();
                } else {
                    complaintBeanList.addAll(result.getData());
                    hotspotHistoryAdapter.addData(complaintBeanList);
                    xrvRecentUpdates.loadMoreComplete();
                }
                if (totalPage > 1) {
                    xrvRecentUpdates.setLoadingMoreEnabled(true);
                }
                if (totalPage == 0) {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvRecentUpdates.refreshComplete();
                } else {
                    xrvRecentUpdates.loadMoreComplete();
                }
                Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
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
