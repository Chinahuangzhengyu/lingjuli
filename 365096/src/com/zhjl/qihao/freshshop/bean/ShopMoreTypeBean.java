package com.zhjl.qihao.freshshop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ShopMoreTypeBean implements Parcelable {

    /**
     * id : 7
     * parent_id : 6
     * name : 蔬果类
     * children : [{"id":0,"parent_id":7,"name":"全部","children":[]},{"id":9,"parent_id":7,"name":"蔬菜","children":[]},{"id":20,"parent_id":7,"name":"水果","children":[]}]
     */

    private int id;
    private int parent_id;
    private String name;
    private ArrayList<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean implements Parcelable {
        /**
         * id : 0
         * parent_id : 7
         * name : 全部
         * children : []
         */

        private int id;
        private int parent_id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.parent_id);
            dest.writeString(this.name);
        }

        public ChildrenBean() {
        }

        protected ChildrenBean(Parcel in) {
            this.id = in.readInt();
            this.parent_id = in.readInt();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<ChildrenBean> CREATOR = new Parcelable.Creator<ChildrenBean>() {
            @Override
            public ChildrenBean createFromParcel(Parcel source) {
                return new ChildrenBean(source);
            }

            @Override
            public ChildrenBean[] newArray(int size) {
                return new ChildrenBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parent_id);
        dest.writeString(this.name);
        dest.writeTypedList(this.children);
    }

    public ShopMoreTypeBean() {
    }

    protected ShopMoreTypeBean(Parcel in) {
        this.id = in.readInt();
        this.parent_id = in.readInt();
        this.name = in.readString();
        this.children = in.createTypedArrayList(ChildrenBean.CREATOR);
    }

    public static final Parcelable.Creator<ShopMoreTypeBean> CREATOR = new Parcelable.Creator<ShopMoreTypeBean>() {
        @Override
        public ShopMoreTypeBean createFromParcel(Parcel source) {
            return new ShopMoreTypeBean(source);
        }

        @Override
        public ShopMoreTypeBean[] newArray(int size) {
            return new ShopMoreTypeBean[size];
        }
    };
}
