package com.artist.web.bookstore.helpers;

import android.content.Context;

import com.artist.web.bookstore.BookViewModelFactory;
import com.artist.web.bookstore.database.BookDatabase;
import com.artist.web.bookstore.favorite.FavRepository;
import com.artist.web.bookstore.network.WebRepository;

public class Utils {

    public static final String BASE_URL ="http://tpbookserver.herokuapp.com/";
    public static final int PAGE_SIZE = 10;
    public static final int PREFETCH_NUMBER = 10;
    public final static int DATA_FETCHING_INTERVAL=10*1000; //10 seconds
    public final static int VISIBLE_FRAGMENT =1;
    public final static int FIRST_BOOK =100;

     public static BookViewModelFactory provideBookViewModelFactory(){

        return new BookViewModelFactory(WebRepository.getInstance());
    }

    public static BookViewModelFactory provideFavBookModelFactory(Context context){
        BookDatabase bookDatabase = BookDatabase.getInstance(context);
        return new BookViewModelFactory(FavRepository.getInstance(bookDatabase.getBookDao()));
    }

}
