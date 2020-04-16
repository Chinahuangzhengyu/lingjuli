package com.zhjl.qihao.abrefactor.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

//团购
@Entity
public class GroupBuyingBean {
    @Id(autoincrement = true)
    private Long id;
    private String url_name;
    private String url;
    private String logo;
    private String note;
    private String service_cell;

    @Generated(hash = 759510491)
    public GroupBuyingBean(Long id, String url_name, String url, String logo,
            String note, String service_cell) {
        this.id = id;
        this.url_name = url_name;
        this.url = url;
        this.logo = logo;
        this.note = note;
        this.service_cell = service_cell;
    }

    @Generated(hash = 928973759)
    public GroupBuyingBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getService_cell() {
        return service_cell;
    }

    public void setService_cell(String service_cell) {
        this.service_cell = service_cell;
    }
}
