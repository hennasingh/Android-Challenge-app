package com.artist.web.bookstore.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities={FavBooks.class},version=1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase sBookDatabase;
    private static final Object LOCK = new Object();
    private static final String BOOK_DB = "books.db";

    public static BookDatabase getInstance(Context context){
        if(sBookDatabase==null){
            synchronized (LOCK) {
                sBookDatabase = create(context);
            }
        }
        return sBookDatabase;
    }

    private static BookDatabase create(Context context) {
        RoomDatabase.Builder<BookDatabase> databaseBuilder = Room.databaseBuilder(
                context.getApplicationContext(),BookDatabase.class,BOOK_DB);


        return databaseBuilder.fallbackToDestructiveMigration().build();
    }

    public abstract BookDao getBookDao();
}
