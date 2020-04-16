package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.freshshop.adapter.OrderListAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopOrderBean;
import com.zhjl.qihao.freshshop.fragment.AddressTypeFragment;
import com.zhjl.qihao.freshshop.view.CustomViewPager;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity;
import com.zhjl.qihao.util.Utils;
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
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.freshshop.activity.ChooseDateActivity.TIME_DATA_RESULT;
import static com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity.BINDING_ADDRESS_SUCCESS;

public class ShopConfirmOrderActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_status_color)
    TextView tvStatusColor;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_confirm_order)
    TextView tvConfirmOrder;
    @BindView(R.id.rl_delivery_time)
    RelativeLayout rlDeliveryTime;
    @BindView(R.id.rl_cash_coupon)
    RelativeLayout rlCashCoupon;
    @BindView(R.id.tv_shop_money)
    TextView tvShopMoney;
    @BindView(R.id.tv_delivery_money)
    TextView tvDeliveryMoney;
    @BindView(R.id.tv_sum_money)
    TextView tvSumMoney;
    @BindView(R.id.tab_address_type)
    TabLayout tabAddressType;
    @BindView(R.id.sb_lose_money)
    SwitchButton sbLoseMoney;
    @BindView(R.id.vp_fragment)
    CustomViewPager vpFragment;
    @BindView(R.id.tv_lose_money)
    TextView tvLoseMoney;
    @BindView(R.id.tv_choose_time)
    TextView tvChooseTime;
    @BindView(R.id.view_left)
    View viewLeft;
    @BindView(R.id.view_right)
    View viewRight;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_time_name)
    TextView tvTimeName;
    @BindView(R.id.et_memo_content)
    EditText etMemoContent;
    @BindView(R.id.tv_order_tips)
    TextView tvOrderTips;
    @BindView(R.id.tv_three)
    TextView tvThree;
    private String[] addressType = {"送货到家", "门店自提", "小区自提"};
    private List<ShopOrderBean.ProductsBean> products = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private BigDecimal sumMoney;
    private boolean isLose;
    private BigDecimal loseMoney;
    private BigDecimal currentMoney;
    private static final int TIME_DATA_REQUEST = 0x557;
    private static final int BINDING_ADDRESS = 0x166;
    private AddressTypeFragment addressFragment;
    private String shop_id = "";
    private String room_id = "";
    private String collect_id = "";
    private ShopOrderBean order;
    private String sendType;
    private boolean formCar;
    private PopupWindow messagePopWindow;
    private BigDecimal delivery;
    private BigDecimal sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_confirm_order);
        ButterKnife.bind(this);
        sendType = getIntent().getStringExtra("sendType");
        order = getIntent().getParcelableExtra("order");
        formCar = getIntent().getBooleanExtra("formCar", false);
        if (order != null) {
            products = order.getProducts();
            if (order.getTotal_points_price().equals("0.00")||order.getTotal_price().equals("0")) {
                rlCashCoupon.setVisibility(View.GONE);
            } else {
                rlCashCoupon.setVisibility(View.VISIBLE);
            }
            tvLoseMoney.setText("N币/可抵¥" + order.getTotal_points_price());
            tvShopMoney.setText("¥" + order.getTotal_price());
            loseMoney = new BigDecimal(order.getTotal_points_price());
            sum = new BigDecimal(order.getTotal_price()).setScale(2, BigDecimal.ROUND_HALF_UP);
            delivery = new BigDecimal(order.getFreight()).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (sendType.equals(addressType[1]) || sendType.equals(addressType[2])) {      //门店自提 小区自提  不要运费
                sumMoney = sum;
                currentMoney = sumMoney;
                tvDeliveryMoney.setText("¥0.00");
            } else if (sendType.equals(addressType[0])){    //送货到家，小于38需要运费
                if (sum.doubleValue() < 38) {
                    sumMoney = sum.add(delivery).setScale(2, BigDecimal.ROUND_HALF_UP);
                    currentMoney = sumMoney;
                    tvDeliveryMoney.setText("¥" + order.getFreight());
                } else {
                    sumMoney = sum;
                    currentMoney = sumMoney;
                    tvDeliveryMoney.setText("¥0.00");
                }
            }
            tvSumMoney.setText("¥" + sumMoney);
            btnSubmit.setText("提交订单¥" + sumMoney);
            if (order.getFreight_tips() != null && !order.getFreight_tips().equals("")) {
                tvOrderTips.setVisibility(View.VISIBLE);
                tvOrderTips.setText(order.getFreight_tips());
            }
        }
        rvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        rvOrderList.setNestedScrollingEnabled(false);
        rvOrderList.setAdapter(new OrderListAdapter(this, products));

        for (int i = 0; i < addressType.length; i++) {
            addressFragment = AddressTypeFragment.getInstance(addressType[i]);
            fragmentList.add(addressFragment);
        }
        vpFragment.setAdapter(new AddressTypeAdapter(getSupportFragmentManager()));
        vpFragment.setOffscreenPageLimit(addressType.length);
//        tabAddressType.setupWithViewPager(vpFragment);
        for (int i = 0; i < tabAddressType.getTabCount(); i++) {
            tabAddressType.getTabAt(i).setCustomView(R.layout.address_type);
            TextView textView = tabAddressType.getTabAt(i).getCustomView().findViewById(R.id.tv_address_type_name);
            LinearLayout llBackground = tabAddressType.getTabAt(i).getCustomView().findViewById(R.id.ll_background);
            textView.setText(addressType[i]);
            if (sendType.equals(addressType[i])) {
                vpFragment.setCurrentItem(i, false);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                llBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (i == 0) {
                    tvOne.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_left_6));
                    tvTwo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
                    vpFragment.setCurrentItem(0, false);
                    tvTwo.setTypeface(Typeface.DEFAULT);
                    tvOne.setTypeface(Typeface.DEFAULT_BOLD);
                    viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_left_6));
                    viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
                } else {
                    tvOne.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
                    tvTwo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_right_6));
                    vpFragment.setCurrentItem(1, false);
                    tvTwo.setTypeface(Typeface.DEFAULT_BOLD);
                    tvOne.setTypeface(Typeface.DEFAULT);
                    viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
                    viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_right_6));
                }
            } else {
                textView.setTypeface(Typeface.DEFAULT);
                llBackground.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
        }
        if (sendType.equals("送货到家")){
            sendHome();
        }else if (sendType.equals("门店自提")){
            shopGet();
        }else if (sendType.equals("小区自提")){
            collectGet();
        }
        tvOne.setOnClickListener(v -> {
            sendHome();
        });
        tvTwo.setOnClickListener(v -> shopGet());
        tvThree.setOnClickListener(v -> collectGet());
//        tabAddressType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                vpFragment.setCurrentItem(tab.getPosition(),false);
//                if (tab.getCustomView() != null) {
//                    TextView textView = tab.getCustomView().findViewById(R.id.tv_address_type_name);
//                    LinearLayout llBackground = tab.getCustomView().findViewById(R.id.ll_background);
//                    textView.setTypeface(Typeface.DEFAULT_BOLD);
//                    if (textView.getText().toString().equals("门店自提")) {
//                        llBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                        if (sum.doubleValue() < 38) {       //总价小于38需要减去运费
//                            sumMoney = currentMoney.subtract(delivery).setScale(2, BigDecimal.ROUND_HALF_UP);
//                            currentMoney = sumMoney;
//                            tvDeliveryMoney.setText("¥0.00");
//                            tvSumMoney.setText("¥" + sumMoney);
//                            btnSubmit.setText("提交订单¥" + sumMoney);
//                        }
//                    } else if (textView.getText().toString().equals("送货到家")){
//                        viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_left_6));
//                        viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
//                        llBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                        if (sum.doubleValue() < 38) {       //总价小于38加上运费
//                            sumMoney = currentMoney.add(delivery).setScale(2, BigDecimal.ROUND_HALF_UP);
//                            currentMoney = sumMoney;
//                            tvDeliveryMoney.setText("¥" + delivery);
//                            tvSumMoney.setText("¥" + sumMoney);
//                            btnSubmit.setText("提交订单¥" + sumMoney);
//                        }
//                        if (room_id.equals("")) {
//                            initPop();
//                        }
//                    }else if (textView.getText().toString().equals("小区自提")){
//                        viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
//                        viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_right_6));
//                        llBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    }
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                if (tab.getCustomView() != null) {
//                    TextView textView = tab.getCustomView().findViewById(R.id.tv_address_type_name);
//                    LinearLayout llBackground = tab.getCustomView().findViewById(R.id.ll_background);
//                    textView.setTypeface(Typeface.DEFAULT);
//                    if (textView.getText().toString().equals("送货到家")) {
//                        viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
//                        viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_right_6));
//                        llBackground.setBackgroundColor(Color.parseColor("#F2F2F2"));
//                    } else if (textView.getText().toString().equals("小区自提")){
//                        viewLeft.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_left_6));
//                        viewRight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
//                        llBackground.setBackgroundColor(Color.parseColor("#F2F2F2"));
//                    }else {
//                        llBackground.setBackgroundColor(Color.parseColor("#F2F2F2"));
//                    }
//                }
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        AddressTypeFragment fragment = (AddressTypeFragment) fragmentList.get(0);   //门店自提
//        AddressTypeFragment fragment1 = (AddressTypeFragment) fragmentList.get(1); //送货到家
//        AddressTypeFragment fragment2 = (AddressTypeFragment) fragmentList.get(2);  //小区自提
//        fragment.setSetAddressChoose((id, type) -> shop_id = id);
//        fragment1.setSetAddressChoose((id, type) -> {
//            room_id = id;
//            if (room_id.equals("") && sendType.equals(addressType[1])) {   //如果入住地址是空的并且选择的是送货到家
//                initPop();
//            }
//        });
//        fragment2.setSetAddressChoose((id, type) -> {
//            collect_id = id;
//        });
        for (int i = 0; i < fragmentList.size(); i++) {
            AddressTypeFragment fragment = (AddressTypeFragment) fragmentList.get(i);
            fragment.setSetAddressChoose((id, type) -> {
                if (type.equals(addressType[0])){   //送货到家
                    room_id = id;
                }else if (type.equals(addressType[1])){     //门店自提
                    shop_id = id;
                }else {     //小区自提
                    collect_id = id;
                }
            });
        }

        sbLoseMoney.setOnCheckedChangeListener((buttonView, isChecked) -> {
                isLose = isChecked;
//                sbLoseMoney.toggle();
            if (isChecked) {
                currentMoney = currentMoney.subtract(loseMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                currentMoney = currentMoney.add(loseMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            tvSumMoney.setText("¥" + currentMoney);
            btnSubmit.setText("提交订单¥" + currentMoney);
        });

    }

    /**
     * 小区自提
     */

    private void collectGet() {
        tvOne.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
        tvTwo.setBackgroundColor(ContextCompat.getColor(mContext,R.color.view_line));
        tvThree.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_right_6));
        vpFragment.setCurrentItem(2, false);
        tvTwo.setTypeface(Typeface.DEFAULT);
        tvOne.setTypeface(Typeface.DEFAULT);
        tvThree.setTypeface(Typeface.DEFAULT_BOLD);
        tvTimeName.setText("自提时间");
    }

    /**
     * 门店自提
     */
    private void shopGet() {
        tvOne.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_left_6));
        tvTwo.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        tvThree.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
        vpFragment.setCurrentItem(1, false);
        tvTwo.setTypeface(Typeface.DEFAULT_BOLD);
        tvOne.setTypeface(Typeface.DEFAULT);
        tvThree.setTypeface(Typeface.DEFAULT);
        tvTimeName.setText("自提时间");
    }

    /**
     * 送货到家
     */
    private void sendHome() {
        tvOne.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_left_6));
        tvTwo.setBackgroundColor(ContextCompat.getColor(mContext,R.color.view_line));
        tvThree.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_viewline_right_6));
        vpFragment.setCurrentItem(0, false);
        tvTwo.setTypeface(Typeface.DEFAULT);
        tvOne.setTypeface(Typeface.DEFAULT_BOLD);
        tvThree.setTypeface(Typeface.DEFAULT);
        tvTimeName.setText("配送时间");
    }

    @OnClick({R.id.img_back, R.id.btn_submit, R.id.rl_delivery_time, R.id.sb_lose_money})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                btnSubmit.setEnabled(false);
                createShopOrder();
                break;
            case R.id.rl_address:
//                intent.setClass(mContext, HomeAddressListActivity.class);
//                startActivity(intent);
                break;
            case R.id.rl_delivery_time:     //自提时间
                intent.setClass(mContext, ChooseDateActivity.class);
                startActivityForResult(intent, TIME_DATA_REQUEST);
                break;
            case R.id.sb_lose_money:       //代金券

                break;
        }
    }

    private void createShopOrder() {
        int currentItem = vpFragment.getCurrentItem();
        if (room_id.equals("") && currentItem == 0) {
            btnSubmit.setEnabled(true);
            Toast.makeText(mContext, "请选择送货到家地址！", Toast.LENGTH_SHORT).show();
//            initPop();
            return;
        }
        if (shop_id.equals("") && currentItem == 1) {
            Toast.makeText(mContext, "请选择自提店铺地址！", Toast.LENGTH_SHORT).show();
            btnSubmit.setEnabled(true);
            return;
        }
        if (collect_id.equals("") && currentItem == 2) {
            Toast.makeText(mContext, "请选择小区自提地址！", Toast.LENGTH_SHORT).show();
            btnSubmit.setEnabled(true);
            return;
        }
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        if (order != null) {
            map.put("order_id", order.getOrder_id());
        }
        if (isLose) {           //是否抵扣
            map.put("deduct", 1);
        } else {
            map.put("deduct", 0);
        }
        if (tvChooseTime.getText().toString().trim().equals("立即配送")) {        //配送时间
            map.put("delivery", "0");
        } else {
            map.put("delivery", tvChooseTime.getText().toString().trim());
        }
        if (addressType[currentItem].equals("门店自提")) {                 //地址类型
            map.put("shop_id", shop_id);
            map.put("take_type", 1);
        } else if (addressType[currentItem].equals("送货到家")) {
            map.put("room_id", room_id);
            map.put("take_type", 2);
        } else {
            map.put("collect_id", collect_id);
            map.put("take_type", 4);
        }
        map.put("memo", etMemoContent.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopOrderCreate(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    if (string.startsWith("[")) {

                    } else {
                        JSONObject object = new JSONObject(string);
                        if (object.has("status")) {
                            boolean status = object.optBoolean("status");
                            if (status) {
                                long order_id = object.optLong("order_id");
                                Intent intent = new Intent();
                                intent.setClass(mContext, ShopPayActivity.class);
                                intent.putExtra("order_id", order_id);
                                intent.putExtra("formCar", formCar);
                                if (order != null) {
                                    if (order.getProducts().size() > 0) {
                                        intent.putExtra("shopName", order.getProducts().get(0).getName());
                                        intent.putExtra("shopImg", order.getProducts().get(0).getImage());
                                    } else {
                                        Toast.makeText(mContext, "订单商品出错！", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                intent.putExtra("currentMoney", currentMoney.doubleValue());
                                startActivity(intent);
                                finish();
                            } else {
                                int type = object.optInt("type");
                                if (type == 0) {
                                    Toast.makeText(ShopConfirmOrderActivity.this, object.optString("message"), Toast.LENGTH_SHORT).show();
                                } else if (type == 1) {
                                    Utils.loginPopWindow(ShopConfirmOrderActivity.this);
                                } else {
                                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                btnSubmit.setEnabled(true);
            }

            @Override
            public void fail() {
                btnSubmit.setEnabled(true);
            }
        });
    }

    private void initPop() {
        PopWindowUtils.getInstance().show("暂未绑定住所，无法使用送货到家", ShopConfirmOrderActivity.this);
        PopWindowUtils.getInstance().setSetYesOnClickListener(() -> {
            Intent intent = new Intent(ShopConfirmOrderActivity.this, AddHomeAddressBindingActivity.class);
            intent.putExtra("isPopAddress", true);
            startActivityForResult(intent, BINDING_ADDRESS);
        });
    }

    class AddressTypeAdapter extends FragmentPagerAdapter {

        public AddressTypeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TIME_DATA_REQUEST && resultCode == TIME_DATA_RESULT) {
            String checkTime = data.getStringExtra("checkTime");
            tvChooseTime.setText(checkTime);
        }
//        else if (requestCode == BINDING_ADDRESS && resultCode == BINDING_ADDRESS_SUCCESS) {
//            if (fragmentList != null && fragmentList.size() != 0) {
//                AddressTypeFragment fragment = (AddressTypeFragment) fragmentList.get(1);
//                fragment.requestHouseAddress();
//            }
//        }
    }
}
