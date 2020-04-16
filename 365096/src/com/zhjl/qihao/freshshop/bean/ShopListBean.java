package com.zhjl.qihao.freshshop.bean;

import java.util.List;

public class ShopListBean {


    /**
     * data : [{"id":1,"name":"草果","have_more_unit":false,"price":"0.02","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/cd87f12c8620bb01e6a6fb4668289153_min.jpg","monthly_sales":"9","promotion_price":"0","stock":"9.940","product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":2,"name":"莲子","have_more_unit":true,"price":"0.01","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/6479380e3b939c08f3ed4678217f9328_min.jpg","monthly_sales":0,"promotion_price":"0","stock":58,"product_more_unit":[{"product_more_unit_id":1,"name":"一两","price":"0.01","stock":58},{"product_more_unit_id":2,"name":"二两","price":"0.01","stock":29}]},{"id":6,"name":"八角","have_more_unit":true,"price":"1.96","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/e7f100569bb2b199e7ece496f9619eda_min.jpg","monthly_sales":0,"promotion_price":"0","stock":61,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":7,"name":"胡椒","have_more_unit":false,"price":"80.00","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191220/3589f9b8320df6835e62a28d1d704759_min.jpg","monthly_sales":"77","promotion_price":"0","stock":1,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":8,"name":"干花椒（新）","have_more_unit":false,"price":"240.00","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/93b93fb54c5e0722bc71ca1d314e2df1_min.jpg","monthly_sales":0,"promotion_price":"0","stock":0,"product_more_unit":[]},{"id":15,"name":"裕泰小花银耳","have_more_unit":false,"price":"9.50","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191220/1bca879b663b658e6009ed38f45a6e4a_min.jpg","monthly_sales":0,"promotion_price":"0","stock":0,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":16,"name":"双塔龙口绿豆粉丝160g","have_more_unit":false,"price":"6.50","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/bc20876185f0134fb572d3a124434d8d_min.jpg","monthly_sales":"15","promotion_price":"0","stock":0,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":22,"name":"裕泰纯天然香菇100g","have_more_unit":false,"price":"22.00","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/09c6807cf0e8ca07464ee71ad71ee0fa_min.jpg","monthly_sales":0,"promotion_price":"0","stock":0,"product_more_unit":[]},{"id":26,"name":"裕泰野生黑木耳100g","have_more_unit":false,"price":"21.80","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191220/e08a3df7c3e794dbcfd3104ebc860680_min.jpg","monthly_sales":0,"promotion_price":"0","stock":0,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]},{"id":28,"name":"裕泰便装红枣200g","have_more_unit":false,"price":"7.00","image":"https://tp.qihaolingjuli.com/attachment/admin/product/image/20191220/4ea0cb6c262465d16e331f1f36869e5d_min.jpg","monthly_sales":0,"promotion_price":"0","stock":0,"product_more_unit":[{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]}]
     * info : {"total":30,"totalPage":3,"currentPage":1}
     */

    private InfoBean info;
    private List<DataBean> data;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class InfoBean {
        /**
         * total : 30
         * totalPage : 3
         * currentPage : 1
         */

        private int total;
        private int totalPage;
        private int currentPage;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 草果
         * have_more_unit : false
         * price : 0.02
         * image : https://tp.qihaolingjuli.com/attachment/admin/product/image/20191219/cd87f12c8620bb01e6a6fb4668289153_min.jpg
         * monthly_sales : 9
         * promotion_price : 0
         * stock : 9.940
         * product_more_unit : [{"product_more_unit_id":3,"name":"一两","price":"1.96","stock":61}]
         */

        private long id;
        private String name;
        private int have_more_unit;
        private String price;
        private String image;
        private String monthly_sales;
        private String promotion_price;
        private String stock;
        private List<ShopDetailBean.ProductMoreUnitBean> product_more_unit;

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

        public int isHave_more_unit() {
            return have_more_unit;
        }

        public void setHave_more_unit(int have_more_unit) {
            this.have_more_unit = have_more_unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMonthly_sales() {
            return monthly_sales;
        }

        public void setMonthly_sales(String monthly_sales) {
            this.monthly_sales = monthly_sales;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public List<ShopDetailBean.ProductMoreUnitBean> getProduct_more_unit() {
            return product_more_unit;
        }

        public void setProduct_more_unit(List<ShopDetailBean.ProductMoreUnitBean> product_more_unit) {
            this.product_more_unit = product_more_unit;
        }


    }
}
