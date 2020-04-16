package com.zhjl.qihao.propertyRepresentations.fragment;

import android.content.Context;
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
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.propertyRepresentations.adapter.EventNotificationAdapter;
import com.zhjl.qihao.propertyRepresentations.api.ExposureInterface;
import com.zhjl.qihao.propertyRepresentations.bean.ExposureListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 事件通报
 */
public class EventNotificationFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_event_notification_list)
    XRecyclerView xrvEventNotificationList;
    Unbinder unbinder;
    @BindView(R.id.img_not_data)
    ImageView imgNotData;
    @BindView(R.id.tv_not_data)
    TextView tvNotData;
    @BindView(R.id.not_data)
    LinearLayout notData;
    private View view;
    private EventNotificationAdapter eventNotificationAdapter;
    private int typeId;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh;
    private int totalPage;
    private List<ExposureListBean.DataBean> data = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static EventNotificationFragment getInstance(int typeId) {
        EventNotificationFragment fragment = new EventNotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_event_notification, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        typeId = getArguments().getInt("typeId");
        notData.setVisibility(View.GONE);
        initData();
        xrvEventNotificationList.setLayoutManager(new LinearLayoutManager(mContext));
        eventNotificationAdapter = new EventNotificationAdapter(mContext, data);
        xrvEventNotificationList.setAdapter(eventNotificationAdapter);
        xrvEventNotificationList.setLoadingMoreEnabled(true);
        xrvEventNotificationList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                isRefresh = true;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage < pageIndex) {
                    pageIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvEventNotificationList.getFootView().setPadding(0, top, 0, bottom);
                    xrvEventNotificationList.setNoMore(true);
                }
            }
        });
        return view;
    }

    private void initData() {
        ExposureInterface exposureInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ExposureInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("typeId", typeId);
        Call<ResponseBody> call = exposureInterface.exposureMessageList(map);
        fragmentRequestData(call, ExposureListBean.class, new RequestResult<ExposureListBean>() {
            @Override
            public void success(ExposureListBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    data = result.getData();
                    xrvEventNotificationList.refreshComplete();
                } else {
                    data.addAll(result.getData());
                    xrvEventNotificationList.loadMoreComplete();
                }
                eventNotificationAdapter.addData(data);
                if (data.size() == 0) {
                    notData.setVisibility(View.VISIBLE);
                    imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_note_not_data));
                    tvNotData.setText("客官别急，事件正在赶来~");
                }
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvEventNotificationList.refreshComplete();
                } else {
                    xrvEventNotificationList.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
