package com.zhjl.qihao.nearbyinteraction.activity;

import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.CustomRadioGroup;
import com.zhjl.qihao.abrefactor.view.GridViewForScrollView;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.ButtonRepect;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.NewPhotoMultipleActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.image.ShowNetWorkImageActivity;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.nearbyinteraction.bean.FileUploadBean;
import com.zhjl.qihao.nearbyinteraction.bean.NearByItemBean;
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

public class NearBySendNoteActivity extends VolleyBaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_send_note)
    TextView tvSendNote;
    @BindView(R.id.et_send_content)
    EditText etSendContent;
    @BindView(R.id.gv_upload_img)
    GridViewForScrollView gvUploadImg;
    @BindView(R.id.rg_note_tag)
    CustomRadioGroup rgNoteTag;

    private List<String> imgIdList = new ArrayList<String>();
    List<String> imgList = new ArrayList<String>();
    List<Boolean> imgDeleteShow = new ArrayList<>();
    private MyUpLoadAdapter imgAdapter;
    public static final int REQUEST_NEARBY_NOTE_ADD_PHOTO = 11111;
    private String labelTypeId;
    private ArrayList<NearByItemBean.DataBean> title = new ArrayList<>();
    private int checkTagId;
    private int checkPosition;
    private FileUploadBean.DataBean resultData;
    public static final int RESULT_SEND_NOTE = 0x664;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_send_note);
        ButterKnife.bind(this);
        labelTypeId = getIntent().getStringExtra("labelTypeId");
        title = getIntent().getParcelableArrayListExtra("title");
        if (title == null) {
            initTypeData();
        } else {
            for (int i = 0; i < title.size(); i++) {
                RadioButton button = new RadioButton(mContext);
                button.setText("#"+title.get(i).getTopicName()+"#");
                button.setPadding(Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 4), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 4));
                button.setTextSize(14);
                button.setButtonDrawable(new StateListDrawable());
                button.setId(i);
                button.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
                button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.nearby_send_tag));
                rgNoteTag.addView(button);
            }
        }
        initData();
        rgNoteTag.setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    checkTagId = title.get(i).getId();
                    checkPosition = i;
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    rb.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    rb.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
                }
            }
            isCanSend();
        });

        imgAdapter = new MyUpLoadAdapter();
        gvUploadImg.setAdapter(imgAdapter);
        gvUploadImg.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            int imgsize = imgAdapter.getCount();
            if (imgList.size() < 5 && arg2 <= 5 && arg2 + 1 == imgsize) {
                tokephote();
            } else {

                showImage(arg2);

            }

        });

        etSendContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isCanSend();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initTypeData() {
        NearByInterfaces apiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        HashMap<String, Object> map = new HashMap();
        map.put("forumLabelTypeId", labelTypeId);
        Call<ResponseBody> call = apiInterfaces.nearbyTypeList(map);
        activityRequestData(call, NearByItemBean.class, new RequestResult<NearByItemBean>() {
            @Override
            public void success(NearByItemBean result, String message) throws Exception {
                title = new ArrayList<>();
                title.addAll(result.getData());
                for (int i = 0; i < title.size(); i++) {
                    RadioButton button = new RadioButton(mContext);
                    button.setText("#"+title.get(i).getTopicName()+"#");
                    button.setPadding(Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 4), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 4));
                    button.setTextSize(14);
                    button.setButtonDrawable(new StateListDrawable());
                    button.setId(i);
                    button.setTextColor(ContextCompat.getColor(mContext, R.color.ff999999));
                    button.setBackground(ContextCompat.getDrawable(mContext, R.drawable.nearby_send_tag));
                    rgNoteTag.addView(button);
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
    private void initData() {
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Call<ResponseBody> call = nearByInterfaces.getFileSign(new HashMap<>());
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

    /**
     * 是否可以发送帖子
     */
    private void isCanSend() {
        for (int i = 0; i < rgNoteTag.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rgNoteTag.getChildAt(i);
            if (rb.isChecked()) {
                if (etSendContent.getText().toString().length() > 0) {
                    tvSendNote.setAlpha(1f);
                    tvSendNote.setEnabled(true);
                    break;
                } else {
                    tvSendNote.setAlpha(0.4f);
                    tvSendNote.setEnabled(false);
                }
            }
        }
    }

    public void tokephote() {
        if (resultData == null) {
            Toast.makeText(mContext, "签名获取失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent takePictureIntent = new Intent(mContext,
                NewPhotoMultipleActivity.class);
        takePictureIntent.putExtra("photonums", 5);
        takePictureIntent.putExtra("fileSign", resultData);
        takePictureIntent.putExtra("type", imgList.size());
        takePictureIntent.putExtra("size", imgList.size());
        startActivityForResult(takePictureIntent, REQUEST_NEARBY_NOTE_ADD_PHOTO);
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

            iv_delete.setOnClickListener(v -> {
                imgList.remove(position);
                imgIdList.remove(position);
                imgDeleteShow.remove(position);
                imgAdapter.notifyDataSetChanged();
            });
            return convertView;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    @OnClick({R.id.img_back, R.id.tv_send_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_send_note:
                boolean fastClick = ButtonRepect.isFastClick();
                if (!fastClick){
                    return;
                }
                tvSendNote.setEnabled(false);
                sendNote();
                break;
        }
    }

    private void sendNote() {
        ComplaintApiInterfaces complaintInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("labelTypeId", labelTypeId);
        map.put("content", etSendContent.getText().toString().trim());
        map.put("topicId", checkTagId);
        RequestBody body = ParamForNet.putContainsArray(map, "picList", imgIdList);
        Call<ResponseBody> call = complaintInterface.sendSubmitMessage(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                JSONObject data = object.optJSONObject("data");
                String forumNoteId = data.optString("forumNoteId");
                Toast.makeText(mContext, object.optString("message"), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext,NearByNoteContentActivity.class);
//                intent.putExtra("forumNoteId",forumNoteId);
//                startActivity(intent);
                Intent intent = getIntent().putExtra("title", title.get(checkPosition));
                setResult(RESULT_SEND_NOTE,intent);
                tvSendNote.setEnabled(true);
                finish();
            }

            @Override
            public void fail() {
                tvSendNote.setEnabled(true);
            }
        });
    }
}
