package com.zhjl.qihao.activity.userlogin.vo;

import java.io.Serializable;

public class ThirdpartyVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String thirdType;
	private String uniqueId;
	private String address ;
	private String sex ;
	private String nikeName;
	private String heardURL;
	
	public String getThirdType() {
		return thirdType;
	}
	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public String getHeardURL() {
		return heardURL;
	}
	public void setHeardURL(String heardURL) {
		this.heardURL = heardURL;
	}
	
}
