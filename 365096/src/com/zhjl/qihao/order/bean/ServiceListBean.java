package com.zhjl.qihao.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ServiceListBean {

    /**
     * status : true
     * product_aftersale : [{"aftersale_id":1,"order_id":126,"shop_name":"","product_name":"大白菜","product_image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","price":"1.80","number":1,"status_name":"换货，处理中"},{"aftersale_id":12,"order_id":120,"shop_name":"","product_name":"大白菜","product_image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","price":"1.80","number":1,"status_name":"换货，处理中"}]
     * total : 2
     * page : 1
     * total_page : 1
     */

    private boolean status;
    private int total;
    private int page;
    private int total_page;
    private List<ProductAftersaleBean> product_aftersale;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<ProductAftersaleBean> getProduct_aftersale() {
        return product_aftersale;
    }

    public void setProduct_aftersale(List<ProductAftersaleBean> product_aftersale) {
        this.product_aftersale = product_aftersale;
    }

    public static class ProductAftersaleBean implements Parcelable {
        /**
         * aftersale_id : 1
         * order_id : 126
         * shop_name :
         * product_name : 大白菜
         * product_image : http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg
         * price : 1.80
         * number : 1
         * status_name : 换货，处理中
         */

        private int aftersale_id;
        private int order_id;
        private String shop_name;
        private String product_name;
        private String product_image;
        private String price;
        private int number;
        private int status_code;
        private String status_name;
        private String unit_name;

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public int getStatus_code() {
            return status_code;
        }

        public void setStatus_code(int status_code) {
            this.status_code = status_code;
        }

        public int getAftersale_id() {
            return aftersale_id;
        }

        public void setAftersale_id(int aftersale_id) {
            this.aftersale_id = aftersale_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public ProductAftersaleBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.aftersale_id);
            dest.writeInt(this.order_id);
            dest.writeString(this.shop_name);
            dest.writeString(this.product_name);
            dest.writeString(this.product_image);
            dest.writeString(this.price);
            dest.writeInt(this.number);
            dest.writeInt(this.status_code);
            dest.writeString(this.status_name);
            dest.writeString(this.unit_name);
        }

        protected ProductAftersaleBean(Parcel in) {
            this.aftersale_id = in.readInt();
            this.order_id = in.readInt();
            this.shop_name = in.readString();
            this.product_name = in.readString();
            this.product_image = in.readString();
            this.price = in.readString();
            this.number = in.readInt();
            this.status_code = in.readInt();
            this.status_name = in.readString();
            this.unit_name = in.readString();
        }

        public static final Creator<ProductAftersaleBean> CREATOR = new Creator<ProductAftersaleBean>() {
            @Override
            public ProductAftersaleBean createFromParcel(Parcel source) {
                return new ProductAftersaleBean(source);
            }

            @Override
            public ProductAftersaleBean[] newArray(int size) {
                return new ProductAftersaleBean[size];
            }
        };
    }
}
