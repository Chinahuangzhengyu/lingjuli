package com.zhjl.qihao.abrefactor.bean;

import java.util.List;

/**
 * 作者： 黄郑宇
 * 时间： 2018/8/22
 * 类作用：获取总积分
 */
public class SumIntegralBean {

    /**
     * points : 477
     * lv : 等级二
     * result : 0
     * message : 暂无商品
     * list : []
     */

    private String points;
    private String lv;
    private String result;
    private String message;
    private List<?> list;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
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

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
