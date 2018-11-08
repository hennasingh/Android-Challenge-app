package com.artist.web.bookstore.network;

import com.artist.web.bookstore.helpers.Utils;
import com.artist.web.bookstore.model.BookDetail;
import com.artist.web.bookstore.model.BookList;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static BooksApi sBooksApi;
    private static ApiManager sApiManager;

    private ApiManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(interceptor).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sBooksApi = retrofit.create(BooksApi.class);

    }

    public static ApiManager getInstance(){
        if(sApiManager==null){
            sApiManager = new ApiManager();
        }
        return sApiManager;
    }

    public void getBookList(Callback<List<BookList>> callback){
        Call<List<BookList>> booksResult = sBooksApi.getBookApiResult();
        booksResult.enqueue(callback);
    }

    public void getSingleBook(int id, Callback<BookDetail> callback){
        Call<BookDetail> bookDetailCall = sBooksApi.getSingleBookDetail(id);
        bookDetailCall.enqueue(callback);

    }
}
