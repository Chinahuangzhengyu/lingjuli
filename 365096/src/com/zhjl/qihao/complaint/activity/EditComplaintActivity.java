package com.zhjl.qihao.complaint.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.api.MainApiInterfaces;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.activity.PhotoMultipleActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.ComplaintTypeBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CustomGridView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoView;

import static com.zhjl.qihao.abrefactor.fragment.NewComplaintFragment.REQUEST_ADD_PHOTO;

public class EditComplaintActivity extends VolleyBaseActivity {
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
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.gv_upload_complaint_img)
    CustomGridView gvUploadComplaintImg;
    @BindView(R.id.tv_complaint)
    TextView tvComplaint;
    @BindView(R.id.gv_change_neighborhood_interaction)
    CustomGridView gvChangeNeighborhoodInteraction;
    @BindView(R.id.status_bar_color)
    TextView statusBarColor;
    @BindView(R.id.rl_edit_complaint)
    RelativeLayout rlEditComplaint;
    @BindView(R.id.ll_add_img)
    LinearLayout llAddImg;
    private PopupWindow showChangePopWindow;
    private boolean isComplaint;//是不是从投诉跳过来的
    private PopupWindow showSurePopWindow;
    private int clickPosition;//选中的下标
    private PopupWindow selectPopupWindow;
    List<String> imgLists = new ArrayList<String>();    //存放图片路径的
    List<String> imgIdList = new ArrayList<String>();    //存放图片id的
    public List<String> photoViews = new ArrayList<>(); //查看大图的
    private MyUpLoadPictureAdapter myUpLoadPictureAdapter;
    private PopupWindow phtotPopwindow;
    private ViewPager vpPhotoView;
    private PhotoViewPagerAdapter photoViewPagerAdapter;
    private ComplaintApiInterfaces complaintApiInterfaces;
    private MyNearByItemAdapter myNearByItemAdapter;
    private List<Object> list = new ArrayList<>();
    private boolean isChick; //是否选择了类型
    private int pictureNum;//图片数量
    private MyHandler myHandler;
    private int flag = 0;   //0非首页分类  1 首页分类


    private static class MyHandler extends Handler {

        private WeakReference<EditComplaintActivity> mActivity;

        public MyHandler(EditComplaintActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EditComplaintActivity activity = mActivity.get();
            if (activity.showSurePopWindow.isShowing()) {
                activity.showSurePopWindow.dismiss();
                String id = (String) msg.obj;
                Intent intent = new Intent(activity, ComplaintDetailActivity.class);
                intent.putExtra("forumNoteId", id);
                if (activity.list.get(activity.clickPosition) instanceof ComplaintTypeBean.ListBean) {
                    ComplaintTypeBean.ListBean dataList = (ComplaintTypeBean.ListBean) activity.list.get(activity.clickPosition);
                    intent.putExtra("detailTitle", dataList.getLabelName());
                }else if (activity.list.get(activity.clickPosition) instanceof NearByTypeBean.DataBean){
                    NearByTypeBean.DataBean dataList = (NearByTypeBean.DataBean) activity.list.get(activity.clickPosition);
                    intent.putExtra("detailTitle", dataList.getLabelName());
                }
                activity.startActivity(intent);
                activity.setResult(0x15,activity.getIntent());
                activity.finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_complaint_activity);
        ButterKnife.bind(this);
        myHandler = new MyHandler(this);
        statusBarColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        String title = getIntent().getStringExtra("isComplaint");
        complaintApiInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        if (title.equals("发表投诉")) {
            isComplaint = true;
            initSendMessage(0);
        } else {
            initSendMessage(1);
            isComplaint = false;
        }
        myNearByItemAdapter = new MyNearByItemAdapter(list);
        gvChangeNeighborhoodInteraction.setAdapter(myNearByItemAdapter);
        gvChangeNeighborhoodInteraction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChick = true;
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        TextView tvPosition = (TextView) gvChangeNeighborhoodInteraction.getChildAt(position);
                        tvPosition.setTextColor(Color.parseColor("#ffffff"));
                        tvPosition.setBackgroundResource(R.drawable.btn_round_style);
                        clickPosition = position;
                    } else {
                        TextView tvPosition = (TextView) gvChangeNeighborhoodInteraction.getChildAt(i);
                        tvPosition.setTextColor(Color.parseColor("#9a9a9a"));
                        tvPosition.setBackgroundResource(R.drawable.neighborhood_style);
                    }
                }

            }
        });
        NewHeaderBar.createCommomBack(this, title, this);
        imgLists.add("");//存4个位置
        imgLists.add("");
        imgLists.add("");
        imgLists.add("");
        myUpLoadPictureAdapter = new MyUpLoadPictureAdapter();
        gvUploadComplaintImg.setAdapter(myUpLoadPictureAdapter);
        tvComplaint.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        View view = View.inflate(mContext, R.layout.show_big_picture, null);
        vpPhotoView = view.findViewById(R.id.vp_photo_view);
        photoViewPagerAdapter = new PhotoViewPagerAdapter();
        vpPhotoView.setAdapter(photoViewPagerAdapter);
        View viewTop = view.findViewById(R.id.view_top);        //图片查看的上区域
        View viewBottom = view.findViewById(R.id.view_bottom);//图片查看的下区域
        viewTop.setOnClickListener(this);
        viewBottom.setOnClickListener(this);
        phtotPopwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        phtotPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, EditComplaintActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        phtotPopwindow.setFocusable(true);
        phtotPopwindow.setOutsideTouchable(true);
        gvUploadComplaintImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imgLists.get(position).equals("")) {
                    Toast.makeText(EditComplaintActivity.this, "暂无图片！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Utils.darkenBackground(0.5f, EditComplaintActivity.this);
                vpPhotoView.setCurrentItem(position);
                phtotPopwindow.showAsDropDown(rlEditComplaint, 0, 0);
            }
        });
        showSurePop();  //初始化popWindow
    }

    private void initSendMessage(int flag) {
        MainApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(MainApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.nearbyType(body);
        activityRequestData(call, NearByTypeBean.class, new RequestResult<NearByTypeBean>() {
            @Override
            public void success(NearByTypeBean result, String message) {
                list.clear();
                list.addAll(result.getData());
                myNearByItemAdapter.addData(list);
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
            case R.id.tv_complaint:
                if (editTitle.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请填写标题！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editContent.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请填写内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isChick) {
                    Toast.makeText(mContext, "请选择类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isComplaint) {
                    showExitPop("您的内容已填写完毕\n确定发表投诉？");
                } else {
                    showExitPop("您的内容已填写完毕\n确定发表文章？");
                }
                break;

            case R.id.img_add:      //图片添加
                pictureNum = 0;
                Intent takePictureIntent = new Intent(mContext,
                        PhotoMultipleActivity.class);
                takePictureIntent.putExtra("photonums", 4);
                if (imgLists.size() != 0) {
                    for (int i = 0; i < imgLists.size(); i++) {
                        if (!imgLists.get(i).equals("")) {
                            pictureNum++;
                        }
                    }
                }
                takePictureIntent.putExtra("type", pictureNum);
                takePictureIntent.putExtra("size", pictureNum);
                startActivityForResult(takePictureIntent, REQUEST_ADD_PHOTO);
                break;
            case R.id.view_top:
                phtotPopwindow.dismiss();
                break;
            case R.id.view_bottom:
                phtotPopwindow.dismiss();
                break;
        }
    }


    private void showSurePop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.show_sure_popwindow, null);
        int height = Utils.dip2px(mContext, 160);
        showSurePopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, height);
        showSurePopWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        showSurePopWindow.setFocusable(true);
        showSurePopWindow.setOutsideTouchable(true);
        showSurePopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
    }

    private void showExitPop(String title) {
        View popView = LayoutInflater.from(this).inflate(R.layout.show_popwindow, null);
        TextView yes = (TextView) popView.findViewById(R.id.yes_show);
        TextView not = (TextView) popView.findViewById(R.id.not_show);
        TextView tvMsg = (TextView) popView.findViewById(R.id.tv_msg);
        tvMsg.setText(title);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePopWindow.dismiss();
                sendMessage();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePopWindow.dismiss();
            }
        });
        int height = Utils.dip2px(mContext, 160);
        showChangePopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, height);
        showChangePopWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.popwindowbg));
        showChangePopWindow.setFocusable(true);
        showChangePopWindow.setOutsideTouchable(true);
        showChangePopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        showChangePopWindow.showAsDropDown(imgAdd, 0, 0);
    }

    private void sendMessage() {
        Map<String, Object> map = new HashMap<>();
        if (list.size() != 0) {
            if (list.get(clickPosition) instanceof ComplaintTypeBean.ListBean) {
                ComplaintTypeBean.ListBean dataList = (ComplaintTypeBean.ListBean) list.get(clickPosition);
                map.put("labelTypeId", dataList.getId());
            }
            if (list.get(clickPosition) instanceof NearByTypeBean.DataBean) {
                NearByTypeBean.DataBean dataList = (NearByTypeBean.DataBean) list.get(clickPosition);
                map.put("labelTypeId", dataList.getId());
            }
        }
        map.put("title", editTitle.getText().toString().trim());
        map.put("content", editContent.getText().toString().trim());
        RequestBody body = ParamForNet.putContainsArray(map, "picList", imgIdList);
        Call<ResponseBody> call = complaintApiInterfaces.sendSubmitMessage(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        if (!string.equals("")) {
                            JSONObject object = new JSONObject(string);
                            String result = object.getString("code");
                            if (result.equals("200")) {
                                if (showChangePopWindow.isShowing()) {
                                    showChangePopWindow.dismiss();
                                }
                                JSONObject data = object.getJSONObject("data");
                                String forumNoteId = data.getString("forumNoteId");
                                showSurePopWindow.showAsDropDown(llAddImg, 0, 0);
                                Message obtain = Message.obtain();
                                obtain.obj = forumNoteId;
                                myHandler.sendMessageDelayed(obtain, 1000);

                            }
                            Toast.makeText(EditComplaintActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditComplaintActivity.this, "发帖失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(EditComplaintActivity.this, "发帖失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditComplaintActivity.this, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //选择相册
    public void onSelectAlbumClick(View view) {
    }

    //拍照
    public void onPhotographClick(View view) {
    }

    //取消
    public void onCancleClick(View view) {
        selectPopupWindow.dismiss();
        Utils.darkenBackground(1f, EditComplaintActivity.this);
    }

    class MyNearByItemAdapter extends BaseAdapter {

        private List<Object> listBeans;

        public MyNearByItemAdapter(List<Object> list) {
            listBeans = list;
        }

        public void addData(List<Object> list) {
            listBeans = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return listBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.edit_item, null);
            }
            TextView tvNeighborhoodName = convertView.findViewById(R.id.tv_neighborhood_name);
            if (listBeans.get(position) instanceof ComplaintTypeBean.ListBean) {
                ComplaintTypeBean.ListBean list = (ComplaintTypeBean.ListBean) listBeans.get(position);
                tvNeighborhoodName.setText(list.getLabelName());
            }
            if (listBeans.get(position) instanceof NearByTypeBean.DataBean) {
                NearByTypeBean.DataBean list = (NearByTypeBean.DataBean) listBeans.get(position);
                tvNeighborhoodName.setText(list.getLabelName());
            }
            return convertView;
        }
    }

    class MyUpLoadPictureAdapter extends BaseAdapter {

        private ImageView imgUpload;

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return imgLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.complaint_img_upload, null);
            }
            imgUpload = convertView.findViewById(R.id.img_upload);

            if (imgLists.size() != 0 && imgLists.size() == 4) {
                PictureHelper.setImageGlide(mContext, 12, imgLists.get(position), imgUpload, R.drawable.round_gray);
            } else {
                for (int i = 0; i < imgLists.size(); i++) {
                    if (position == i) {
                        PictureHelper.setOptionsGlide(mContext, 12, imgLists.get(position), imgUpload, R.drawable.round_gray);
                    } else {
                        continue;
                    }
                }
            }
            return convertView;
        }
    }

    class PhotoViewPagerAdapter extends PagerAdapter {


        public void addData(List<String> list) {
            photoViews = list;
            if (photoViews.size() != 0) {
                for (int i = photoViews.size() - 1; i > 0; i--) {
                    if (photoViews.get(i).equals("")) {
                        photoViews.remove(i);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return photoViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (photoViews.size() != 0) {
                PhotoView photoView = new PhotoView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                photoView.setLayoutParams(params);
                if (!photoViews.get(position).contains("_small")) {
                    PictureHelper.setImageView(mContext, photoViews.get(position), photoView, R.drawable.img_err);
                } else {
                    String[] split = photoViews.get(position).split("\\.");
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].endsWith("_small")) {
                            split[i] = split[i].substring(0, split[i].length() - 6);
                        }
                        if (i == split.length - 1) {
                            sb.append(split[i]);
                        } else {
                            sb.append(split[i] + ".");
                        }
                    }
                    PictureHelper.setImageView(mContext, sb.toString(), photoView, R.drawable.img_err);
                }
                container.addView(photoView);
                return photoView;
            } else {
                Toast.makeText(mContext, "暂无图片！", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_PHOTO && data != null) {
            List<String> mSamllPathList = (List<String>) data.getExtras()
                    .getSerializable("samllPath");
            Log.i("111111", mSamllPathList + "");
            for (int i = 0; i < mSamllPathList.size(); i++) {
                String url = mSamllPathList.get(i);
                if (url != null) {
                    for (int j = 0; j < imgLists.size(); j++) {
                        if (imgLists.get(j).equals("")) {
                            imgLists.remove(j);
                            imgLists.add(j, url);
                            break;
                        }
                    }
//                    img_delete_show.add(false);
                }
            }
            imgIdList.addAll((List<String>) data.getExtras().getSerializable(
                    ("imageId")));
            //图片适配器刷新
            myUpLoadPictureAdapter.notifyDataSetChanged();
            //查看大图适配器刷新
            photoViews.clear();
            photoViews.addAll(imgLists);
            photoViewPagerAdapter.addData(photoViews);
        } else if (requestCode == 10 && data != null) {
            finish();
        }
    }
}
