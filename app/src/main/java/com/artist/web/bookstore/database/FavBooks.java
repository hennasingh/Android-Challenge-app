package com.artist.web.bookstore.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "books")
public class FavBooks {

    @PrimaryKey
    @NonNull
    private int id;

    private String title;

    private String isbn;

    private Integer mPrice;

    @ColumnInfo(name="currency_code")
    private String mCurrencyCode;

     private String author;

    public FavBooks(@NonNull int id, String title, String isbn, Integer price,
                    String currencyCode, String author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        mPrice = price;
        mCurrencyCode = currencyCode;
        this.author = author;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
