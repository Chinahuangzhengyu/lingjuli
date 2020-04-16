package com.zhjl.qihao.abrefactor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 邻里分类
 */
public class NearByTypeBean {


    /**
     * code : 200
     * data : [{"id":"14236573564627946fe3bbaf4088a9fa","labelName":"随手美拍"},{"id":"142365738535242ef6d9af5a4e28948c","labelName":"生活杂谈"},{"id":"14236573933914ed497113a54a3b8601","labelName":"兴趣爱好"},{"id":"15011332730031c45d2da38746e78218","labelName":"二手闲置"},{"id":"150113327300b328ed806fbe4096acc4","labelName":"心灵鸡汤"},{"id":"150113327300baec56ef218b4babaaa0","labelName":"互利互助"},{"id":"150123148717137221cb421a461891e6","labelName":"物业投诉"},{"id":"150123148717596e839d4af34a7eb756","labelName":"平台投诉"}]
     * message : 获取分类列表成功
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
         * id : 14236573564627946fe3bbaf4088a9fa
         * labelName : 随手美拍
         */

        private String id;
        private String labelName;
        private String icon;
        private String homeIcon;

        public String getHomeIcon() {
            return homeIcon;
        }

        public void setHomeIcon(String homeIcon) {
            this.homeIcon = homeIcon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.labelName);
            dest.writeString(this.icon);
            dest.writeString(this.homeIcon);
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.labelName = in.readString();
            this.icon = in.readString();
            this.homeIcon = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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
}
