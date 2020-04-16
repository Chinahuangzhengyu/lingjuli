package com.zhjl.qihao.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
/***
 * 
 * @description 消息Bean
 * @author south
 *
 */
@Table(name = "messagecenterdata")
public class MessageCenterBean {
	@Id(column = "_id")
	private int _id;
	private String data;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
