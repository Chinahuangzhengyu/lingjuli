package com.zhjl.qihao.abrefactor.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 公益活动
 */
@Entity
public class PublicBenefitActivitiesBean {
    @Id(autoincrement = true)
    private Long id;
    private String url_name;
    private String url;
    private String logo;
    private String note;
    private String shopid;
    private String shopname;
    private String service_cell;
    @Generated(hash = 1755275048)
    public PublicBenefitActivitiesBean(Long id, String url_name, String url,
            String logo, String note, String shopid, String shopname,
            String service_cell) {
        this.id = id;
        this.url_name = url_name;
        this.url = url;
        this.logo = logo;
        this.note = note;
        this.shopid = shopid;
        this.shopname = shopname;
        this.service_cell = service_cell;
    }
    @Generated(hash = 267936504)
    public PublicBenefitActivitiesBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl_name() {
        return this.url_name;
    }
    public void setUrl_name(String url_name) {
        this.url_name = url_name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getShopid() {
        return this.shopid;
    }
    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
    public String getShopname() {
        return this.shopname;
    }
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    public String getService_cell() {
        return this.service_cell;
    }
    public void setService_cell(String service_cell) {
        this.service_cell = service_cell;
    }
    

}
