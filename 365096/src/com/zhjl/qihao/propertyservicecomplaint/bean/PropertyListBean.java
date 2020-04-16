package com.zhjl.qihao.propertyservicecomplaint.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PropertyListBean implements Parcelable {


    /**
     * code : 200
     * data : [{"createTime":"2020-03-17 17:33","description":"嘿嘿","id":89,"picture":{"fileSize":51927,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_15844376079821.jpg","height":"1920.0","width":"1920.0"},"status":0,"title":"投诉"},{"createTime":"2020-03-17 16:01","description":"测试","id":88,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"},{"createTime":"2019-12-25 02:56","description":"哈啊哈哈啊哈哈哈","id":75,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"},{"createTime":"2019-12-25 02:44","description":"哈哈哈","id":74,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"},{"createTime":"2019-12-19 01:51","description":"绿化有问题","id":71,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"投诉"},{"createTime":"2019-12-19 01:51","description":"花园绿化一直不好用","id":70,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"投诉"},{"createTime":"2019-12-03 10:32","description":"投诉","id":40,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":1,"title":"建议"},{"createTime":"2019-12-03 10:32","description":"嘿嘿","id":39,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"},{"createTime":"2019-12-03 10:24","description":"哈哈","id":38,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"},{"createTime":"2019-12-03 10:04","description":"不行","id":37,"picture":{"fileSize":0,"filename":"","height":"","width":""},"status":0,"title":"建议"}]
     * message : 获取数据列表成功
     * total : 0
     * totalPage : 4
     */

    private int code;
    private String message;
    private int total;
    private int totalPage;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 2020-03-17 17:33
         * description : 嘿嘿
         * id : 89
         * picture : {"fileSize":51927,"filename":"https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_15844376079821.jpg","height":"1920.0","width":"1920.0"}
         * status : 0
         * title : 投诉
         */

        private String createTime;
        private String description;
        private long id;
        private PictureBean picture;
        private int status;
        private String title;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public PictureBean getPicture() {
            return picture;
        }

        public void setPicture(PictureBean picture) {
            this.picture = picture;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class PictureBean {
            /**
             * fileSize : 51927
             * filename : https://7hao.oss-cn-beijing.aliyuncs.com/psms/20200317/picture_15844376079821.jpg
             * height : 1920.0
             * width : 1920.0
             */

            private int fileSize;
            private String filename;
            private String height;
            private String width;

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeInt(this.total);
        dest.writeInt(this.totalPage);
        dest.writeList(this.data);
    }

    public PropertyListBean() {
    }

    protected PropertyListBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.total = in.readInt();
        this.totalPage = in.readInt();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<PropertyListBean> CREATOR = new Creator<PropertyListBean>() {
        @Override
        public PropertyListBean createFromParcel(Parcel source) {
            return new PropertyListBean(source);
        }

        @Override
        public PropertyListBean[] newArray(int size) {
            return new PropertyListBean[size];
        }
    };
}
