package com.artist.web.bookstore.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.artist.web.bookstore.model.BookList;

import java.util.List;

public class BookDetailPagerAdapter extends FragmentStatePagerAdapter {

    private List<BookList> mBookList;
    private static final String TAG = BookDetailPagerAdapter.class.getSimpleName();

    public BookDetailPagerAdapter(FragmentManager fm, List<BookList> bookList) {
        super(fm);
        mBookList = bookList;
    }

    @Override
    public Fragment getItem(int position) {
        BookList book = mBookList.get(position);
        Log.d(TAG,"Sending bookId from PagerAdapter to detail Fragment " + book.getId());
        return BookFragment.newInstance(book.getId());
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }
}
