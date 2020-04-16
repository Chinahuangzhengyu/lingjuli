package com.zhjl.qihao.abcommon;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface RequestListener<T> {
	/**
     * 成功
     *
     * @param <T>
     */
    public void requestSuccess(T result,int action) throws JSONException;
	/**
     * 错误
     */
    public void requestError(VolleyError e,int action);
}
