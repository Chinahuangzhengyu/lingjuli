package com.zhjl.qihao.abrefactor.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.michoi.unlock.UnlockManager;
import com.michoi.unlock.mode.RightsRecord;
import com.michoi.unlock.mode.RightsResponseMode;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.bean.OpenDoorListBean;
import com.zhjl.qihao.abrefactor.utils.Utils;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.opendoor.activity.OpenDoorAddressChooseActivity;
import com.zhjl.qihao.opendoor.activity.OpenDoorChooseActivity;
import com.zhjl.qihao.opendoor.activity.OpenDoorShareActivity;
import com.zhjl.qihao.opendoor.api.OpenInterface;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.opendoor.activity.OpenDoorAddressChooseActivity.OPEN_DOOR_ADDRESS_RESULT;


public class OpenDoorFragment extends VolleyBaseFragment {

    @BindView(R.id.tv_open_door)
    TextView tvOpenDoor;
    @BindView(R.id.tv_share_code)
    TextView tvShareCode;
    Unbinder unbinder;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.img_open_door_code)
    ImageView imgOpenDoorCode;
    @BindView(R.id.tv_open_door_name)
    TextView tvOpenDoorName;
    @BindView(R.id.tv_open_door_address)
    TextView tvOpenDoorAddress;
    @BindView(R.id.rl_choose_smallCommunity)
    RelativeLayout rlChooseSmallCommunity;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.sv_qr)
    ScrollView svQr;
    @BindView(R.id.img_not_data)
    ImageView imgNotData;
    @BindView(R.id.tv_not_data)
    TextView tvNotData;
    @BindView(R.id.not_data)
    LinearLayout notData;
    private View view;
    private ArrayList<OpenDoorListBean.DataBean> data;
    private byte[] decode;
    private int areaId;
    private String Id;
    private int position;
    private static final int OPEN_DOOR_REQUEST_CODE = 0x111;
    private static final int WRITE_SETTINGS_PERMISSION = 10026;
    private int screenBrightness;
    private ArrayList<RightsRecord> rights;
    private String qrCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static OpenDoorFragment getInstance(ArrayList<OpenDoorListBean.DataBean> data, String qrCode) {
        OpenDoorFragment openDoorFragment = new OpenDoorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        bundle.putString("qrCode", qrCode);
        openDoorFragment.setArguments(bundle);
        return openDoorFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.ab_fragment_opendoor, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        screenBrightness =  mSession.getScreenBrightness();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        data = getArguments().getParcelableArrayList("data");
        if (data == null || data.size() == 0) {
            notData.setVisibility(View.VISIBLE);
            svQr.setVisibility(View.GONE);
            imgNotData.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_collection_bg));
            tvNotData.setText("当前小区暂未安装智能门禁，敬请期待！");
        } else {
            svQr.setVisibility(View.VISIBLE);
            notData.setVisibility(View.GONE);
        }
        qrCode = getArguments().getString("qrCode");
        byte[] decode = Base64.decode(qrCode, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        if (bitmap != null) {
            imgOpenDoorCode.setImageBitmap(bitmap);
        }
        if (data != null && data.size() > 0) {
            tvOpenDoorAddress.setText(data.get(position).getSmallCommunityName());
            tvOpenDoorName.setText(data.get(position).getGroupName());
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 8 && hour < 18) {
                Utils.setBrightness(getActivity(), 180);
            }
        }
        initData();
        new Thread(() -> {
            RightsResponseMode responseMode = UnlockManager.getInstance().getLockRights(mSession.getRegisterMobile(), "12628", 0);
            if (responseMode!=null){
                rights = responseMode.getRights();
            }
        }).start();
        return view;

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initData();
//    }

    public void imgData(ArrayList<OpenDoorListBean.DataBean> data, String qrCode) {
        initData();
        if (data != null && data.size() > 0) {
            svQr.setVisibility(View.VISIBLE);
            notData.setVisibility(View.GONE);
        } else {
            svQr.setVisibility(View.GONE);
            notData.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        OpenInterface openInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OpenInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = openInterface.lockGroupList(body);
        fragmentRequestData(call, OpenDoorListBean.class, new RequestResult<OpenDoorListBean>() {
            @Override
            public void success(OpenDoorListBean result, String message) throws Exception {
                data = result.getData();
                if (result.getData().size() > 0) {
                    tvOpenDoorAddress.setText(result.getData().get(position).getSmallCommunityName());
                    tvOpenDoorName.setText(result.getData().get(position).getGroupName());
                    Id = result.getData().get(position).getDoorAccessId();
                    areaId = result.getData().get(position).getAreaId();
                    requestCode(result.getData().get(position).getDoorAccessId(), result.getData().get(position).getAreaId());

                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestCode(String id, int areaId) {
        OpenInterface openInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OpenInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("doorAccessId", id);
        map.put("areaId", areaId + "");
        map.put("minutes", "30");
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = openInterface.getOpenDoorCode(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                JSONObject data = object.optJSONObject("data");
                String qrCode = data.getString("qrCode");
                decode = Base64.decode(qrCode, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                imgOpenDoorCode.setImageBitmap(bitmap);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hidden) {
            if (data == null || data.size() == 0) {
                return;
            }
            if (hour > 8 && hour < 18) {
                Utils.setBrightness(getActivity(), screenBrightness);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            if (data == null || data.size() == 0) {
                return;
            }
            if (hour > 8 && hour < 18) {
                Utils.setBrightness(getActivity(), 180);
            }
        }
    }


    @OnClick({R.id.rl_choose_smallCommunity, R.id.tv_open_door, R.id.tv_share_code, R.id.ll_code})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_code:
                initData();
                break;
            case R.id.rl_choose_smallCommunity:
                if (data != null && data.size() > 0) {
                    intent.setClass(mContext, OpenDoorAddressChooseActivity.class);
                    intent.putExtra("position", position);
                    intent.putParcelableArrayListExtra("data", data);
                    startActivityForResult(intent, OPEN_DOOR_REQUEST_CODE);
                }
                break;
            case R.id.tv_open_door:
                new Thread(() -> {
                    RightsResponseMode responseMode = UnlockManager.getInstance().getLockRights(mSession.getRegisterMobile(), "12628", 0);
                    if (responseMode!=null){
                        rights = responseMode.getRights();
                    }
                    getActivity().runOnUiThread(() -> {
                        if (rights != null && rights.size() > 0) {
                            intent.setClass(mContext, OpenDoorChooseActivity.class);
                            intent.putExtra("rights", rights);
                            intent.putExtra("areaId", areaId);
                            startActivity(intent);
                        }
                    });
                }).start();
                break;
            case R.id.tv_share_code:
                if (decode != null && decode.length > 0) {
                    intent.setClass(mContext, OpenDoorShareActivity.class);
                    intent.putExtra("code", decode);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "暂无可分享二维码！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == OPEN_DOOR_ADDRESS_RESULT) {
            position = data.getIntExtra("position", 0);
            areaId = data.getIntExtra("areaId", 0);
            Id = data.getStringExtra("Id");
            tvOpenDoorAddress.setText(this.data.get(position).getSmallCommunityName());
            tvOpenDoorName.setText(this.data.get(position).getGroupName());
            initData();
        }
    }
}














