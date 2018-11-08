package com.artist.web.bookstore.model;

import com.google.gson.annotations.SerializedName;

public class BookDetail {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("isbn")
    private String mISBN;

   @SerializedName("description")
    private String mDescription;

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

    public String getISBN() {
        return mISBN;
    }

    public void setISBN(String ISBN) {
        mISBN = ISBN;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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
}

