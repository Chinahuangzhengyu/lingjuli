package com.zhjl.qihao.searchcontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.complaint.activity.ComplaintDetailActivity;
import com.zhjl.qihao.hotspot.adapter.HotspotHistoryAdapter;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.hotspot.bean.HotspotHistoryListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ChatEditText;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class SearchContentActivity extends VolleyBaseActivity {


    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_search_all)
    ChatEditText etSearchAll;
    @BindView(R.id.img_search_all)
    ImageView imgSearchAll;
    @BindView(R.id.search_title)
    LinearLayout searchTitle;
    @BindView(R.id.xrv_search_content)
    XRecyclerView xrvSearchContent;
    private List<HotspotHistoryListBean.DataBean> complaintBeanList = new ArrayList<>();
    private int pageIndex = 1;
    private int totalPage;
    private int totalPageNum;
    private HotspotHistoryAdapter hotspotHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);
        ButterKnife.bind(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        xrvSearchContent.setLayoutManager(new LinearLayoutManager(mContext));
        hotspotHistoryAdapter = new HotspotHistoryAdapter(mContext, complaintBeanList);
        xrvSearchContent.setAdapter(hotspotHistoryAdapter);
        hotspotHistoryAdapter.setSetOnItemClickListener(new HotspotHistoryAdapter.SetOnItemClickListener() {
            @Override
            public void setOnItem(View view, int position) {
                if (islogin()){
                    Intent intent = new Intent(SearchContentActivity.this, ComplaintDetailActivity.class);
                    intent.putExtra("detailTitle", complaintBeanList.get(position).getCreateUser().getNickname());
                    intent.putExtra("forumNoteId", complaintBeanList.get(position).getForumNoteId());
                    intent.putExtra("noteSource", "0");
                    intent.putExtra("isHotspot", "2");
                    startActivity(intent);
                }else {
                    showTips();
                }

            }
        });
        xrvSearchContent.setPullRefreshEnabled(false);
        xrvSearchContent.setLoadingMoreEnabled(true);
        xrvSearchContent.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (totalPageNum > pageIndex) {
                    pageIndex++;
                    searchContent();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvSearchContent.getFootView().setPadding(0, top, 0, bottom);
                    xrvSearchContent.setNoMore(true);
                }
            }
        });
        etSearchAll.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    searchContent();
                }
                return true;
            }
        });
    }

    @OnClick({R.id.img_back, R.id.et_search_all, R.id.img_search_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_search_all:
                complaintBeanList.clear();
                searchContent();
                break;
        }
    }

    private void searchContent() {
        if (etSearchAll.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, "请输入搜索内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        HotspotApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
//        map.put("labelTypeId","");
//        map.put("userId", mSession.getUserId());
        map.put("key", etSearchAll.getText().toString().trim());
        map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        map.put("pageIndex", pageIndex+"");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.historyList(body);
        activityRequestData(call, HotspotHistoryListBean.class, new RequestResult<HotspotHistoryListBean>() {
            @Override
            public void success(HotspotHistoryListBean result, String message) {
                if (result.getData().size() != 0) {
                    totalPage = result.getTotalPage();
                    complaintBeanList.addAll(result.getData());
                    hotspotHistoryAdapter.addData(complaintBeanList);
                } else {
                    Toast.makeText(mContext, "没有该条记录！", Toast.LENGTH_SHORT).show();
                }
                xrvSearchContent.loadMoreComplete();
            }

            @Override
            public void fail() {
                xrvSearchContent.loadMoreComplete();
            }
        });
    }
}
