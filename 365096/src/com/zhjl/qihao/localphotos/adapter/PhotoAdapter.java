package com.zhjl.qihao.localphotos.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.localphotos.bean.PhotoInfo;
import com.zhjl.qihao.localphotos.util.ThumbnailsUtil;
import com.zhjl.qihao.abutil.PictureHelper;

/**
 * 相片适配器
 *
 * @author south
 */
public class PhotoAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<PhotoInfo> list;
    private ViewHolder viewHolder;
    private GridView gridView;
    private int width;
    private int height;
    private Context mContext;

    public PhotoAdapter(Context context, List<PhotoInfo> list, GridView gridView) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.gridView = gridView;
        Session session = Session.get(mContext);
        session.setScreenSize();
        width = Session.get(context).getWidth() / 2;
        height = Session.get(context).getHeight() / 4;
        mContext = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 刷新view
     *
     * @param index
     */
    public void refreshView(int index) {
        int visiblePos = gridView.getFirstVisiblePosition();
        View view = gridView.getChildAt(index - visiblePos);
        ViewHolder holder = (ViewHolder) view.getTag();

        if (list.get(index).isChoose()) {
            holder.selectImage.setImageResource(R.drawable.gou_selected);
        } else {
            holder.selectImage.setImageResource(R.drawable.gou_normal);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_selectphoto, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.selectImage = (ImageView) convertView.findViewById(R.id.selectImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).isChoose()) {
            viewHolder.selectImage.setImageResource(R.drawable.gou_selected);
        } else {
            viewHolder.selectImage.setImageResource(R.drawable.gou_normal);
        }
        LayoutParams layoutParams = viewHolder.image.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        viewHolder.image.setLayoutParams(layoutParams);
        final PhotoInfo photoInfo = list.get(position);
        if (photoInfo != null) {
            PictureHelper.showPictureWithCustom(mContext, viewHolder.image,
                    photoInfo.getPath_file(), R.drawable.square_default_diagram);
        } else {
            viewHolder.image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.img_loading));
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
        public ImageView selectImage;
    }
}
