package com.zhjl.qihao.systemsetting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.PhotoSingleActivity;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.view.CircleImageView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 个人资料
 */
public class MyEditorActivity extends VolleyBaseActivity {


    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.btn_keep)
    Button btnKeep;
    private static final int REQUEST_ADD_PHOTO = 0x1;
    private String imageId;
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_editor);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "个人资料", this);
        etName.setText(mSession.getNick());
        etSign.setText(mSession.getSign());
        PictureHelper.setHeadImageView(mContext, mSession.getSmallHeadPicPath(), imgHead, R.drawable.ic_head);
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSign.setSelection(etSign.getText().toString().trim().length());
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 拍照返回 显示图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO 自动生成的方法存根
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && data != null) {
            if (data.hasExtra("samllPath")) {
                String samllPath = data.getStringExtra("samllPath");
                imageId = data.getStringExtra("imageId");
                path = samllPath;
                if (samllPath != null && !samllPath.equals("")) {
                    PictureHelper.setPlaceholderImageView(mContext, samllPath, imgHead, R.drawable.ic_head);

                }
            }
        }
    }

    public void tokephote() {
        Intent takePictureIntent = new Intent(mContext,
                PhotoSingleActivity.class);
        startActivityForResult(takePictureIntent, REQUEST_ADD_PHOTO);
    }

    /**
     *
     */
    public static String filterAlphabet(String alph) { // 過濾器
        alph = alph
                .replaceAll(
                        "[^(a-zA-Z0-9\\u4e00-\\u9fa5\\.,。，！!…… + - * / ? ？ @ # ＃ $ ＄ % ％  & ﹢ = 《 》 〈 〉 ﹛ ﹜ ﹫ ﹖ ﹠ （ ） ( ) \\ )]",
                        "");
        return alph;

    }

    @OnClick({R.id.iv_back, R.id.btn_keep, R.id.img_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_keep:
                if (etName.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
//                String filterString = filterAlphabet(etName.getText().toString().trim());
//                if (etName.getText().toString().trim().equals("")) {
//                    Toast.makeText(mContext, "昵称不能带特殊字符！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String filterSignString = filterAlphabet(etSign.getText().toString().trim());
//                if (!filterSignString.equals(etSign.getText().toString().trim())) {
//                    Toast.makeText(mContext, "签名不能带特殊字符！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                requestUpdateInfo();
                break;
            case R.id.img_head:
                tokephote();
                break;
        }
    }

    private void requestUpdateInfo() {
        SettingInterface settingInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("avatarId", imageId);
        map.put("nickname", etName.getText().toString().trim());
        map.put("sign", etSign.getText().toString().trim());
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = settingInterface.updateInfo(body);
        activityRequestData(call, null, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                if (!path.equals("")) {
                    mSession.setSmallHeadPicPath(path);
                }
                mSession.setSign(etSign.getText().toString().trim());
                mSession.setNick(etName.getText().toString().trim());
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void fail() {

            }
        });
    }
}
