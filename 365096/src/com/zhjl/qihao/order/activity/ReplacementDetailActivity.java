package com.zhjl.qihao.order.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.ServiceChooseBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
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

public class ReplacementDetailActivity extends VolleyBaseActivity {

    @BindView(R.id.lv_shop_list)
    ListViewForScrollView lvShopList;
    @BindView(R.id.tv_shop_status)
    TextView tvShopStatus;
    @BindView(R.id.tv_shop_status_content)
    TextView tvShopStatusContent;
    @BindView(R.id.rl_shop_status)
    RelativeLayout rlShopStatus;
    @BindView(R.id.tv_return_price)
    TextView tvReturnPrice;
    @BindView(R.id.rl_return_money)
    RelativeLayout rlReturnMoney;
    @BindView(R.id.tv_case)
    TextView tvCase;
    @BindView(R.id.tv_case_choose)
    TextView tvCaseChoose;
    @BindView(R.id.tv_case_explain)
    TextView tvCaseExplain;
    @BindView(R.id.et_return_info_content)
    EditText etReturnInfoContent;
    @BindView(R.id.gv_upload_replacement_img)
    GridViewForScrollView gvUploadReplacementImg;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_lose_price)
    TextView tvLosePrice;
    @BindView(R.id.rl_lose_money)
    RelativeLayout rlLoseMoney;
    private ArrayList<ServiceChooseBean> list = new ArrayList<>();
    List<String> imgSmallList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    List<String> imgIdList = new ArrayList<String>();
    List<Boolean> imgDeleteShow = new ArrayList<>();
    private MyUpLoadAdapter imgAdapter;
    public static final int REQUEST_ADD_PHOTO = 1;
    private String postKey = "";
    private int type = 3;
    private ShopListAdapter shopListAdapter;
    private int orderItemId;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_detail);
        ButterKnife.bind(this);
        initData();
        if (getIntent().getBooleanExtra("isNoPay", false)) {
            orderItemId = getIntent().getIntExtra("order_item_id", 0);
            orderId = getIntent().getLongExtra("order_id", 0);
            initDataDetail();
        } else {
            list = getIntent().getParcelableArrayListExtra("list");
            if (list.size()>0){
                tvReturnPrice.setText("¥" + list.get(0).getTotal_price());
                if (list.get(0).getPoints_deduct_price()==0){
                    rlLoseMoney.setVisibility(View.GONE);
                }else {
                    tvLosePrice.setText("¥"+list.get(0).getPoints_deduct_price());
                    rlLoseMoney.setVisibility(View.VISIBLE);
                }
            }
        }
        String name = getIntent().getStringExtra("name");
        NewHeaderBar.createCommomBack(this, name, this);
        shopListAdapter = new ShopListAdapter(list);
        lvShopList.setAdapter(shopListAdapter);
        if (name.equals("申请退款")) {
            type = 1;
            tvShopStatus.setText("货物状态");
            tvCaseExplain.setText("退款说明");
            if (list.size() > 0) {
                if (list.get(0).getOrder_status() == 3) {
                    tvShopStatusContent.setText("已收到货品");
                } else {
                    tvShopStatusContent.setText("未收到货品");
                }
            }else {
                tvShopStatusContent.setText("未收到货品");
            }
        } else if (name.equals("申请退款退货")) {
            type = 2;
            tvShopStatus.setText("货物状态");
            tvCaseExplain.setText("退款说明");
            if (list.size() > 0) {
                if (list.get(0).getOrder_status() == 3) {
                    tvShopStatusContent.setText("已收到货品");
                } else {
                    tvShopStatusContent.setText("未收到货品");
                }
            }else {
                tvShopStatusContent.setText("未收到货品");
            }
        } else if (name.equals("申请换货")) {
            type = 3;
            rlLoseMoney.setVisibility(View.GONE);
            rlReturnMoney.setVisibility(View.GONE);
            tvCaseExplain.setText("换货说明");
            tvShopStatusContent.setTypeface(Typeface.DEFAULT);
            tvShopStatusContent.setTextColor(Color.parseColor("#FFB126"));
            if (list.size() > 0) {
                if (list.get(0).getDelivery_type_code() == 1) {        //门店自提
                    tvShopStatus.setText("门店自提");
                    tvShopStatusContent.setText(list.get(0).getDelivery_address());
                } else if (list.get(0).getDelivery_type_code() == 2) {
                    tvShopStatus.setText("送货上门");
                    tvShopStatusContent.setText(list.get(0).getDelivery_address());
                }
            }
        }

        imgAdapter = new MyUpLoadAdapter();
        gvUploadReplacementImg.setAdapter(imgAdapter);
        gvUploadReplacementImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int imgsize = imgAdapter.getCount();
                if (imgList.size() < 5 && arg2 <= 5 && arg2 + 1 == imgsize) {
                    tokePhoto();
                } else {

                    showImage(arg2);

                }

            }
        });
    }

    private void initDataDetail() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("order_id", orderId);
        map.put("order_item_id", orderItemId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.chooseServiceType(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                String string = (String) result;
                JSONObject object = new JSONObject(string);
                boolean status = object.optBoolean("status");
                if (status) {
                    list.clear();
                    Gson gson = new Gson();
                    ServiceChooseBean serviceChooseBean = gson.fromJson(string, ServiceChooseBean.class);
                    list.add(serviceChooseBean);
                    tvReturnPrice.setText("¥" + list.get(0).getTotal_price());
                    if (type!=3){
                        if (list.get(0).getPoints_deduct_price()==0){
                            rlLoseMoney.setVisibility(View.GONE);
                        }else {
                            tvLosePrice.setText("¥"+list.get(0).getPoints_deduct_price());
                            rlLoseMoney.setVisibility(View.VISIBLE);
                        }
                    }else {
                        rlLoseMoney.setVisibility(View.GONE);
                    }

                    shopListAdapter.addData(list);
                } else {
                    Utils.phpIsLogin(ReplacementDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void initData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.getOneNumber(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    postKey = object.optString("post_key");
                } else {
                    Utils.phpIsLogin(ReplacementDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    public void tokePhoto() {
        Intent takePictureIntent = new Intent(mContext,
                PhpPhotoMultipleActivity.class);
        takePictureIntent.putExtra("photonums", 5);
        takePictureIntent.putExtra("type", imgList.size());
        takePictureIntent.putExtra("size", imgList.size());
        takePictureIntent.putExtra("form", "shopService");
        takePictureIntent.putExtra("postKey", postKey);
        startActivityForResult(takePictureIntent, REQUEST_ADD_PHOTO);
    }

    public void showImage(int index) {
        Intent it = new Intent(mContext, PhpShowNetWorkImageActivity.class);
        String[] strings = new String[imgSmallList.size()];
        for (int i = 0; i < imgSmallList.size(); i++) {
            strings[i] = imgSmallList.get(i);
        }
        it.putExtra("urls", strings);
        it.putExtra("nowImage", imgSmallList.get(index));
        startActivity(it);
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (etReturnInfoContent.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请填写缘由！", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitData();
                break;
        }
    }

    private void submitData() {
        if (list.size() == 0) {
            Toast.makeText(mContext, "数据出错！", Toast.LENGTH_SHORT).show();
            return;
        }
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("order_id", list.get(0).getOrder_id());
        map.put("order_item_id", list.get(0).getOrder_item_id());
        map.put("type", type);
        map.put("content", etReturnInfoContent.getText().toString().trim());
        map.put("post_key", postKey);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.submitServiceRequest(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                if (object.optBoolean("status")) {
                    Intent intent = new Intent();
                    intent.putExtra("orderType", "");
                    intent.setClass(mContext, NewOrderTypeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Utils.phpIsLogin(ReplacementDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

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
                    requestDelete(position);
                }
            });
            return convertView;
        }

    }

    private void requestDelete(final int position) {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("id", imgIdList.get(position));
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.deleteServiceImg(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    imgList.remove(position);
                    imgDeleteShow.remove(position);
                    imgAdapter.notifyDataSetChanged();
                } else {
                    Utils.phpIsLogin(ReplacementDetailActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    class ShopListAdapter extends BaseAdapter {

        private List<ServiceChooseBean> list;

        public ShopListAdapter(List<ServiceChooseBean> list) {
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
                convertView = View.inflate(mContext, R.layout.shop_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                holder.tvShopName.setText(list.get(position).getName());
                holder.tvShopCount.setText("x" + list.get(position).getNumber());
                holder.tvShopPrice.setText("¥"+list.get(position).getPrice());
                if (list.get(position).getUnit_name()!=null&&!list.get(position).getUnit_name().equals("")){
                    holder.tvShopType.setVisibility(View.VISIBLE);
                    holder.tvShopType.setText(list.get(position).getUnit_name());
                }else {
                    holder.tvShopType.setVisibility(View.GONE);
                }
                PictureHelper.setImageView(mContext, list.get(position).getImage(), holder.imgShop, R.drawable.img_loading);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        public void addData(ArrayList<ServiceChooseBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_price)
        TextView tvShopPrice;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_shop_count)
        TextView tvShopCount;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && data != null) {
            List<String> mSmallPathList = (List<String>) data.getExtras()
                    .getSerializable("smallPath");
            List<String> path = (List<String>) data.getExtras()
                    .getSerializable("path");
            List<String> idList = (List<String>) data.getExtras()
                    .getSerializable("id");
            for (int i = 0; i < mSmallPathList.size(); i++) {
                String url = mSmallPathList.get(i);
                if (url != null) {
                    imgList.add(url);
                    imgDeleteShow.add(false);
                }
            }
            imgSmallList.addAll(path);
            imgIdList.addAll(idList);
            imgAdapter.notifyDataSetChanged();
        }
    }
}
