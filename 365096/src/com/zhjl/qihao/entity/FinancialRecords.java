package com.zhjl.qihao.entity;

import java.io.Serializable;

public class FinancialRecords implements Serializable{

	private String timer;
	private String type ;
	private String details;
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	
}
