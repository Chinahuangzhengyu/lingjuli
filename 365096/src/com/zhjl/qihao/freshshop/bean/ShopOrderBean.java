package com.zhjl.qihao.freshshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderBean implements Parcelable {


    /**
     * status : true
     * products : [{"id":3,"name":"大白菜","price":"2.00","promotion_price":"1.80","points_price":"0.10","image":"http://192.168.1.48/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg","number":1}]
     * total_price : 1.80
     * freight : 0.00
     * total_points_price : 0.00
     * take_type : 2
     * fromProductCart : 0
     * order_id : mall_order_157438905212105efdc44859435a9e96_1574738532
     */

    private boolean status;
    private String total_price;
    private String freight;
    private String freight_tips;
    private String total_points_price;
    private int take_type;
    private int fromProductCart;
    private String order_id;
    private List<ProductsBean> products;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFreight_tips() {
        return freight_tips;
    }

    public void setFreight_tips(String freight_tips) {
        this.freight_tips = freight_tips;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getTotal_points_price() {
        return total_points_price;
    }

    public void setTotal_points_price(String total_points_price) {
        this.total_points_price = total_points_price;
    }

    public int getTake_type() {
        return take_type;
    }

    public void setTake_type(int take_type) {
        this.take_type = take_type;
    }

    public int getFromProductCart() {
        return fromProductCart;
    }

    public void setFromProductCart(int fromProductCart) {
        this.fromProductCart = fromProductCart;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Parcelable {
        /**
         * id : 3
         * name : 大白菜
         * price : 2.00
         * promotion_price : 1.80
         * points_price : 0.10
         * image : http://192.168.1.48/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg
         * number : 1
         */

        private int id;
        private String name;
        private String price;
        private String promotion_price;
        private String points_price;
        private String image;
        private int number;
        private long product_more_unit_id;
        private String unit_name;

        public long getProduct_more_unit_id() {
            return product_more_unit_id;
        }

        public void setProduct_more_unit_id(long product_more_unit_id) {
            this.product_more_unit_id = product_more_unit_id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getPoints_price() {
            return points_price;
        }

        public void setPoints_price(String points_price) {
            this.points_price = points_price;
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

        public ProductsBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.price);
            dest.writeString(this.promotion_price);
            dest.writeString(this.points_price);
            dest.writeString(this.image);
            dest.writeInt(this.number);
            dest.writeLong(this.product_more_unit_id);
            dest.writeString(this.unit_name);
        }

        protected ProductsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.price = in.readString();
            this.promotion_price = in.readString();
            this.points_price = in.readString();
            this.image = in.readString();
            this.number = in.readInt();
            this.product_more_unit_id = in.readLong();
            this.unit_name = in.readString();
        }

        public static final Creator<ProductsBean> CREATOR = new Creator<ProductsBean>() {
            @Override
            public ProductsBean createFromParcel(Parcel source) {
                return new ProductsBean(source);
            }

            @Override
            public ProductsBean[] newArray(int size) {
                return new ProductsBean[size];
            }
        };
    }

    public ShopOrderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeString(this.total_price);
        dest.writeString(this.freight);
        dest.writeString(this.freight_tips);
        dest.writeString(this.total_points_price);
        dest.writeInt(this.take_type);
        dest.writeInt(this.fromProductCart);
        dest.writeString(this.order_id);
        dest.writeTypedList(this.products);
        dest.writeString(this.message);
    }

    protected ShopOrderBean(Parcel in) {
        this.status = in.readByte() != 0;
        this.total_price = in.readString();
        this.freight = in.readString();
        this.freight_tips = in.readString();
        this.total_points_price = in.readString();
        this.take_type = in.readInt();
        this.fromProductCart = in.readInt();
        this.order_id = in.readString();
        this.products = in.createTypedArrayList(ProductsBean.CREATOR);
        this.message = in.readString();
    }

    public static final Creator<ShopOrderBean> CREATOR = new Creator<ShopOrderBean>() {
        @Override
        public ShopOrderBean createFromParcel(Parcel source) {
            return new ShopOrderBean(source);
        }

        @Override
        public ShopOrderBean[] newArray(int size) {
            return new ShopOrderBean[size];
        }
    };
}
