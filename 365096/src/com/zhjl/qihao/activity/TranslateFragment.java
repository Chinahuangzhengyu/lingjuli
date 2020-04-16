package com.zhjl.qihao.activity;

import java.io.Serializable;

import com.zhjl.qihao.base.BaseWebFragment;

public class TranslateFragment implements Serializable {
	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 1L;
	private BaseWebFragment bwf;
	private static TranslateFragment tf;
	
	public static TranslateFragment getInstant() {
		if(null==tf){
			tf=new TranslateFragment();
		}
		return tf;
	}

	public BaseWebFragment getBwf() {
		return bwf;
	}

	public void setBwf(BaseWebFragment bwf) {
		this.bwf = bwf;
	}
}
