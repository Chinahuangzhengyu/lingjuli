package com.zhjl.qihao.propertyservicerepair.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.PhotoMultipleActivity;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
import com.zhjl.qihao.propertyservicerepair.api.PropertyRepairInterface;
import com.zhjl.qihao.propertyservicerepair.bean.PropertyRepairInfoBean;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONException;
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

public class PropertyRepairFragment extends VolleyBaseFragment {

    @BindView(R.id.rl_property_repair_type)
    RelativeLayout rlPropertyRepairType;
    @BindView(R.id.rl_repair_address)
    RelativeLayout rlRepairAddress;
    @BindView(R.id.et_repair_content)
    EditText etRepairContent;
    @BindView(R.id.tv_text_count)
    TextView tvTextCount;
    @BindView(R.id.gv_upload_repair_img)
    GridViewForScrollView gvUploadRepairImg;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.tv_choose_type)
    TextView tvChooseType;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    private View view;
    private MyUpLoadAdapter imgAdapter;
    public static final int REQUEST_ADD_PHOTO = 1;
    public static final int REQUEST_CODE = 0x2;
    private List<String> imgIdList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    List<Boolean> imgDeleteShow = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private List<String> repairAddressLists = new ArrayList<>();
    private List<String> propertyTypeLists = new ArrayList<>();
    private int typeId;
    private String smallCommunityCode;
    private List<PropertyRepairInfoBean.DataBean.TypesBean> types;
    private List<PropertyRepairInfoBean.DataBean.SmallCommunityListBean> smallCommunityList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_property_repair, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        rlLoading.setVisibility(View.VISIBLE);
        initData();
        etRepairContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTextCount.setText(etRepairContent.getText().toString().trim().length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgAdapter = new MyUpLoadAdapter();
        gvUploadRepairImg.setAdapter(imgAdapter);
        gvUploadRepairImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int imgsize = imgAdapter.getCount();
                if (imgList.size() < 5 && arg2 <= 5 && arg2 + 1 == imgsize) {
                    tokephote();
                } else {

                    showImage(arg2);

                }

            }
        });
//        gvUploadRepairImg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (imgDeleteShow.size() > position) {
//                    boolean value = !imgDeleteShow.get(position);
//                    imgDeleteShow.set(position, value);
//                    imgAdapter.notifyDataSetChanged();
//                }
//
//                return true;
//            }
//        });
        return view;
    }

    private void initData() {
        PropertyRepairInterface repairInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyRepairInterface.class);
        Map<String, Object> map = new HashMap<>();
        if (mSession.getSmallCommunityCode() != null) {
            map.put("smallCommunityCode", mSession.getSmallCommunityCode());
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = repairInterface.propertyRepairInfo(body);
        fragmentRequestData(call, PropertyRepairInfoBean.class, new RequestResult<PropertyRepairInfoBean>() {
            @Override
            public void success(PropertyRepairInfoBean result, String message) {
                if (result.getData() != null) {
                    types = result.getData().getTypes();
                    if (types.size() > 0) {
                        tvChooseType.setText(types.get(0).getName());
                        typeId = types.get(0).getTypeId();
                        tvChooseType.setTextColor(Color.parseColor("#FF1F1F1F"));
                    }
                    for (int i = 0; i < types.size(); i++) {
                        propertyTypeLists.add(types.get(i).getName());
                    }
                    smallCommunityList = result.getData().getSmallCommunityList();
                    if (smallCommunityList.size() > 0) {
                        tvChooseAddress.setText(smallCommunityList.get(0).getSmallCommunityName());
                        tvChooseAddress.setTextColor(Color.parseColor("#FF1F1F1F"));
                        smallCommunityCode = smallCommunityList.get(0).getSmallCommunityCode();
                    }
                    for (int i = 0; i < smallCommunityList.size(); i++) {
                        repairAddressLists.add(smallCommunityList.get(i).getSmallCommunityName());
                    }
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
            }
        });
    }

    public class MyUpLoadAdapter extends BaseAdapter {
        private LayoutInflater inflater = LayoutInflater.from(mContext);

        @Override
        public int getCount() {
            if (imgList.size() < 5 && imgList.size() > 0) {
                return imgList.size() + 1;
            } else if (imgList.size() <= 0) {
                return 1;
            } else {
                return imgList.size();
            }
        }

        @Override
        public Object getItem(int arg0) {
            // TODO 自动生成的方法存根
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO 自动生成的方法存根
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup arg2) {
            convertView = inflater.inflate(R.layout.item_imgview, null);
            RoundImageView img_pic = (RoundImageView) convertView
                    .findViewById(R.id.img_pic);
            ImageView iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            ImageView iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

            iv_add.setVisibility(View.VISIBLE);
            if (imgList.size() >= 1 && position < imgList.size()) {
                iv_delete.setVisibility(View.VISIBLE);
                img_pic.setVisibility(View.VISIBLE);
                PictureHelper.showPictureWithSquare(mContext, img_pic,
                        imgList.get(position));
                iv_add.setVisibility(View.GONE);
            }

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgList.remove(position);
                    imgIdList.remove(position);
                    imgDeleteShow.remove(position);
                    imgAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_property_repair_type, R.id.rl_repair_address, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_property_repair_type:
                if (propertyTypeLists.size() == 0) {
                    Toast.makeText(mContext, "该小区暂未开通物业报修！", Toast.LENGTH_SHORT).show();
                    return;
                }
                initPropertyRepairPop(propertyTypeLists, tvChooseType);
                break;
            case R.id.rl_repair_address:
                if (repairAddressLists.size() == 0) {
                    Toast.makeText(mContext, "暂无报修地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                initPropertyRepairPop(repairAddressLists, tvChooseAddress);
                break;
            case R.id.btn_submit:
                if (etRepairContent.getText().toString().trim().equals("")){
                    Toast.makeText(mContext, "故障描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                btnSubmit.setEnabled(false);
                submitData();
                break;
        }
    }

    private void submitData() {
        PropertyRepairInterface repairInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyRepairInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("description", etRepairContent.getText().toString().trim());
        map.put("repairAddress", tvChooseAddress.getText().toString());
        map.put("repairTypeId", typeId);
        map.put("smallCommunityCode", smallCommunityCode);
        RequestBody body = ParamForNet.putContainsArray(map, "pictures", imgIdList);
        Call<ResponseBody> call = repairInterface.propertyRepairSubmit(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                refreshData.refresh();
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                JSONObject data = object.optJSONObject("data");
                int id = data.optInt("id");
                Intent intent = new Intent();
                etRepairContent.getText().clear();
                imgList.clear();
                imgDeleteShow.clear();
                imgAdapter.notifyDataSetChanged();
                intent.setClass(mContext, RepairDetailActivity.class);
                intent.putExtra("repairId",id);
                startActivityForResult(intent,REQUEST_CODE);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                btnSubmit.setEnabled(true);
            }

            @Override
            public void fail() {
                btnSubmit.setEnabled(true);
            }
        });
    }

    public RefreshData refreshData;

    public void setRefreshData(RefreshData refreshData) {
        this.refreshData = refreshData;
    }

    public interface RefreshData {
        void refresh();
    }

    private void initPropertyRepairPop(final List<String> list, final TextView tv) {
        /** 隐藏软键盘 **/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        //条件选择器
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (list.size()>0){
                    if (tv == tvChooseType) {
                        typeId = types.get(options1).getTypeId();
                    }
                    if (tv == tvChooseAddress) {
                        smallCommunityCode = smallCommunityList.get(options1).getSmallCommunityCode();
                    }
                    tv.setText(list.get(options1));
                    tv.setTextColor(Color.parseColor("#FF1F1F1F"));
                }
            }
        }).setLayoutRes(R.layout.pop_choose_room, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tvSure = v.findViewById(R.id.tv_sure);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.returnData();
                        pvOptions.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.dismiss();
                    }
                });
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    public void tokephote() {
        Intent takePictureIntent = new Intent(mContext,
                PhotoMultipleActivity.class);
        takePictureIntent.putExtra("photonums", 5);
        takePictureIntent.putExtra("type", imgList.size());
        takePictureIntent.putExtra("size", imgList.size());
        startActivityForResult(takePictureIntent, REQUEST_ADD_PHOTO);
    }

    public void showImage(int index) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        String[] strings = new String[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            if (!imgList.get(i).contains("_small")) {
                strings[i] = imgList.get(i);
            } else {
                String[] split = imgList.get(i).split("\\.");
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < split.length; j++) {
                    if (split[j].endsWith("_small")) {
                        split[j] = split[j].substring(0, split[j].length() - 6);
                    }
                    if (j == split.length - 1) {
                        sb.append(split[j]);
                    } else {
                        sb.append(split[j] + ".");
                    }
                }
                strings[i] = sb.toString();
            }
        }
        it.putExtra("urls", strings);
        it.putExtra("nowImage", imgList.get(index));
//		dialog.dismiss();
        startActivity(it);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && data != null) {
            List<String> mSamllPathList = (List<String>) data.getExtras()
                    .getSerializable("samllPath");
            Log.i("111111", mSamllPathList + "");
            for (int i = 0; i < mSamllPathList.size(); i++) {
                String url = mSamllPathList.get(i);
                if (url != null) {

                    imgList.add(url);
                    imgDeleteShow.add(false);
                }
            }
            imgIdList.addAll((List<String>) data.getExtras().getSerializable(
                    ("imageId")));
            imgAdapter.notifyDataSetChanged();
        }
//        else if (requestCode == 10 && data != null) {
//            finish();
//        }
//
//        else if (requestCode == REQUEST_PERMISSION_SETTING) {
//            if (RequestPermissionUtils.checkPermission(this, new String[]{Manifest.permission.RECORD_AUDIO})) {
//                startSpeech();
//            } else {
//                Toast.makeText(mContext, "设置麦克风权限失败！", Toast.LENGTH_SHORT).show();
//            }
//        }

    }

}
