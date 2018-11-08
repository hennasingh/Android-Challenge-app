package com.artist.web.bookstore.favorite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.artist.web.bookstore.database.AppExecutors;
import com.artist.web.bookstore.database.BookDao;
import com.artist.web.bookstore.database.FavBooks;
import com.artist.web.bookstore.model.BookDetail;

import java.util.List;

public class FavRepository {

    private static volatile FavRepository sFavRepository;

    private final BookDao mBookDao;

    private FavRepository(BookDao bookDao){
        mBookDao =bookDao;
    }

    private MutableLiveData<Boolean> isFav = new MutableLiveData<>();

    public static FavRepository getInstance(BookDao bookDao){

          if(sFavRepository==null){
                    sFavRepository = new FavRepository(bookDao);
          }

        return sFavRepository;
    }

    public DataSource.Factory<Integer, FavBooks> getAllFavs() {
        return mBookDao.getAllFavBooks();
    }

    public void insertBook(final FavBooks bookDetail){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mBookDao.insertBook(bookDetail);
            }
        });
    }

    public void deleteBooks(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mBookDao.deleteAll();
            }
        });
    }

    public void deleteBookWithId(final Integer bookId){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mBookDao.deleteBook(bookId);
            }
        });
    }

    public void checkFav(final Integer bookId){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                BookDetail book =mBookDao.getBook(bookId);
                if(book!=null){
                   isFav.postValue(true);

                }else{
                    isFav.postValue(false);
                }
            }
        });
    }

    public LiveData<Boolean> checkIfFav(Integer bookId){
        checkFav(bookId);
        return isFav;
    }

    public LiveData<List<FavBooks>> getBooks(){
       return mBookDao.getAllBooks();
    }


}
