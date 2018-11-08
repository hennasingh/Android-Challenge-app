package com.artist.web.bookstore.booklist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artist.web.bookstore.BookViewModelFactory;
import com.artist.web.bookstore.R;
import com.artist.web.bookstore.ResultDisplay;
import com.artist.web.bookstore.espresso.SimpleIdlingResource;
import com.artist.web.bookstore.favorite.FavoriteActivity;
import com.artist.web.bookstore.model.BookList;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.artist.web.bookstore.helpers.Utils.DATA_FETCHING_INTERVAL;
import static com.artist.web.bookstore.helpers.Utils.provideBookViewModelFactory;

public class BookListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

   @BindView(R.id.rv_books)
    RecyclerView mRecyclerView;

   @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

   @BindView(R.id.toolbar)
   Toolbar mToolbar;

   @BindView(R.id.text_empty_list)
    TextView mEmptyView;

   @BindView(R.id.progress_indicator)
    ProgressBar mLoadingBar;

    BookAdapter mBookAdapter;
    BookListViewModel mBookViewModel;
    private Unbinder mUnbinder;
    private onBookSelectCallback mBookSelectCallback;
    private long mLastFetchedDataTimeStamp;

    @BindString(R.string.error_message)
    String errorMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing the factory
        BookViewModelFactory bookListModelFactory = provideBookViewModelFactory();

        //initializing the ViewModel
        mBookViewModel = ViewModelProviders.of(this,bookListModelFactory).get(BookListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_list,container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mRefreshLayout.setOnRefreshListener(this);

        SimpleIdlingResource.setIdleState(false);

        setToolbar();
        return view;
    }

    @Override
    public void onRefresh() {
        if(mLastFetchedDataTimeStamp < DATA_FETCHING_INTERVAL){
            mRefreshLayout.setRefreshing(false);
        }
        mBookViewModel.queryBookApi();
    }
    private void setToolbar() {
        mToolbar.inflateMenu(R.menu.extra_options);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.favorites){
                    startActivity(FavoriteActivity.newIntent(getContext()));
                }
                return true;
            }
        });
    }

    /**
     * for hosting activities
     */
    public interface onBookSelectCallback{
        void onBookSelected(int bookId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mBookSelectCallback = (onBookSelectCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.implement_interface_alert));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBookSelectCallback = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBookViewModel.getBookListData().observe(this, new Observer<ResultDisplay<List<BookList>>>() {
            @Override
            public void onChanged(@Nullable ResultDisplay<List<BookList>> listResultDisplay) {
                switch(listResultDisplay.state){
                    case ResultDisplay.STATE_SUCCESS:
                        loadSuccessful(true);
                        mLoadingBar.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        if(mBookAdapter==null) {
                            mBookAdapter = new BookAdapter(listResultDisplay.data,mBookSelectCallback);
                            mRecyclerView.setAdapter(mBookAdapter);
                        }else{
                            mBookAdapter.setBooks(listResultDisplay.data);
                            mBookAdapter.notifyDataSetChanged();
                        }
                        mLastFetchedDataTimeStamp=System.currentTimeMillis();
                        mRefreshLayout.setRefreshing(false);
                        break;
                    case ResultDisplay.STATE_ERROR:
                        loadSuccessful(false);
                        mLoadingBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                        String error = String.format(errorMessage,listResultDisplay.errorMessage);
                        mEmptyView.setText(error);

                        break;
                    case ResultDisplay.STATE_LOADING:
                        loadSuccessful(false);
                        mLoadingBar.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        break;
                }

            }
        });
    }

    private void loadSuccessful(boolean isSuccessful) {

        SimpleIdlingResource.setIdleState(isSuccessful);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
    }
