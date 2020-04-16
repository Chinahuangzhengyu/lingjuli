package com.zhjl.qihao.integration.bean;

public class CardListBean {

    /**
     * card_id : 10003
     * money : 125.00
     */

    private String card_id;
    private String money;
    private int is_default;

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
