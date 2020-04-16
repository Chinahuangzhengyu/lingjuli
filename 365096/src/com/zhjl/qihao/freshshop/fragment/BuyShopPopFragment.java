package com.zhjl.qihao.freshshop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.activity.ShopConfirmOrderActivity;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopDetailBean;
import com.zhjl.qihao.freshshop.bean.ShopOrderBean;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

@SuppressLint("ValidFragment")
public class BuyShopPopFragment extends DialogFragment {

    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.rg_delivery_type)
    CustomRadioGroup rgDeliveryType;
    @BindView(R.id.img_shop_reduce)
    ImageView imgShopReduce;
    @BindView(R.id.tv_shop_sum)
    TextView tvShopSum;
    @BindView(R.id.img_shop_add)
    ImageView imgShopAdd;
    @BindView(R.id.btn_sure)
    Button btnSure;
    Unbinder unbinder;
    @BindView(R.id.ll_delivery_type)
    LinearLayout llDeliveryType;
    @BindView(R.id.img_shop)
    RoundImageView imgShop;
    @BindView(R.id.tv_monthly_sales)
    TextView tvMonthlySales;
    @BindView(R.id.rg_shop_type)
    CustomRadioGroup rgShopType;
    @BindView(R.id.ll_shop_type)
    LinearLayout llShopType;
    private View view;
    private VolleyBaseActivity context;
    private ShopDetailBean shopDetailBean;
    private int count = 1;
    private final Session session;
    private long id;
    private int position;    //配送方式的下标
    private SpannableString string;
    private StrikethroughSpan span;
    private long moreTypePosition = -1;//多类型id
    private long stock;

    public BuyShopPopFragment(VolleyBaseActivity context, ShopDetailBean shopDetailBean) {
        this.context = context;
        this.shopDetailBean = shopDetailBean;
        session = Session.get(context);
    }

    public boolean isBuyCar;

    public void setBuyCar(boolean buyCar) {
        isBuyCar = buyCar;
    }

    private String[] strings = {"送货到家", "门店自提", "小区自提"}; //1.自提 2.送货到家 3.同城配送 4.小区自提
    private int sendType = 2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_attrs_pop, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        if (isBuyCar) {
            llDeliveryType.setVisibility(View.GONE);
        } else {
            llDeliveryType.setVisibility(View.VISIBLE);
        }
        if (shopDetailBean != null && shopDetailBean.getProduct() != null) {
            if (shopDetailBean.getProduct().getMinImages().size() > 0) {
                PictureHelper.setImageView(context, shopDetailBean.getProduct().getMinImages().get(0), imgShop, R.drawable.img_loading);
            } else {
                imgShop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.img_loading));
            }
            tvShopName.setText(shopDetailBean.getProduct().getName());


            if (shopDetailBean.getProduct().getPromotion_price().equals("0.00") || shopDetailBean.getProduct().getPromotion_price().equals("0")) {
                tvOldPrice.setVisibility(View.GONE);
                tvPrice.setText(shopDetailBean.getProduct().getPrice());
            } else {
                tvOldPrice.setVisibility(View.VISIBLE);
                tvPrice.setText(shopDetailBean.getProduct().getPromotion_price());
                tvOldPrice.setText("¥" + shopDetailBean.getProduct().getPrice());
            }
            string = new SpannableString(tvOldPrice.getText().toString().trim());
            span = new StrikethroughSpan();
            string.setSpan(span, 0, tvOldPrice.getText().toString().trim().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvOldPrice.setText(string);
            id = shopDetailBean.getProduct().getId();
            if (shopDetailBean.getProduct().getStock() == null || shopDetailBean.getProduct().getStock().equals("") || Double.parseDouble(shopDetailBean.getProduct().getStock()) == 0) {
                tvStock.setText("商品已售罄");
            } else {
                tvStock.setText("库存" + shopDetailBean.getProduct().getStock());
                try {
                    stock = Long.parseLong(shopDetailBean.getProduct().getStock());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "库存出错！", Toast.LENGTH_SHORT).show();
                }
            }
            tvMonthlySales.setText("月售" + shopDetailBean.getProduct().getMonthly_sales());
            if (shopDetailBean.getProduct().getProduct_more_unit() != null && shopDetailBean.getProduct().getProduct_more_unit().size() != 0) {
                rgShopType.setVisibility(View.VISIBLE);
                llShopType.setVisibility(View.VISIBLE);
                moreTypePosition = shopDetailBean.getProduct().getProduct_more_unit().get(0).getProduct_more_unit_id();
                for (int i = 0; i < shopDetailBean.getProduct().getProduct_more_unit().size(); i++) {
                    final RadioButton button = new RadioButton(context);
                    button.setButtonDrawable(new StateListDrawable());
                    button.setBackground(ContextCompat.getDrawable(context, R.drawable.rg_status_14));
                    button.setTextColor(Color.parseColor("#999999"));
                    button.setTextSize(14);
                    button.setId(i);
                    button.setText(shopDetailBean.getProduct().getProduct_more_unit().get(i).getName());
                    if (i == 0) {
                        button.setChecked(true);
                        button.setTextColor(Color.parseColor("#ffffff"));
                        tvStock.setText("库存" + shopDetailBean.getProduct().getProduct_more_unit().get(i).getStock());
                    }
                    int left = Utils.dip2px(context, 12);
                    int top = Utils.dip2px(context, 4);
                    button.setPadding(left, top, left, top);
                    rgShopType.addView(button);
                }
                rgShopType.setOnCheckedChangeListener((group, checkedId) -> {
                    for (int i = 0; i < shopDetailBean.getProduct().getProduct_more_unit().size(); i++) {
                        RadioButton radioButton = (RadioButton) group.getChildAt(i);
                        if (checkedId == radioButton.getId()) {
                            radioButton.setTextColor(Color.parseColor("#FFFFFF"));
                            moreTypePosition = shopDetailBean.getProduct().getProduct_more_unit().get(i).getProduct_more_unit_id();
                            tvStock.setText("库存" + shopDetailBean.getProduct().getProduct_more_unit().get(i).getStock());
                            tvPrice.setText(shopDetailBean.getProduct().getProduct_more_unit().get(i).getPrice());
                        } else {
                            radioButton.setTextColor(Color.parseColor("#999999"));
                        }
                    }
                });
            } else {
                llShopType.setVisibility(View.GONE);
                rgShopType.setVisibility(View.GONE);
            }
        }


        for (int i = 0; i < strings.length; i++) {
            final RadioButton button = new RadioButton(context);
            button.setButtonDrawable(new StateListDrawable());
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.rg_status_14));
            button.setTextColor(Color.parseColor("#999999"));
            button.setTextSize(14);
            button.setText(strings[i]);
            button.setId(i);
            if (i == 0) {
                button.setChecked(true);
                button.setTextColor(Color.parseColor("#ffffff"));
            }
            int left = Utils.dip2px(context, 12);
            int top = Utils.dip2px(context, 4);
            button.setPadding(left, top, left, top);
            rgDeliveryType.addView(button);
        }

        rgDeliveryType.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < strings.length; i++) {
                RadioButton radioButton = (RadioButton) group.getChildAt(i);
                if (checkedId == radioButton.getId()) {
                    radioButton.setTextColor(Color.parseColor("#FFFFFF"));
                    if (radioButton.getText().toString().equals("门店自提")) {
                        sendType = 1;
                        position = 1;
                    } else if (radioButton.getText().toString().equals("送货到家")) {
                        sendType = 2;
                        position = 0;
                    } else {
                        sendType = 4;
                        position = 2;
                    }
                } else {
                    radioButton.setTextColor(Color.parseColor("#999999"));
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_cancel, R.id.btn_sure, R.id.img_shop_reduce, R.id.img_shop_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.btn_sure:
                if (isBuyCar) {
                    requestAddShopCar();
                } else {
                    requestShopSure();
                }
                break;
            case R.id.img_shop_reduce:      //减少
                if (count > 1) {
                    count--;
                }
                tvShopSum.setText(count + "");
                break;
            case R.id.img_shop_add:         //增加
                if (stock <= count) {
                    Toast.makeText(context, "库存不足，无法购买更多！", Toast.LENGTH_SHORT).show();
                    return;
                }
                count++;
                tvShopSum.setText(count + "");
                break;
        }
    }

    private void requestAddShopCar() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        if (moreTypePosition != -1) {
            map.put("product_more_unit_id", moreTypePosition);
        }
        map.put("product_id", id);
        map.put("number", count);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.addShopCar(body);
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        dismiss();
                        onSureClickListener.OnClick(true, moreTypePosition);
                        Toast.makeText(context, "加入购物车成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        int type = object.optInt("type");
                        if (type == 0) {
                            Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                        } else if (type == 1) {
                            dismiss();
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

    private void requestShopSure() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        map.put("take_type", sendType);
        map.put("fromProductCart", 0);   //1是 0否
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapList = new HashMap<>();
        mapList.put("id", id);
        mapList.put("number", count);
        if (moreTypePosition != -1) {
            mapList.put("product_more_unit_id", moreTypePosition);
        }
        list.add(mapList);
        RequestBody body = ParamForNet.putArrayObject(map, "ids", list);
        Call<ResponseBody> call = shopInterface.shopOrderSure(body);
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
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
                            Intent intent = new Intent(context, ShopConfirmOrderActivity.class);
                            intent.putExtra("order", shopOrderBean);
                            intent.putExtra("sendType", strings[position]);
                            startActivity(intent);
                            context.overridePendingTransition(0, 0);
                        } else {
                            Toast.makeText(context, shopOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int type = object.optInt("type");
                        if (type == -1) {
                            Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else if (type == 1) {
                            dismiss();
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

    private OnSureClickListener onSureClickListener;

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }


    public interface OnSureClickListener {
        void OnClick(boolean isBuyCar, long moreTypeId);
    }
}
