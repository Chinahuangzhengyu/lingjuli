package com.zhjl.qihao.common.vo;

public class UidBean {
	private String Uid = null;
	private static UidBean uidBean;

	public static UidBean getInstant() {
		if (null == uidBean) {
			uidBean = new UidBean();
		}
		return uidBean;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}
}
