package com.zhjl.qihao.abrefactor.bean;

/**
 * 作者： 黄郑宇
 * 时间： 2018/6/7
 * 类作用：
 */

public class ListBean {
    private String str;
    private int refences;

    public ListBean(String str, int refences) {
        this.str = str;
        this.refences = refences;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getRefences() {
        return refences;
    }

    public void setRefences(int refences) {
        this.refences = refences;
    }
}
