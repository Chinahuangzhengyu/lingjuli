package com.zhjl.qihao.freshshop.fragment;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.address.activity.AddressListActivity;
import com.zhjl.qihao.address.activity.HomeAddressListActivity;
import com.zhjl.qihao.chooseCity.activty.ChooseCityActivity;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.AddressListBean;
import com.zhjl.qihao.freshshop.bean.AddressRoomBean;
import com.zhjl.qihao.freshshop.bean.CollectAddressListBean;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
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

import static com.zhjl.qihao.address.activity.HomeAddressListActivity.ADDRESS_RESULT;
import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

public class AddressTypeFragment extends VolleyBaseFragment {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    Unbinder unbinder;
    private View view;
    private String sendType;
    private ShopInterface shopInterface;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //    public static final int FINE_LOCATION_CODE = 11110;
//    private MyHandler handler = new MyHandler(this);
    private ArrayList<AddressListBean> offAddress = new ArrayList<>();
    private boolean isFirst = false;
    private double latitude;
    private double longitude;
    private ArrayList<AddressRoomBean.RoomsBean> onAddress = new ArrayList<>();
    private static final int ADDRESS_CODE = 0x555;
    private int position = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mSession = Session.get(getActivity());
    }

    public static AddressTypeFragment getInstance(String sendType) {
        AddressTypeFragment fragment = new AddressTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sendType", sendType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static class MyHandler extends Handler {
        private final WeakReference<AddressTypeFragment> mfragment;
        private AddressTypeFragment instance;

        public MyHandler(AddressTypeFragment fragment) {
            mfragment = new WeakReference<AddressTypeFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            instance = mfragment.get();
//            if (instance.isAdded()) {
//                return;
//            }
            if (!instance.isFirst) {
                instance.isFirst = true;
                instance.requestShopAddress(String.valueOf(instance.longitude), String.valueOf(instance.latitude));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_address_type, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        rlAddress.setOnClickListener(this);
        sendType = getArguments().getString("sendType");
        shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
//        if (sendType.equals("送货到家")) {
//            requestHouseAddress();
//        } else if (sendType.equals("门店自提")){
//            initMap();
//        }else {
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_order_address);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvChooseAddress.setCompoundDrawables(drawable, null, null, null);
        tvChooseAddress.setText("请选择地址");
//        }
        return view;
    }


//    private void initMap() {
//        mLocationClient = new LocationClient(mContext.getApplicationContext());
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
//        //注册监听函数
//        mLocationClient.setLocOption(option);
//        if (RequestPermissionUtils.checkPermission(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})) {
//            mLocationClient.start();// 定位SDK
//        } else {
//            RequestPermissionUtils.requestFPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
//        }
//
//
//    }

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

//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                if (!isFirst) {
//                    Message msg = handler.obtainMessage();
//                    msg.what = 111;
//                    msg.obj = true;
//                    handler.sendMessage(msg);
//                }
//            }
        }
    }

    //请求门店自提的地址
    private void requestShopAddress(String lon, String lat) {
        Map<String, Object> map = new HashMap<>();
        map.put("lon", lon);
        map.put("lat", lat);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getShopAddress(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.optJSONObject(i);
                        AddressListBean addressListBean = new AddressListBean();
                        addressListBean.setName(data.optString("name"));
                        addressListBean.setId(data.optInt("id"));
                        addressListBean.setAddress(data.optString("address"));
                        addressListBean.setZone_name(data.optString("zone_name"));
                        addressListBean.setLongitude(data.optString("longitude"));
                        addressListBean.setLatitude(data.optString("latitude"));
                        offAddress.add(addressListBean);
                    }
                    if (offAddress.size() > 0) {
                        tvAddress.setText(offAddress.get(0).getZone_name());
                        tvChooseAddress.setText(offAddress.get(0).getAddress() + offAddress.get(0).getName());
                        tvName.setText(mSession.getNick());
                        tvPhone.setText(mSession.getRegisterMobile());
                        setAddressChoose.getAddress(offAddress.get(0).getId() + "", "门店自提");
                    }
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

    //请求送货到家的地址
    public void requestHouseAddress() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.getHouseAddress(body);
        fragmentRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                if (string.startsWith("[")) {
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_order_address);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvChooseAddress.setCompoundDrawables(drawable, null, null, null);
                    tvChooseAddress.setText("请选择地址");
                    setAddressChoose.getAddress("", "送货到家");
                } else {
                    Gson gson = new Gson();
                    AddressRoomBean roomBean = gson.fromJson(string, AddressRoomBean.class);
                    onAddress = roomBean.getRooms();
                    if (roomBean.getDefaultX() != null) {
                        tvAddress.setText(roomBean.getDefaultX().getArea());
                        tvChooseAddress.setText(roomBean.getDefaultX().getName() + roomBean.getDefaultX().getRoom_name());
                        tvName.setText(mSession.getNick());
                        tvPhone.setText(mSession.getRegisterMobile());
                        setAddressChoose.getAddress(roomBean.getDefaultX().getRoom_id(), "送货到家");
                    } else if (roomBean.getRooms().size() > 0) {
                        tvAddress.setText(roomBean.getRooms().get(0).getArea());
                        tvChooseAddress.setText(roomBean.getRooms().get(0).getName() + roomBean.getRooms().get(0).getRome_name());
                        tvName.setText(mSession.getNick());
                        tvPhone.setText(mSession.getRegisterMobile());
                        setAddressChoose.getAddress(roomBean.getRooms().get(0).getRoom_id(), "送货到家");
                    }
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
            case R.id.rl_address:
                Intent intent = new Intent(getActivity(), HomeAddressListActivity.class);
                intent.putExtra("sendType", sendType);
                intent.putExtra("position", position);
//                if (sendType.equals("送货到家")) {
//                    intent.putParcelableArrayListExtra("onAddress", onAddress);
//                } else if (sendType.equals("门店自提")) {
//                    intent.putParcelableArrayListExtra("offAddress", offAddress);
//                }
                AddressTypeFragment.this.startActivityForResult(intent, ADDRESS_CODE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == FINE_LOCATION_CODE) {
//            boolean isAllGranted = true;
//
//            // 判断是否所有的权限都已经授予了
//            for (int grant : grantResults) {
//                if (grant != PackageManager.PERMISSION_GRANTED) {
//                    isAllGranted = false;
//                    break;
//                }
//            }
//            for (int i = 0; i < grantResults.length; i++) {
//
//                //判断权限的结果，如果点击不在提示
//                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
////                        RequestPermissionUtils.showShortCut(getActivity(), "开启定位需要位置信息权限！", rlAddress);
//                    }
//                }
//            }
//            if (isAllGranted) {
//                mLocationClient.start();// 定位SDK
//            } else {
//                requestShopAddress("", "");
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (RequestPermissionUtils.checkPermission(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})) {
                mLocationClient.start();// 定位SDK
            } else {
                Toast.makeText(mContext, "设置位置信息权限失败！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADDRESS_CODE && resultCode == ADDRESS_RESULT) {
            position = data.getIntExtra("position", 0);
            List<Object> addressList = (List<Object>) data.getSerializableExtra("addressList");
            try {
                if (addressList != null && addressList.size() > 0) {
                    if (addressList.get(position) instanceof AddressListBean) {
                        AddressListBean dataOff = (AddressListBean) addressList.get(position);
                        tvAddress.setText(dataOff.getZone_name());
                        tvChooseAddress.setCompoundDrawables(null,null,null,null);
                        tvChooseAddress.setText(dataOff.getAddress() + dataOff.getName());
                        setAddressChoose.getAddress(dataOff.getId() + "", "门店自提");
                        tvName.setText("联系电话");
                        tvPhone.setText(dataOff.getTel());
                    } else if (addressList.get(position) instanceof AddressRoomBean.RoomsBean) {
                        AddressRoomBean.RoomsBean dataOn = (AddressRoomBean.RoomsBean) addressList.get(position);
                        tvAddress.setText(dataOn.getArea());
                        tvChooseAddress.setCompoundDrawables(null,null,null,null);
                        tvChooseAddress.setText(dataOn.getName() + dataOn.getRome_name());
//                        onAddress.clear();
//                        for (int i = 0; i < addressList.size(); i++) {
//                            onAddress.add((AddressRoomBean.RoomsBean) addressList.get(i));
//                        }
                        setAddressChoose.getAddress(dataOn.getRoom_id(), "送货到家");
                        tvName.setText("联系电话");
                        tvPhone.setText(mSession.getRegisterMobile());
                    } else {
                        CollectAddressListBean.CollectesBean dataCollect = (CollectAddressListBean.CollectesBean) addressList.get(position);
                        tvAddress.setText(dataCollect.getCommunity_name());
                        tvChooseAddress.setCompoundDrawables(null,null,null,null);
                        tvChooseAddress.setText(dataCollect.getAddress());
                        setAddressChoose.getAddress(dataCollect.getId() + "", "小区自提");
                        tvName.setText("联系电话");
                        tvPhone.setText(dataCollect.getTel());
                    }
                }
            } catch (Exception e) {
                Toast.makeText(mContext, "绑定出错！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public interface SetAddressChoose {
        void getAddress(String id, String type);
    }

    public SetAddressChoose setAddressChoose;

    public void setSetAddressChoose(SetAddressChoose setAddressChoose) {
        this.setAddressChoose = setAddressChoose;
    }
}
