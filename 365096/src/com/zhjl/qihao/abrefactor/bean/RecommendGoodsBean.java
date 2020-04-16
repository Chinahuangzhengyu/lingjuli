package com.zhjl.qihao.abrefactor.bean;

/**
 * Created by Administrator on 2017/7/9.
 */

public class RecommendGoodsBean {

    /**
     * userid : 578197  ---店铺id
     * company : 北京零1便利店
     * id : 13244    -----产品id
     * name : 牛头牌牛肉干
     * about : 测试
     * product_pic : http://120.55.185.167/uploadfile/good_product/1498783066.jpg
     * url :
     * end_time : 2017-09-07 11:22:00
     * catid : 10080504
     */

    private String userid;
    private String id;
    private String name;
    private String about;
    private String product_pic;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }
}
