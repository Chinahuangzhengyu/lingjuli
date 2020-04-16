package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;

/***
 * 
 * @description 信息状态
 * @author south
 *
 */
public class InfoResponseStateBean implements Serializable {
	
	private String system;
	
	private String legal;
	private String customer;
	private String forum;
	private String property;
	private String propertyNotice;
	private String order;
	private String orderNotice;
	private String result;
	private String message;
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getForum() {
		return forum;
	}
	public void setForum(String forum) {
		this.forum = forum;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPropertyNotice() {
		return propertyNotice;
	}
	public void setPropertyNotice(String propertyNotice) {
		this.propertyNotice = propertyNotice;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderNotice() {
		return orderNotice;
	}
	public void setOrderNotice(String orderNotice) {
		this.orderNotice = orderNotice;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
