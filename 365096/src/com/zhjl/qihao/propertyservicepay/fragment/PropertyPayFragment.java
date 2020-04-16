package com.zhjl.qihao.propertyservicepay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.freshshop.view.MarginDecoration;
import com.zhjl.qihao.propertyservicepay.activity.ConfirmOrderActivity;
import com.zhjl.qihao.propertyservicepay.adapter.PropertyPayListAdapter;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayInfoBean;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.math.BigDecimal;
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

public class PropertyPayFragment extends VolleyBaseFragment {

    @BindView(R.id.rl_choose_room)
    RelativeLayout rlChooseRoom;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.btn_pay)
    Button btnPay;
    Unbinder unbinder;
    @BindView(R.id.rv_pay_time)
    RecyclerView lvPayTime;
    @BindView(R.id.tv_pay_last_time)
    TextView tvPayLastTime;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_sum_money)
    TextView tvSumMoney;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    @BindView(R.id.tv_house_area)
    TextView tvHouseArea;
    @BindView(R.id.sv_property_pay)
    NestedScrollView svPropertyPay;
    @BindView(R.id.tv_pay_detail)
    TextView tvPayDetail;
    private View view;
    private boolean isClick = false;
    private int cateId;
    private List<PropertyPayInfoBean.DataBean.StandardsBean> list = new ArrayList<>();
    private int packageId;
    private PropertyPayInfoBean.DataBean data;
    private OptionsPickerView pvOptions;
    private List<String> communityList = new ArrayList<>();
    private String totalAmount;
    private String residentId;
    private String roomId;
    private ArrayList<UserRoomListBean.DataBean> roomList;
    private double[] NbNumber;
    private BigDecimal giveNbCount;
    private BigDecimal losePrice;
    private PropertyPayListAdapter propertyPayListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static PropertyPayFragment newInstance(int cateId, ArrayList<UserRoomListBean.DataBean> roomList) {
        PropertyPayFragment propertyPayFragment = new PropertyPayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", cateId);
        bundle.putParcelableArrayList("roomList", roomList);
        propertyPayFragment.setArguments(bundle);
        return propertyPayFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_pay, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        svPropertyPay.setNestedScrollingEnabled(false);
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
                        tvHouseArea.setText(roomList.get(i).getUsableArea() + "平方米");
                        residentId = roomList.get(i).getResidentId();
                        roomId = roomList.get(i).getRoomId();
                    } else {
                        tvChoose.setText(roomList.get(0).getSmallCommunityName() + roomList.get(0).getRoomName());
                        tvHouseArea.setText(roomList.get(0).getUsableArea() + "平方米");
                        residentId = roomList.get(0).getResidentId();
                        roomId = roomList.get(0).getRoomId();
                    }
                }
                communityList.add(sb.toString());
            }
        }

        rlLoading.setVisibility(View.VISIBLE);
        initData();
        lvPayTime.setLayoutManager(new GridLayoutManager(mContext, 2));
        propertyPayListAdapter = new PropertyPayListAdapter(mContext, list);
        MarginDecoration decoration = new MarginDecoration(mContext);
        decoration.setMargin(8);
        lvPayTime.addItemDecoration(decoration);
        lvPayTime.setAdapter(propertyPayListAdapter);
        lvPayTime.setFocusableInTouchMode(false);
        propertyPayListAdapter.setSetOnItemClickListener(new PropertyPayListAdapter.SetOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        LinearLayout llItem = (LinearLayout) lvPayTime.getChildAt(i);
                        LinearLayout item = (LinearLayout) llItem.getChildAt(0);
                        item.setBackground(ContextCompat.getDrawable(mContext, R.drawable.stroke_solid_circle_green_6));
                        BigDecimal bigDecimal = new BigDecimal(list.get(position).getProportion());
                        BigDecimal lose = new BigDecimal(100);
                        BigDecimal price = bigDecimal.multiply(lose).setScale(0, BigDecimal.ROUND_HALF_UP);
                        tvPayDetail.setText("额外赠送" + price + "%的N币");
                        if (list.get(position).getProportion() == 0) {
                            tvPayDetail.setVisibility(View.GONE);
                        } else {
                            tvPayDetail.setVisibility(View.VISIBLE);
                        }
                        packageId = list.get(position).getId();
                        initCalculateData();
                    } else {
                        LinearLayout llItem = (LinearLayout) lvPayTime.getChildAt(i);
                        LinearLayout item = (LinearLayout) llItem.getChildAt(0);
                        item.setBackground(ContextCompat.getDrawable(mContext, R.drawable.stroke_solid_circle_bbbbbb_6));
                    }
                }
            }
        });
        return view;
    }

    private void initCalculateData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cateId", cateId);
        map.put("packageId", packageId);
        map.put("residentId", residentId);
        map.put("roomId", roomId);
        map.put("sourceType", 0);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = propertyPayInterface.propertyCalculate(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                try {
                    String str = (String) result;
                    JSONObject object = new JSONObject(str);
                    JSONObject data = object.optJSONObject("data");
                    totalAmount = data.optString("totalAmount");
                    tvSumMoney.setText("¥" + totalAmount);
                    btnPay.setText("立即缴费¥" + totalAmount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cateId", cateId);
        map.put("residentId", residentId);
        map.put("roomId", roomId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = propertyPayInterface.getPropertyPayInfo(body);
        fragmentRequestData(call, PropertyPayInfoBean.class, new RequestResult<PropertyPayInfoBean>() {
            @Override
            public void success(PropertyPayInfoBean result, String message) {
                list.clear();
                llPay.removeAllViews();
                if (result.getData() != null) {
                    data = result.getData();
                    tvPayLastTime.setText(data.getLastPaymentTime());
                    packageId = data.getStandards().get(0).getId();
                    list.addAll(data.getStandards());
                    residentId = data.getResidentId();
                    for (int i = 0; i < data.getCostItems().size(); i++) {
                        View view = View.inflate(mContext, R.layout.add_pay_item, null);
                        TextView tvPayName = view.findViewById(R.id.tv_pay_name);
                        TextView tvPrice = view.findViewById(R.id.tv_price);
                        tvPayName.setText(data.getCostItems().get(i).getItemName());
                        tvPrice.setText(data.getCostItems().get(i).getCostMoney());
                        llPay.addView(view);
                    }
                    BigDecimal bigDecimal = new BigDecimal(list.get(0).getProportion());
                    if (list.get(0).getProportion() == 0) {
                        tvPayDetail.setVisibility(View.GONE);
                    }
                    BigDecimal lose = new BigDecimal(100);
                    BigDecimal price = bigDecimal.multiply(lose).setScale(0, BigDecimal.ROUND_HALF_UP);
                    tvPayDetail.setText("额外赠送" + price + "%的N币");
                    propertyPayListAdapter.addData(list);
                    initCalculateData();
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_choose_room, R.id.tv_pay_time, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_room:
                if (roomList != null && roomList.size() > 0) {
                    initChooseRoomPop();
                } else {
                    Toast.makeText(mContext, "暂无选择小区！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_pay_time:
                break;
            case R.id.btn_pay:
                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra("isCarPay", false);
                intent.putExtra("cateId", cateId);
                intent.putExtra("orderMoney", totalAmount);
                intent.putExtra("packageId", packageId);
                intent.putExtra("roomId", roomId);
                intent.putExtra("serviceOrder", data);
                intent.putExtra("residentId", residentId);
                startActivity(intent);
                break;
        }
    }

    private void initChooseRoomPop() {
        /** 隐藏软键盘 **/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        //条件选择器
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (communityList.size() > 0) {
                    tvChoose.setText(communityList.get(options1));
                    residentId = roomList.get(options1).getResidentId();
                    roomId = roomList.get(options1).getRoomId();
                    tvHouseArea.setText(roomList.get(options1).getUsableArea() + "平方米");
                    initData();
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
