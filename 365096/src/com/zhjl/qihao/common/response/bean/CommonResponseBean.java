package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;

public class CommonResponseBean<T> implements Serializable {
	
	private String result;
	private String message;
	private String totalNum;
	private T data;
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
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
}
