package com.zhjl.qihao.order.bean;

import java.util.List;

public class ShopDetailBean {


    /**
     * status : true
     * order_no : mall20191203000000005
     * payment_type :
     * create_time : 2019-12-03 14:48:59
     * pay_time : 0
     * address :
     * tel :
     * name :
     * latitude : false
     * longitude : false
     * status_name : 等待付款
     * status_code : 0
     * delivery_title :
     * delivery_detail :
     * original_price : 0.01
     * total_price : 0.01
     * deduct_price : 0.00
     * delivery_price : 0.00
     * delivery_type : 送货到家
     * delivery_code : 2
     * order_items : [{"product_id":4,"name":"百事可乐","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191127/069feb219a2ca2b098deec8962b10583_min.jpg","comment":0,"can_after_sale":1,"after_sale_format":"","price":"0.01","number":1}]
     */

    private boolean status;
    private String order_no;
    private String payment_type;
    private String pick_code;
    private String create_time;
    private String pay_time;
    private String address;
    private String tel;
    private String name;
    private double latitude;
    private double longitude;
    private String status_name;
    private int status_code;
    private String delivery_title;
    private String delivery_detail;
    private String original_price;
    private String total_price;
    private String deduct_price;
    private String delivery_price;
    private String delivery_type;
    private int delivery_code;
    private String memo;
    private List<OrderItemsBean> order_items;

    public String getPick_code() {
        return pick_code;
    }

    public void setPick_code(String pick_code) {
        this.pick_code = pick_code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getDelivery_title() {
        return delivery_title;
    }

    public void setDelivery_title(String delivery_title) {
        this.delivery_title = delivery_title;
    }

    public String getDelivery_detail() {
        return delivery_detail;
    }

    public void setDelivery_detail(String delivery_detail) {
        this.delivery_detail = delivery_detail;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getDeduct_price() {
        return deduct_price;
    }

    public void setDeduct_price(String deduct_price) {
        this.deduct_price = deduct_price;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public int getDelivery_code() {
        return delivery_code;
    }

    public void setDelivery_code(int delivery_code) {
        this.delivery_code = delivery_code;
    }

    public List<OrderItemsBean> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderItemsBean> order_items) {
        this.order_items = order_items;
    }

    public static class OrderItemsBean {
        /**
         * product_id : 4
         * name : 百事可乐
         * image : http://tp.qihaolingjuli.com/attachment/image/product/20191127/069feb219a2ca2b098deec8962b10583_min.jpg
         * comment : 0
         * can_after_sale : 1
         * after_sale_format :
         * price : 0.01
         * number : 1
         */

        private int product_id;
        private String name;
        private String image;
        private int comment;
        private int can_after_sale;
        private String after_sale_format;
        private String price;
        private int number;
        private int order_item_id;
        private String unit_name;

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public int getOrder_item_id() {
            return order_item_id;
        }

        public void setOrder_item_id(int order_item_id) {
            this.order_item_id = order_item_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getCan_after_sale() {
            return can_after_sale;
        }

        public void setCan_after_sale(int can_after_sale) {
            this.can_after_sale = can_after_sale;
        }

        public String getAfter_sale_format() {
            return after_sale_format;
        }

        public void setAfter_sale_format(String after_sale_format) {
            this.after_sale_format = after_sale_format;
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
    }
}
