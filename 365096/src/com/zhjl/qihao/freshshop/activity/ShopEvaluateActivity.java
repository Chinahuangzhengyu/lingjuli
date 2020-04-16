package com.zhjl.qihao.freshshop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.freshshop.adapter.ShopEvaluateAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopEvaluateBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopEvaluateActivity extends VolleyBaseActivity {
    @BindView(R.id.xrv_shop_evaluate)
    XRecyclerView xrvShopEvaluate;
    @BindView(R.id.rg_evaluate)
    CustomRadioGroup rgEvaluate;
    private int score = 0;//0:全部，5：好评，4：中评，3：差评
    private int pageIndex = 1;
    private int pageSize = 10;
    private long pid;
    private int totalPage;
    private boolean isRefresh = true;
    private List<ShopEvaluateBean.ProductCommentsBean> list = new ArrayList<>();
    private ShopEvaluateAdapter shopEvaluateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_evaluate);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "商品评价", this);
        pid = getIntent().getLongExtra("pid", 0);
        initData();
        xrvShopEvaluate.setPullRefreshEnabled(false);
        xrvShopEvaluate.setLayoutManager(new LinearLayoutManager(mContext));
        shopEvaluateAdapter = new ShopEvaluateAdapter(this, list);
        xrvShopEvaluate.setAdapter(shopEvaluateAdapter);

        xrvShopEvaluate.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                pageIndex++;
                if (totalPage > pageIndex) {
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvShopEvaluate.getFootView().setPadding(0, top, 0, bottom);
                    xrvShopEvaluate.setNoMore(true);
                }
            }
        });


        rgEvaluate.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                RadioButton rb = (RadioButton) group.getChildAt(i);
                if (rb.getId() == checkedId) {
                    rb.setTextColor(Color.parseColor("#FFFFFF"));
                    rb.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_19));
                    String check = rb.getText().toString().trim();
                    if (check.equals("好评")) {
                        score = 5;
                    } else if (check.equals("中评")) {
                        score = 4;
                    } else if (check.equals("差评")) {
                        score = 3;
                    } else {
                        score = 0;
                    }
                    pageIndex = 1;
                    isRefresh = true;
                    initData();
                } else {
                    rb.setTextColor(Color.parseColor("#1F1F1F"));
                    rb.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_white_19));
                }
            }
        });
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        map.put("page", pageIndex);
        map.put("limit", pageSize);
        map.put("score", score);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopEvaluate(body);
        activityRequestPhpData(call, new RequestResult<Object>() {

            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    if (isRefresh) {
                        list.clear();
                    }
                    JSONObject object = new JSONObject(string);
                    ShopEvaluateBean shopEvaluateBean = new ShopEvaluateBean();
                    JSONObject info = object.optJSONObject("info");
                    totalPage = info.optInt("totalPage");
                    if (totalPage > 1) {
                        xrvShopEvaluate.setLoadingMoreEnabled(true);
                    }
                    JSONArray array = object.optJSONArray("product_comments");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.optJSONObject(i);
                        ShopEvaluateBean.ProductCommentsBean productCommentsBean = new ShopEvaluateBean.ProductCommentsBean();
                        productCommentsBean.setPhone_user_id(jsonObject.optString("phone_user_id"));
                        productCommentsBean.setContent(jsonObject.optString("content"));
                        productCommentsBean.setScore_status(jsonObject.optString("score_status"));
                        productCommentsBean.setNick_name(jsonObject.optString("nick_name"));
                        productCommentsBean.setCreate_date(jsonObject.optString("create_date"));
                        productCommentsBean.setUser_picture(jsonObject.optString("user_picture"));
                        if (jsonObject.has("images")) {
                            JSONObject images = jsonObject.optJSONObject("images");
                            JSONArray min = images.optJSONArray("min");
                            List<String> listMin = new ArrayList<>();
                            for (int j = 0; j < min.length(); j++) {
                                listMin.add(min.optString(j));
                            }
                            JSONArray middle = images.optJSONArray("middle");
                            List<String> listMiddle = new ArrayList<>();
                            for (int j = 0; j < middle.length(); j++) {
                                listMiddle.add(middle.optString(j));
                            }
                            ShopEvaluateBean.ProductCommentsBean.ImagesBean imagesBean = new ShopEvaluateBean.ProductCommentsBean.ImagesBean();
                            imagesBean.setMin(listMin);
                            imagesBean.setMiddle(listMiddle);
                            productCommentsBean.setImages(imagesBean);
                        }
                        list.add(productCommentsBean);
                        shopEvaluateBean.setProduct_comments(list);
                    }

                    shopEvaluateAdapter.addData(list);
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
