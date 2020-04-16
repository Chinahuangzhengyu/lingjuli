package com.zhjl.qihao.abrefactor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.adapter.HotspotListAdapter;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.abrefactor.bean.NewHotspotHistoryBean;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.hotspot.api.HotspotApiInterfaces;
import com.zhjl.qihao.nearbyinteraction.activity.NearByListActivity;
import com.zhjl.qihao.nearbyinteraction.activity.SearchNoteActivity;
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
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class NewHotspotFragment extends VolleyBaseFragment {
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tv_send_note)
    TextView tvSendNote;
    @BindView(R.id.vp_hotspot_interaction)
    ViewPager vpHotspotInteraction;
    @BindView(R.id.xrv_hotspot_content_list)
    XRecyclerView xrvHotspotContentList;
    Unbinder unbinder;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.ll_search_note)
    LinearLayout llSearchNote;
    private View view;
    private ArrayList<NearByTypeBean.DataBean> data = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<NewHotspotHistoryBean.DataBean> listData = new ArrayList<>();
    private int totalPage;
    private boolean isRefresh;
    private HotspotListAdapter nearByListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_new_hotspot, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initHead();
        initData();
        vpHotspotInteraction.setPageMargin(Utils.dip2px(mContext, 20));
        xrvHotspotContentList.setLayoutManager(new LinearLayoutManager(mContext));
        nearByListAdapter = new HotspotListAdapter(mContext, listData);
        xrvHotspotContentList.setAdapter(nearByListAdapter);
        xrvHotspotContentList.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                    xrvHotspotContentList.getFootView().setPadding(0, top, 0, bottom);
                    xrvHotspotContentList.setNoMore(true);
                }
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private void initData() {
        HotspotApiInterfaces hotspotApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(HotspotApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = hotspotApiInterfaces.hotspotList(body);
        fragmentRequestData(call, NewHotspotHistoryBean.class, new RequestResult<NewHotspotHistoryBean>() {
            @Override
            public void success(NewHotspotHistoryBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    listData.clear();
                    listData.addAll(result.getData());
                    xrvHotspotContentList.refreshComplete();
                } else {
                    listData.addAll(result.getData());
                    xrvHotspotContentList.loadMoreComplete();
                }
                nearByListAdapter.addData(listData);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvHotspotContentList.refreshComplete();
                } else {
                    xrvHotspotContentList.loadMoreComplete();
                }
            }
        });
    }

    private void initHead() {
        MainApiInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", 2);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = apiInterfaces.nearbyType(body);
        fragmentRequestData(call, NearByTypeBean.class, new RequestResult<NearByTypeBean>() {
            @Override
            public void success(NearByTypeBean result, String message) {
                data.clear();
                if (result.getData().size() != 0) {
                    data = (ArrayList<NearByTypeBean.DataBean>) result.getData();
                    vpHotspotInteraction.setAdapter(new HotspotPagerAdapter());
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail() {
                Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class HotspotPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public float getPageWidth(int position) {
            return 0.18f;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 64), Utils.dip2px(mContext, 64));
            view.setLayoutParams(lp);
            PictureHelper.setImageView(mContext, data.get(position).getIcon(), view, R.drawable.img_loading);
            container.addView(view);
            view.setOnClickListener(v -> {      //邻里互动分类点击
                Intent intent = new Intent(mContext, NearByListActivity.class);
                intent.putExtra("nearbyName", data.get(position).getLabelName());
                intent.putExtra("nearbyId", data.get(position).getId());
                startActivity(intent);
            });
            return view;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_send_note,R.id.ll_search_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_note:
                SendNoteTypeFragment sendNoteTypeFragment = SendNoteTypeFragment.getInstance(data);
                sendNoteTypeFragment.show(getChildFragmentManager(), "5");
                break;
            case R.id.ll_search_note:
                Intent intent = new Intent(mContext, SearchNoteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
