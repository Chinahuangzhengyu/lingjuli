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
import com.zhjl.qihao.hotspot.adapter.EveryWeekHotspotAdapter;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 每周热点
 */
public class EveryWeekHotspotFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_every_week_hotspot)
    XRecyclerView xrvEveryWeekHotspot;
    Unbinder unbinder;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    private View view;
    private EveryWeekHotspotAdapter everyWeekHotspotAdapter;
    private List<EveryWeekHotspotListBean.DataBean> list = new ArrayList<>();
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
            view = inflater.inflate(R.layout.fragment_every_week_hotspot, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        rlLoading.setVisibility(View.VISIBLE);
        xrvEveryWeekHotspot.setLayoutManager(new LinearLayoutManager(mContext));
        everyWeekHotspotAdapter = new EveryWeekHotspotAdapter(mContext, list, false);
        xrvEveryWeekHotspot.setAdapter(everyWeekHotspotAdapter);
        initData();
        everyWeekHotspotAdapter.setSetOnItemClickListener(new EveryWeekHotspotAdapter.SetOnItemClickListener() {
            @Override
            public void setOnItem(View view, int position) {
                if (islogined()) {
                    Intent intent = new Intent(getActivity(), ComplaintDetailActivity.class);
                    if (list.get(position).getForumLabel() != null) {
                        intent.putExtra("detailTitle", list.get(position).getForumLabel());
                        intent.putExtra("forumNoteId", list.get(position).getForumNoteId());
                        intent.putExtra("isHotspot", "1");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "类别名称错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showtips();
                }
            }
        });
//        ((DefaultItemAnimator) xrvEveryWeekHotspot.getItemAnimator()).setSupportsChangeAnimations(false);
//        xrvEveryWeekHotspot.getItemAnimator().setChangeDuration(0);
        xrvEveryWeekHotspot.setLoadingListener(new XRecyclerView.LoadingListener() {


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
                    xrvEveryWeekHotspot.getFootView().setPadding(0, top, 0, bottom);
                    xrvEveryWeekHotspot.setNoMore(true);
                }
            }
        });
        return view;
    }

    private void initData() {
        HotspotApiInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        HashMap<String, Object> map = new HashMap();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
//        map.put("labelTypeId","");
//        map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = apiInterfaces.hotspotList(body);
        fragmentRequestData(call, EveryWeekHotspotListBean.class, new RequestResult<EveryWeekHotspotListBean>() {
            @Override
            public void success(EveryWeekHotspotListBean result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    list.clear();
                    list.addAll(result.getData());
                    everyWeekHotspotAdapter.addData(list);
                    xrvEveryWeekHotspot.refreshComplete();
                } else {
                    list.addAll(result.getData());
                    everyWeekHotspotAdapter.addData(list);
                    xrvEveryWeekHotspot.loadMoreComplete();
                }
                if (totalPage > 1) {
                    xrvEveryWeekHotspot.setLoadingMoreEnabled(true);
                }
                if (totalPage == 0) {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh){
                    xrvEveryWeekHotspot.refreshComplete();
                }else {
                    xrvEveryWeekHotspot.loadMoreComplete();
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
