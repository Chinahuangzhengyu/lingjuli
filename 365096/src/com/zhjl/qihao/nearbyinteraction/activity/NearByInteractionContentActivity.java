package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.complaint.activity.ComplaintDetailActivity;
import com.zhjl.qihao.complaint.activity.EditComplaintActivity;
import com.zhjl.qihao.hotspot.adapter.EveryWeekHotspotAdapter;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBean;
import com.zhjl.qihao.searchcontent.SearchContentActivity;
import com.zhjl.qihao.util.Utils;
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

/**
 * 邻里互动内容页面
 */
public class NearByInteractionContentActivity extends VolleyBaseActivity {
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.img_sort)
    ImageView imgSort;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.xrv_nearby_content_list)
    XRecyclerView xrvNearbyContentList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_send_content)
    TextView tvSendContent;
    @BindView(R.id.img_back)
    ImageView imgBack;
    private List<EveryWeekHotspotListBean.DataBean> complaintBeanList = new ArrayList<>();
    private String title;
    private PopupWindow nearBySortPop;
    private String nearbyId;
    private EveryWeekHotspotAdapter everyWeekHotspotAdapter;
    private int pageIndex = 1;
    private int totalPage;
    private boolean isRefresh = true;
    private boolean isLoadMore;
    private int type = 0;
    private HotspotApiInterfaces apiInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_interaction_content);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        title = getIntent().getStringExtra("nearbyName");
        nearbyId = getIntent().getStringExtra("nearbyId");
        tvTitle.setText(title);
        switch (title) {
            case "生活杂谈":
                tvTitle.setTextColor(Color.parseColor("#ff9482"));
                break;
            case "兴趣爱好":
                tvTitle.setTextColor(Color.parseColor("#67b4f2"));
                break;
            case "二手闲置":
                tvTitle.setTextColor(Color.parseColor("#f9c236"));
                break;
            case "心灵鸡汤":
                tvTitle.setTextColor(Color.parseColor("#ff9cb7"));
                break;
            case "随手美拍":
                tvTitle.setTextColor(Color.parseColor("#afa3f8"));
                break;
            case "互利互助":
                tvTitle.setTextColor(Color.parseColor("#65d49d"));
                break;
        }
        xrvNearbyContentList.setLayoutManager(new LinearLayoutManager(this));
        everyWeekHotspotAdapter = new EveryWeekHotspotAdapter(mContext, complaintBeanList,true);
        xrvNearbyContentList.setAdapter(everyWeekHotspotAdapter);
        imgSort.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        tvSendContent.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        everyWeekHotspotAdapter.setSetOnItemClickListener((view, position) -> {
            Intent intent = new Intent(NearByInteractionContentActivity.this, ComplaintDetailActivity.class);
            intent.putExtra("isHotspot", "1");
            intent.putExtra("detailTitle", title);
            intent.putExtra("forumNoteId", complaintBeanList.get(position).getForumNoteId());
            intent.putExtra("noteSource", "0");
            startActivity(intent);
        });

        xrvNearbyContentList.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    int bottom = Utils.dip2px(mContext, 78);
                    xrvNearbyContentList.getFootView().setPadding(0, top, 0, bottom);
                    xrvNearbyContentList.setNoMore(true);
                }
            }
        });
        initPopWindow();
        initData();
    }


    private void initPopWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.nearby_sort_popwindow, null);
        TextView tvSortHots = popView.findViewById(R.id.tv_sort_hots);
        TextView tvSortTime = popView.findViewById(R.id.tv_sort_time);
        tvSortHots.setOnClickListener(this);
        tvSortTime.setOnClickListener(this);
        int with = Utils.dip2px(mContext, 64);
        int height = Utils.dip2px(mContext, 75);
        nearBySortPop = new PopupWindow(popView, with, height);
        nearBySortPop.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.sort_bg));
        nearBySortPop.setFocusable(true);
        nearBySortPop.setOutsideTouchable(true);
        nearBySortPop.setAnimationStyle(R.style.AnimationPopupCenter);
    }

    private void sortData(int type) {
        HashMap<String, Object> map = new HashMap();
        map.put("pageIndex", pageIndex);
        map.put("labelTypeId", nearbyId);
        map.put("sort", type);
        map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = apiInterfaces.noteList(body);
        activityRequestData(call, EveryWeekHotspotListBean.class, new RequestResult<EveryWeekHotspotListBean>() {
            @Override
            public void success(EveryWeekHotspotListBean result, String message) {
                if (result.getData().size() != 0) {
                    totalPage = result.getTotalPage();
                    if (totalPage>1){
                        xrvNearbyContentList.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        complaintBeanList.clear();
                        complaintBeanList.addAll(result.getData());
                        everyWeekHotspotAdapter.addData(complaintBeanList);
                        xrvNearbyContentList.refreshComplete();
                    }
                    if (isLoadMore) {
                        complaintBeanList.addAll(result.getData());
                        everyWeekHotspotAdapter.addData(complaintBeanList);
                        xrvNearbyContentList.loadMoreComplete();
                    }
                } else {
                    if (isRefresh){
                        xrvNearbyContentList.refreshComplete();
                    }else {
                        xrvNearbyContentList.loadMoreComplete();
                    }
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (nearBySortPop.isShowing()) {
                    nearBySortPop.dismiss();
                }
                if (isRefresh){
                    xrvNearbyContentList.refreshComplete();
                }else {
                    xrvNearbyContentList.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh){
                    xrvNearbyContentList.refreshComplete();
                }else {
                    xrvNearbyContentList.loadMoreComplete();
                }
                if (nearBySortPop.isShowing()) {
                    nearBySortPop.dismiss();
                }
            }
        });
    }

    private void initData() {
        apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        HashMap<String, Object> map = new HashMap();
        map.put("pageIndex", pageIndex);
        map.put("labelTypeId", nearbyId);
        map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = apiInterfaces.noteList(body);
        activityRequestData(call, EveryWeekHotspotListBean.class, new RequestResult<EveryWeekHotspotListBean>() {
            @Override
            public void success(EveryWeekHotspotListBean result, String message) {
                if (result.getData().size() != 0) {
                    totalPage = result.getTotalPage();
                    if (totalPage>1){
                        xrvNearbyContentList.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        complaintBeanList.clear();
                        complaintBeanList.addAll(result.getData());
                        everyWeekHotspotAdapter.addData(complaintBeanList);
                        xrvNearbyContentList.refreshComplete();
                    }
                    if (isLoadMore) {
                        complaintBeanList.addAll(result.getData());
                        everyWeekHotspotAdapter.addData(complaintBeanList);
                        xrvNearbyContentList.loadMoreComplete();
                    }
                } else {
                    if (isRefresh){
                        xrvNearbyContentList.refreshComplete();
                    }else {
                        xrvNearbyContentList.loadMoreComplete();
                    }
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                if (isRefresh){
                    xrvNearbyContentList.refreshComplete();
                }else {
                    xrvNearbyContentList.loadMoreComplete();
                }
            }

            @Override
            public void fail() {
                if (isRefresh){
                    xrvNearbyContentList.refreshComplete();
                }else {
                    xrvNearbyContentList.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_sort:
                if (nearBySortPop.isShowing()) {
                    nearBySortPop.dismiss();
                } else {
                    nearBySortPop.showAsDropDown(imgSort, -Utils.dip2px(mContext, 40), 0);
                }
                break;
            case R.id.tv_sort_hots:
                type = 1;
                isRefresh = true;
                isLoadMore = false;
                pageIndex = 1;
                sortData(type);
                break;
            case R.id.tv_sort_time:
                type = 0;
                isRefresh = true;
                isLoadMore = false;
                pageIndex = 1;
                sortData(type);
                break;
            case R.id.img_search:
                Intent searchIntent = new Intent(this, SearchContentActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.tv_send_content:
                if (islogin()) {
                    Intent intent = new Intent(this, EditComplaintActivity.class);
                    intent.putExtra("isComplaint", "发表文章");
                    startActivity(intent);
                } else {
                    showTips();
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0x15){
            isRefresh = true;
            initData();
        }
    }
}
