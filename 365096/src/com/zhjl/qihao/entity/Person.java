package com.zhjl.qihao.entity;

public class Person {
	private String name;
	private String pinYinName;
	private String address;
	private String id;
	private String flag;
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	public Person(String name, String pinYinName) {
		super();
		this.name = name;
		this.pinYinName = pinYinName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinYinName() {
		return pinYinName;
	}

	public void setPinYinName(String pinYinName) {
		this.pinYinName = pinYinName;
	}

}
