package com.zhjl.qihao.entity;


/***
 * 
 * @description 消息Bean
 * @author south
 *
 */
public class InfoBean  {

	
	private String id;
	
	private String titlePicPath;
	/**标题*/
	private String title;
	/**提示*/
	private String summary;
	/***/
	private String serviceIdFk;
	/***/
	private String subServiceType;
	/***/
	private String serviceType;
	/**小区名称*/
	private String smailCommunityName;
	/**链接*/
	private String link;
	/**发布日期*/
	private String publishTime;
	/**状态*/
	private String typeDesc;
	
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getServiceIdFk() {
		return serviceIdFk;
	}
	public void setServiceIdFk(String serviceIdFk) {
		this.serviceIdFk = serviceIdFk;
	}
	public String getSubServiceType() {
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSmailCommunityName() {
		return smailCommunityName;
	}
	public void setSmailCommunityName(String smailCommunityName) {
		this.smailCommunityName = smailCommunityName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
