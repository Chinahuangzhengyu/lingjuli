package com.zhjl.qihao.address.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.address.adapter.HomeAddressAdapter;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.AddressListBean;
import com.zhjl.qihao.freshshop.bean.AddressRoomBean;
import com.zhjl.qihao.freshshop.bean.CollectAddressListBean;
import com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
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

import static com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity.BINDING_ADDRESS_SUCCESS;

public class HomeAddressListActivity extends VolleyBaseActivity {
    @BindView(R.id.rv_home_address_list)
    RecyclerView rvHomeAddressList;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_home_building)
    TextView tvHomeBuilding;
    private List<Object> addressList = new ArrayList<>();
    public static final int ADDRESS_RESULT = 0x556;
    private String sendType = "";
    private HomeAddressAdapter homeAddressAdapter;
    private int position;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private MyHandler handler = new MyHandler(this);
    public static final int FINE_LOCATION_CODE = 11110;
    private boolean isFirst = false;
    private double latitude;
    private double longitude;
    private ShopInterface shopInterface;
    private ArrayList<AddressListBean> offAddress = new ArrayList<>();


    private static class MyHandler extends Handler {
        private final WeakReference<HomeAddressListActivity> mactivity;
        private HomeAddressListActivity instance;

        public MyHandler(HomeAddressListActivity activity) {
            mactivity = new WeakReference<HomeAddressListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            instance = mactivity.get();
//            if (instance.isAdded()) {
//                return;
//            }
            if (!instance.isFirst) {
                instance.isFirst = true;
                instance.requestShopAddress(String.valueOf(instance.longitude), String.valueOf(instance.latitude));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_address_list);
        ButterKnife.bind(this);
        sendType = getIntent().getStringExtra("sendType");
        position = getIntent().getIntExtra("position", -1);
        shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);

        if (sendType.equals("送货到家")) {
            tvHomeBuilding.setVisibility(View.VISIBLE);
//            ArrayList<AddressRoomBean.RoomsBean> onAddress = getIntent().getParcelableArrayListExtra("onAddress");
//            addressList.addAll(onAddress);
            requestHouseAddress();
        } else if (sendType.equals("门店自提")) {
            tvHomeBuilding.setVisibility(View.GONE);
            initMap();
//            ArrayList<AddressListBean> offAddress = getIntent().getParcelableArrayListExtra("offAddress");
//            addressList.addAll(offAddress);
        } else if (sendType.equals("小区自提")) {
            tvHomeBuilding.setVisibility(View.VISIBLE);
            requestCollectAddress();
        }
        rvHomeAddressList.setLayoutManager(new LinearLayoutManager(mContext));
        homeAddressAdapter = new HomeAddressAdapter(this, addressList,position);
        rvHomeAddressList.setAdapter(homeAddressAdapter);

        homeAddressAdapter.setSetOnItemClickListener((view, position1, tv) -> {
            position = position1;
            for (int i = 0; i < rvHomeAddressList.getLayoutManager().getItemCount(); i++) {
                if (i == position1) {
                    homeAddressAdapter.check(position1);
                    Intent intent = new Intent();
                    intent.putExtra("position", position1);
                    intent.putExtra("addressList", (Serializable) addressList);
                    setResult(ADDRESS_RESULT, intent);
                    finish();
                }
            }
        });
    }


    //请求门店自提的地址
    private void requestShopAddress(String lon, String lat) {
        Map<String, Object> map = new HashMap<>();
        map.put("lon", lon);
        map.put("lat", lat);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getShopAddress(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                addressList.clear();
                try {
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.optJSONObject(i);
                        AddressListBean addressListBean = new AddressListBean();
                        addressListBean.setName(data.optString("name"));
                        addressListBean.setId(data.optInt("id"));
                        addressListBean.setAddress(data.optString("address"));
                        addressListBean.setTel(data.optString("tel"));
                        addressListBean.setZone_name(data.optString("zone_name"));
                        addressListBean.setLongitude(data.optString("longitude"));
                        addressListBean.setLatitude(data.optString("latitude"));
                        offAddress.add(addressListBean);
                    }
                    addressList.addAll(offAddress);
                    homeAddressAdapter.addData(addressList);
                    isFirst = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                isFirst = true;
            }
        });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //纬度
            latitude = location.getLatitude();
            //经度
            longitude = location.getLongitude();

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (!isFirst) {
                    Message msg = handler.obtainMessage();
                    msg.what = 111;
                    msg.obj = true;
                    handler.sendMessage(msg);
                }
            }
        }
    }

    private void initMap() {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //注册监听函数
        mLocationClient.setLocOption(option);
        if (RequestPermissionUtils.checkPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})) {
            mLocationClient.start();// 定位SDK
        } else {
            RequestPermissionUtils.requestPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x177 && resultCode == BINDING_ADDRESS_SUCCESS) {
            requestHouseAddress();
        }else if (requestCode == 0x178 && resultCode == BINDING_ADDRESS_SUCCESS){
            requestCollectAddress();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                        RequestPermissionUtils.showShortCut(getActivity(), "开启定位需要位置信息权限！", rlAddress);
                    }
                }
            }
            if (isAllGranted) {
                mLocationClient.start();// 定位SDK
            } else {
                requestShopAddress("", "");
            }
        }
    }

    /**
     * 小区自提
     */
    private void requestCollectAddress() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getCollectAddress(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                String status = object.optString("status");
                addressList.clear();
                if (status.equals("true")) {
                    Gson gson = new Gson();
                    CollectAddressListBean addressListBean = gson.fromJson((String) result, CollectAddressListBean.class);
                    addressList.addAll(addressListBean.getCollectes());
                    homeAddressAdapter.addData(addressList);
                } else {
                    Utils.phpIsLogin(HomeAddressListActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (addressList.size() > 0) {
//                Intent intent = getIntent();
//                intent.putExtra("position", position);
//                intent.putExtra("addressList", (Serializable) addressList);
//                setResult(ADDRESS_RESULT, intent);
//            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //请求送货到家的地址
    public void requestHouseAddress() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Call<ResponseBody> call = shopInterface.getHouseAddress(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                addressList.clear();
                if (string.startsWith("[")) {

                } else {
                    Gson gson = new Gson();
                    AddressRoomBean roomBean = gson.fromJson(string, AddressRoomBean.class);
                    addressList.addAll(roomBean.getRooms());
                    homeAddressAdapter.addData(addressList);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_home_building})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
//                if (addressList.size() > 0) {
//                    Intent intent = getIntent();
//                    intent.putExtra("position", position);
//                    intent.putExtra("addressList", (Serializable) addressList);
//                    setResult(ADDRESS_RESULT, intent);
//                }
                finish();
                break;
            case R.id.tv_home_building:
                Intent intent = new Intent(HomeAddressListActivity.this, AddHomeAddressBindingActivity.class);
                intent.putExtra("isPopAddress", true);
                if (sendType.equals("小区自提")){
                    startActivityForResult(intent, 0x178);
                }else {
                    startActivityForResult(intent, 0x177);
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(myListener); //注销掉监听
            mLocationClient.stop(); //停止定位服务
        }
    }
}
