package com.zhjl.qihao.base;

/**
 * 
 * @description 保存照片的最大数量
 * @version 1.0
 * @author 黄南榆
 * @date 2014年11月13日
 */
public class PhotoNumsBean {
	public static PhotoNumsBean bean;
	private int number = 0;

	public static PhotoNumsBean getInstant() {
		// TODO Auto-generated method stub
		if (null == bean) {
			bean = new PhotoNumsBean();
		}
		return bean;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
