package com.zhjl.qihao.propertyservicepay.bean;

import java.util.List;

public class PropertyPayRecordBean {

    /**
     * code : 200
     * data : [{"createTime":null,"items":[{"costMoney":20,"itemName":"物业管理费用","standard":"平方米"}],"orderSn":"wy2019102814332037","roomName":"H31单元0101","totalAmount":2160},{"createTime":"2019-10-30","items":[{"costMoney":20,"itemName":"物业管理费用","standard":"平方米"}],"orderSn":"wy2019102811523716","roomName":"H31单元0101","totalAmount":1080},{"createTime":"2019-10-29","items":[{"costMoney":20,"itemName":"物业管理费用","standard":"平方米"}],"orderSn":"wy2019102811420770","roomName":"H31单元0101","totalAmount":2160},{"createTime":null,"items":[{"costMoney":20,"itemName":"物业管理费用","standard":"平方米"}],"orderSn":"wy2019102811301817","roomName":"H31单元0101","totalAmount":2160}]
     * message : 获取数据成功
     * total : 0
     * totalPage : 1
     */

    private int code;
    private String message;
    private int total;
    private int totalPage;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : null
         * items : [{"costMoney":20,"itemName":"物业管理费用","standard":"平方米"}]
         * orderSn : wy2019102814332037
         * roomName : H31单元0101
         * totalAmount : 2160
         */

        private String createTime;
        private String orderSn;
        private String roomName;
        private double totalAmount;
        private List<ItemsBean> items;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * costMoney : 20
             * itemName : 物业管理费用
             * standard : 平方米
             */

            private double costMoney;
            private String itemName;
            private String standard;

            public double getCostMoney() {
                return costMoney;
            }

            public void setCostMoney(double costMoney) {
                this.costMoney = costMoney;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }
        }
    }
}
