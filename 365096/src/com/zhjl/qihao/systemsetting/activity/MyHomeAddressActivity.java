package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.integration.utils.PopWindowUtils;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.systemsetting.bean.HomeDetailBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

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

import static com.zhjl.qihao.systemsetting.activity.AddHomeAddressBindingActivity.UPDATE_HOME;

public class MyHomeAddressActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_home_name)
    TextView tvHomeName;
    @BindView(R.id.tv_number_update)
    TextView tvNumberUpdate;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_small_community)
    TextView tvSmallCommunity;
    @BindView(R.id.tv_home_number)
    TextView tvHomeNumber;
    @BindView(R.id.tv_home_type)
    TextView tvHomeType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.btn_update_home)
    Button btnUpdateHome;
    @BindView(R.id.gv_img_upload)
    GridViewForScrollView gvImgUpload;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    private String residentId = ""; //入住id
    private MyUpLoadAdapter imgAdapter;
    private List<HomeDetailBean.DataBean.PicturesBean> data = new ArrayList<>();
    private int residentType;
    private ArrayList<HomeDetailBean.DataBean.PicturesBean> pictures = new ArrayList<>();
    private SettingInterface settingInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_address);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "我的住所", this);
        settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
        tvRight.setTextSize(18);
        residentId = getIntent().getStringExtra("residentId");
        initData();
        imgAdapter = new MyUpLoadAdapter();
        gvImgUpload.setAdapter(imgAdapter);
        gvImgUpload.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            showImage(arg2);
        });

    }

    private void initData() {
        SettingInterface settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", residentId);
        Call<ResponseBody> call = settingInterface.getResidentDetail(map);
        activityRequestData(call, HomeDetailBean.class, new RequestResult<HomeDetailBean>() {
            @Override
            public void success(HomeDetailBean result, String message) throws Exception {
                tvHomeName.setText(result.getData().getResidentName());
                tvNumber.setText(result.getData().getMobile());
                if (result.getData().getRoomInfo() != null) {
                    tvHomeNumber.setText(result.getData().getRoomInfo().getRoomName());
                }
                residentType = result.getData().getResidentType();
                if (residentType == 1) {
                    tvHomeType.setText("业主");
                } else if (residentType == 2) {
                    tvHomeType.setText("家人");
                } else {
                    tvHomeType.setText("租客");
                }
                if (result.getData().getSmallCommunityInfo() != null) {
                    tvSmallCommunity.setText(result.getData().getSmallCommunityInfo().getSmallCommunityName());
                }
                if (residentType == 1 && result.getData().getPictures() != null && result.getData().getPictures().size() > 0) {
                    pictures = result.getData().getPictures();
                    llType.setVisibility(View.VISIBLE);
                    imgAdapter.addData(result.getData().getPictures());
                } else {
                    llType.setVisibility(View.GONE);
                }
                if (result.getData().getStatus() == 0) {
                    tvRight.setText("解绑");
                    tvName.setText("住所绑定审核中");
                    btnUpdateHome.setVisibility(View.VISIBLE);
                } else if (result.getData().getStatus() == 1) {
                    tvRight.setText("解绑");
                    btnUpdateHome.setVisibility(View.GONE);
                    tvName.setText("住所绑定成功");
                } else {
                    tvRight.setText("重新申请");
                    btnUpdateHome.setVisibility(View.VISIBLE);
                    tvName.setText("住所绑定审核未通过");
                }
            }

            @Override
            public void fail() {

            }
        });
    }


    /**
     * 查看本地图片
     */
    public void showImage() {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        it.putExtra("urls", new String[1]);
        it.putExtra("localPic", R.drawable.img_contract);
        it.putExtra("isLocal", true);
        it.putExtra("index", 0);
        startActivity(it);
    }

    public void showImage(int index) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        String[] strings = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            strings[i] = data.get(i).getFilename();
        }
        it.putExtra("urls", strings);
        it.putExtra("index", index);
        it.putExtra("nowImage", data.get(index).getFilename());
        startActivity(it);
    }

    public class MyUpLoadAdapter extends BaseAdapter {
        private LayoutInflater inflater = LayoutInflater.from(mContext);

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO 自动生成的方法存根
            return data.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO 自动生成的方法存根
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup arg2) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.home_item_pic, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PictureHelper.setImageView(mContext, data.get(position).getFilename(), holder.imgHome, R.drawable.img_loading);
            return convertView;
        }

        public void addData(List<HomeDetailBean.DataBean.PicturesBean> pictures) {
            data = pictures;
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.img_home)
            RoundImageView imgHome;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.tv_number_update, R.id.iv_back, R.id.tv_right, R.id.btn_update_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_number_update:
                Intent intent = new Intent(mContext, UpdateNumberActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                if (tvRight.getText().toString().equals("解绑")) {
                    PopWindowUtils.getInstance().show("您是否确认解绑？", this);
                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> requestUnBuilding());
                } else {
                    PopWindowUtils.getInstance().show("您是否确认重新申请？", this);
                    PopWindowUtils.getInstance().setSetYesOnClickListener(() -> requestUpdate());

                }
                break;
            case R.id.btn_update_home:
                Intent intent1 = new Intent(mContext, AddHomeAddressBindingActivity.class);
                intent1.putExtra("isUpdate", true);
                intent1.putExtra("name", tvHomeName.getText());
                intent1.putExtra("community", tvSmallCommunity.getText());
                intent1.putExtra("room", tvHomeNumber.getText());
                intent1.putExtra("userType", residentType);
                intent1.putExtra("residentId", residentId);
                intent1.putParcelableArrayListExtra("pic", pictures);
                startActivityForResult(intent1, 0x3);
                break;
        }
    }

    /**
     * 重新申请
     */
    private void requestUpdate() {
        List<String> listId = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
            listId.add(String.valueOf(pictures.get(i).getOssId()));
        }
        Call<ResponseBody> call = RequestUtils.updateRoomInfo(tvHomeName.getText().toString(), residentId, residentType, listId, settingInterface);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, "申请成功！", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 解绑房间
     */
    private void requestUnBuilding() {
        Map<String, Object> map = new HashMap<>();
        map.put("residentId", residentId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.unBuildingRoom(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, "解绑成功！", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UPDATE_HOME) {
            finish();
        }
    }
}
