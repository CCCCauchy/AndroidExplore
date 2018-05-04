package com.cauchy.androidexplore.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author cauchy
 * @date 2018/5/4 17:19
 * @since 5.1.0
 */
public class Book implements Parcelable {
    private String name;
    private String category;

    public Book(String name, String category) {
        this.name = name;
        this.category = category;
    }

    protected Book(Parcel in) {
        name = in.readString();
        category = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
    }

    public void readFromParcel(Parcel in){
        name = in.readString();
        category = in.readString();
    }

}
