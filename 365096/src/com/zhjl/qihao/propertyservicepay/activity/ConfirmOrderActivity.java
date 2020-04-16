package com.zhjl.qihao.propertyservicepay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.CarBean;
import com.zhjl.qihao.propertyservicepay.bean.PropertyPayInfoBean;
import com.zhjl.qihao.util.NewHeaderBar;
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
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ConfirmOrderActivity extends VolleyBaseActivity {
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.tv_sum_pay_money)
    TextView tvSumPayMoney;
    private boolean isCarPay;
    private int cateId;
    private int packageId;
    private String residentId;
    private PropertyPayInfoBean.DataBean order;
    private String totalAmount;
    private String roomId;
    private ArrayList<CarBean> carList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "确认订单", this);
        isCarPay = getIntent().getBooleanExtra("isCarPay", false);
        order = getIntent().getParcelableExtra("serviceOrder");
        cateId = getIntent().getIntExtra("cateId", 0);
        totalAmount = getIntent().getStringExtra("orderMoney");
        packageId = getIntent().getIntExtra("packageId", 0);
        residentId = getIntent().getStringExtra("residentId");
        roomId = getIntent().getStringExtra("roomId");
        if (isCarPay){
            carList = getIntent().getParcelableArrayListExtra("carList");
        }
        tvSumPayMoney.setText("¥" + totalAmount);
        if (order != null) {
            for (int i = 0; i < order.getCostItems().size(); i++) {
                View view = View.inflate(mContext, R.layout.add_pay_item, null);
                TextView tvPayName = view.findViewById(R.id.tv_pay_name);
                TextView tvPrice = view.findViewById(R.id.tv_price);
                tvPayName.setText(order.getCostItems().get(i).getItemName());
                tvPrice.setText(order.getCostItems().get(i).getCostMoney() + "");
                llPayType.addView(view);
            }
        }
    }

    @OnClick({R.id.btn_pay, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                btnPay.setEnabled(false);
                createOrder(isCarPay);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void createOrder(boolean isCar) {
        PropertyPayInterface payInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("cateId", cateId);
        map.put("packageId", packageId);
        map.put("residentId", residentId);
        map.put("roomId", roomId);
        map.put("sourceType", 0);
        RequestBody body;
        if (isCar){
            List<Map<String,Object>> maps = new ArrayList<>();
            for (int i = 0; i < carList.size(); i++) {
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put(carList.get(i).getKey(),carList.get(i).getValue());
                maps.add(objectMap);
            }
            body = ParamForNet.putArrayObject(map,"attrs",maps);
        }else {
            body = ParamForNet.put(map);
        }
        Call<ResponseBody> call = payInterface.createPropertyOrder(body);
        activityRequestData(call,  null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.optJSONObject("data");
                    double totalAmount = data.optDouble("totalAmount");
                    String orderSn = data.optString("orderSn");
                    long time = data.optLong("inValid");
                    btnPay.setEnabled(true);
                    if (!orderSn.equals("")) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, ConfirmPayActivity.class);
                        intent.putExtra("totalAmount", totalAmount);
                        intent.putExtra("orderSn", orderSn);
                        intent.putExtra("inValid", time);
                        intent.putExtra("isCarPay", isCarPay);
                        mSession.setCarPay(isCarPay);
                        startActivity(intent);
                    } else {
                        ToastUtils.showToast(mContext,"订单出错了，请重试！");
                    }
                } catch (Exception e) {
                    btnPay.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                btnPay.setEnabled(true);
            }
        });

    }
}
