package com.zhjl.qihao.systemsetting.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.michoi.unlock.UnlockManager;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.activity.ChooseSmallCity;
import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.ButtonRepect;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.NewPhotoMultipleActivity;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.FileUploadBean;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.systemsetting.bean.AllCommunityBean;
import com.zhjl.qihao.systemsetting.bean.BuildingBean;
import com.zhjl.qihao.systemsetting.bean.HomeDetailBean;
import com.zhjl.qihao.systemsetting.bean.RoomsBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
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

public class AddHomeAddressBindingActivity extends VolleyBaseActivity {
    @BindView(R.id.tv_choose_small_community)
    TextView tvChooseSmallCommunity;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.lv_address_type)
    ListViewForScrollView lvAddressType;
    @BindView(R.id.btn_binding)
    Button btnBinding;
    @BindView(R.id.tv_small_name)
    TextView tvSmallName;
    @BindView(R.id.tv_building_name)
    TextView tvBuildingName;
    @BindView(R.id.tv_choose_building)
    TextView tvChooseBuilding;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.tv_choose_room)
    TextView tvChooseRoom;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.img_user_type)
    ImageView imgUserType;
    @BindView(R.id.tv_user_type)
    TextView tvUserType;
    @BindView(R.id.rl_user_type)
    RelativeLayout rlUserType;
    @BindView(R.id.gv_img_upload)
    GridViewForScrollView gvImgUpload;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.rl_building)
    RelativeLayout rlBuilding;
    private SettingInterface settingInterface;
    private List<String> smallCommunityList = new ArrayList<>();    //小区数据
    private List<String> buildingList = new ArrayList<>();    //楼栋数据
    private List<String> roomList = new ArrayList<>();    //房间数据
    private List<AllCommunityBean.DataBean> data = new ArrayList<>();
    private OptionsPickerView communityPickerView;
    private List<BuildingBean.DataBean> buildingLists = new ArrayList<>();
    private List<RoomsBean.DataBean> roomsList = new ArrayList<>();
    private ArrayList<LoginBean.DataBean.UserInfoBean.UserRoomsBean> rooms = new ArrayList<>();
    private boolean isClick = false;
    private String roomId;
    private String smallCommunityCode;
    private UserTypeAdapter userTypeAdapter;
    private Map<Integer, String> list = new HashMap<>();
    private int userType = 1;
    private int currentPosition;
    private int selectPosition;//住户类型选中的下标
    private int currentBuildPosition;
    private boolean isRegister;
    private boolean isPopAddress;
    public static final int BINDING_ADDRESS_SUCCESS = 0x167;
    public static final int REQUEST_ADD_PHOTO = 2;
    public static final int UPDATE_HOME = 3;
    private ProgressDialog dialog;
    private List<String> imgIdList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    private FileUploadBean.DataBean resultData;
    private MyUpLoadAdapter imgAdapter;
    private boolean isUpdate;
    private ArrayList<HomeDetailBean.DataBean.PicturesBean> pic;
    private String residentId;
    private boolean isFirst = true; //是否是第一次上传图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_address_binding);
        ButterKnife.bind(this);
        isRegister = getIntent().getBooleanExtra("isRegister", false);
        isPopAddress = getIntent().getBooleanExtra("isPopAddress", false);
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        imgList.add("");    //设置图例站位图
        imgList.add("");
        imgIdList.add("");
        imgIdList.add("");
        if (isUpdate) {
            NewHeaderBar.createCommomBack(this, "修改住所绑定", this);
            String name = getIntent().getStringExtra("name");
            pic = getIntent().getParcelableArrayListExtra("pic");
            for (int i = 0; i < pic.size(); i++) {
                imgList.add(pic.get(i).getFilename());
                imgIdList.add(String.valueOf(pic.get(i).getOssId()));
            }
            String community = getIntent().getStringExtra("community");
            residentId = getIntent().getStringExtra("residentId");
            String room = getIntent().getStringExtra("room");
            userType = getIntent().getIntExtra("userType", 0);
            if (userType == 1) {
                selectPosition = 0;
                tvUserType.setText("业主");
                llType.setVisibility(View.VISIBLE);
            } else if (userType == 2) {
                selectPosition = 1;
                tvUserType.setText("家人");
                llType.setVisibility(View.GONE);
            } else {
                selectPosition = 2;
                tvUserType.setText("租客");
                llType.setVisibility(View.GONE);
            }
            tvChooseSmallCommunity.setText(community);
            tvChooseSmallCommunity.setEnabled(false);
            tvChooseSmallCommunity.setCompoundDrawables(null, null, null, null);
            tvChooseRoom.setText(room);
            tvChooseRoom.setEnabled(false);
            tvChooseRoom.setCompoundDrawables(null, null, null, null);
            etName.setText(name);
            rlBuilding.setVisibility(View.GONE);
        } else {
            NewHeaderBar.createCommomBack(this, "住所绑定", this);
            initData();
            requestRoomList();
        }
        list.put(1, "业主");
        list.put(2, "家人");
        list.put(3, "租客");
//        if (imgList.size() == 0) {
//            imgList.add("");    //设置默认图
//            imgList.add("");
//        } else {
//            isFirst = false;
//        }
        initUploadData();
        userTypeAdapter = new UserTypeAdapter();
        lvAddressType.setAdapter(userTypeAdapter);
        lvAddressType.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    LinearLayout llItem = (LinearLayout) lvAddressType.getChildAt(i);
                    LinearLayout llItemContent = (LinearLayout) llItem.getChildAt(0);
                    TextView tvItem = (TextView) llItemContent.getChildAt(0);
                    tvItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_6));
                    tvItem.setTextColor(Color.parseColor("#ffffff"));
                    tvUserType.setText(list.get(i + 1));
                    userType = i + 1;
                    if (userType == 1) {
                        llType.setVisibility(View.VISIBLE);
                        tvMessage.setText("注：业主增加住所提交后平台客服专员会在24小时内以短信告知您增加结果，请注意查收。家人或租客增加住所请提醒业主审核");
                    } else {
                        llType.setVisibility(View.GONE);
                        tvMessage.setText("注：请填写真实信息，以便后期门禁使用。绑定提交后平台客服专员会在24小时内以短信告知您申请结果，请注意查收！");
                    }
                } else {
                    LinearLayout llItem = (LinearLayout) lvAddressType.getChildAt(i);
                    LinearLayout llItemContent = (LinearLayout) llItem.getChildAt(0);
                    TextView tvItem = (TextView) llItemContent.getChildAt(0);
                    tvItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_white_6));
                    tvItem.setTextColor(Color.parseColor("#1F1F1F"));
                }
            }
        });
        imgAdapter = new MyUpLoadAdapter();
        gvImgUpload.setAdapter(imgAdapter);
        gvImgUpload.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            int imgsize = imgAdapter.getCount();
            if (imgList.size() < 7 && arg2 < 7 && arg2 + 1 == imgsize) {
                tokephote();
            } else {
//                if (imgList.get(arg2).equals("")) {
//                    showLocalImage(arg2);
//                } else {
//                    showImage(arg2);
//                }
                showImage(arg2);
            }
        });
    }

    public void tokephote() {
        if (resultData == null) {
            Toast.makeText(mContext, "签名获取失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent takePictureIntent = new Intent(mContext,
                NewPhotoMultipleActivity.class);
        takePictureIntent.putExtra("photonums", 5);
        takePictureIntent.putExtra("type", imgList.size());
        takePictureIntent.putExtra("size", imgList.size() - 2);
        takePictureIntent.putExtra("fileSign", resultData);
        startActivityForResult(takePictureIntent, REQUEST_ADD_PHOTO);
    }

    /**
     * 查看本地图片
     */
    public void showLocalImage(int position) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        it.putExtra("urls", new String[2]);
        it.putExtra("localPic", new int[]{R.drawable.img_contract, R.drawable.img_contract2});
        it.putExtra("isLocal", true);
        it.putExtra("index", position);
        startActivity(it);
    }

    public void showImage(int index) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        String[] strings = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            strings[i] = imgList.get(i);
        }
        it.putExtra("urls", strings);
        it.putExtra("index", index);
        it.putExtra("localPic", new int[]{R.drawable.img_contract, R.drawable.img_contract2});
        it.putExtra("isLocal", true);
        it.putExtra("nowImage", imgList.get(index));
        startActivity(it);
    }


    public class MyUpLoadAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (imgList.size() < 7 && imgList.size() > 0) {
                return imgList.size() + 1;
            } else if (imgList.size() <= 0) {
                return imgList.size() + 1;
            } else {
                return imgList.size();
            }
        }

        @Override
        public Object getItem(int arg0) {
            // TODO 自动生成的方法存根
            return imgList.get(arg0);
        }


        @Override
        public long getItemId(int arg0) {
            // TODO 自动生成的方法存根
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.home_item_imgview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ivAdd.setVisibility(View.VISIBLE);
            if (imgList.size() >= 1 && position < imgList.size()) {
                holder.imgPic.setVisibility(View.VISIBLE);
                if (imgList.get(position).equals("") && position == 0) {
                    holder.ivDelete.setVisibility(View.GONE);
                    holder.imgPic.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_contract));
                } else if (imgList.get(position).equals("") && position == 1) {
                    holder.ivDelete.setVisibility(View.GONE);
                    holder.imgPic.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_contract_two));
                } else {
                    holder.ivDelete.setVisibility(View.VISIBLE);
                    PictureHelper.showPictureWithSquare(mContext, holder.imgPic,
                            imgList.get(position));
                }
                holder.ivAdd.setVisibility(View.GONE);
            } else {
                holder.ivDelete.setVisibility(View.GONE);
                holder.imgPic.setVisibility(View.GONE);
            }
            holder.ivDelete.setOnClickListener(v -> {
                imgList.remove(position);
                imgIdList.remove(position);
                imgAdapter.notifyDataSetChanged();
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.img_pic)
            RoundImageView imgPic;
            @BindView(R.id.iv_add)
            ImageView ivAdd;
            @BindView(R.id.iv_delete)
            ImageView ivDelete;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void requestRoomList() {
        PropertyPayInterface propertyPayInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Call<ResponseBody> call = propertyPayInterface.userRoomList(ParamForNet.put(new HashMap<String, Object>()));
        activityRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData().size() > 0) {
                    rooms.clear();
                    for (int i = 0; i < result.getData().size(); i++) {
                        LoginBean.DataBean.UserInfoBean.UserRoomsBean roomsBean = new LoginBean.DataBean.UserInfoBean.UserRoomsBean();
                        roomsBean.setRoomName(result.getData().get(i).getRoomName());
                        roomsBean.setRoomId(result.getData().get(i).getRoomId());
                        roomsBean.setSmallCommunityName(result.getData().get(i).getSmallCommunityName());
                        rooms.add(roomsBean);
                    }
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        RequestBody body = ParamForNet.put(new HashMap<String, Object>());
        Call<ResponseBody> call = settingInterface.getAllCommunityList(body);
        activityRequestData(call, AllCommunityBean.class, new RequestResult<AllCommunityBean>() {
            @Override
            public void success(AllCommunityBean result, String message) {
                data = result.getData();
                for (int i = 0; i < result.getData().size(); i++) {
                    smallCommunityList.add(result.getData().get(i).getSmallCommunityName());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 获取上传文件签名
     */
    private void initUploadData() {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("directory", "resident-apply");
        map.put("type", 1);
        Call<ResponseBody> call = nearByInterfaces.getFileSign(map);
        activityRequestData(call, FileUploadBean.class, new RequestResult<FileUploadBean>() {
            @Override
            public void success(FileUploadBean result, String message) throws Exception {
                resultData = result.getData();
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_binding, R.id.tv_choose_small_community, R.id.tv_choose_building, R.id.tv_choose_room, R.id.rl_user_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_binding:
                if (isUpdate) {
                    boolean fastClick = ButtonRepect.isFastClick();
                    if (!fastClick) {
                        return;
                    }
                    requestUpdate();
                } else {
                    String chooseSmall = tvChooseSmallCommunity.getText().toString().trim();
                    String chooseBuilding = tvChooseBuilding.getText().toString().trim();
                    String chooseRoom = tvChooseRoom.getText().toString().trim();
                    if (chooseSmall.equals("请选择小区") || chooseSmall.equals("")) {
                        Toast.makeText(mContext, "请先选择小区！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (chooseBuilding.equals("请选择楼栋") || chooseBuilding.equals("")) {
                        Toast.makeText(mContext, "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (chooseRoom.equals("请选择房间") || chooseRoom.equals("")) {
                        Toast.makeText(mContext, "请选择房间！", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etName.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, "请输入真实姓名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requestResident();
                }
                break;
            case R.id.tv_choose_small_community:    //选择小区
                initSmallCommunityPop(smallCommunityList, tvChooseSmallCommunity);
                break;
            case R.id.tv_choose_building:       //选择楼栋
                String content = tvChooseSmallCommunity.getText().toString().trim();
                if (!content.equals("请选择小区") && !content.equals("")) {
                    initSmallCommunityPop(buildingList, tvChooseBuilding);
                } else {
                    Toast.makeText(mContext, "请先选择小区！", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.tv_choose_room:       //选择房间
                Utils.hideInput(this);
                String smallContent = tvChooseSmallCommunity.getText().toString().trim();
                String buildingContent = tvChooseBuilding.getText().toString().trim();
                if (smallContent.equals("请选择小区") || smallContent.equals("")) {
                    Toast.makeText(mContext, "请先选择小区！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (buildingContent.equals("请选择楼栋") || buildingContent.equals("")) {
                    Toast.makeText(mContext, "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                initSmallCommunityPop(roomList, tvChooseRoom);
                break;
            case R.id.rl_user_type:
                Utils.hideInput(this);
                isClick = !isClick;
                if (isClick) {
                    Utils.rotateAnim(isClick, imgUserType);
                    lvAddressType.setVisibility(View.VISIBLE);
                } else {
                    Utils.rotateAnim(isClick, imgUserType);
                    lvAddressType.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void requestUpdate() {
        Call<ResponseBody> call = RequestUtils.updateRoomInfo(etName.getText().toString().trim(), residentId, userType, imgIdList, settingInterface);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                setResult(UPDATE_HOME, getIntent());
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestResident() {
        btnBinding.setEnabled(false);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("绑定中。。。");
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("name", etName.getText().toString().trim());
        map.put("roomId", roomId);
        map.put("smallCommunityCode", smallCommunityCode);
        map.put("type", userType);
        RequestBody body;
        if (userType == 1) {
            body = ParamForNet.putContainsArray(map, "pictures", imgIdList);
        } else {
            body = ParamForNet.put(map);
        }
        Call<ResponseBody> call = settingInterface.userResident(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                JSONObject data = object.optJSONObject("data");
                boolean occupy = data.optBoolean("occupy");
                Intent intent = new Intent();
                String occupyPhone = data.optString("occupyPhone");
                boolean isDoorAccess = data.optBoolean("isDoorAccess");
                if (isDoorAccess) {
                    JSONObject registerInfo = data.getJSONObject("registerInfo");
                    String groupCode = registerInfo.optString("groupCode");
                    String username = registerInfo.optString("username");
                    String floorNo = registerInfo.optString("floorNo");
                    String roomNo = registerInfo.optString("roomNo");
                    String buildingNo = registerInfo.optString("buildingNo");
                    String unitNo = registerInfo.optString("unitNo");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            UnlockManager.getInstance().applyForRights(mSession.getRegisterMobile(), username, buildingNo, unitNo, floorNo, roomNo, groupCode, "");
                        }
                    }.start();
                }
                if (occupy) {       //是否绑定过
                    intent.setClass(mContext, AddressSureActivity.class);
                    intent.putExtra("phone", occupyPhone);
                    startActivity(intent);
                } else {
                    if (isRegister && rooms.size() > 1) {
                        intent.setClass(mContext, ChooseSmallCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (isRegister && rooms.size() <= 1) {
                        intent.setClass(mContext, RefactorMainActivity.class);
                        mSession.setSmallCommunityName(tvChooseSmallCommunity.getText().toString());
                        mSession.setSmallCommunityCode(AddHomeAddressBindingActivity.this.data.get(currentPosition).getSmallCommunityCode());
                        startActivity(intent);
                    } else {
                        if (isPopAddress) {
                            setResult(BINDING_ADDRESS_SUCCESS, getIntent());
                            finish();
                        } else {
                            //拿去房间列表最后一个residentId为解绑id
                            JSONArray rooms = data.optJSONArray("rooms");
                            JSONObject roomsObject = rooms.getJSONObject(rooms.length() - 1);
                            String residentId = roomsObject.getString("residentId");
                            intent.setClass(mContext, MyHomeAddressActivity.class);
                            intent.putExtra("name", etName.getText().toString().trim());
                            intent.putExtra("phone", occupyPhone);
                            intent.putExtra("smallCommunity", tvChooseSmallCommunity.getText().toString().trim());
                            intent.putExtra("room", tvChooseRoom.getText().toString().trim());
                            intent.putExtra("residentId", residentId);
                            intent.putExtra("userType", list.get(userType));
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                btnBinding.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void fail() {
                dialog.dismiss();
                btnBinding.setEnabled(true);
            }
        });
    }


    private void requestBuilding(String smallCommunityCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("smallCommunityCode", smallCommunityCode);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.getOneCommunityList(body);
        activityRequestData(call, BuildingBean.class, new RequestResult<BuildingBean>() {
            @Override
            public void success(BuildingBean result, String message) {
                buildingLists = result.getData();
                buildingList.clear();
                for (int i = 0; i < buildingLists.size(); i++) {
                    buildingList.add(buildingLists.get(i).getPartitionName());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestRooms(String partitionCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("partitionCode", partitionCode);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.getOneRoomList(body);
        activityRequestData(call, RoomsBean.class, new RequestResult<RoomsBean>() {
            @Override
            public void success(RoomsBean result, String message) {
                roomList.clear();
                roomsList = result.getData();
                for (int i = 0; i < roomsList.size(); i++) {
                    roomList.add(roomsList.get(i).getRoomName());
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initSmallCommunityPop(List<String> list, final TextView tvChoose) {
        Utils.hideInput(this);
        communityPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (tvChooseSmallCommunity == tvChoose) {
                    if (data.size() > 0) {
                        if (currentPosition != options1) {
                            tvChooseBuilding.setText("请选择楼栋");
                            tvChooseRoom.setText("请选择房间");
                            currentPosition = options1;
                        }
                        tvChoose.setText(smallCommunityList.get(options1));
                        smallCommunityCode = data.get(options1).getSmallCommunityCode();
                        requestBuilding(data.get(options1).getSmallCommunityCode());
                    }
                } else if (tvChooseBuilding == tvChoose) {
                    if (buildingLists.size() > 0) {
                        if (currentBuildPosition != options1) {
                            tvChooseRoom.setText("请选择房间");
                            currentBuildPosition = options1;
                        }
                        tvChoose.setText(buildingList.get(options1));
                        requestRooms(buildingLists.get(options1).getPartitionCode());
                    }
                } else if (tvChooseRoom == tvChoose) {
                    if (roomsList.size() > 0) {
                        tvChoose.setText(roomList.get(options1));
                        roomId = roomsList.get(options1).getRoomId();
                    }
                }
            }
        }).setLayoutRes(R.layout.pop_choose_time, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tvChooseRoom = v.findViewById(R.id.tv_submit);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvChooseRoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        communityPickerView.returnData();
                        communityPickerView.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        communityPickerView.dismiss();
                    }
                });
            }
        }).build();
        communityPickerView.setPicker(list);
        communityPickerView.show();
    }

    class UserTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.pay_time_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == selectPosition) {
                holder.tvPayTimeItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_6));
                holder.tvPayTimeItem.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.tvPayTimeItem.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_white_6));
                holder.tvPayTimeItem.setTextColor(Color.parseColor("#FF1F1F1F"));
            }
            holder.tvPayTimeItem.setText(list.get(position + 1));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_pay_time_item)
            TextView tvPayTimeItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data != null) {
            List<String> mSamllPathList = (List<String>) data.getExtras()
                    .getSerializable("samllPath");
//            if (isFirst) {
//                imgList.clear();
//            }
            for (int i = 0; i < mSamllPathList.size(); i++) {
                String url = mSamllPathList.get(i);
                if (url != null) {
                    imgList.add(url);
                }
            }
            imgIdList.addAll((List<String>) data.getExtras().getSerializable(
                    ("imageId")));
            imgAdapter.notifyDataSetChanged();
            isFirst = false;
        }

    }
}
