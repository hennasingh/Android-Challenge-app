package com.artist.web.bookstore.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.artist.web.bookstore.ResultDisplay;
import com.artist.web.bookstore.model.BookDetail;
import com.artist.web.bookstore.network.WebRepository;

public class BookDetailViewModel extends ViewModel {

    private WebRepository mWebRepository;

    public BookDetailViewModel(WebRepository webRepository){
        mWebRepository = webRepository;
    }


    public LiveData<ResultDisplay<BookDetail>> getSingleBookDetails(){
        return mWebRepository.getSingleBookDetail();
    }

    public void fetchSingleBookDetail(int id){
        mWebRepository.getSingleBookDetails(id);
    }
}
