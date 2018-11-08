package com.artist.web.bookstore.favorite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.artist.web.bookstore.database.FavBooks;

import java.util.List;

import static com.artist.web.bookstore.helpers.Utils.PAGE_SIZE;
import static com.artist.web.bookstore.helpers.Utils.PREFETCH_NUMBER;

public class FavoriteViewModel extends ViewModel {

    private FavRepository mFavRepository;
    private LiveData<PagedList<FavBooks>> mFavBookList;


    public FavoriteViewModel(FavRepository repository){
        mFavRepository = repository;
        init();
    }

    private void init() {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(PREFETCH_NUMBER)
                .setPageSize(PAGE_SIZE)
                .build();

        mFavBookList = new LivePagedListBuilder<>(mFavRepository.getAllFavs(),pagedListConfig)
                .build();
    }

    public LiveData<PagedList<FavBooks>> getFavBooks(){
        return mFavBookList;
    }

    public void deleteAllBooks(){
        mFavRepository.deleteBooks();
    }

    public void deleteById(Integer id){
        mFavRepository.deleteBookWithId(id);
    }

    public void insertBook(FavBooks bookDetail){
        mFavRepository.insertBook(bookDetail);
    }

    public LiveData<Boolean> checkIfFav(Integer id){
        return mFavRepository.checkIfFav(id);
    }


    public LiveData<List<FavBooks>> getWidgetBookList(){
        return mFavRepository.getBooks();
    }

}
