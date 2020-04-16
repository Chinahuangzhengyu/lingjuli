package com.zhjl.qihao.localphotos.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.localphotos.bean.AlbumInfo;
import com.zhjl.qihao.localphotos.util.ThumbnailsUtil;
import com.zhjl.qihao.abutil.PictureHelper;

/**
 * 相册适配器
 * @author south
 */
public class PhotoFolderAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<AlbumInfo> list;
	private ViewHolder viewHolder;
	private Context mContext;
	
	public PhotoFolderAdapter(Context context,List<AlbumInfo> list){
		mInflater = LayoutInflater.from(context);
		this.list = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_photofolder, null);
			viewHolder.image = (ImageView)convertView.findViewById(R.id.imageView);
			viewHolder.text = (TextView)convertView.findViewById(R.id.info);
			viewHolder.num = (TextView)convertView.findViewById(R.id.num);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AlbumInfo albumInfo = list.get(position);
		PictureHelper.showPictureWithCustom(mContext, viewHolder.image, ThumbnailsUtil.MapgetHashValue(albumInfo.getImage_id(),
				albumInfo.getPath_file()), R.drawable.square_default_diagram);
		viewHolder.text.setText(albumInfo.getName_album());
		viewHolder.num.setText("("+list.get(position).getList().size()+"张)");
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView image;
		public TextView text;
		public TextView num;
	}
}
