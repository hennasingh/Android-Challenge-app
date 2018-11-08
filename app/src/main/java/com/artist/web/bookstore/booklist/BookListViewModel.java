package com.artist.web.bookstore.booklist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.artist.web.bookstore.ResultDisplay;
import com.artist.web.bookstore.model.BookList;
import com.artist.web.bookstore.network.WebRepository;

import java.util.List;

public class BookListViewModel extends ViewModel {

    private WebRepository mWebRepository;
    private MutableLiveData<List<BookList>> mListMutableLiveData = new MutableLiveData<>();

    public BookListViewModel(WebRepository webRepository){
        mWebRepository = webRepository;
        queryBookApi();
    }

    public LiveData<ResultDisplay<List<BookList>>> getBookListData(){

        return mWebRepository.getBookListObservable();
    }

    public void queryBookApi(){
        mWebRepository.getBookListResult();
        mListMutableLiveData = mWebRepository.getBookListData();

    }

    public LiveData<List<BookList>> getBookList(){
        return mListMutableLiveData;
    }

}
