package com.zhjl.qihao.nearbyinteraction.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NearByItemBean implements Parcelable {

    /**
     * code : 200
     * data : [{"comment":"测试话题","id":1,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题"},{"comment":"测试话题02","id":2,"topicIcon":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png","topicName":"测试话题01"}]
     * message : 获取数据成功
     */

    private int code;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * comment : 测试话题
         * id : 1
         * topicIcon : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200214/d.png
         * topicName : 测试话题
         */

        private String comment;
        private int id;
        private String topicIcon;
        private String topicName;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopicIcon() {
            return topicIcon;
        }

        public void setTopicIcon(String topicIcon) {
            this.topicIcon = topicIcon;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.comment);
            dest.writeInt(this.id);
            dest.writeString(this.topicIcon);
            dest.writeString(this.topicName);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.comment = in.readString();
            this.id = in.readInt();
            this.topicIcon = in.readString();
            this.topicName = in.readString();
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
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    public NearByItemBean() {
    }

    protected NearByItemBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<NearByItemBean> CREATOR = new Parcelable.Creator<NearByItemBean>() {
        @Override
        public NearByItemBean createFromParcel(Parcel source) {
            return new NearByItemBean(source);
        }

        @Override
        public NearByItemBean[] newArray(int size) {
            return new NearByItemBean[size];
        }
    };
}
