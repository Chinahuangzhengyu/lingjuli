package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;
import java.util.List;

import com.zhjl.qihao.entity.InfoBean;
/***
 * 
 * @description 信息请求消息响应
 * @author south
 *
 */
public class InfoResposeBean implements Serializable {

	private String totalNumber;
	private String result;
	private String message;
	private List<InfoBean> list;
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
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
	public List<InfoBean> getList() {
		return list;
	}
	public void setList(List<InfoBean> list) {
		this.list = list;
	}
	
	
	
}
