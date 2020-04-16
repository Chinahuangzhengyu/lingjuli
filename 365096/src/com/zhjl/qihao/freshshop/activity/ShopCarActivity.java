package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.freshshop.adapter.ShopCarAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopCarListBean;
import com.zhjl.qihao.freshshop.bean.ShopOrderBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class ShopCarActivity extends VolleyBaseActivity {

    @BindView(R.id.rv_shop_car)
    RecyclerView rvShopCar;
    @BindView(R.id.nsv_shop_car)
    NestedScrollView nsvShopCar;
    @BindView(R.id.tv_choose_shop)
    TextView tvChooseShop;
    @BindView(R.id.ll_not_data)
    LinearLayout llNotData;
    @BindView(R.id.tv_shop_all_check)
    TextView tvShopAllCheck;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_send_price)
    TextView tvSendPrice;
    @BindView(R.id.tv_calc)
    TextView tvCalc;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private List<ShopCarListBean> shopList = new ArrayList<>();
    private ShopCarAdapter shopCarAdapter;
    private boolean isAllCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "购物车", this);
        rlLoading.setVisibility(View.VISIBLE);
        rvShopCar.setLayoutManager(new LinearLayoutManager(mContext));
        nsvShopCar.setNestedScrollingEnabled(false);
        shopCarAdapter = new ShopCarAdapter(this, shopList);
        rvShopCar.setAdapter(shopCarAdapter);
        shopCarAdapter.setSetOnItemClickListener(new ShopCarAdapter.SetOnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, boolean isAllFinish, double sumPrice) {
                addAll(isAllFinish);
                String formatSumPrice = String.format("%.2f", sumPrice);
                tvSumPrice.setText("合计：" + formatSumPrice);
            }
        });
        shopCarAdapter.setGetPriceChangeListener(new ShopCarAdapter.GetPriceChangeListener() {
            @Override
            public void getPrice(double sumPrice) {
                if (sumPrice == 0 && shopCarAdapter.check.size() == 0) {
                    llNotData.setVisibility(View.VISIBLE);
                    nsvShopCar.setVisibility(View.GONE);
                }
                String formatSumPrice = String.format("%.2f", sumPrice);
                tvSumPrice.setText("合计：" + formatSumPrice);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addAll(false);
        if (shopCarAdapter != null) {
            double sumPrice = shopCarAdapter.allCheck(false);
            String formatSumPrice = String.format("%.2f", sumPrice);
            tvSumPrice.setText("合计：" + formatSumPrice);
        } else {
            tvSumPrice.setText("合计：0.00");
        }
        initData();
    }

    private void addAll(boolean isAllFinish) {
        if (isAllFinish) {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_car_check);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvShopAllCheck.setCompoundDrawables(drawable, null, null, null);
            isAllCheck = true;
        } else {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_shop_car_circle);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvShopAllCheck.setCompoundDrawables(drawable, null, null, null);
            isAllCheck = false;
        }
    }


    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopCarList(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                shopList.clear();
                try {
                    if (string.startsWith("[")) {
                        JSONArray array = new JSONArray(string);
                        if (array.length() > 0) {
                            nsvShopCar.setVisibility(View.VISIBLE);
                            llNotData.setVisibility(View.GONE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                ShopCarListBean shopCarListBean = new ShopCarListBean();
                                shopCarListBean.setName(object.optString("name"));
                                shopCarListBean.setCheap_price(object.optString("cheap_price"));
                                shopCarListBean.setProduct_more_unit_id(object.optLong("product_more_unit_id"));
                                shopCarListBean.setPrice(object.optString("price"));
                                shopCarListBean.setUnit_name(object.optString("unit_name"));
                                shopCarListBean.setImage(object.optString("image"));
                                shopCarListBean.setNumber(object.optInt("number"));
                                shopCarListBean.setProduct_id(object.optLong("product_id"));
                                shopCarListBean.setId(object.optString("id"));
                                shopCarListBean.setOn_sale(object.optInt("on_sale"));
                                shopCarListBean.setStock(object.optInt("stock"));
                                shopList.add(shopCarListBean);
                            }
                            shopCarAdapter.addData(shopList);
                        } else {
                            addAll(false);
                            shopCarAdapter.addData(shopList);
                            tvSumPrice.setText("合计：0.00");
                            nsvShopCar.setVisibility(View.GONE);
                            llNotData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        JSONObject object = new JSONObject(string);
                        boolean status = object.optBoolean("status");
                        if (!status) {
                            int type = object.optInt("type");
                            if (type == 0) {
                                Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                            } else if (type == 1) {
                                Utils.loginPopWindow(ShopCarActivity.this);
                            }
                        }
                    }
                    rlLoading.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    rlLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.tv_choose_shop, R.id.tv_shop_all_check, R.id.tv_calc, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_shop:
                Intent intent = new Intent(mContext, FreshShopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shop_all_check:
                isAllCheck = !isAllCheck;
                addAll(isAllCheck);
                double sumPrice = shopCarAdapter.allCheck(isAllCheck);
                String formatSumPrice = String.format("%.2f", sumPrice);
                tvSumPrice.setText("合计：" + formatSumPrice);
                break;
            case R.id.tv_calc:
                boolean oneClick = shopCarAdapter.isOneClick();
                if (!oneClick) {
                    Toast.makeText(mContext, "请选择商品！", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestShopSure();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void requestShopSure() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("take_type", 2);
        map.put("fromProductCart", 1);   //1是 0否
        List<Map<String, Object>> list = new ArrayList<>();
        if (shopCarAdapter != null) {
            Map<Integer, Boolean> check = shopCarAdapter.check;
            for (int i = 0; i < check.size(); i++) {
                if (check.get(i)) {
                    Map<String, Object> mapList = new HashMap<>();
                    mapList.put("id", shopCarAdapter.shopList.get(i).getProduct_id());
                    mapList.put("number", shopCarAdapter.shopList.get(i).getNumber());
                    mapList.put("product_more_unit_id", shopCarAdapter.shopList.get(i).getProduct_more_unit_id());
                    list.add(mapList);
                }
            }
        }
        RequestBody body = ParamForNet.putArrayObject(map, "ids", list);
        Call<ResponseBody> call = shopInterface.shopOrderSure(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        Gson gson = new Gson();
                        ShopOrderBean shopOrderBean = gson.fromJson(string, ShopOrderBean.class);
                        if (shopOrderBean.getMessage() == null || shopOrderBean.getMessage().equals("")) {
                            Intent intent = new Intent(mContext, ShopConfirmOrderActivity.class);
                            intent.putExtra("order", shopOrderBean);
                            intent.putExtra("formCar", true);
                            intent.putExtra("sendType", "送货到家");
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, shopOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int type = object.optInt("type");
                        if (type == -1) {
                            Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (type == 1) {
                            Utils.loginPopWindow(ShopCarActivity.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }
}
