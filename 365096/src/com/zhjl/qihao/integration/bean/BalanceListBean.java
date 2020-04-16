package com.zhjl.qihao.integration.bean;

import java.util.List;

public class BalanceListBean {

    /**
     * status : true
     * money : 124.98
     * spend_list : []
     * total : 0
     * total_page : 0
     * current_page : 0
     */

    private boolean status;
    private String money;
    private int total;
    private int total_page;
    private int current_page;
    private List<SpendListBean> spend_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<SpendListBean> getSpend_list() {
        return spend_list;
    }

    public void setSpend_list(List<SpendListBean> spend_list) {
        this.spend_list = spend_list;
    }

    public static class SpendListBean {

        private String create_time;
        private String price;
        private String content;

        public String getCreateTime() {
            return create_time;
        }

        public void setCreateTime(String create_time) {
            this.create_time = create_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
