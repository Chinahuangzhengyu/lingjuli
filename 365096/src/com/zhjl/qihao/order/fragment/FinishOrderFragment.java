package com.zhjl.qihao.order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.order.adapter.AllOrderAdapter;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.AllOrderListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

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

public class FinishOrderFragment extends VolleyBaseFragment {
    @BindView(R.id.xrv_all_order)
    XRecyclerView xrvAllOrder;
    Unbinder unbinder;
    @BindView(R.id.not_data)
    LinearLayout notData;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;

    private int status = 3;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean isFinish;
    private AllOrderAdapter allOrderAdapter;
    private int totalPage;
    private List<AllOrderListBean.OrderListBean> orderList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_all_order, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvAllOrder.setLayoutManager(new LinearLayoutManager(mContext));
        allOrderAdapter = new AllOrderAdapter(getActivity(), orderList);
        xrvAllOrder.setAdapter(allOrderAdapter);
        xrvAllOrder.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                isLoadMore = false;
                pageIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                isLoadMore = true;
                pageIndex++;
                if (pageIndex <= totalPage) {
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvAllOrder.getFootView().setPadding(0, top, 0, bottom);
                    xrvAllOrder.setNoMore(true);
                }
            }
        });
        allOrderAdapter.setGetOrderStatus(new AllOrderAdapter.getOrderStatus() {
            @Override
            public void getStatus(int type) {
                isRefresh = true;
                pageIndex = 1;
                initData();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        rlLoading.setVisibility(View.VISIBLE);
        isRefresh = true;
        isLoadMore = false;
        pageIndex = 1;
        initData();
    }

    private void initData() {
        OrderApiInterface orderApiInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("status", status);
        map.put("page", pageIndex);
        map.put("limit", pageSize);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderApiInterface.orderList(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                if (status) {
                    Gson gson = new Gson();
                    AllOrderListBean orderListBean = gson.fromJson(string, AllOrderListBean.class);
                    AllOrderListBean.ListInfoBean listInfo = orderListBean.getList_info();
                    totalPage = listInfo.getTotal_page();
                    if (totalPage > 1) {
                        xrvAllOrder.setLoadingMoreEnabled(true);
                    }
                    if (isRefresh) {
                        orderList.clear();
                        xrvAllOrder.refreshComplete();
                    }
                    if (isLoadMore) {
                        xrvAllOrder.loadMoreComplete();
                    }
                    orderList.addAll(orderListBean.getOrder_list());
                    allOrderAdapter.addData(orderList);
                    if (orderList.size() == 0) {
                        notData.setVisibility(View.VISIBLE);
                    } else {
                        notData.setVisibility(View.GONE);
                    }
                } else {
                    int type = object.optInt("type");
                    if (type == 0) {
                        Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    } else if (type == 1) {
                        Utils.loginPopWindow(getActivity());
                    } else {
                        Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                }
                if (isRefresh) {
                    xrvAllOrder.refreshComplete();
                }
                if (isLoadMore) {
                    xrvAllOrder.loadMoreComplete();
                }
                isFinish = true;
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvAllOrder.refreshComplete();
                }
                if (isLoadMore) {
                    xrvAllOrder.loadMoreComplete();
                }
                isFinish = true;
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
