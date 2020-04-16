package com.zhjl.qihao.ablogin.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class LoginBean implements Parcelable {


    /**
     * code : 200
     * data : {"userInfo":{"avatar":{"pictureId":"156170324850c2e3eea3aa704209b425","picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","timestamp":1561732048000},"deviceId":"FErBh3BpGh4Sq2Euye0P2ZvdTbQomckZ","id":"15220328700869de92519083445f881d","loginType":1,"mobileNumber":"18201137023","multiRoom":false,"nBitNumber":0,"nickname":"你好","residentId":"152205700521e5be13c152fd41d281b7","roomCode":"","roomId":"","roomName":"H31单元2602","selectModel":true,"sign":"我只在乎十年后的自己，怎么看现在的我！","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","userRooms":[{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55}],"userType":1},"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiLkvaDlpb0iLCJsb2dpblR5cGUiOjEsIm1vYmlsZSI6IjE4MjAxMTM3MDIzIiwiaXNzIjoicWloYW9samwuY29tIiwidGltZXN0YW1wIjoxNTc0MzI0MDU3Nzk2fQ.LFHFbwlBnvFErBh3BpGh4Sq2Euye0P2ZvdTbQomckZw"}
     * message : 用户登录成功
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
         * userInfo : {"avatar":{"pictureId":"156170324850c2e3eea3aa704209b425","picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","timestamp":1561732048000},"deviceId":"FErBh3BpGh4Sq2Euye0P2ZvdTbQomckZ","id":"15220328700869de92519083445f881d","loginType":1,"mobileNumber":"18201137023","multiRoom":false,"nBitNumber":0,"nickname":"你好","residentId":"152205700521e5be13c152fd41d281b7","roomCode":"","roomId":"","roomName":"H31单元2602","selectModel":true,"sign":"我只在乎十年后的自己，怎么看现在的我！","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","userRooms":[{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55}],"userType":1}
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiLkvaDlpb0iLCJsb2dpblR5cGUiOjEsIm1vYmlsZSI6IjE4MjAxMTM3MDIzIiwiaXNzIjoicWloYW9samwuY29tIiwidGltZXN0YW1wIjoxNTc0MzI0MDU3Nzk2fQ.LFHFbwlBnvFErBh3BpGh4Sq2Euye0P2ZvdTbQomckZw
         */

        private UserInfoBean userInfo;
        private String token;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserInfoBean implements Parcelable {
            /**
             * avatar : {"pictureId":"156170324850c2e3eea3aa704209b425","picturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","smallPicturePath":"http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg","timestamp":1561732048000}
             * deviceId : FErBh3BpGh4Sq2Euye0P2ZvdTbQomckZ
             * id : 15220328700869de92519083445f881d
             * loginType : 1
             * mobileNumber : 18201137023
             * multiRoom : false
             * nBitNumber : 0
             * nickname : 你好
             * residentId : 152205700521e5be13c152fd41d281b7
             * roomCode :
             * roomId :
             * roomName : H31单元2602
             * selectModel : true
             * sign : 我只在乎十年后的自己，怎么看现在的我！
             * smallCommunityCode : 0002001
             * smallCommunityName : 北京百花KASO创意公园
             * userRooms : [{"residentId":"152205700521e5be13c152fd41d281b7","residentType":"1","roomCode":"00020010010126002","roomId":"15012319586070b12ee7bf774221a997","roomName":"H31单元2602","smallCommunityCode":"0002001","smallCommunityName":"北京百花KASO创意公园","usableArea":55}]
             * userType : 1
             */

            private AvatarBean avatar;
            private String deviceId;
            private String id;
            private int loginType;
            private String mobileNumber;
            private boolean multiRoom;
            private int nBitNumber;
            private String nickname;
            private String residentId;
            private String roomCode;
            private String roomId;
            private String roomName;
            private boolean selectModel;
            private String sign;
            private String smallCommunityCode;
            private String smallCommunityName;
            private int userType;
            private ArrayList<UserRoomsBean> userRooms;

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getLoginType() {
                return loginType;
            }

            public void setLoginType(int loginType) {
                this.loginType = loginType;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public boolean isMultiRoom() {
                return multiRoom;
            }

            public void setMultiRoom(boolean multiRoom) {
                this.multiRoom = multiRoom;
            }

            public int getNBitNumber() {
                return nBitNumber;
            }

            public void setNBitNumber(int nBitNumber) {
                this.nBitNumber = nBitNumber;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getResidentId() {
                return residentId;
            }

            public void setResidentId(String residentId) {
                this.residentId = residentId;
            }

            public String getRoomCode() {
                return roomCode;
            }

            public void setRoomCode(String roomCode) {
                this.roomCode = roomCode;
            }

            public String getRoomId() {
                return roomId;
            }

            public void setRoomId(String roomId) {
                this.roomId = roomId;
            }

            public String getRoomName() {
                return roomName;
            }

            public void setRoomName(String roomName) {
                this.roomName = roomName;
            }

            public boolean isSelectModel() {
                return selectModel;
            }

            public void setSelectModel(boolean selectModel) {
                this.selectModel = selectModel;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
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

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public ArrayList<UserRoomsBean> getUserRooms() {
                return userRooms;
            }

            public void setUserRooms(ArrayList<UserRoomsBean> userRooms) {
                this.userRooms = userRooms;
            }

            public static class AvatarBean implements Parcelable {
                /**
                 * pictureId : 156170324850c2e3eea3aa704209b425
                 * picturePath : http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg
                 * smallPicturePath : http://192.168.1.50:8080/psms/upload/2019-6-28/945b6dcb1e6a4028912bf64219d79528.jpg
                 * timestamp : 1561732048000
                 */

                private String pictureId;
                private String picturePath;
                private String smallPicturePath;
                private long timestamp;

                public String getPictureId() {
                    return pictureId;
                }

                public void setPictureId(String pictureId) {
                    this.pictureId = pictureId;
                }

                public String getPicturePath() {
                    return picturePath;
                }

                public void setPicturePath(String picturePath) {
                    this.picturePath = picturePath;
                }

                public String getSmallPicturePath() {
                    return smallPicturePath;
                }

                public void setSmallPicturePath(String smallPicturePath) {
                    this.smallPicturePath = smallPicturePath;
                }

                public long getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(long timestamp) {
                    this.timestamp = timestamp;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.pictureId);
                    dest.writeString(this.picturePath);
                    dest.writeString(this.smallPicturePath);
                    dest.writeLong(this.timestamp);
                }

                public AvatarBean() {
                }

                protected AvatarBean(Parcel in) {
                    this.pictureId = in.readString();
                    this.picturePath = in.readString();
                    this.smallPicturePath = in.readString();
                    this.timestamp = in.readLong();
                }

                public static final Creator<AvatarBean> CREATOR = new Creator<AvatarBean>() {
                    @Override
                    public AvatarBean createFromParcel(Parcel source) {
                        return new AvatarBean(source);
                    }

                    @Override
                    public AvatarBean[] newArray(int size) {
                        return new AvatarBean[size];
                    }
                };
            }

            public static class UserRoomsBean implements Parcelable {
                /**
                 * residentId : 152205700521e5be13c152fd41d281b7
                 * residentType : 1
                 * roomCode : 00020010010126002
                 * roomId : 15012319586070b12ee7bf774221a997
                 * roomName : H31单元2602
                 * smallCommunityCode : 0002001
                 * smallCommunityName : 北京百花KASO创意公园
                 * usableArea : 55
                 */

                private String residentId;
                private String residentType;
                private String roomCode;
                private String roomId;
                private String roomName;
                private String smallCommunityCode;
                private String smallCommunityName;
                private int usableArea;

                public String getResidentId() {
                    return residentId;
                }

                public void setResidentId(String residentId) {
                    this.residentId = residentId;
                }

                public String getResidentType() {
                    return residentType;
                }

                public void setResidentType(String residentType) {
                    this.residentType = residentType;
                }

                public String getRoomCode() {
                    return roomCode;
                }

                public void setRoomCode(String roomCode) {
                    this.roomCode = roomCode;
                }

                public String getRoomId() {
                    return roomId;
                }

                public void setRoomId(String roomId) {
                    this.roomId = roomId;
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

                public int getUsableArea() {
                    return usableArea;
                }

                public void setUsableArea(int usableArea) {
                    this.usableArea = usableArea;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.residentId);
                    dest.writeString(this.residentType);
                    dest.writeString(this.roomCode);
                    dest.writeString(this.roomId);
                    dest.writeString(this.roomName);
                    dest.writeString(this.smallCommunityCode);
                    dest.writeString(this.smallCommunityName);
                    dest.writeInt(this.usableArea);
                }

                public UserRoomsBean() {
                }

                protected UserRoomsBean(Parcel in) {
                    this.residentId = in.readString();
                    this.residentType = in.readString();
                    this.roomCode = in.readString();
                    this.roomId = in.readString();
                    this.roomName = in.readString();
                    this.smallCommunityCode = in.readString();
                    this.smallCommunityName = in.readString();
                    this.usableArea = in.readInt();
                }

                public static final Creator<UserRoomsBean> CREATOR = new Creator<UserRoomsBean>() {
                    @Override
                    public UserRoomsBean createFromParcel(Parcel source) {
                        return new UserRoomsBean(source);
                    }

                    @Override
                    public UserRoomsBean[] newArray(int size) {
                        return new UserRoomsBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.avatar, flags);
                dest.writeString(this.deviceId);
                dest.writeString(this.id);
                dest.writeInt(this.loginType);
                dest.writeString(this.mobileNumber);
                dest.writeByte(this.multiRoom ? (byte) 1 : (byte) 0);
                dest.writeInt(this.nBitNumber);
                dest.writeString(this.nickname);
                dest.writeString(this.residentId);
                dest.writeString(this.roomCode);
                dest.writeString(this.roomId);
                dest.writeString(this.roomName);
                dest.writeByte(this.selectModel ? (byte) 1 : (byte) 0);
                dest.writeString(this.sign);
                dest.writeString(this.smallCommunityCode);
                dest.writeString(this.smallCommunityName);
                dest.writeInt(this.userType);
                dest.writeList(this.userRooms);
            }

            public UserInfoBean() {
            }

            protected UserInfoBean(Parcel in) {
                this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
                this.deviceId = in.readString();
                this.id = in.readString();
                this.loginType = in.readInt();
                this.mobileNumber = in.readString();
                this.multiRoom = in.readByte() != 0;
                this.nBitNumber = in.readInt();
                this.nickname = in.readString();
                this.residentId = in.readString();
                this.roomCode = in.readString();
                this.roomId = in.readString();
                this.roomName = in.readString();
                this.selectModel = in.readByte() != 0;
                this.sign = in.readString();
                this.smallCommunityCode = in.readString();
                this.smallCommunityName = in.readString();
                this.userType = in.readInt();
                this.userRooms = new ArrayList<UserRoomsBean>();
                in.readList(this.userRooms, UserRoomsBean.class.getClassLoader());
            }

            public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
                @Override
                public UserInfoBean createFromParcel(Parcel source) {
                    return new UserInfoBean(source);
                }

                @Override
                public UserInfoBean[] newArray(int size) {
                    return new UserInfoBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.userInfo, flags);
            dest.writeString(this.token);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.userInfo = in.readParcelable(UserInfoBean.class.getClassLoader());
            this.token = in.readString();
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

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
