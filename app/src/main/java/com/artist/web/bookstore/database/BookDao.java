package com.artist.web.bookstore.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.artist.web.bookstore.model.BookDetail;

import java.util.List;
@Dao
public interface BookDao {

    @Insert
    void insertBook(FavBooks favBook);

    @Query("SELECT * FROM books")
    LiveData<List<FavBooks>> getAllBooks();

    @Query("SELECT * FROM books")
    DataSource.Factory<Integer,FavBooks> getAllFavBooks();

    @Query("DELETE FROM books WHERE id =:bookId")
    void deleteBook(Integer bookId);

    @Query("SELECT * FROM books WHERE id=:bookId")
    BookDetail getBook(Integer bookId);

    @Delete
    void deleteAll(FavBooks... bookDetails);

}
