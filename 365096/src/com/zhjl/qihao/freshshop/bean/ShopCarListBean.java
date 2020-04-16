package com.zhjl.qihao.freshshop.bean;

public class ShopCarListBean {

    /**
     * product_id : 3
     * name : 大白菜
     * image : http://192.168.1.48/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg
     * number : 2
     * price : 1.80
     * cheap_price : 0.20
     * on_sale : 1
     * stock : 999
     */

    private long product_id;
    private String name;
    private String image;
    private int number;
    private String price;
    private String cheap_price;
    private int on_sale;
    private int stock;
    private String id;
    private long product_more_unit_id;
    private String unit_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public long getProduct_more_unit_id() {
        return product_more_unit_id;
    }

    public void setProduct_more_unit_id(long product_more_unit_id) {
        this.product_more_unit_id = product_more_unit_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCheap_price() {
        return cheap_price;
    }

    public void setCheap_price(String cheap_price) {
        this.cheap_price = cheap_price;
    }

    public int getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(int on_sale) {
        this.on_sale = on_sale;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
