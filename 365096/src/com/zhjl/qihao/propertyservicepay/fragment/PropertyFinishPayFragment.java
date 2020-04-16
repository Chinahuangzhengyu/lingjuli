package com.zhjl.qihao.propertyservicepay.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.propertyservicepay.adapter.PropertyPayOrderAdapter;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayRecordBean;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class PropertyFinishPayFragment extends VolleyBaseFragment {

    @BindView(R.id.rl_choose_room)
    RelativeLayout rlChooseRoom;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_choose_time)
    TextView tvChooseTime;
    @BindView(R.id.xrv_property_pay_detail)
    XRecyclerView xrvPropertyPayDetail;
    Unbinder unbinder;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.tv_choose_finish_time)
    TextView tvChooseFinishTime;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private OptionsPickerView pvOptions;
    private List<String> communityList = new ArrayList<>();
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private int cateId;
    private ArrayList<UserRoomListBean.DataBean> roomList;
    private List<PropertyPayRecordBean.DataBean> data = new ArrayList<>();
    private int pageIndex = 1;
    private int pageSize = 10;
    private String roomId;
    private String startTime = "";
    private String endTime = "";
    private PropertyPayOrderAdapter propertyPayOrderAdapter;
    private boolean isRefresh;
    private int totalPage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static PropertyFinishPayFragment newInstance(int cateId, ArrayList<UserRoomListBean.DataBean> roomList) {
        PropertyFinishPayFragment propertyFinishPayFragment = new PropertyFinishPayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        bundle.putParcelableArrayList("roomList", roomList);
        propertyFinishPayFragment.setArguments(bundle);
        return propertyFinishPayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_finish_pay, container, false);
        }
        unbinder = ButterKnife.bind(this, view);

        xrvPropertyPayDetail.setLayoutManager(new LinearLayoutManager(mContext));
        propertyPayOrderAdapter = new PropertyPayOrderAdapter(mContext, data);
        xrvPropertyPayDetail.setAdapter(propertyPayOrderAdapter);
        xrvPropertyPayDetail.setLoadingMoreEnabled(true);
        xrvPropertyPayDetail.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData();
                isRefresh = true;
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage > pageIndex) {
                    pageIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvPropertyPayDetail.getFootView().setPadding(0, top, 0, bottom);
                    xrvPropertyPayDetail.setNoMore(true);
                }
            }
        });


        format = new SimpleDateFormat("yyyy-MM-dd");
        cateId = getArguments().getInt("cateId");
        roomList = getArguments().getParcelableArrayList("roomList");

        if (roomList != null && roomList.size() > 0) {
            for (int i = 0; i < roomList.size(); i++) {
                StringBuffer sb = new StringBuffer();
                if (roomList.get(i).getSmallCommunityName() != null) {
                    sb.append(roomList.get(i).getSmallCommunityName());
                }
                if (roomList.get(i).getRoomName() != null) {
                    sb.append(roomList.get(i).getRoomName());
                }
                if (mSession.getSmallCommunityName() != null && roomList.get(i).getSmallCommunityName() != null) {
                    if (mSession.getSmallCommunityName().equals(roomList.get(i).getSmallCommunityName())) {
                        tvChoose.setText(sb.toString());
                        roomId = roomList.get(i).getRoomId();
                    }
                }
                communityList.add(sb.toString());
            }
        }
        rlLoading.setVisibility(View.VISIBLE);
        initData();
        return view;

    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cateId", cateId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("roomId", roomId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = propertyPayInterface.propertyRecordList(body);
        fragmentRequestData(call, PropertyPayRecordBean.class, new RequestResult<PropertyPayRecordBean>() {
            @Override
            public void success(PropertyPayRecordBean result, String message) {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    data.addAll(result.getData());
                    propertyPayOrderAdapter.addData(data);
                    xrvPropertyPayDetail.refreshComplete();
                } else {
                    data.addAll(result.getData());
                    propertyPayOrderAdapter.addData(data);
                    xrvPropertyPayDetail.loadMoreComplete();
                }
                if (totalPage > 1) {
                    xrvPropertyPayDetail.setLoadingMoreEnabled(true);
                }
                if (totalPage == 0) {
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvPropertyPayDetail.refreshComplete();
                } else {
                    xrvPropertyPayDetail.loadMoreComplete();
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

    @OnClick({R.id.rl_choose_room, R.id.tv_choose_time, R.id.tv_choose_finish_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_room:
                initChooseRoomPop();
                break;
            case R.id.tv_choose_time:
                initChooseTimePop(tvChooseTime);
                break;
            case R.id.tv_choose_finish_time:
                initChooseTimePop(tvChooseFinishTime);
                break;
        }
    }

    private void initChooseTimePop(final TextView tvTime) {
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String str = PropertyFinishPayFragment.this.format.format(date);
                tvTime.setText(str);
                tvTime.setTextColor(Color.parseColor("#1f1f1f"));
                if (tvTime == tvChooseTime) {
                    startTime = str;
                } else if (tvTime == tvChooseFinishTime) {
                    endTime = str;
                }
                if (!startTime.equals("") && !endTime.equals("")) {
                    isRefresh = true;
                    initData();
                }
            }
        }).setSubmitColor(Color.parseColor("#1f1f1f"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .build();
        pvTime.show();
    }

    private void initChooseRoomPop() {
        //条件选择器
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (communityList.size()>0){
                    tvChoose.setText(communityList.get(options1));
                    isRefresh = true;
                    if (roomList != null && roomList.size() > 0) {
                        roomId = roomList.get(options1).getRoomId();
                        initData();
                    }
                }

            }
        }).setLayoutRes(R.layout.pop_choose_room, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tvSure = v.findViewById(R.id.tv_sure);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.returnData();
                        pvOptions.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.dismiss();
                    }
                });
            }
        }).build();
        pvOptions.setPicker(communityList);
        pvOptions.show();
    }
}
