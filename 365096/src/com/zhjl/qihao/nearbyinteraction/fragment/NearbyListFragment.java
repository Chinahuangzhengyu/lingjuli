package com.zhjl.qihao.nearbyinteraction.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.nearbyinteraction.adapter.NearByListAdapter;
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
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.nearbyinteraction.activity.NearByListActivity.REQUEST_SEND_NOTE;
import static com.zhjl.qihao.nearbyinteraction.activity.NearByNoteContentActivity.RESULT_NOTE_DATA;
import static com.zhjl.qihao.nearbyinteraction.activity.NearBySendNoteActivity.RESULT_SEND_NOTE;
import static com.zhjl.qihao.nearbyinteraction.adapter.NearByListAdapter.REQUEST_DELETE_NOTE;
import static com.zhjl.qihao.nearbyinteraction.adapter.NearByNoteContentAdapter.RESULT_DELETE_NOTE;

public class NearbyListFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_nearby_content_list)
    XRecyclerView xrvNearbyContentList;
    Unbinder unbinder;
    @BindView(R.id.img_not_data)
    ImageView imgNotData;
    @BindView(R.id.tv_not_data)
    TextView tvNotData;
    @BindView(R.id.not_data)
    LinearLayout notData;
    private View view;
    private List<NearByItemListContentBean.DataBean> listData = new ArrayList<>();
    private NearByItemBean.DataBean titleBean;
    private String nearbyId = "";
    private int pageIndex = 1;
    private int pageSize = 10;
    private int totalPage;
    private boolean isRefersh = true;
    private NearByListAdapter nearByListAdapter;
    private VolleyBaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (VolleyBaseActivity) context;
    }

    public static NearbyListFragment getInstance(String nearbyId, NearByItemBean.DataBean titleBean) {
        NearbyListFragment fragment = new NearbyListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("titleBean", titleBean);
        bundle.putString("nearbyId", nearbyId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_nearby_list, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        notData.setVisibility(View.GONE);
        titleBean = getArguments().getParcelable("titleBean");
        nearbyId = getArguments().getString("nearbyId");
        xrvNearbyContentList.setLayoutManager(new LinearLayoutManager(mContext));
        nearByListAdapter = new NearByListAdapter(mContext, listData);
        xrvNearbyContentList.setAdapter(nearByListAdapter);
        xrvNearbyContentList.setLoadingMoreEnabled(true);
        if (titleBean != null) {
            initData();
        }
        xrvNearbyContentList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefersh = true;
                pageIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                isRefersh = false;
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

//        nearByListAdapter.setDeleteNote((id, position) -> deleteNote(id,position));
        return view;
    }

    private void initData() {
        HotspotApiInterfaces hotspotApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("labelTypeId", nearbyId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        map.put("sort", 0);
        map.put("topicId", titleBean.getId());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = hotspotApiInterfaces.noteList(body);
        fragmentRequestData(call, NearByItemListContentBean.class, new RequestResult<NearByItemListContentBean>() {
            @Override
            public void success(NearByItemListContentBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefersh) {
                    listData.clear();
                    listData.addAll(result.getData());
                    xrvNearbyContentList.refreshComplete();
                } else {
                    listData.addAll(result.getData());
                    xrvNearbyContentList.loadMoreComplete();
                }
                nearByListAdapter.addData(listData);
                if (listData.size() == 0) {
                    notData.setVisibility(View.VISIBLE);
                    imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_note_not_data));
                    tvNotData.setText("客官别急，帖子正在赶来~");
                } else {
                    notData.setVisibility(View.GONE);
                }
            }

            @Override
            public void fail() {
                if (isRefersh) {
                    xrvNearbyContentList.refreshComplete();
                } else {
                    xrvNearbyContentList.loadMoreComplete();
                }
            }
        });
    }


//    private void deleteNote(String forumNoteId, int position) {
//        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
//        Map<String, Object> map = new HashMap<>();
//        map.put("forumNoteId", forumNoteId);
//        Call<ResponseBody> call = nearByInterfaces.noteDelete(ParamForNet.put(map));
//        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
//            @Override
//            public void success(Object result, String message) throws Exception {
//                if (listData.size()>position){
//                    listData.remove(position);
//                    nearByListAdapter.addData(listData);
//                }
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEND_NOTE && resultCode == RESULT_SEND_NOTE) {
            isRefersh = true;
            pageIndex = 1;
            initData();
        } else if (requestCode == REQUEST_DELETE_NOTE && resultCode == RESULT_DELETE_NOTE) {
//            isRefersh = true;
//            pageIndex = 1;
//            initData();
            int position = data.getIntExtra("position", -1);
            if (position < listData.size() && position != -1) {
                listData.remove(position);
                nearByListAdapter.addData(listData);
            }
        } else if (requestCode == REQUEST_DELETE_NOTE && resultCode == RESULT_NOTE_DATA) {
            if (data != null) {
                NearByNoteContentBean.DataBean resultData = data.getParcelableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (resultData != null) {
                    if (position >= listData.size()) {
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
