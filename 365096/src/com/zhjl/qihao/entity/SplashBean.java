package com.zhjl.qihao.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
@Table(name="splash_bean")
public class SplashBean {
	@Id(column = "_id")
	private int _id;
	private String titlePicPath;
	private String title;
	private String link;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTitlePicPath() {
		return titlePicPath;
	}

	public void setTitlePicPath(String titlePicPath) {
		this.titlePicPath = titlePicPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
