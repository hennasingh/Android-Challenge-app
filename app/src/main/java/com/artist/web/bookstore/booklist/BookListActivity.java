package com.artist.web.bookstore.booklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.FrameLayout;

import com.artist.web.bookstore.R;
import com.artist.web.bookstore.SingleFragmentActivity;
import com.artist.web.bookstore.detail.BookFragment;
import com.artist.web.bookstore.detail.BookPagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.artist.web.bookstore.helpers.Utils.FIRST_BOOK;

public class BookListActivity extends SingleFragmentActivity implements BookListFragment.onBookSelectCallback {

    private static final String TAG = BookListActivity.class.getSimpleName();
    @Nullable
    @BindView(R.id.detail_fragment_container)
    FrameLayout mDetailLayout;

    Unbinder mUnbinder;

     Boolean isTwoPane;
    @Override
    protected Fragment createFragment() {
        return new BookListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnbinder=ButterKnife.bind(this);
        if(mDetailLayout==null){
            isTwoPane = false;
        }else{
            isTwoPane = true;
            Fragment newDetail = BookFragment.newInstance(FIRST_BOOK);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,newDetail)
                    .commit();
        }
    }

    @Override
    public void onBookSelected(int bookId) {
        if(!isTwoPane){
            Intent intent = BookPagerActivity.newIntent(this,bookId);
            Log.d(TAG, "BookPager Activity called with id "+bookId);
            startActivity(intent);
        }else{
            Fragment newDetail = BookFragment.newInstance(bookId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
