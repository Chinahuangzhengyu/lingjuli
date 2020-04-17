package com.zhjl.qihao.homemanage.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.homemanage.adapter.HomeManageAdapter;
import com.zhjl.qihao.homemanage.api.HomeManageInterface;
import com.zhjl.qihao.homemanage.bean.HomeManageListBean;
import com.zhjl.qihao.homemanage.fragment.SelectRoomFragment;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.util.NewHeaderBar;
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
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.homemanage.activity.HomeManageDetailActivity.RESULT_HOME_AGREE_CODE;

public class HomeManageActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.xrv_home_manage)
    XRecyclerView xrvHomeManage;
    private HomeManageAdapter homeManageAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String roomId = "";
    private ArrayList<UserRoomListBean.DataBean> rooms = new ArrayList<>();
    private boolean isRefresh;
    private OptionsPickerView pvOptions;
    private List<String> repairAddressLists = new ArrayList<>();
    private int currentPosition = 0;
    private int totalPage;
    private List<HomeManageListBean.DataBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manage);
        ButterKnife.bind(this);
        roomId = mSession.getRoomId();
        NewHeaderBar.createCommomBack(this, "房屋管理", this);
        initRightView();
        initRoom();
        initData();
        xrvHomeManage.setLayoutManager(new LinearLayoutManager(mContext));
        homeManageAdapter = new HomeManageAdapter(this, data);
        xrvHomeManage.setAdapter(homeManageAdapter);
        xrvHomeManage.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageIndex = 1;
                initData();
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
                    xrvHomeManage.getFootView().setPadding(0, top, 0, bottom);
                    xrvHomeManage.setNoMore(true);
                }
            }
        });
    }

    private void initRoom() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Call<ResponseBody> call = propertyPayInterface.userRoomList(ParamForNet.put(new HashMap<>()));
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                rooms.clear();
                rooms.addAll(result.getData());
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initAddressPop() {
        SelectRoomFragment selectRoomFragment = SelectRoomFragment.getInstance(rooms);
        selectRoomFragment.show(getSupportFragmentManager(), "5");
        selectRoomFragment.setSetOnItemClickLintener(roomId -> {
            this.roomId = roomId;
            isRefresh = true;
            pageIndex = 1;
            initData();
        });
    }

    private void initData() {
        HomeManageInterface manageInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(HomeManageInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("roomId", roomId);
        Call<ResponseBody> call = manageInterface.applyList(map);
        activityRequestData(call, HomeManageListBean.class, new RequestResult<HomeManageListBean>() {
            @Override
            public void success(HomeManageListBean result, String message) throws Exception {
                totalPage = result.getTotalPage();
                if (isRefresh) {
                    data.clear();
                    xrvHomeManage.refreshComplete();
                } else {
                    xrvHomeManage.loadMoreComplete();
                }
                data.addAll(result.getData());
                homeManageAdapter.addData(data);
            }

            @Override
            public void fail() {
                if (isRefresh) {
                    xrvHomeManage.refreshComplete();
                } else {
                    xrvHomeManage.loadMoreComplete();
                }
            }
        });
    }


    /**
     * 设置头部右边布局
     */
    private void initRightView() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        tvRight.setText(mSession.getSmallCommunityName());
        tvRight.setTextSize(16);
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_home_manage_select);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRight.setCompoundDrawables(null, null, drawable, null);
        tvRight.setCompoundDrawablePadding(Utils.dip2px(mContext, 4));
        tvRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_f2f2f2_6));
        tvRight.setPadding(Utils.dip2px(mContext, 8), Utils.dip2px(mContext, 6), Utils.dip2px(mContext, 8), Utils.dip2px(mContext, 6));
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                initAddressPop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_HOME_AGREE_CODE) {
            if (data != null) {
//                int position = data.getIntExtra("position", -1);
//                if (position != -1) {
//                    this.data.get(position).setStatus(1);
//                    homeManageAdapter.addData(this.data);
//                }
                isRefresh = true;
                pageIndex = 1;
                initData();
            }
        }
    }
}
