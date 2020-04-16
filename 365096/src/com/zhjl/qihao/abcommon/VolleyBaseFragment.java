/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.zhjl.qihao.abcommon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.util.SsX509TrustManager;
import com.zhjl.qihao.view.LoadingAlertDialog;
import com.zhjl.qihao.volley.VolleyRequestManager;

import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author 黄南榆
 * @version 1.0
 * @description volley 框架使用基类
 * @date 2014-8-21
 */
public class VolleyBaseFragment extends Fragment implements OnClickListener, RequestListener<JSONObject> {
    private LoadingAlertDialog dialog;
    TextView textView;
    protected Session mSession;
    protected Context mContext;
    private List<Call> calls;   //网络请求

    @Override
    public void onStop() {
        super.onStop();
//        VolleyRequestManager.cancelAll(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mSession = Session.get(getActivity());
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSession = Session.get(getActivity());
        MobclickAgent.onPageStart(this.getClass().getName());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        callCancel();
    }

    /**
     * dp转PX
     *
     * @param context
     * @param dp
     * @return
     */
    protected int dp2Px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + (int) 0.5f);
    }

    /**
     * MD5加密算法
     */
    protected String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String md5code = buf.toString();
            if (md5code.length() > 16) {

                return md5code.substring(0, md5code.length() - 16);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param //str (需要输出模块的名字)
     * @return void
     * @throws
     * @description 提示未登录, 并跳转至登陆界面
     */
    protected void showtips() {
        Toast.makeText(getActivity(), getResources().getString(R.string.tips_please_login),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        intent.putExtra("Visitor", "visitor");
        startActivity(intent);

    }

    /**
     * @param str
     * @return void
     * @throws
     * @description 提示不是该小区的业主
     */
    protected void showtoast(String str) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(getResources().getString(R.string.tips_not_owner_1))
                .append(str)
                .append(getResources().getString(R.string.tips_not_owner_2));
        Toast.makeText(getActivity(), sbf.toString(), Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * @return boolean
     * @throws
     * @description 判断是否登录
     * @version 1.0
     */
    protected boolean islogined() {
        String userId = mSession.getUserId();
        String toneKey = mSession.getmToken();
        if (!TextUtils.isEmpty(userId)) {
            return true;
        } else if (!TextUtils.isEmpty(toneKey)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @return boolean
     * @throws
     * @description 判断是否为业主
     * @version 1.0
     */
    private boolean isowner() {
        if (mSession.getRoomCode() == null) {
            return false;
        } else {
            return true;
        }
    }

    protected void executeRequest(Request<?> request) {
        SsX509TrustManager.allowAllSSL();
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyRequestManager.addRequest(request, this);
    }

    /**
     * 显示请求数据的processdialog
     */
    protected void showprocessdialog() {

        if (null == dialog) {
            dialog = new LoadingAlertDialog(getActivity());
        }
        dialog.show();
    }

    /**
     * 取消请求数据的processdialog
     */
    protected void dismissdialog() {
        if (dialog != null && dialog.isShowing()) {
//			dialog.cancel();
            dialog.dismiss();
        }
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.d("---------error--------");
                showErrortoast();
                dismissdialog();
            }
        };
    }

    protected Response.ErrorListener errorListener(
            final PullToRefreshListView listView) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listView.onRefreshComplete();
                showErrortoast();
                dismissdialog();
            }
        };
    }

    protected void showErrortoast() {
        // TODO Auto-generated method stub
        dismissdialog();
        ToastUtils.showToast(getActivity(), "网络不给力，请稍后再试！");
    }

    public String mAudioId;

    public String getmAudioId() {
        return mAudioId;
    }

    public void setmAudioId(String mAudioId) {
        this.mAudioId = mAudioId;
    }

    public String getmAudioPath() {
        return mAudioPath;
    }

    public void setmAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath;
    }

    public String mAudioPath;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    public String audioPath = "";

    public ProgressDialog getUploadProcessdialog() {
        if (mProgressDialog != null) {
            return mProgressDialog;
        } else {
            return null;
        }
    }

    private static final int TAG_UPLOAD_SUCCESS = 0x011;
    private static final int TAG_UPLOAD_FAILD = 0x02;
    private boolean isDestroy;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isDestroy) {
                return;
            }
            mProgressDialog.dismiss();
            if (msg.what == TAG_UPLOAD_SUCCESS) {
                setmAudioId(mAudioId);
                setmAudioPath(mAudioPath);
                AudioPath audioPath = AudioPath.getAudioPath();
                if (null == audioPath.getList_AudioPath()) {
                    List<String> path = new ArrayList<String>();
                    path.add(mAudioPath);
                } else {
                    audioPath.getList_AudioPath().add(mAudioPath);
                }
                // System.out.println("mImageId:"+mImageId+"  mSamllPath:"+mSamllPath);
                // // 上传成功
                // Intent aintent = new Intent(mContext,
                // PhotoManagerActivity.class);
                // aintent.putExtra("imageId", mImageId);
                // aintent.putExtra("samllPath", mSamllPath);
                // // 设置RESULT_OK
                // setResult(RESULT_OK, aintent);
                // finish();
            } else if (msg.what == TAG_UPLOAD_FAILD) {
                showUploadFaildDialog();
            }
        }
    };

    public static class AudioPath {
        public static AudioPath audioPath;
        private List<String> list_AudioPath;

        public static AudioPath getAudioPath() {
            if (audioPath == null) {
                audioPath = new AudioPath();
            }
            return audioPath;
        }

        public List<String> getList_AudioPath() {
            return list_AudioPath;
        }

        public void setList_AudioPath(List<String> list_AudioPath) {
            this.list_AudioPath = list_AudioPath;
        }

    }

    /**
     * 上传图片失败
     */
    private void showUploadFaildDialog() {
        mAlertDialog.show();
    }




    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestSuccess(JSONObject result, int action) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestError(VolleyError e, int action) {
        // TODO Auto-generated method stub
        errorListener2();
    }

    protected void errorListener2() {
        showErrortoast();
    }


    /**
     * =============================================================
     * ======================== 分享代码开始 =========================
     * =============================================================
     */

    public void showSharedDialog(String text, final String file) {
        UMImage image = new UMImage(getActivity(), new File(file));//本地文件

        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        LogUtils.d("进行分享----------------" + share_media);
                        ShareAction shareAction = new ShareAction(getActivity());
                        UMImage image = new UMImage(getActivity().getApplicationContext(), new File(file));
                        shareAction.withMedia(image);
                        shareAction.setPlatform(share_media).setCallback(umShareListener).share();
                        //	new ShareAction(getActivity()).withText("hello").setCallback(umShareListener).share();
                    }
                }).open();
    }
    /*public void showSharedDialog(String text,final String file){
        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
				.setShareboardclickCallback(new ShareBoardlistener() {
					@Override
					public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

						ShareAction shareAction = new ShareAction(getActivity());

						//UMImage image = new UMImage(getActivity().getApplicationContext(),new File(file));
						UMImage image = new UMImage(getActivity().getApplicationContext(),R.drawable.km_shake);
					//	LogUtils.e("Image1",new File(file).toString());
						LogUtils.e("Image1",image.toString());
						image.setThumb(image);
						//shareAction.withMedia(image);
						shareAction.setPlatform(share_media).setCallback(umShareListener).withMedia(image).share();
					}
				}).open();
	}*/

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(getActivity(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            LogUtils.e("Image2", throwable.getMessage());
            if (throwable != null) {
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * =============================================================
     * ======================== 分享代码结束 =========================
     * =============================================================
     */


    public <T> void fragmentRequestData(Call<ResponseBody> call, final Class<T> bean, final RequestResult<T> requestResult) {
        addCalls(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        string = string.replaceAll("\r|\n", "");
                        if (!string.equals("")) {
                            JSONObject parse = new JSONObject(string);
                            String message = parse.getString("message");
                            String result = parse.getString("code");
                            if (result.equals("200")) {
                                if (bean == null) {
                                    requestResult.success((T) string, message);
                                } else {
                                    Gson gson = new Gson();
                                    T t = gson.fromJson(string, bean);
                                    requestResult.success(t, message);
                                }
                            } else if (result.equals("403")) {
                                mSession.clear();
                                call.cancel();
                                JPushInterface.deleteAlias(mContext, 111);
                                ZHJLApplication.getInstance().finishAll();
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, UserLoginActivity.class);
                                startActivity(intent);
                            } else {
                                requestResult.fail();
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            requestResult.fail();
                            Toast.makeText(mContext, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        requestResult.fail();
                    }
                } else {
                    requestResult.fail();
                    Toast.makeText(mContext, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()){
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }
        });
    }
    public <T> void fragmentRequestPhpData(Call<ResponseBody> call, final RequestResult<T> requestResult) {
        addCalls(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        string = string.replaceAll("\r|\n", "");
                        if (!string.equals("")) {
                            requestResult.success((T) string,"");
                        } else {
                            requestResult.fail();
                            Toast.makeText(mContext, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    }catch (UnsupportedOperationException e){
                        //无线轮播的坑
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        requestResult.fail();
                    }
                } else {
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()){
                    Toast.makeText(mContext, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    requestResult.fail();
                }
            }
        });
    }

    public interface RequestResult<T> {
        void success(T result, String message) throws Exception;

        void fail();
    }
}
