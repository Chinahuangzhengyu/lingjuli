package com.zhjl.qihao.common.response.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SharedDataBean implements Serializable {

	private String result;
	private String message;
	private String totalNum;
	private List<SharedBean> noteList;
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
	public List<SharedBean> getNoteList() {
		return noteList;
	}

	public List<SharedBean> getNoteList(String labelName) {
		if(noteList == null || noteList.size() <= 0){
			return noteList;
		}

		List<SharedBean> list = new ArrayList<>();
		for(SharedBean sb : noteList){
			if(labelName.equals(sb.getLabelName())){
				list.add(sb);
			}
		}
		return list;

	}
	public void setNoteList(List<SharedBean> noteList) {
		this.noteList = noteList;
	}
	
	
}
