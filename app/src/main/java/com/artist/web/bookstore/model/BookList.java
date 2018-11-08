package com.artist.web.bookstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BookList implements Parcelable {


    @SerializedName("id")
    private Integer mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("isbn")
    private String mIsbn;

    @SerializedName("price")
    private Integer mPrice;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("author")
    private String mAuthor;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIsbn() {
        return mIsbn;
    }

    public void setIsbn(String isbn) {
        mIsbn = isbn;
    }

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer price) {
        mPrice = price;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mIsbn);
        dest.writeValue(this.mPrice);
        dest.writeString(this.mCurrencyCode);
        dest.writeString(this.mAuthor);
    }

    public BookList() {
    }

    protected BookList(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mIsbn = in.readString();
        this.mPrice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mCurrencyCode = in.readString();
        this.mAuthor = in.readString();
    }

    public static final Parcelable.Creator<BookList> CREATOR = new Parcelable.Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel source) {
            return new BookList(source);
        }

        @Override
        public BookList[] newArray(int size) {
            return new BookList[size];
        }
    };
}

