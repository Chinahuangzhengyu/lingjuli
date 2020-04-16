package com.zhjl.qihao.nearbyinteraction.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FileUploadBean {

    /**
     * code : 200
     * data : {"accessKeyId":"LTAI4FhvTNq7uApvB6mu5sVM","signature":"Dafsjm2q6q3UUwupt2aYWi8DfKk=","host":"https://7hao.oss-cn-beijing.aliyuncs.com","callback":"eyJjYWxsYmFja0JvZHlUeXBlIjoiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwiY2FsbGJhY2tVcmwiOiJodHRwczovL3RwLnFpaGFvbGluZ2p1bGkuY29tL2FwaS9vc3MvY2FsbGJhY2siLCJjYWxsYmFja0JvZHkiOiJidWNrZXQ9JHtidWNrZXR9JmZpbGVuYW1lPSR7b2JqZWN0fSZldGFnPSR7ZXRhZ30mc2l6ZT0ke3NpemV9Jm1pbWVUeXBlPSR7bWltZVR5cGV9JmltYWdlSW5mby5oZWlnaHQ9JHtpbWFnZUluZm8uaGVpZ2h0fSZpbWFnZUluZm8ud2lkdGg9JHtpbWFnZUluZm8ud2lkdGh9JmltYWdlSW5mby5mb3JtYXQ9JHtpbWFnZUluZm8uZm9ybWF0fSJ9","dir":"psms/20200218","policy":"eyJleHBpcmF0aW9uIjoiMjAyMC0wMi0xOFQwNzowNjoxNS44MThaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsNTI0MjgwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCJwc21zLzIwMjAwMjE4Il1dfQ=="}
     * message : 处理成功
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
         * accessKeyId : LTAI4FhvTNq7uApvB6mu5sVM
         * signature : Dafsjm2q6q3UUwupt2aYWi8DfKk=
         * host : https://7hao.oss-cn-beijing.aliyuncs.com
         * callback : eyJjYWxsYmFja0JvZHlUeXBlIjoiYXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVkIiwiY2FsbGJhY2tVcmwiOiJodHRwczovL3RwLnFpaGFvbGluZ2p1bGkuY29tL2FwaS9vc3MvY2FsbGJhY2siLCJjYWxsYmFja0JvZHkiOiJidWNrZXQ9JHtidWNrZXR9JmZpbGVuYW1lPSR7b2JqZWN0fSZldGFnPSR7ZXRhZ30mc2l6ZT0ke3NpemV9Jm1pbWVUeXBlPSR7bWltZVR5cGV9JmltYWdlSW5mby5oZWlnaHQ9JHtpbWFnZUluZm8uaGVpZ2h0fSZpbWFnZUluZm8ud2lkdGg9JHtpbWFnZUluZm8ud2lkdGh9JmltYWdlSW5mby5mb3JtYXQ9JHtpbWFnZUluZm8uZm9ybWF0fSJ9
         * dir : psms/20200218
         * policy : eyJleHBpcmF0aW9uIjoiMjAyMC0wMi0xOFQwNzowNjoxNS44MThaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsNTI0MjgwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCJwc21zLzIwMjAwMjE4Il1dfQ==
         */

        private String accessKeyId;
        private String signature;
        private String host;
        private String callback;
        private String dir;
        private String policy;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getCallback() {
            return callback;
        }

        public void setCallback(String callback) {
            this.callback = callback;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.accessKeyId);
            dest.writeString(this.signature);
            dest.writeString(this.host);
            dest.writeString(this.callback);
            dest.writeString(this.dir);
            dest.writeString(this.policy);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.accessKeyId = in.readString();
            this.signature = in.readString();
            this.host = in.readString();
            this.callback = in.readString();
            this.dir = in.readString();
            this.policy = in.readString();
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
}
