package com.zhjl.qihao.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceChooseBean implements Parcelable {


    /**
     * status : true
     * order_id : 126
     * order_status : 3
     * order_item_id : 121
     * name : 大白菜
     * price : 1.80
     * number : 1
     * image : http://tp.qihaolingjuli.com/attachment/image/product/20191125/135a66e535590514ed6315e8e7546e49_min.jpg
     * delivery_type_format : 门店自提
     * delivery_type_code : 1
     * delivery_address : 贵州栖好网络科技有限公司修改3
     * total_price : 1.80
     */

    private boolean status;
    private int order_id;
    private int order_status;
    private int order_item_id;
    private String name;
    private String price;
    private int number;
    private String image;
    private String delivery_type_format;
    private int delivery_type_code;
    private String delivery_address;
    private String total_price;
    private float points_deduct_price;
    private String unit_name;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public float getPoints_deduct_price() {
        return points_deduct_price;
    }

    public void setPoints_deduct_price(float points_deduct_price) {
        this.points_deduct_price = points_deduct_price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDelivery_type_format() {
        return delivery_type_format;
    }

    public void setDelivery_type_format(String delivery_type_format) {
        this.delivery_type_format = delivery_type_format;
    }

    public int getDelivery_type_code() {
        return delivery_type_code;
    }

    public void setDelivery_type_code(int delivery_type_code) {
        this.delivery_type_code = delivery_type_code;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public ServiceChooseBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeInt(this.order_id);
        dest.writeInt(this.order_status);
        dest.writeInt(this.order_item_id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeInt(this.number);
        dest.writeString(this.image);
        dest.writeString(this.delivery_type_format);
        dest.writeInt(this.delivery_type_code);
        dest.writeString(this.delivery_address);
        dest.writeString(this.total_price);
        dest.writeFloat(this.points_deduct_price);
        dest.writeString(this.unit_name);
    }

    protected ServiceChooseBean(Parcel in) {
        this.status = in.readByte() != 0;
        this.order_id = in.readInt();
        this.order_status = in.readInt();
        this.order_item_id = in.readInt();
        this.name = in.readString();
        this.price = in.readString();
        this.number = in.readInt();
        this.image = in.readString();
        this.delivery_type_format = in.readString();
        this.delivery_type_code = in.readInt();
        this.delivery_address = in.readString();
        this.total_price = in.readString();
        this.points_deduct_price = in.readFloat();
        this.unit_name = in.readString();
    }

    public static final Creator<ServiceChooseBean> CREATOR = new Creator<ServiceChooseBean>() {
        @Override
        public ServiceChooseBean createFromParcel(Parcel source) {
            return new ServiceChooseBean(source);
        }

        @Override
        public ServiceChooseBean[] newArray(int size) {
            return new ServiceChooseBean[size];
        }
    };
}
