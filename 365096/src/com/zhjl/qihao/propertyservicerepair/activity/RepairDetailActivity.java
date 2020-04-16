package com.zhjl.qihao.propertyservicerepair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.propertyservicecomplaint.api.ComplaintInterface;
import com.zhjl.qihao.propertyservicecomplaint.bean.PropertyComplaintRecordBean;
import com.zhjl.qihao.propertyservicecomplaint.bean.PropertyListDetailBean;
import com.zhjl.qihao.propertyservicerepair.api.PropertyRepairInterface;
import com.zhjl.qihao.propertyservicerepair.bean.PropertyRepairRecordDetailBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class RepairDetailActivity extends VolleyBaseActivity {
    @BindView(R.id.ll_finish)
    LinearLayout llFinish;
    @BindView(R.id.btn_remind)
    Button btnRemind;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_head_left)
    LinearLayout llHeadLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_header_bar)
    RelativeLayout rlHeaderBar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_type)
    TextView tvNameType;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_number_content)
    TextView tvNumberContent;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_fault_type)
    TextView tvFaultType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.gv_photo)
    GridViewForScrollView gvPhoto;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.tv_result_time)
    TextView tvResultTime;
    @BindView(R.id.tv_result_person)
    TextView tvResultPerson;
    @BindView(R.id.tv_fault_case)
    TextView tvFaultCase;
    @BindView(R.id.rl_case)
    RelativeLayout rlCase;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.gv_finish_photo)
    GridViewForScrollView gvFinishPhoto;
    @BindView(R.id.tv_content_name)
    TextView tvContentName;
    @BindView(R.id.ll_remind)
    LinearLayout llRemind;
    @BindView(R.id.tv_finish_name)
    TextView tvFinishName;
    @BindView(R.id.rl_loading)
    FrameLayout rlLoading;
    public static final int RESULT_CODE = 0x3;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_address_detail_name)
    TextView tvAddressDetailName;
    @BindView(R.id.ll_complaint_address)
    LinearLayout llComplaintAddress;
    private int repairId;
    private List<PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean> repairPictures = new ArrayList<>();
    private DetailPhotoAdapter detailPhotoAdapter;
    private DetailPhotoAdapter resultPhotoAdapter;
    private List<PropertyRepairRecordDetailBean.DataBean.ResultsBean.ProcessPicsBean> processPics = new ArrayList<>();
    private PropertyComplaintRecordBean.DataBean data;
    private long id;
    private List<PropertyListDetailBean.DataBean.OssPictureVOBean> pictures = new ArrayList<>();
    private List<PropertyListDetailBean.DataBean.ResultsBean.PicturesBean> processComplaintPics = new ArrayList<>();
    private boolean isComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_detail);
        ButterKnife.bind(this);
        btnRemind.setOnClickListener(this);
        isComplaint = getIntent().getBooleanExtra("isComplaint", false);
        rlLoading.setVisibility(View.VISIBLE);
        if (isComplaint) {
            NewHeaderBar.createCommomBack(this, "投诉详情", this);
            id = getIntent().getLongExtra("id", 0);
            initComplaint();
        } else {
            repairId = getIntent().getIntExtra("repairId", 0);
            NewHeaderBar.createCommomBack(this, "报修详情", this);
            initRepair();
        }
        detailPhotoAdapter = new DetailPhotoAdapter(repairPictures);
        gvPhoto.setAdapter(detailPhotoAdapter);
        gvPhoto.setOnItemClickListener((parent, view, position, id) -> {
            if (isComplaint) {
                showImage(position, pictures);
            } else {
                showImage(position, repairPictures);
            }
        });
        resultPhotoAdapter = new DetailPhotoAdapter(processPics);
        gvFinishPhoto.setAdapter(resultPhotoAdapter);
        gvFinishPhoto.setOnItemClickListener((parent, view, position, id) -> {
            if (isComplaint) {
                showImage(position, processComplaintPics);
            } else {
                showImage(position, processPics);
            }
        });
    }

    public void showImage(int index, List<?> list) {
        Intent it = new Intent(mContext, ShowNetWorkImageActivity.class);
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean) {
                PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean data = (PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean) list.get(i);
                strings[i] = data.getPicturePath();
            } else if (list.get(i) instanceof PropertyListDetailBean.DataBean.OssPictureVOBean) {
                PropertyListDetailBean.DataBean.OssPictureVOBean data = (PropertyListDetailBean.DataBean.OssPictureVOBean) list.get(i);
                strings[i] = data.getFilename();
            } else if (list.get(i) instanceof PropertyListDetailBean.DataBean.ResultsBean.PicturesBean) {
                PropertyListDetailBean.DataBean.ResultsBean.PicturesBean data = (PropertyListDetailBean.DataBean.ResultsBean.PicturesBean) list.get(i);
                strings[i] = data.getPicturePath();
            } else {
                PropertyRepairRecordDetailBean.DataBean.ResultsBean.ProcessPicsBean data = (PropertyRepairRecordDetailBean.DataBean.ResultsBean.ProcessPicsBean) list.get(i);
                strings[i] = data.getPicturePath();
            }
        }

        it.putExtra("urls", strings);
        it.putExtra("nowImage", strings[index]);
//		dialog.dismiss();
        startActivity(it);
    }

    private void initComplaint() {
        tvName.setText("投诉");
        tvNumber.setText("投诉单号");
        tvContentName.setText("投诉描述");
        rlCase.setVisibility(View.GONE);
        rlType.setVisibility(View.GONE);
        llComplaintAddress.setVisibility(View.VISIBLE);
        ComplaintInterface complaintInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = complaintInterface.selectOneComplaint(body);
        activityRequestData(call, PropertyListDetailBean.class, new RequestResult<PropertyListDetailBean>() {
            @Override
            public void success(PropertyListDetailBean result, String message) {
                if (result.getData() != null) {
                    tvName.setText(result.getData().getTitle());
                    if (result.getData().getStatus() == 0) {
                        tvNameType.setText("待处理");
                    } else {
                        tvNameType.setText("已处理");
                    }
                    tvNumberContent.setText(result.getData().getComplaintNo());
                    tvAddress.setText("投诉人");
                    tvAddressName.setText(result.getData().getComplaint());
                    tvAddressDetailName.setText(result.getData().getAddress());
                    tvContent.setText(result.getData().getDescription());
                    if (result.getData().getOssPictureVO() != null && result.getData().getOssPictureVO().size() > 0) {
                        pictures = result.getData().getOssPictureVO();
                        detailPhotoAdapter.addData(pictures);       //投诉图片
                    }
                    if (result.getData().getResults() != null && result.getData().getResults().size() > 0) {
                        llFinish.setVisibility(View.VISIBLE);
                        vLine.setVisibility(View.VISIBLE);
                        llRemind.setVisibility(View.GONE);
                        tvResultTime.setText(result.getData().getResults().get(0).getCreateDate());
                        tvResultPerson.setText(result.getData().getResults().get(0).getReceiver());
                        tvResult.setText(result.getData().getResults().get(0).getResult());
                        if (result.getData().getResults().get(0).getPictures().size() > 0) {
                            processComplaintPics = result.getData().getResults().get(0).getPictures();  //投诉结果图片
                            resultPhotoAdapter.addData(processComplaintPics);
                        }
                    } else {
                        llFinish.setVisibility(View.GONE);
                        vLine.setVisibility(View.GONE);
                        btnRemind.setVisibility(View.VISIBLE);
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

    private void initRepair() {
        tvNumber.setText("报修单号");
        tvContentName.setText("故障描述");
        llComplaintAddress.setVisibility(View.GONE);
        rlCase.setVisibility(View.VISIBLE);
        rlType.setVisibility(View.VISIBLE);
        PropertyRepairInterface repairInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyRepairInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("repairId", repairId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = repairInterface.propertyRepairRecordDetail(body);
        activityRequestData(call, PropertyRepairRecordDetailBean.class, new RequestResult<PropertyRepairRecordDetailBean>() {
            @Override
            public void success(PropertyRepairRecordDetailBean result, String message) {
                if (result.getData() != null) {
                    tvName.setText(result.getData().getTypeName());
//                    if (result.getData().getStatus().equals("未处理")){
//                        tvNameType.setTextColor(Color.parseColor("#23AC38"));
//                    }else {
//                        tvNameType.setTextColor(Color.parseColor("#BBBBBB"));
//                    }
                    tvNameType.setText(result.getData().getStatus());
                    tvNumberContent.setText(result.getData().getRepairNo());
                    tvAddressName.setText(result.getData().getAddress());
                    tvFaultType.setText(result.getData().getBrokenType());
                    tvContent.setText(result.getData().getDescription());
                    if (result.getData().getRepairPictures().size() > 0) {
                        repairPictures = result.getData().getRepairPictures();
                        detailPhotoAdapter.addData(repairPictures);
                    }
                    if (result.getData().getResults() != null && result.getData().getResults().size() > 0) {
                        llFinish.setVisibility(View.VISIBLE);
                        vLine.setVisibility(View.VISIBLE);
                        btnRemind.setVisibility(View.GONE);
                        tvFinishName.setVisibility(View.VISIBLE);
                        tvResultTime.setText(result.getData().getResults().get(0).getCreateTime());
                        tvResultPerson.setText(result.getData().getResults().get(0).getDealPeople());
                        tvFaultCase.setText(result.getData().getResults().get(0).getBrokenCause());
                        tvResult.setText(result.getData().getResults().get(0).getProcessResult());
                        if (result.getData().getResults().get(0).getProcessPics().size() > 0) {
                            processPics = result.getData().getResults().get(0).getProcessPics();
                            resultPhotoAdapter.addData(processPics);
                        }
                    } else {
                        llFinish.setVisibility(View.GONE);
                        vLine.setVisibility(View.GONE);
                        btnRemind.setVisibility(View.VISIBLE);
                        tvFinishName.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_head_left:
                Intent intent = getIntent();
                setResult(RESULT_CODE, intent);
                finish();
                break;
            case R.id.btn_remind:
                requestRemind();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            setResult(RESULT_CODE, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void requestRemind() {
        PropertyRepairInterface repairInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(PropertyRepairInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("repairId", repairId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = repairInterface.propertyRepairRemind(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail() {

            }
        });
    }

    class DetailPhotoAdapter extends BaseAdapter {

        private List<?> list;

        public DetailPhotoAdapter(List<?> list) {
            this.list = list;
        }

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
                convertView = View.inflate(mContext, R.layout.detail_pic, null);
                holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.img_pic);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.get(position) instanceof PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean) {
                PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean data = (PropertyRepairRecordDetailBean.DataBean.RepairPicturesBean) list.get(position);
                PictureHelper.setImageView(mContext, data.getSmallPicturePath(), holder.img, R.drawable.img_err);
            } else if (list.get(position) instanceof PropertyListDetailBean.DataBean.OssPictureVOBean) {
                PropertyListDetailBean.DataBean.OssPictureVOBean data = (PropertyListDetailBean.DataBean.OssPictureVOBean) list.get(position);
                PictureHelper.setImageView(mContext, data.getFilename(), holder.img, R.drawable.img_err);
            } else if (list.get(position) instanceof PropertyListDetailBean.DataBean.ResultsBean.PicturesBean) {
                PropertyListDetailBean.DataBean.ResultsBean.PicturesBean data = (PropertyListDetailBean.DataBean.ResultsBean.PicturesBean) list.get(position);
                PictureHelper.setImageView(mContext, data.getSmallPicturePath(), holder.img, R.drawable.img_err);
            } else {
                PropertyRepairRecordDetailBean.DataBean.ResultsBean.ProcessPicsBean data = (PropertyRepairRecordDetailBean.DataBean.ResultsBean.ProcessPicsBean) list.get(position);
                PictureHelper.setImageView(mContext, data.getSmallPicturePath(), holder.img, R.drawable.img_err);
            }
            return convertView;
        }

        public void addData(List<?> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        RoundImageView img;
    }
}
