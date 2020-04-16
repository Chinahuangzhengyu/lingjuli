package com.zhjl.qihao.freshshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.freshshop.activity.ShopDetailActivity;
import com.zhjl.qihao.freshshop.adapter.ShopContentAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopDetailBean;
import com.zhjl.qihao.freshshop.bean.ShopListBean;
import com.zhjl.qihao.freshshop.utils.AnimatorUtils;
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

public class ShopListFragment extends VolleyBaseFragment {

    @BindView(R.id.xrv_shop_content)
    XRecyclerView xrvShopContent;
    Unbinder unbinder;
    private View view;
    private ShopContentAdapter shopContentAdapter;
    private List<ShopListBean.DataBean> data = new ArrayList<>();
    private int id;
    private int pagerIndex = 1;
    private int pagerSize = 10;
    private boolean isRefresh = true;
    private boolean isFirst = true;     //第一次请求，把数据存入
    private int totalPage;
    private RelativeLayout rlImgShopCar;
    private ImageView imgShopCar;
    private long number;
    private TextView tvShopCartSum;
    private SharedPreferences shopNameSp;
    private VolleyBaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (VolleyBaseActivity) context;
    }

    public static ShopListFragment getInstance(int id) {
        ShopListFragment shopListFragment = new ShopListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        shopListFragment.setArguments(bundle);
        return shopListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_list, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        id = getArguments().getInt("id");
        xrvShopContent.setLayoutManager(new LinearLayoutManager(mContext));
        shopContentAdapter = new ShopContentAdapter(getActivity(), data);
        xrvShopContent.setAdapter(shopContentAdapter);
        rlImgShopCar = getActivity().findViewById(R.id.rl_img_shop_car);
        imgShopCar = getActivity().findViewById(R.id.img_shop_car);
        tvShopCartSum = getActivity().findViewById(R.id.tv_shop_cart_sum);
        shopContentAdapter.setSetOnItemClickListener((view, position) -> {
            Intent intent = new Intent(mContext, ShopDetailActivity.class);
            intent.putExtra("id", data.get(position).getId());
            intent.putExtra("mountCount", data.get(position).getMonthly_sales());
            startActivity(intent);
        });
        shopContentAdapter.setOnAddCarClickListener((view, carView, position) -> {
            if (data.get(position).getStock() == null || data.get(position).getStock().equals("") || Double.parseDouble(data.get(position).getStock()) == 0) {
                Toast.makeText(mContext, "已售罄的商品无法加入购物车！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (data.get(position).isHave_more_unit()==1) {
                //商品属性的popWindow
                ShopDetailBean shopDetailBean = new ShopDetailBean();
                ShopDetailBean.ProductBean productBean = new ShopDetailBean.ProductBean();
                productBean.setHave_more_unit(1);
                productBean.setId(data.get(position).getId());
                productBean.setMonthly_sales(data.get(position).getMonthly_sales());
                productBean.setName(data.get(position).getName());
                productBean.setPrice(data.get(position).getPrice());
                productBean.setPromotion_price(data.get(position).getPromotion_price());
                productBean.setStock(data.get(position).getStock());
                List<String> imgs = new ArrayList<>();
                imgs.add(data.get(position).getImage());
                productBean.setMinImages(imgs);
                productBean.setProduct_more_unit(data.get(position).getProduct_more_unit());
                shopDetailBean.setProduct(productBean);
                BuyShopPopFragment fragment = new BuyShopPopFragment(mContext, shopDetailBean);
                fragment.setBuyCar(true);
                fragment.show(getChildFragmentManager(), "1");
                fragment.setOnSureClickListener((isBuyCar, moreTypeId) -> {
                    AnimatorUtils.doCartAnimator(ShopListFragment.this.getActivity(), view, rlImgShopCar, rlImgShopCar, imgShopCar, null);
//                    requestAddShopCar(position, moreTypeId);
                    initShopSum();
                });
            } else {
                AnimatorUtils.doCartAnimator(getActivity(), view, rlImgShopCar, rlImgShopCar, imgShopCar, null);
                requestAddShopCar(position, -1);
            }
        });
        xrvShopContent.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pagerIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (totalPage > pagerIndex) {
                    pagerIndex++;
                    initData();
                } else {
                    int top = Utils.dip2px(mContext, 10);
                    int bottom = Utils.dip2px(mContext, 30);
                    xrvShopContent.getFootView().setPadding(0, top, 0, bottom);
                    xrvShopContent.setNoMore(true);
                }
            }
        });
        initData();
        return view;
    }


    private void requestAddShopCar(int position, long moreTypeId) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("product_id", data.get(position).getId());
        if (moreTypeId != -1) {
            map.put("product_more_unit_id", moreTypeId);
        }
        map.put("number", 1);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.addShopCar(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        initShopSum();
                    } else {
                        int type = object.optInt("type");
                        if (type == 0) {
                            Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                        } else if (type == 1) {
                            Utils.loginPopWindow(getActivity());
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

    private void initShopSum() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getShopCarNum(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                number = object.optLong("number");
                if (number == 0) {
                    tvShopCartSum.setVisibility(View.INVISIBLE);
                } else {
                    tvShopCartSum.setText(number + "");
                    tvShopCartSum.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    public void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cid", id);
        map.put("limit", pagerSize);
        map.put("page", pagerIndex);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopList(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                Gson gson = new Gson();
                ShopListBean shopListBean = gson.fromJson(string, ShopListBean.class);
                totalPage = shopListBean.getInfo().getTotalPage();
                if (totalPage > 1) {
                    if (xrvShopContent != null) {
                        xrvShopContent.setLoadingMoreEnabled(true);
                    }
                }
                if (isRefresh) {
                    data.clear();
                }
                data.addAll(shopListBean.getData());
                shopContentAdapter.addData(data);
                if (xrvShopContent != null) {
                    if (isRefresh) {
                        xrvShopContent.refreshComplete();
                    } else {
                        xrvShopContent.loadMoreComplete();
                    }
                }

            }

            @Override
            public void fail() {
                if (xrvShopContent != null) {
                    if (isRefresh) {
                        xrvShopContent.refreshComplete();
                    } else {
                        xrvShopContent.loadMoreComplete();
                    }
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
