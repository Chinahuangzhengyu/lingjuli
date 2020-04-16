package com.zhjl.qihao.complaint.adapter;

//物业记录适配器

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.complaint.bean.PropertyProgressBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.CustomGridView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import uk.co.senab.photoview.PhotoView;

public class ComplaintPropertyAdapter extends RecyclerView.Adapter<ComplaintPropertyAdapter.MyViewHolder> {

    private VolleyBaseActivity activity;
    private List<PropertyProgressBean.DataBean> lists;
    private List<String> imgs = new ArrayList<>();
    private Session session;
    private ViewPager vpPhotoView;
    private PhotoViewPagerAdapter photoViewPagerAdapter;
    private PopupWindow phtotPopwindow;

    public ComplaintPropertyAdapter(VolleyBaseActivity activity, List<PropertyProgressBean.DataBean> lists) {
        this.activity = activity;
        this.lists = lists;
        session = Session.get(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.complaint_detail_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.rlComplaintDetailItem.setBackgroundResource(R.drawable.btn_round_style);
            holder.imgSureAgree.setTextColor(Color.parseColor("#ffffff"));
            holder.imgHead.setVisibility(View.INVISIBLE);
            holder.tvTime.setTextColor(Color.parseColor("#ffffff"));
            holder.tvName.setText(Html.fromHtml("<font color = \"#ffffff\">" + lists.get(position).getReplayNote() + "</font>"));
            holder.tvMore.setVisibility(View.GONE);
            holder.tvReply.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imgSureAgree.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.img_sure_agree);
            params.rightMargin = Utils.px2dip(activity, 15);
            if (lists.get(position).getPics() != null && !lists.get(position).getPics().equals("")) {
                holder.gvCommentImg.setVisibility(View.VISIBLE);
                for (int i = 0; i < lists.get(position).getPics().size(); i++) {
                    imgs.add(lists.get(position).getPics().get(i));
                }
                holder.gvCommentImg.setAdapter(new MyAdapter(imgs));
                View photoView = View.inflate(activity, R.layout.show_big_picture, null);
                vpPhotoView = photoView.findViewById(R.id.vp_photo_view);
                View viewTop = photoView.findViewById(R.id.view_top);
                View viewBottom = photoView.findViewById(R.id.view_bottom);
                viewTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phtotPopwindow.dismiss();
                    }
                });
                viewBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phtotPopwindow.dismiss();
                    }
                });
                photoViewPagerAdapter = new PhotoViewPagerAdapter();
                vpPhotoView.setAdapter(photoViewPagerAdapter);
                phtotPopwindow = new PopupWindow(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                phtotPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Utils.darkenBackground(1f, activity);
                        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                    }
                });
                phtotPopwindow.setFocusable(true);
                phtotPopwindow.setOutsideTouchable(true);
                holder.gvCommentImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Utils.darkenBackground(0.5f, activity);
                        vpPhotoView.setCurrentItem(position);
                        phtotPopwindow.showAsDropDown(holder.rlComplaintDetailItem, 0, 0);
                    }
                });
            } else {
                holder.gvCommentImg.setVisibility(View.GONE);
            }
            holder.tvTime.setText(lists.get(position).getHowLong());
            isSureAgree(holder, lists.get(position).getPraiseStatus());
            holder.imgSureAgree.setText(lists.get(position).getPraiseNum() + "");
            holder.imgSureAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.imgSureAgree.setEnabled(false);
                    requestAddSureAgreeData(position, holder);

                }
            });
        } catch (Exception e) {
            Toast.makeText(activity, "请求出错！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    /**
     * 请求点赞接口
     */
    private void requestAddSureAgreeData(final int position, final ComplaintPropertyAdapter.MyViewHolder holder) {
        if (lists != null && lists.size() != 0) {
            ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(activity).createService(ComplaintApiInterfaces.class);
            int priseStatus = lists.get(position).getPraiseStatus();
            HashMap<String, Object> map = new HashMap<>();
            if (priseStatus == 1) {
                map.put("praiseStatus", 0);    //0取消点赞
            } else {
                map.put("praiseStatus", 1);    //点赞
            }
            map.put("replyId", lists.get(position).getId());
            RequestBody body = ParamForNet.put(map);
            Call<ResponseBody> call = interfaces.addRecordAgree(body);
            activity.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
                @Override
                public void success(Object result, String message) {
                    String num = holder.imgSureAgree.getText().toString();
                    if (message.equals("点赞成功")) {
                        isSureAgree(holder, 1);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum++;
                            holder.imgSureAgree.setText(countNum + "");
                            lists.get(position).setPraiseStatus(1);
                            lists.get(position).setPraiseNum(countNum);
                        }
                    } else if (message.equals("取消点赞成功")) {
                        isSureAgree(holder, 0);
                        if (num != null) {
                            int countNum = Integer.parseInt(num);
                            countNum--;
                            holder.imgSureAgree.setText(countNum + "");
                            lists.get(position).setPraiseStatus(0);
                            lists.get(position).setPraiseNum(countNum);
                        }
                    }
                    holder.imgSureAgree.setEnabled(true);
                }

                @Override
                public void fail() {
                    holder.imgSureAgree.setEnabled(true);
                }
            });
        } else {
            holder.imgSureAgree.setEnabled(true);
            Toast.makeText(activity, "点赞失败！", Toast.LENGTH_SHORT).show();
        }
    }

    private void isSureAgree(ComplaintPropertyAdapter.MyViewHolder holder, int booleanValue) {
        if (booleanValue == 0) {
            Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great_normal);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.imgSureAgree.setCompoundDrawables(drawable, null, null, null);
            holder.imgSureAgree.setTextColor(Color.parseColor("#9a9a9a"));
        } else {
            Drawable drawable = activity.getResources().getDrawable(R.drawable.sweet_cicle_great);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.imgSureAgree.setCompoundDrawables(drawable, null, null, null);
            holder.imgSureAgree.setTextColor(Color.parseColor("#f3705a"));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void addData(List<PropertyProgressBean.DataBean> list) {
        this.lists = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_more)
        ImageView tvMore;
        @BindView(R.id.gv_comment_img)
        CustomGridView gvCommentImg;
        @BindView(R.id.img_sure_agree)
        TextView imgSureAgree;
        @BindView(R.id.rl_complaint_detail_item)
        RelativeLayout rlComplaintDetailItem;
        @BindView(R.id.tv_reply)
        TextView tvReply;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyAdapter extends BaseAdapter {
        private List<String> imgs;

        public MyAdapter(List<String> images) {
            imgs = images;
        }

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public Object getItem(int position) {
            return imgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.complaint_img_upload, null);
                holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.img_upload);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PictureHelper.setOptionsGlide(activity, 12, imgs.get(position), holder.img, R.drawable.img_err);
            return convertView;
        }
    }

    class ViewHolder {
        ImageView img;
    }

    class PhotoViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (imgs.size() != 0) {
                PhotoView photoView = new PhotoView(activity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                photoView.setLayoutParams(params);
                if (imgs.get(position).endsWith("_small")) {
                    PictureHelper.setImageView(activity, imgs.get(position), photoView, R.drawable.img_err);
                } else {
                    String[] split = imgs.get(position).split("\\.");
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
                    PictureHelper.setImageView(activity, sb.toString(), photoView, R.drawable.img_err);
                }
                container.addView(photoView);
                return photoView;
            } else {
                Toast.makeText(activity, "暂无图片！", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
