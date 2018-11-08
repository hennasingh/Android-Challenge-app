package com.artist.web.bookstore.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artist.web.bookstore.BookViewModelFactory;
import com.artist.web.bookstore.R;
import com.artist.web.bookstore.database.FavBooks;
import com.artist.web.bookstore.helpers.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.text_empty_list)
    TextView mEmptyView;

    @BindView(R.id.progress_indicator)
    ProgressBar mLoading;

    FavoriteViewModel mFavoriteViewModel;
    FavoriteAdapter mFavoriteAdapter;

    List<FavBooks> mFavBooks;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing the factory
        BookViewModelFactory favModelFactory = Utils.provideFavBookModelFactory(getContext());

        //initializing the ViewModel
        mFavoriteViewModel = ViewModelProviders.of(this,favModelFactory).get(FavoriteViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        setUI();

        mFavoriteAdapter = new FavoriteAdapter();
        mRecyclerView.setAdapter(mFavoriteAdapter);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onNavigateUp();
                }
            });

        return view;
    }

    private void setUI() {
        mFavoriteViewModel.getFavBooks().observe(this, new Observer<PagedList<FavBooks>>() {
            @Override
            public void onChanged(@Nullable PagedList<FavBooks> favBooks) {
                if(favBooks!=null){
                    mFavBooks = favBooks;
                    mLoading.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    mFavoriteAdapter.submitList(favBooks);

                }else{
                    mEmptyView.setVisibility(View.VISIBLE);
                    mLoading.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
      }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            //Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                mFavoriteViewModel.deleteById(mFavBooks.get(position).getId());

                Snackbar.make(mCoordinatorLayout, R.string.book_removed, Snackbar.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }
}
