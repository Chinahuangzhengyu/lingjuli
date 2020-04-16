package com.zhjl.qihao.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "RecImessage")
public class RecMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -958671002396781364L;
	@DatabaseField(id = true, dataType = DataType.STRING, index = true, width = 30)
	private String pid;
	@DatabaseField(dataType = DataType.STRING, width = 1000)
	private String title;
	@DatabaseField(dataType = DataType.STRING, width = 1024)
	private String message_url;
	@DatabaseField(dataType = DataType.STRING, width = 1024)
	private String local_message_url;
	@DatabaseField(dataType = DataType.STRING, width = 2)
	private String message_type;
	@DatabaseField(dataType = DataType.STRING, width = 1000)
	private String publisher;
	@DatabaseField(dataType = DataType.STRING, width = 1000)
	private String receiver;
	@DatabaseField(dataType = DataType.STRING, width = 1, defaultValue = "1")
	private String state;
	@DatabaseField(dataType = DataType.STRING, width = 20)
	private String create_time;
	@DatabaseField(dataType = DataType.STRING, width = 1, defaultValue = "1")
	private String is_remin;
	@DatabaseField(dataType = DataType.STRING, width = 1, defaultValue = "0")
	private String is_loacl;

	public RecMessage() {
		super();
	}

	public RecMessage(String pid, String title, String message_url,
			String message_type, String publisher, String receiver,
			String state, String create_time, String is_remin) {
		super();
		this.pid = pid;
		this.title = title;
		this.message_url = message_url;
		this.message_type = message_type;
		this.publisher = publisher;
		this.receiver = receiver;
		this.state = state;
		this.create_time = create_time;
		this.is_remin = is_remin;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage_url() {
		return message_url;
	}

	public void setMessage_url(String message_url) {
		this.message_url = message_url;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getIs_remin() {
		return is_remin;
	}

	public void setIs_remin(String is_remin) {
		this.is_remin = is_remin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIs_loacl() {
		return is_loacl;
	}

	public void setIs_loacl(String is_loacl) {
		this.is_loacl = is_loacl;
	}

	public String getLocal_message_url() {
		return local_message_url;
	}

	public void setLocal_message_url(String local_message_url) {
		this.local_message_url = local_message_url;
	}

}
