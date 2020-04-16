package com.zhjl.qihao.order.bean;

import java.util.List;

public class ServiceAfterDetailBean {


    /**
     * status : true
     * no : 20191205000155151
     * deal_date : 0
     * status_name : 换货处理中
     * status_code : 3
     * deal_code : 0
     * create_date : 2019-12-05 17:37:30
     * content : 我要退款………………。。。。。。。
     * reply :
     * price : 3.00
     * return_msg : 到贵州省凯里市博南小区H33栋3单元0202取回原商品并更换
     * images : [{"min":"http://tp.qihaolingjuli.com/attachment/image/user/15220328700869de92519083445f881d/aftersale/20191205/5cfeb8211d322fdd95fa1416bf10c843_min.jpg","image":"http://tp.qihaolingjuli.com/attachment/image/user/15220328700869de92519083445f881d/aftersale/20191205/5cfeb8211d322fdd95fa1416bf10c843_middle.jpg"}]
     */

    private boolean status;
    private String no;
    private String deal_date;
    private String status_name;
    private int status_code;
    private int deal_code;
    private String create_date;
    private String content;
    private String reply;
    private String price;
    private String return_msg;
    private List<ImagesBean> images;
    private float points_deduct_price;

    public float getPoints_deduct_price() {
        return points_deduct_price;
    }

    public void setPoints_deduct_price(float points_deduct_price) {
        this.points_deduct_price = points_deduct_price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(String deal_date) {
        this.deal_date = deal_date;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getDeal_code() {
        return deal_code;
    }

    public void setDeal_code(int deal_code) {
        this.deal_code = deal_code;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * min : http://tp.qihaolingjuli.com/attachment/image/user/15220328700869de92519083445f881d/aftersale/20191205/5cfeb8211d322fdd95fa1416bf10c843_min.jpg
         * image : http://tp.qihaolingjuli.com/attachment/image/user/15220328700869de92519083445f881d/aftersale/20191205/5cfeb8211d322fdd95fa1416bf10c843_middle.jpg
         */

        private String min;
        private String image;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
