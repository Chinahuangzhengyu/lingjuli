package com.zhjl.qihao.abmine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/13.
 */

public class UserMsg implements Parcelable {
    public int user_id;
    public String user_name;
    public boolean isMale;
    public Book book;

    public UserMsg(int user_id, String user_name, boolean isMale) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.isMale = isMale;
    }

    private UserMsg(Parcel in) {
        user_id=in.readInt();
        user_name=in.readString();
        isMale=in.readInt()==1;
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());

    }

    public static final Creator<UserMsg> CREATOR = new Creator<UserMsg>() {
        @Override
        public UserMsg createFromParcel(Parcel in) {
            return new UserMsg(in);
        }

        @Override
        public UserMsg[] newArray(int size) {
            return new UserMsg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeString(user_name);
        dest.writeInt(isMale?1:0);
        dest.writeParcelable(book,0);
    }
}
