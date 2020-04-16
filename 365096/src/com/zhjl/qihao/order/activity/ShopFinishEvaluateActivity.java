package com.zhjl.qihao.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.PhotoMultipleActivity;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
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

import static com.zhjl.qihao.order.activity.OrderDetailActivity.RESULT_ORDER;

public class ShopFinishEvaluateActivity extends VolleyBaseActivity {
    @BindView(R.id.et_repair_content)
    EditText etRepairContent;
    @BindView(R.id.gv_upload_evaluate_img)
    GridViewForScrollView gvUploadEvaluateImg;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.rg_evaluate)
    RadioGroup rgEvaluate;
    @BindView(R.id.rb_check_good)
    RadioButton rbCheckGood;
    @BindView(R.id.rb_check_middle)
    RadioButton rbCheckMiddle;
    @BindView(R.id.rb_check_bad)
    RadioButton rbCheckBad;
    private MyUpLoadAdapter imgAdapter;
    List<String> imgSmallList = new ArrayList<String>();
    private List<String> imgIdList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    List<Boolean> imgDeleteShow = new ArrayList<>();
    public static final int REQUEST_ADD_PHOTO = 1;
    private String postKey = "";
    private int score = 5; //3.差评 4.中评 5.好评
    private int orderItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_finish_evaluate);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "商品评价", this);
        orderItemId = getIntent().getIntExtra("order_item_id", 0);
        initData();
        imgAdapter = new MyUpLoadAdapter();
        gvUploadEvaluateImg.setAdapter(imgAdapter);
        gvUploadEvaluateImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        rgEvaluate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbCheckGood.getId()) {
                    score = 5;
                } else if (checkedId == rbCheckMiddle.getId()) {
                    score = 4;
                } else if (checkedId == rbCheckBad.getId()) {
                    score = 3;
                }
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
                    Utils.phpIsLogin(ShopFinishEvaluateActivity.this, object.optInt("type"), object.optString("message"));
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
        takePictureIntent.putExtra("postKey", postKey);
        takePictureIntent.putExtra("form","shopEvaluate");
        takePictureIntent.putExtra("type", imgList.size());
        takePictureIntent.putExtra("size", imgList.size());
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
        Call<ResponseBody> call = orderInterface.deleteShopEvaluateImg(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    imgList.remove(position);
                    imgIdList.remove(position);
                    imgDeleteShow.remove(position);
                    imgAdapter.notifyDataSetChanged();
                } else {
                    Utils.phpIsLogin(ShopFinishEvaluateActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if (etRepairContent.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请填写商品的评价！", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitData();
                break;
        }
    }

    private void submitData() {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", mSession.getmToken());
        map.put("score", score);
        map.put("content", etRepairContent.getText().toString().trim());
        map.put("oiid", orderItemId);
        map.put("post_key", postKey);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.submitComment(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                if (object.optBoolean("status")){
                    Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    setResult(RESULT_ORDER,intent);
                    finish();
                }else {
                    Utils.phpIsLogin(ShopFinishEvaluateActivity.this,object.optInt("type"),object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });
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
