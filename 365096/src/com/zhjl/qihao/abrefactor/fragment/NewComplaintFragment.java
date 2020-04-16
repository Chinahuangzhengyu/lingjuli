package com.zhjl.qihao.abrefactor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.PhotoMultipleActivity;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.propertyservicecomplaint.api.ComplaintInterface;
import com.zhjl.qihao.propertyservicepay.api.PropertyPayInterface;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.propertyservicerepair.activity.RepairDetailActivity;
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

public class NewComplaintFragment extends VolleyBaseFragment {

    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_complaint_type)
    TextView tvComplaintType;
    @BindView(R.id.rb_proposal)
    RadioButton rbProposal;
    @BindView(R.id.rb_complaint)
    RadioButton rbComplaint;
    @BindView(R.id.rg_complaint_type)
    RadioGroup rgComplaintType;
    @BindView(R.id.rl_property_complaint_type)
    RelativeLayout rlPropertyComplaintType;
    @BindView(R.id.tv_choose_name)
    TextView tvChooseName;
    @BindView(R.id.rl_complaint_address)
    LinearLayout rlComplaintAddress;
    @BindView(R.id.et_complaint_content)
    EditText etComplaintContent;
    @BindView(R.id.tv_text_count)
    TextView tvTextCount;
    @BindView(R.id.gv_upload_complaint_img)
    GridViewForScrollView gvUploadComplaintImg;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    Unbinder unbinder;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.et_choose_address)
    EditText etChooseAddress;
    @BindView(R.id.rl_complaint_name)
    RelativeLayout rlComplaintName;
    private View view;
    public static final int REQUEST_ADD_PHOTO = 11111;
    private List<String> repairAddressLists;
    private List<String> imgIdList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    List<Boolean> imgDeleteShow = new ArrayList<>();
    private MyUpLoadAdapter imgAdapter;
    private OptionsPickerView pvOptions;
    private List<String> communityList;
    private String labelTypeId;
    private int type = 1;
    private int currentPosition = 0;
    private List<UserRoomListBean.DataBean> data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.new_complaint_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        repairAddressLists = new ArrayList<>();
        rlLoading.setVisibility(View.VISIBLE);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initSendMessage();
        etComplaintContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTextCount.setText(etComplaintContent.getText().toString().trim().length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgAdapter = new MyUpLoadAdapter();
        gvUploadComplaintImg.setAdapter(imgAdapter);
        gvUploadComplaintImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        rgComplaintType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbComplaint.getId()) {
                    type = 0;
                } else if (checkedId == rbProposal.getId()) {
                    type = 1;
                }
            }
        });

//        gvUploadComplaintImg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private void initSendMessage() {
        PropertyPayInterface payInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyPayInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = payInterface.userRoomList(body);
        fragmentRequestData(call, UserRoomListBean.class, new RequestResult<UserRoomListBean>() {
            @Override
            public void success(UserRoomListBean result, String message) {
                if (result.getData() != null && result.getData().size() > 0) {
                    data = result.getData();
                    if (data.size() > 0) {
                        tvChooseName.setText(data.get(0).getSmallCommunityName() + data.get(0).getRoomName());
                        tvChooseName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                    }
                    repairAddressLists.clear();
                    for (int i = 0; i < data.size(); i++) {
                        repairAddressLists.add(data.get(i).getSmallCommunityName() + data.get(i).getRoomName());
                    }
                } else {
                    Toast.makeText(mContext, "获取入住房间失败！", Toast.LENGTH_SHORT).show();
                }
                rlLoading.setVisibility(View.GONE);
            }

            @Override
            public void fail() {
                rlLoading.setVisibility(View.GONE);
            }
        });
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

    private void requestNote() {
        if (data == null || data.size() == 0) {
            Toast.makeText(mContext, "暂无入住房间，无法投诉！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etChooseAddress.getText().toString().trim().length() == 0) {
            Toast.makeText(mContext, "请填写投诉地址！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etComplaintContent.getText().toString().trim().equals("")) {
            Toast.makeText(mContext, "请填写问题描述！", Toast.LENGTH_SHORT).show();
            return;
        }
        ComplaintInterface complaintInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("roomId", data.get(currentPosition).getRoomId());
        map.put("roomName", data.get(currentPosition).getRoomName());
        map.put("address", etChooseAddress.getText().toString().trim());
        map.put("smallCommunityCode", data.get(currentPosition).getSmallCommunityCode());
        map.put("smallCommunityName", data.get(currentPosition).getSmallCommunityName());
        map.put("description", etComplaintContent.getText().toString().trim());
        RequestBody body = ParamForNet.putContainsArray(map, "pictures", imgIdList);
        Call<ResponseBody> call = complaintInterfaces.createComplaint(body);
        fragmentRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                JSONObject data = object.optJSONObject("data");
                int id = data.optInt("id");
                Intent intent = new Intent();
                intent.setClass(mContext, RepairDetailActivity.class);
                intent.putExtra("isComplaint", true);
                etComplaintContent.getText().clear();
                imgList.clear();
                imgDeleteShow.clear();
                imgAdapter.notifyDataSetChanged();
                intent.putExtra("id", id);
                startActivity(intent);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail() {

            }
        });
    }


    @OnClick({R.id.tv_submit, R.id.rl_complaint_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                requestNote();
                break;
            case R.id.rl_complaint_name:
                initAddressPop();
                break;
        }
    }

    public interface RefreshData {
        void refresh();
    }

    private void initAddressPop() {
        //条件选择器
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tvChooseName.setText(repairAddressLists.get(options1));
                tvChooseName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
                if (data != null && data.size() > 0) {
                    currentPosition = options1;
                }

            }
        }).setLayoutRes(R.layout.pop_choose_room, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tvChooseRoom = v.findViewById(R.id.tv_sure);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvChooseRoom.setOnClickListener(new View.OnClickListener() {
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
        pvOptions.setPicker(repairAddressLists);
        pvOptions.show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data != null) {
            List<String> mSamllPathList = (List<String>) data.getExtras()
                    .getSerializable("samllPath");
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
