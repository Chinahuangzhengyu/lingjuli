package com.zhjl.qihao.entity;

public class Letter {

	
	private String id;
	/**发件人*/
	private String sender;
	/**收件人*/
	public String addressee;
	/**发送时间*/
	private String sendTimer;
	/**收件时间*/
	private String receiveTimer;
	/**标题*/
	private String title;
	/**内容*/
	private String body;
	
	private boolean flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getSendTimer() {
		return sendTimer;
	}

	public void setSendTimer(String sendTimer) {
		this.sendTimer = sendTimer;
	}

	public String getReceiveTimer() {
		return receiveTimer;
	}

	public void setReceiveTimer(String receiveTimer) {
		this.receiveTimer = receiveTimer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
