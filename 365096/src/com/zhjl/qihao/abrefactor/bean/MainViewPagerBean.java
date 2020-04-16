package com.zhjl.qihao.abrefactor.bean;

public class MainViewPagerBean {

    /**
     * id : 11
     * title : 极品海参
     * iamge : http://tp.qihaolingjuli.com/attachment/admin/advertisement/image/20191205/f425aaf0bf7f2971916177bbdb0f11a5.jpg
     * advertisement_type : H5web
     * url : https://m.jd.com/
     * to_id : 0
     */

    private int id;
    private String title;
    private String iamge;
    private String advertisement_type;
    private String url;
    private long to_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public String getAdvertisement_type() {
        return advertisement_type;
    }

    public void setAdvertisement_type(String advertisement_type) {
        this.advertisement_type = advertisement_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTo_id() {
        return to_id;
    }

    public void setTo_id(long to_id) {
        this.to_id = to_id;
    }
}
