package com.zhjl.qihao.localphotos.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**    
 *  相册bean<br>
 *  {@link #image_id}图片id<br>
 *  {@link #path_absolute} 绝对路径<br>
 *  {@link #path_file} 用于显示的路径<br>
 *  {@link #name_album} 相册名称<br>
 */
public class AlbumInfo implements Parcelable {

	private static final long serialVersionUID = 1L;
	private int image_id;
	private String path_absolute;
	private String path_file;
	private String name_album;
	private List<PhotoInfo> list;
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getPath_absolute() {
		return path_absolute;
	}
	public void setPath_absolute(String path_absolute) {
		this.path_absolute = path_absolute;
	}
	public String getPath_file() {
		return path_file;
	}
	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}
	public String getName_album() {
		return name_album;
	}
	public void setName_album(String name_album) {
		this.name_album = name_album;
	}
	public List<PhotoInfo> getList() {
		return list;
	}
	public void setList(List<PhotoInfo> list) {
		this.list = list;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.image_id);
		dest.writeString(this.path_absolute);
		dest.writeString(this.path_file);
		dest.writeString(this.name_album);
		dest.writeList(this.list);
	}

	public AlbumInfo() {
	}

	protected AlbumInfo(Parcel in) {
		this.image_id = in.readInt();
		this.path_absolute = in.readString();
		this.path_file = in.readString();
		this.name_album = in.readString();
		this.list = new ArrayList<PhotoInfo>();
		in.readList(this.list, PhotoInfo.class.getClassLoader());
	}

	public static final Parcelable.Creator<AlbumInfo> CREATOR = new Parcelable.Creator<AlbumInfo>() {
		@Override
		public AlbumInfo createFromParcel(Parcel source) {
			return new AlbumInfo(source);
		}

		@Override
		public AlbumInfo[] newArray(int size) {
			return new AlbumInfo[size];
		}
	};
}
