package com.artist.web.bookstore.network;

import com.artist.web.bookstore.model.BookDetail;
import com.artist.web.bookstore.model.BookList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BooksApi {

    @GET("books")
    Call<List<BookList>> getBookApiResult();

    @GET("book/{id}")
    Call<BookDetail> getSingleBookDetail(@Path("id") int id);

}
