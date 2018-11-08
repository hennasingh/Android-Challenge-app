package com.artist.web.bookstore;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.artist.web.bookstore.booklist.BookListViewModel;
import com.artist.web.bookstore.detail.BookDetailViewModel;
import com.artist.web.bookstore.favorite.FavRepository;
import com.artist.web.bookstore.favorite.FavoriteViewModel;
import com.artist.web.bookstore.network.WebRepository;

public class BookViewModelFactory implements ViewModelProvider.Factory {

    private WebRepository mWebRepository;
    private FavRepository mFavRepository;

    public BookViewModelFactory(WebRepository webRepository){
        mWebRepository=webRepository;
    }

    public BookViewModelFactory(FavRepository favRepository){
        mFavRepository = favRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(BookListViewModel.class)) {
            return (T) new BookListViewModel(mWebRepository);
        }else if(modelClass.isAssignableFrom(BookDetailViewModel.class)){
            return (T) new BookDetailViewModel(mWebRepository);
        }else if(modelClass.isAssignableFrom(FavoriteViewModel.class)){
            return (T) new FavoriteViewModel(mFavRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
