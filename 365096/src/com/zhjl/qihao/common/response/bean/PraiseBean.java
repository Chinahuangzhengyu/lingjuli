package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;

public class PraiseBean implements Serializable{

	private String praisedUserId;
	private String picturePath;
	private String createResource;
	public String getPraisedUserId() {
		return praisedUserId;
	}
	public void setPraisedUserId(String praisedUserId) {
		this.praisedUserId = praisedUserId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getCreateResource() {
		return createResource;
	}
	public void setCreateResource(String createResource) {
		this.createResource = createResource;
	}
	
}
