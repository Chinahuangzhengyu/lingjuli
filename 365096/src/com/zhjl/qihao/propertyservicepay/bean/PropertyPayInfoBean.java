package com.zhjl.qihao.propertyservicepay.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PropertyPayInfoBean implements Parcelable {

    /**
     * code : 200
     * data : {"costItems":[{"chargeItemState":null,"costMoney":20,"itemDescr":"平方米","itemId":"1571817827662aa67c3f7bc54fa8bbc0","itemName":"物业管理费用"}],"standards":[{"givePoint":0,"id":1,"name":"一年","proportion":0.4,"type":0},{"givePoint":0,"id":2,"name":"半年","proportion":0.2,"type":0},{"givePoint":0,"id":3,"name":"季度","proportion":0.1,"type":0},{"givePoint":0,"id":4,"name":"月","proportion":0,"type":0}],"address":"北京百花KASO创意公园H31单元2602","lastPaymentTime":"","residentId":"152205700521e5be13c152fd41d281b7"}
     * message : ok
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Parcelable {
        /**
         * costItems : [{"chargeItemState":null,"costMoney":20,"itemDescr":"平方米","itemId":"1571817827662aa67c3f7bc54fa8bbc0","itemName":"物业管理费用"}]
         * standards : [{"givePoint":0,"id":1,"name":"一年","proportion":0.4,"type":0},{"givePoint":0,"id":2,"name":"半年","proportion":0.2,"type":0},{"givePoint":0,"id":3,"name":"季度","proportion":0.1,"type":0},{"givePoint":0,"id":4,"name":"月","proportion":0,"type":0}]
         * address : 北京百花KASO创意公园H31单元2602
         * lastPaymentTime :
         * residentId : 152205700521e5be13c152fd41d281b7
         */

        private String address;
        private String lastPaymentTime;
        private String residentId;
        private List<CostItemsBean> costItems;
        private List<StandardsBean> standards;
        private List<RoomsBean> rooms;

        public List<RoomsBean> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomsBean> rooms) {
            this.rooms = rooms;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLastPaymentTime() {
            return lastPaymentTime;
        }

        public void setLastPaymentTime(String lastPaymentTime) {
            this.lastPaymentTime = lastPaymentTime;
        }

        public String getResidentId() {
            return residentId;
        }

        public void setResidentId(String residentId) {
            this.residentId = residentId;
        }

        public List<CostItemsBean> getCostItems() {
            return costItems;
        }

        public void setCostItems(List<CostItemsBean> costItems) {
            this.costItems = costItems;
        }

        public List<StandardsBean> getStandards() {
            return standards;
        }

        public void setStandards(List<StandardsBean> standards) {
            this.standards = standards;
        }

        public static class CostItemsBean implements Parcelable {
            /**
             * chargeItemState : null
             * costMoney : 20
             * itemDescr : 平方米
             * itemId : 1571817827662aa67c3f7bc54fa8bbc0
             * itemName : 物业管理费用
             */

            private String costMoney;
            private String itemDescr;
            private String itemId;
            private String itemName;


            public String getCostMoney() {
                return costMoney;
            }

            public void setCostMoney(String costMoney) {
                this.costMoney = costMoney;
            }

            public String getItemDescr() {
                return itemDescr;
            }

            public void setItemDescr(String itemDescr) {
                this.itemDescr = itemDescr;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.costMoney);
                dest.writeString(this.itemDescr);
                dest.writeString(this.itemId);
                dest.writeString(this.itemName);
            }

            public CostItemsBean() {
            }

            protected CostItemsBean(Parcel in) {
                this.costMoney = in.readString();
                this.itemDescr = in.readString();
                this.itemId = in.readString();
                this.itemName = in.readString();
            }

            public static final Creator<CostItemsBean> CREATOR = new Creator<CostItemsBean>() {
                @Override
                public CostItemsBean createFromParcel(Parcel source) {
                    return new CostItemsBean(source);
                }

                @Override
                public CostItemsBean[] newArray(int size) {
                    return new CostItemsBean[size];
                }
            };
        }
        public static class RoomsBean{
            private String residentId;
            private String roomName;
            private String smallCommunityCode;
            private String smallCommunityName;

            public String getResidentId() {
                return residentId;
            }

            public void setResidentId(String residentId) {
                this.residentId = residentId;
            }

            public String getRoomName() {
                return roomName;
            }

            public void setRoomName(String roomName) {
                this.roomName = roomName;
            }

            public String getSmallCommunityCode() {
                return smallCommunityCode;
            }

            public void setSmallCommunityCode(String smallCommunityCode) {
                this.smallCommunityCode = smallCommunityCode;
            }

            public String getSmallCommunityName() {
                return smallCommunityName;
            }

            public void setSmallCommunityName(String smallCommunityName) {
                this.smallCommunityName = smallCommunityName;
            }
        }
        public static class StandardsBean implements Parcelable {
            /**
             * givePoint : 0
             * id : 1
             * name : 一年
             * proportion : 0.4
             * type : 0
             */

            private int givePoint;
            private int id;
            private String name;
            private double proportion;
            private int type;
            private long giveNBItNumber;

            public long getGiveNBItNumber() {
                return giveNBItNumber;
            }

            public void setGiveNBItNumber(long giveNBItNumber) {
                this.giveNBItNumber = giveNBItNumber;
            }

            public int getGivePoint() {
                return givePoint;
            }

            public void setGivePoint(int givePoint) {
                this.givePoint = givePoint;
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

            public double getProportion() {
                return proportion;
            }

            public void setProportion(double proportion) {
                this.proportion = proportion;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.givePoint);
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeDouble(this.proportion);
                dest.writeInt(this.type);
                dest.writeLong(this.giveNBItNumber);
            }

            public StandardsBean() {
            }

            protected StandardsBean(Parcel in) {
                this.givePoint = in.readInt();
                this.id = in.readInt();
                this.name = in.readString();
                this.proportion = in.readDouble();
                this.type = in.readInt();
                this.giveNBItNumber = in.readLong();
            }

            public static final Creator<StandardsBean> CREATOR = new Creator<StandardsBean>() {
                @Override
                public StandardsBean createFromParcel(Parcel source) {
                    return new StandardsBean(source);
                }

                @Override
                public StandardsBean[] newArray(int size) {
                    return new StandardsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeString(this.lastPaymentTime);
            dest.writeString(this.residentId);
            dest.writeList(this.costItems);
            dest.writeList(this.standards);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.address = in.readString();
            this.lastPaymentTime = in.readString();
            this.residentId = in.readString();
            this.costItems = new ArrayList<CostItemsBean>();
            in.readList(this.costItems, CostItemsBean.class.getClassLoader());
            this.standards = new ArrayList<StandardsBean>();
            in.readList(this.standards, StandardsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public PropertyPayInfoBean() {
    }

    protected PropertyPayInfoBean(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<PropertyPayInfoBean> CREATOR = new Parcelable.Creator<PropertyPayInfoBean>() {
        @Override
        public PropertyPayInfoBean createFromParcel(Parcel source) {
            return new PropertyPayInfoBean(source);
        }

        @Override
        public PropertyPayInfoBean[] newArray(int size) {
            return new PropertyPayInfoBean[size];
        }
    };
}
