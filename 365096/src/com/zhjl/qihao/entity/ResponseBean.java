package com.zhjl.qihao.entity;

import java.io.Serializable;
import java.util.List;

public class ResponseBean<T> implements Serializable {

	private String result;
	private String message;

	public List<T> list;

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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
