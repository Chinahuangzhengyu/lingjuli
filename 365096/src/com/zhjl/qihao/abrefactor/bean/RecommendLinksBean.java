package com.zhjl.qihao.abrefactor.bean;

/**
 * Created by Administrator on 2017/7/9.
 * 发现好物的数据结构
 */

public class RecommendLinksBean {

    /**
     * url_name : 西池
     * url : http://www.baidu.com
     * logo : http://120.55.185.167/uploadfile/all/1498794555.jpg
     * note : 大沙发
     */

    private String url_name;
    private String url;
    private String logo;
    private String note;
    private String service_cell;

    public String getService_cell() {
        return service_cell;
    }

    public void setService_cell(String service_cell) {
        this.service_cell = service_cell;
    }

    public String getUrl_name() {
        return url_name;
    }

    public void setUrl_name(String url_name) {
        this.url_name = url_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
