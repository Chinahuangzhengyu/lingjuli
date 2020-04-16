package com.zhjl.qihao.abmine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/13.
 */

public class Book implements Parcelable {

    private int book_num;
    private String book_name;

    public Book(int book_num, String book_name) {
        this.book_num = book_num;
        this.book_name = book_name;
    }

    public int getBook_num() {
        return book_num;
    }

    public void setBook_num(int book_num) {
        this.book_num = book_num;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    private Book(Parcel in) {
        book_num=in.readInt();
        book_name=in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(book_num);
        dest.writeString(book_name);
    }
}
