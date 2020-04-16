package com.zhjl.qihao.ablogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.adapter.ChooseSmallCityAdapter;
import com.zhjl.qihao.ablogin.api.LoginInterface;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.freshshop.adapter.CashCouponAdapter;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ChooseSmallCity extends VolleyBaseActivity {

    @BindView(R.id.rv_choose_small_city)
    RecyclerView rvChooseSmallCity;
    private ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms = new ArrayList<>();
    private ChooseSmallCityAdapter chooseSmallCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_small_city);
        ButterKnife.bind(this);
        NewHeaderBar headerBar = NewHeaderBar.createCommomBack(this, "选择住所", this);
        headerBar.setGoneIvBack();
        final ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> listExtra = getIntent().getParcelableArrayListExtra("rooms");
        if (listExtra!=null){
            rooms.addAll(listExtra);
        }
        if (rooms.size()==0){
            requestRoomList();
        }
        rvChooseSmallCity.setLayoutManager(new LinearLayoutManager(mContext));
        chooseSmallCityAdapter = new ChooseSmallCityAdapter(mContext, rooms);
        rvChooseSmallCity.setAdapter(chooseSmallCityAdapter);
        chooseSmallCityAdapter.setSetOnItemClickListener(new CashCouponAdapter.SetOnItemClickListener() {
            @Override
            public void onClick(View view, int position, TextView tv) {
                for (int i = 0; i < rvChooseSmallCity.getChildCount(); i++) {
                    if (i==position){
                        RelativeLayout rl = (RelativeLayout) rvChooseSmallCity.getChildAt(i);
                        TextView textView = (TextView) rl.getChildAt(2);
                        textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.car_choose_click));
                        requestChangeRoom(rooms.get(i).getRoomId());
                    }else {
                        RelativeLayout rl = (RelativeLayout) rvChooseSmallCity.getChildAt(i);
                        TextView textView = (TextView) rl.getChildAt(2);
                        textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.car_choose));
                    }
                }
            }
        });
    }

    private void requestRoomList() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Call<ResponseBody> call = propertyPayInterface.userRoomList(ParamForNet.put(new HashMap<String, Object>()));
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData().size()>0){
                    rooms.clear();
                    for (int i = 0; i < result.getData().size(); i++) {
                        LoginBean.DataBean.UserInfoBean.UserRoomsBean roomsBean = new LoginBean.DataBean.UserInfoBean.UserRoomsBean();
                        roomsBean.setRoomName(result.getData().get(i).getRoomName());
                        roomsBean.setRoomId(result.getData().get(i).getRoomId());
                        roomsBean.setSmallCommunityName(result.getData().get(i).getSmallCommunityName());
                        rooms.add(roomsBean);
                    }
                    chooseSmallCityAdapter.addData(rooms);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestChangeRoom(String roomId) {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        Map<String,Object> map = new HashMap<>();
        map.put("roomId",roomId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.switchChangeRoom(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
               String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    JSONObject data = object.optJSONObject("data");
                    mSession.setUserType(data.optInt("userType")+"");
                    mSession.setRoomCode(data.optString("roomCode"));
                    mSession.setRoomName(data.optString("roomName"));
                    mSession.setRoomId(data.optString("roomId"));
                    mSession.setSmallCommunityCode(data.optString("smallCommunityCode"));
                    mSession.setSmallCommunityName(data.optString("smallCommunityName"));
                    Intent intent = new Intent(mContext, RefactorMainActivity.class);
                    startActivity(intent);
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
