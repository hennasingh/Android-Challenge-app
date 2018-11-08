package com.artist.web.bookstore.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.artist.web.bookstore.BookViewModelFactory;
import com.artist.web.bookstore.R;
import com.artist.web.bookstore.booklist.BookListViewModel;
import com.artist.web.bookstore.model.BookList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.artist.web.bookstore.helpers.Utils.VISIBLE_FRAGMENT;
import static com.artist.web.bookstore.helpers.Utils.provideBookViewModelFactory;

public class BookPagerActivity extends AppCompatActivity {

    private static final String TAG = BookPagerActivity.class.getSimpleName();

    private static final String EXTRA_BOOK_ID ="book_id";

    @BindView(R.id.book_view_pager)
    ViewPager mViewPager;

    BookListViewModel mBookListViewModel;
    int bookId;

    public static Intent newIntent(Context context, int bookId){
        Intent intent = new Intent(context,BookPagerActivity.class);
        intent.putExtra(EXTRA_BOOK_ID,bookId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_pager);
        ButterKnife.bind(this);

        //iniatize factory
        BookViewModelFactory bookFactory = provideBookViewModelFactory();
        mBookListViewModel = ViewModelProviders.of(this,bookFactory).get(BookListViewModel.class);

        bookId = getIntent().getIntExtra(EXTRA_BOOK_ID,100);
        observeAndSendList();
    }

    private void observeAndSendList() {
        mBookListViewModel.getBookList().observe(this, new Observer<List<BookList>>() {
            @Override
            public void onChanged(@Nullable List<BookList> bookLists) {

                Log.d(TAG, "Sending BookList to PagerAdapter "+bookLists.toString());
                mViewPager.setAdapter(new BookDetailPagerAdapter(getSupportFragmentManager(),bookLists));
                mViewPager.setOffscreenPageLimit(VISIBLE_FRAGMENT);

                for(int i=0; i<bookLists.size();i++){
                    if(bookLists.get(i).getId().equals(bookId)){
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
                mBookListViewModel.getBookList().removeObserver(this);
            }
        });
    }
}
