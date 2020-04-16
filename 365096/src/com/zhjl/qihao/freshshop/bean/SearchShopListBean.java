package com.zhjl.qihao.freshshop.bean;

import java.util.List;

public class SearchShopListBean {


    /**
     * products : [{"id":3660,"name":"红皮土豆 约500g±50g/份","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/bf82476318b7112e940649d515956214_min.jpg","price":"1.68","promotion_price":"0.00","monthly_sales":"2"},{"id":11,"name":"裕泰绿豆500g","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/96650835ece669178feef3820f98b564_min.jpg","price":"11.50","promotion_price":"0.00","monthly_sales":0},{"id":16,"name":"双塔龙口绿豆粉丝160g","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/bc20876185f0134fb572d3a124434d8d_min.jpg","price":"6.50","promotion_price":"0.00","monthly_sales":0},{"id":36,"name":"干豆豉 约200g/份","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/99420271ed2f55a1c9175af2e2a9253d_min.jpg","price":"4.00","promotion_price":"0.00","monthly_sales":0},{"id":62,"name":"黄豆  约500g/份","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/d9e7fbd3cab0fb93d659c5fd73a7f7c5_min.jpg","price":"3.50","promotion_price":"0.00","monthly_sales":0},{"id":66,"name":"绿豆 约500g/份","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/76bd645d8b8eec613f8d68a6b31d5854_min.jpg","price":"6.50","promotion_price":"0.00","monthly_sales":0},{"id":68,"name":"红豆  约500g/份","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/fc2f5a40ef038a35133d973de88c3bcf_min.jpg","price":"7.80","promotion_price":"0.00","monthly_sales":0},{"id":825,"name":"奥利奥秘制红豆97g","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/fe3a1253eee709b96c5a9af8229a7c6c_min.jpg","price":"7.00","promotion_price":"0.00","monthly_sales":0},{"id":1077,"name":"凯福重庆怪味胡豆100g","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/890c2b7a495d99f63906473d304a8e55_min.jpg","price":"2.50","promotion_price":"0.00","monthly_sales":0},{"id":1096,"name":"老农夫炭烧豆干120g","image":"https://mall.qihaolingjuli.com/attachment/admin/product/image/20191220/7c3895d93f5ddad39463c89c094aaff8_min.jpg","price":"4.00","promotion_price":"0.00","monthly_sales":0}]
     * info : {"total":41,"current_page":1,"total_page":5}
     */

    private InfoBean info;
    private List<ProductsBean> products;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class InfoBean {
        /**
         * total : 41
         * current_page : 1
         * total_page : 5
         */

        private int total;
        private int current_page;
        private int total_page;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }
    }

    public static class ProductsBean {
        /**
         * id : 3660
         * name : 红皮土豆 约500g±50g/份
         * image : https://mall.qihaolingjuli.com/attachment/admin/product/image/20191219/bf82476318b7112e940649d515956214_min.jpg
         * price : 1.68
         * promotion_price : 0.00
         * monthly_sales : 2
         */

        private long id;
        private String name;
        private String image;
        private String price;
        private String promotion_price;
        private String monthly_sales;
        private long stock;

        public long getStock() {
            return stock;
        }

        public void setStock(long stock) {
            this.stock = stock;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getMonthly_sales() {
            return monthly_sales;
        }

        public void setMonthly_sales(String monthly_sales) {
            this.monthly_sales = monthly_sales;
        }
    }
}
