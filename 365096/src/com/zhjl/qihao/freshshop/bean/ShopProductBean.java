package com.zhjl.qihao.freshshop.bean;

public class ShopProductBean {


    /**
     * id : 8
     * name : 墨茄
     * price : 1000.00
     * promotion_price : 0.00
     * image : http://tp.qihaolingjuli.com/attachment/image/product/20191203/d49cedcae73806bfd25525860b8421b3_min.jpg
     */

    private long id;
    private String name;
    private String price;
    private String promotion_price;
    private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
