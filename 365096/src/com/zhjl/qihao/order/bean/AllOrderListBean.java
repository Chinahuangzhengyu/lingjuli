package com.zhjl.qihao.order.bean;

import java.util.List;

public class AllOrderListBean {

    /**
     * status : true
     * order_list : [{"order_id":62,"status":0,"price":"1.80","items":[{"order_item_id":55,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":61,"status":0,"price":"1.80","items":[{"order_item_id":54,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":60,"status":0,"price":"1.80","items":[{"order_item_id":53,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":58,"status":0,"price":"1.80","items":[{"order_item_id":51,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":57,"status":0,"price":"1.80","items":[{"order_item_id":50,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":56,"status":0,"price":"1.80","items":[{"order_item_id":49,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":55,"status":0,"price":"1.80","items":[{"order_item_id":48,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":54,"status":0,"price":"1.80","items":[{"order_item_id":47,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}],"status_format":"等待付款"},{"order_id":44,"status":0,"price":"0.01","items":[],"status_format":"等待付款"},{"order_id":41,"status":0,"price":"0.01","items":[],"status_format":"等待付款"}]
     * list_info : {"total":13,"page":1,"total_page":2}
     */

    private boolean status;
    private ListInfoBean list_info;
    private List<OrderListBean> order_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ListInfoBean getList_info() {
        return list_info;
    }

    public void setList_info(ListInfoBean list_info) {
        this.list_info = list_info;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class ListInfoBean {
        /**
         * total : 13
         * page : 1
         * total_page : 2
         */

        private int total;
        private int page;
        private int total_page;

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
    }

    public static class OrderListBean {
        /**
         * order_id : 62
         * status : 0
         * price : 1.80
         * items : [{"order_item_id":55,"number":1,"comment":0,"price":"1.80","name":"大白菜","image":"http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","can_after_sale":1,"after_sale_format":""}]
         * status_format : 等待付款
         */

        private long order_id;
        private int status;
        private String price;
        private String pick_code;
        private String status_format;
        private List<ItemsBean> items;
        private String  delivery_type;
        private int delivery_type_status;

        public String getPick_code() {
            return pick_code;
        }

        public void setPick_code(String pick_code) {
            this.pick_code = pick_code;
        }

        public int getDelivery_type_status() {
            return delivery_type_status;
        }

        public void setDelivery_type_status(int delivery_type_status) {
            this.delivery_type_status = delivery_type_status;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus_format() {
            return status_format;
        }

        public void setStatus_format(String status_format) {
            this.status_format = status_format;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * order_item_id : 55
             * number : 1
             * comment : 0
             * price : 1.80
             * name : 大白菜
             * image : http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg
             * can_after_sale : 1
             * after_sale_format :
             */

            private int order_item_id;
            private int number;
            private int comment;
            private String price;
            private String name;
            private String image;
            private int can_after_sale;
            private String after_sale_format;
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

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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
        }
    }
}
