package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.abrefactor.bean.NewHotspotHistoryBean;
import com.zhjl.qihao.abrefactor.bean.NewHotspotListBean;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.activity.EditComplaintActivity;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBean;
import com.zhjl.qihao.hotspot.bean.EveryWeekHotspotListBeanTwo;
import com.zhjl.qihao.hotspot.bean.HotspotHistoryListBean;
import com.zhjl.qihao.nearbyinteraction.adapter.NearByListAdapter;
import com.zhjl.qihao.nearbyinteraction.adapter.NearByListMoreAdapter;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.NearByItemBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByItemListContentBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByNoteContentBean;
import com.zhjl.qihao.util.Utils;
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

import static com.zhjl.qihao.myaction.adapter.MySendAdapter.REQUEST_NOTE_DATA;
import static com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity.RESULT_NOTE_DATA;

//邻里互动选择页面
public class NearByInteractionActivity extends VolleyBaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tv_search_shop)
    TextView tvSearchShop;
    @BindView(R.id.xrv_nearby_content_list)
    XRecyclerView xrvNearbyContentList;
//    private List<NearByItemBean> list = new ArrayList<>();
    private List<EveryWeekHotspotListBeanTwo.DataBean>  listData = new ArrayList<>();
    private ArrayList<NearByTypeBean.DataBean> data = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private NearByListMoreAdapter nearByListAdapter;
    private int totalPage;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_interaction);
        ButterKnife.bind(this);
        data = getIntent().getParcelableArrayListExtra("data");
//        if (data != null) {
//            for (int i = 0; i < data.size(); i++) {
//                if (data.get(i) != null) {
//                    switch (data.get(i).getLabelName()) {
//                        case "生活杂谈":
//                            list.add(new NearByItemBean(R.drawable.img_shzt, "生活杂谈", data.get(i).getId()));
//                            break;
//                        case "兴趣爱好":
//                            list.add(new NearByItemBean(R.drawable.img_xqah, "兴趣爱好", data.get(i).getId()));
//                            break;
//                        case "二手闲置":
//                            list.add(new NearByItemBean(R.drawable.img_esxz, "二手闲置", data.get(i).getId()));
//                            break;
//                        case "心灵鸡汤":
//                            list.add(new NearByItemBean(R.drawable.img_xljt, "心灵鸡汤", data.get(i).getId()));
//                            break;
//                        case "随手美拍":
//                            list.add(new NearByItemBean(R.drawable.img_ssmp, "随手美拍", data.get(i).getId()));
//                            break;
//                        case "互利互助":
//                            list.add(new NearByItemBean(R.drawable.img_hlhz, "互利互助", data.get(i).getId()));
//                            break;
//                    }
//                }
//            }
//        }

        xrvNearbyContentList.setLayoutManager(new LinearLayoutManager(mContext));
        nearByListAdapter = new NearByListMoreAdapter(this, listData,data);
        xrvNearbyContentList.setAdapter(nearByListAdapter);
        xrvNearbyContentList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvNearbyContentList.getFootView().setPadding(0, top, 0, bottom);
                    xrvNearbyContentList.setNoMore(true);
                }
            }
        });
        initData();
//        gvNearbyInteraction.setAdapter(new MyAdapter());
//        imgSearch.setOnClickListener(this);
//        imgBack.setOnClickListener(this);
//        gvNearbyInteraction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(NearByInteractionActivity.this, NearByInteractionContentActivity.class);
//                intent.putExtra("nearbyName", list.get(position).getName());
//                intent.putExtra("nearbyId", list.get(position).getNameId());
//                startActivity(intent);
//            }
//        });
//        nearByListAdapter.setDeleteNote((id, position) -> deleteNote(id,position));
    }

    private void deleteNote(String forumNoteId, int position) {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        Call<ResponseBody> call = nearByInterfaces.noteDelete(ParamForNet.put(map));
        activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                if (listData.size()>position){
                    listData.remove(position);
                    nearByListAdapter.addData(listData);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        HotspotApiInterfaces hotspotApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        Map<String,Object> map = new HashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = hotspotApiInterfaces.noteList(body);
        activityRequestData(call,  EveryWeekHotspotListBeanTwo.class, new RequestResult<EveryWeekHotspotListBeanTwo>() {
            @Override
            public void success(EveryWeekHotspotListBeanTwo result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh){
                    listData.clear();
                    listData.addAll(result.getData());
                    xrvNearbyContentList.refreshComplete();
                }else {
                    listData.addAll(result.getData());
                    xrvNearbyContentList.loadMoreComplete();
                }
                nearByListAdapter.addData(listData);
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

    @OnClick({R.id.img_back, R.id.ll_search_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_search_note:
                Intent intent = new Intent(mContext,SearchNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_NOTE_DATA&&resultCode==RESULT_NOTE_DATA){
            if (data != null) {
                NearByNoteContentBean.DataBean resultData = data.getParcelableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (resultData!=null){
                    if (position>=listData.size()){
                        return;
                    }
                    listData.get(position).setPraiseNum(resultData.getPraiseNum());
                    listData.get(position).setBrowseNumber(resultData.getBrowseNumber());
                    listData.get(position).setDiscussNum(resultData.getDiscussNum());
                    nearByListAdapter.addData(listData);
                }
            }
        }
    }
}
