package com.zhjl.qihao.propertyservicepay.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.zhjl.qihao.propertyservicepay.bean.CarBean;
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

public class CarPayFragment extends VolleyBaseFragment {

    @BindView(R.id.rl_car_choose_room)
    RelativeLayout rlCarChooseRoom;
    @BindView(R.id.rb_car_number)
    RadioButton rbCarNumber;
    @BindView(R.id.rb_car_number2)
    RadioButton rbCarNumber2;
    @BindView(R.id.rg_car_choose)
    RadioGroup rgCarChoose;
    @BindView(R.id.et_car_number)
    EditText etCarNumber;
    @BindView(R.id.btn_car_pay)
    Button btnCarPay;
    Unbinder unbinder;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.tv_sum_money)
    TextView tvSumMoney;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_pay_last_time)
    TextView tvPayLastTime;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    @BindView(R.id.rv_car_pay_time)
    RecyclerView rvCarPayTime;
    @BindView(R.id.sv_car_pay)
    NestedScrollView svCarPay;
    @BindView(R.id.tv_pay_detail)
    TextView tvPayDetail;
    private View view;
    private List<PropertyPayInfoBean.DataBean.StandardsBean> list = new ArrayList<>();
    private boolean isClick = false;
    private OptionsPickerView pvOptions;
    private List<String> communityList = new ArrayList<>();
    private int cateId;
    private ArrayList<UserRoomListBean.DataBean> roomList;
    private PropertyPayInfoBean.DataBean data;
    private int packageId;
    private String residentId;
    private String totalAmount;
    private boolean isCarNumber = true;  //判断是否是车牌号
    private ArrayList<CarBean> carList = new ArrayList<>();
    private String[] NbNumber;
    private String roomId;
    private BigDecimal losePrice;
    private PropertyPayListAdapter propertyPayListAdapter;

    public static CarPayFragment getInstance(int carId, ArrayList<UserRoomListBean.DataBean> roomList) {
        CarPayFragment carPayFragment = new CarPayFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("roomList", roomList);
        bundle.putInt("carId", carId);
        carPayFragment.setArguments(bundle);
        return carPayFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_car_pay, container, false);

        }
        unbinder = ButterKnife.bind(this, view);
        svCarPay.setNestedScrollingEnabled(false);
        cateId = getArguments().getInt("carId");
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
                        residentId = roomList.get(i).getResidentId();
                        roomId = roomList.get(i).getRoomId();
                    } else {
                        tvChoose.setText(roomList.get(0).getSmallCommunityName() + roomList.get(0).getRoomName());
                        residentId = roomList.get(0).getResidentId();
                        roomId = roomList.get(0).getRoomId();
                    }
                }
                communityList.add(sb.toString());
            }
        }


        rgCarChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etCarNumber.getText().clear();
                switch (checkedId) {
                    case R.id.rb_car_number:
                        etCarNumber.setHint("点击输入车牌号");
                        isCarNumber = true;
                        break;
                    case R.id.rb_car_number2:
                        isCarNumber = false;
                        etCarNumber.setHint("点击输入车位号");
                        break;
                }
            }
        });
        rlLoading.setVisibility(View.VISIBLE);
        initData();
        rvCarPayTime.setLayoutManager(new GridLayoutManager(mContext, 2));
        propertyPayListAdapter = new PropertyPayListAdapter(mContext, list);
        MarginDecoration decoration = new MarginDecoration(mContext);
        decoration.setMargin(8);
        rvCarPayTime.addItemDecoration(decoration);
        rvCarPayTime.setAdapter(propertyPayListAdapter);
        rvCarPayTime.setFocusableInTouchMode(false);
        propertyPayListAdapter.setSetOnItemClickListener(new PropertyPayListAdapter.SetOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        LinearLayout llItem = (LinearLayout) rvCarPayTime.getChildAt(i);
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
                        LinearLayout llItem = (LinearLayout) rvCarPayTime.getChildAt(i);
                        LinearLayout item = (LinearLayout) llItem.getChildAt(0);
                        item.setBackground(ContextCompat.getDrawable(mContext, R.drawable.stroke_solid_circle_bbbbbb_6));
                    }
                }
            }
        });
        return view;
    }

    private void initData() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cateId", cateId);
        map.put("roomId", roomId);
        map.put("residentId", residentId);
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
                    btnCarPay.setText("立即缴费¥" + totalAmount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_car_choose_room, R.id.btn_car_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_car_choose_room:
                if (roomList != null && roomList.size() > 0) {
                    initChooseRoomPop();
                } else {
                    Toast.makeText(mContext, "暂无选择小区！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_car_pay:
                if (etCarNumber.getText().toString().trim().length() > 0) {
                    Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                    intent.putExtra("isCarPay", true);
                    intent.putExtra("cateId", cateId);
                    intent.putExtra("orderMoney", totalAmount);
                    intent.putExtra("roomId", roomId);
                    intent.putExtra("packageId", packageId);
                    intent.putExtra("serviceOrder", data);
                    intent.putExtra("residentId", residentId);
                    carList.clear();
                    if (isCarNumber) {
                        carList.add(new CarBean("车牌号", etCarNumber.getText().toString().trim()));
                    } else {
                        carList.add(new CarBean("车位号", etCarNumber.getText().toString().trim()));
                    }
                    intent.putParcelableArrayListExtra("carList", carList);
                    startActivity(intent);
                } else {
                    if (isCarNumber) {
                        Toast.makeText(mContext, "请输入完整的车牌号", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "请输入完整的车位号", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    private void initChooseRoomPop() {
        //条件选择器
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (communityList.size() > 0) {
                    tvChoose.setText(communityList.get(options1));
                    residentId = roomList.get(options1).getResidentId();
                    roomId = roomList.get(options1).getRoomId();
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
