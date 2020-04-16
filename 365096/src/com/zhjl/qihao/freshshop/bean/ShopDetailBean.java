package com.zhjl.qihao.freshshop.bean;

import java.util.List;

public class ShopDetailBean {

    /**
     * product : {"id":6,"name":"大白菜","summary":"优质大白菜特惠促销","price":"200.00","points_price":"0.00","content":"<!DOCTYPE HTML>\n<html>\n<head>\n    <style type=\"text/css\">\n        body{margin:0;}\n        .content{width:100%;padding:8px 16px;text-align: center}\n        .content img{max-width:100%;}\n    <\/style>\n<\/head>\n    <body>\n        <div class=\"content\">\n            <img src=\"http://192.168.1.48/attachment/image/content/image/20191115/20191115202530_92688_middle.jpg\" alt=\"\" /><img src=\"http://192.168.1.48/attachment/image/content/image/20191115/20191115202135_41712_middle.jpg\" alt=\"\" /><img src=\"http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_47074_middle.jpg\" alt=\"\" /><img src=\"http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_43813_middle.jpg\" alt=\"\" /><img src=\"http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_52307_middle.jpg\" alt=\"\" />\n        <\/div>\n    <\/body>\n<\/html>\n","promotion_price":"1.50","minImages":["http://192.168.1.48/attachment/image/product/20191115/1a64ff5ea8e039fa27745bdd077660dd_min.jpg","http://192.168.1.48/attachment/image/product/20191115/bf8dddfb07933ea424e062b57f3f4ad0_min.jpg","http://192.168.1.48/attachment/image/product/20191115/ca9ef4b2ed2b165228c50bb6612a9a9b_min.jpg","http://192.168.1.48/attachment/image/product/20191115/1f2465827cf42169a0a5db97d9df897f_min.jpg"],"midImages":["http://192.168.1.48/attachment/image/product/20191115/1a64ff5ea8e039fa27745bdd077660dd_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/bf8dddfb07933ea424e062b57f3f4ad0_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/ca9ef4b2ed2b165228c50bb6612a9a9b_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/1f2465827cf42169a0a5db97d9df897f_middle.jpg"]}
     * comments : [{"id":1,"phone_user_id":"150125593209311b1b8755e240e386c4","content":"东西很好我很喜欢","score":5,"create_date":"2019-11-16","score_status":"好评","nick_name":"黄佛丁","user_picture":"http://192.168.1.48/attachment/image/user/info/20191116/8221187f6a6f4e928074af9179a4e0f5_small.jpg"},{"id":2,"phone_user_id":"15237048182780fe50c0428540e79463","content":"不错不错","score":4,"create_date":"2019-11-16","score_status":"中评","nick_name":"骆驼","user_picture":"http://192.168.1.48/attachment/image/user/info/20191116/7a19707b661449fd8fc4c581ac9b72f3_small.jpg"}]
     */

    private ProductBean product;
    private List<CommentsBean> comments;
    private int comment_total;

    public int getComment_total() {
        return comment_total;
    }

    public void setComment_total(int comment_total) {
        this.comment_total = comment_total;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class ProductBean {
        /**
         * id : 6
         * name : 大白菜
         * summary : 优质大白菜特惠促销
         * price : 200.00
         * points_price : 0.00
         * content : <!DOCTYPE HTML>
         <html>
         <head>
         <style type="text/css">
         body{margin:0;}
         .content{width:100%;padding:8px 16px;text-align: center}
         .content img{max-width:100%;}
         </style>
         </head>
         <body>
         <div class="content">
         <img src="http://192.168.1.48/attachment/image/content/image/20191115/20191115202530_92688_middle.jpg" alt="" /><img src="http://192.168.1.48/attachment/image/content/image/20191115/20191115202135_41712_middle.jpg" alt="" /><img src="http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_47074_middle.jpg" alt="" /><img src="http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_43813_middle.jpg" alt="" /><img src="http://192.168.1.48/attachment/image/content/image/20191115/20191115202136_52307_middle.jpg" alt="" />
         </div>
         </body>
         </html>
         * promotion_price : 1.50
         * minImages : ["http://192.168.1.48/attachment/image/product/20191115/1a64ff5ea8e039fa27745bdd077660dd_min.jpg","http://192.168.1.48/attachment/image/product/20191115/bf8dddfb07933ea424e062b57f3f4ad0_min.jpg","http://192.168.1.48/attachment/image/product/20191115/ca9ef4b2ed2b165228c50bb6612a9a9b_min.jpg","http://192.168.1.48/attachment/image/product/20191115/1f2465827cf42169a0a5db97d9df897f_min.jpg"]
         * midImages : ["http://192.168.1.48/attachment/image/product/20191115/1a64ff5ea8e039fa27745bdd077660dd_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/bf8dddfb07933ea424e062b57f3f4ad0_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/ca9ef4b2ed2b165228c50bb6612a9a9b_middle.jpg","http://192.168.1.48/attachment/image/product/20191115/1f2465827cf42169a0a5db97d9df897f_middle.jpg"]
         */

        private long id;
        private String name;
        private String summary;
        private String price;
        private String points_price;
        private String detail_url;
        private String promotion_price;
        private List<String> minImages;
        private List<String> midImages;
        private String monthly_sales;
        private String stock;
        private int have_more_unit;
        private List<ProductMoreUnitBean> product_more_unit;

        public List<ProductMoreUnitBean> getProduct_more_unit() {
            return product_more_unit;
        }

        public void setProduct_more_unit(List<ProductMoreUnitBean> product_more_unit) {
            this.product_more_unit = product_more_unit;
        }

        public int isHave_more_unit() {
            return have_more_unit;
        }

        public void setHave_more_unit(int have_more_unit) {
            this.have_more_unit = have_more_unit;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getMonthly_sales() {
            return monthly_sales;
        }

        public void setMonthly_sales(String monthly_sales) {
            this.monthly_sales = monthly_sales;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPoints_price() {
            return points_price;
        }

        public void setPoints_price(String points_price) {
            this.points_price = points_price;
        }

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public List<String> getMinImages() {
            return minImages;
        }

        public void setMinImages(List<String> minImages) {
            this.minImages = minImages;
        }

        public List<String> getMidImages() {
            return midImages;
        }

        public void setMidImages(List<String> midImages) {
            this.midImages = midImages;
        }
    }

    public static class ProductMoreUnitBean {
        /**
         * product_more_unit_id : 3
         * name : 一两
         * price : 1.96
         * stock : 61
         */

        private long product_more_unit_id;
        private String name;
        private String price;
        private int stock;

        public long getProduct_more_unit_id() {
            return product_more_unit_id;
        }

        public void setProduct_more_unit_id(long product_more_unit_id) {
            this.product_more_unit_id = product_more_unit_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
    public static class CommentsBean {
        /**
         * id : 1
         * phone_user_id : 150125593209311b1b8755e240e386c4
         * content : 东西很好我很喜欢
         * score : 5
         * create_date : 2019-11-16
         * score_status : 好评
         * nick_name : 黄佛丁
         * user_picture : http://192.168.1.48/attachment/image/user/info/20191116/8221187f6a6f4e928074af9179a4e0f5_small.jpg
         */

        private int id;
        private String phone_user_id;
        private String content;
        private int score;
        private String create_date;
        private String score_status;
        private String nick_name;
        private String user_picture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone_user_id() {
            return phone_user_id;
        }

        public void setPhone_user_id(String phone_user_id) {
            this.phone_user_id = phone_user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getScore_status() {
            return score_status;
        }

        public void setScore_status(String score_status) {
            this.score_status = score_status;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public void setUser_picture(String user_picture) {
            this.user_picture = user_picture;
        }
    }
}
