package com.zhjl.qihao.freshshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddressRoomBean implements Parcelable {

    /**
     * default : {"area":"贵州省凯里市","room_id":"15012319586070b12ee7bf774221a997","name":"北京百花KASO创意公园","room_name":"","code":"0002001"}
     * rooms : [{"area":"贵州省凯里市","room_id":"15012319586070b12ee7bf774221a997","name":"北京百花KASO创意公园","rome_name":"H31单元2602","code":"0002001","selected":1},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0},{"area":"贵州省凯里市","room_id":"150518819022d21e158f3ca8468fa2cb","name":"北京百花KASO创意公园","rome_name":"H61单元0102","code":"0002001","selected":0}]
     */

    @SerializedName("default")
    private DefaultBean defaultX;
    private ArrayList<RoomsBean> rooms;

    public DefaultBean getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(DefaultBean defaultX) {
        this.defaultX = defaultX;
    }

    public ArrayList<RoomsBean> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomsBean> rooms) {
        this.rooms = rooms;
    }

    public static class DefaultBean implements Parcelable {
        /**
         * area : 贵州省凯里市
         * room_id : 15012319586070b12ee7bf774221a997
         * name : 北京百花KASO创意公园
         * room_name :
         * code : 0002001
         */

        private String area;
        private String room_id;
        private String name;
        private String room_name;
        private String code;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.area);
            dest.writeString(this.room_id);
            dest.writeString(this.name);
            dest.writeString(this.room_name);
            dest.writeString(this.code);
        }

        public DefaultBean() {
        }

        protected DefaultBean(Parcel in) {
            this.area = in.readString();
            this.room_id = in.readString();
            this.name = in.readString();
            this.room_name = in.readString();
            this.code = in.readString();
        }

        public static final Creator<DefaultBean> CREATOR = new Creator<DefaultBean>() {
            @Override
            public DefaultBean createFromParcel(Parcel source) {
                return new DefaultBean(source);
            }

            @Override
            public DefaultBean[] newArray(int size) {
                return new DefaultBean[size];
            }
        };
    }

    public static class RoomsBean implements Parcelable {
        /**
         * area : 贵州省凯里市
         * room_id : 15012319586070b12ee7bf774221a997
         * name : 北京百花KASO创意公园
         * rome_name : H31单元2602
         * code : 0002001
         * selected : 1
         */

        private String area;
        private String room_id;
        private String name;
        private String rome_name;
        private String code;
        private int selected;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRome_name() {
            return rome_name;
        }

        public void setRome_name(String rome_name) {
            this.rome_name = rome_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.area);
            dest.writeString(this.room_id);
            dest.writeString(this.name);
            dest.writeString(this.rome_name);
            dest.writeString(this.code);
            dest.writeInt(this.selected);
        }

        public RoomsBean() {
        }

        protected RoomsBean(Parcel in) {
            this.area = in.readString();
            this.room_id = in.readString();
            this.name = in.readString();
            this.rome_name = in.readString();
            this.code = in.readString();
            this.selected = in.readInt();
        }

        public static final Creator<RoomsBean> CREATOR = new Creator<RoomsBean>() {
            @Override
            public RoomsBean createFromParcel(Parcel source) {
                return new RoomsBean(source);
            }

            @Override
            public RoomsBean[] newArray(int size) {
                return new RoomsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.defaultX, flags);
        dest.writeList(this.rooms);
    }

    public AddressRoomBean() {
    }

    protected AddressRoomBean(Parcel in) {
        this.defaultX = in.readParcelable(DefaultBean.class.getClassLoader());
        this.rooms = new ArrayList<RoomsBean>();
        in.readList(this.rooms, RoomsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddressRoomBean> CREATOR = new Parcelable.Creator<AddressRoomBean>() {
        @Override
        public AddressRoomBean createFromParcel(Parcel source) {
            return new AddressRoomBean(source);
        }

        @Override
        public AddressRoomBean[] newArray(int size) {
            return new AddressRoomBean[size];
        }
    };
}
