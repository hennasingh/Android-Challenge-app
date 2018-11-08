package com.artist.web.bookstore.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artist.web.bookstore.BookViewModelFactory;
import com.artist.web.bookstore.R;
import com.artist.web.bookstore.ResultDisplay;
import com.artist.web.bookstore.database.FavBooks;
import com.artist.web.bookstore.favorite.FavoriteViewModel;
import com.artist.web.bookstore.helpers.Utils;
import com.artist.web.bookstore.model.BookDetail;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_BOOK ="book_parcel_arg";

    Unbinder mUnbinder;

    @BindView(R.id.book_title)
    TextView mBookTitle;

    @BindView(R.id.book_author_value)
    TextView mBookAuthor;

    @BindView(R.id.book_price_value)
    TextView mBookPrice;

    @BindView(R.id.book_isbn_value)
    TextView mBookISBN;

    @BindString(R.string.book_price)
    String mBookCost;

    @BindView(R.id.book_description)
    TextView mBookDescription;

    @BindView(R.id.book_image)
    ImageView mBookImage;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.share_icon)
    ImageButton mShareIcon;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.content_layout)
    LinearLayout mContentLayout;

    @BindView(R.id.loading)
    ProgressBar mProgressBar;

    @BindView(R.id.error_message)
    TextView mErrorMsg;

    BookDetailViewModel mBookDetailViewModel;
    FavoriteViewModel mFavoriteViewModel;

    int mBookId;
    BookDetail mBookDetail;

    private static final int FAV_TAG=0;
    private static final int FAV_NOT_TAG =1;
    private static final String TAG = BookFragment.class.getSimpleName();

    public static BookFragment newInstance(int bookId){
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK,bookId);

        BookFragment bookFragment = new BookFragment();
        bookFragment.setArguments(args);
        return bookFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookViewModelFactory bookViewModelFactory = Utils.provideBookViewModelFactory();
        mBookDetailViewModel = ViewModelProviders.of(this,bookViewModelFactory).get(BookDetailViewModel.class);

        //getting FavViewModel
        BookViewModelFactory favModelFactory = Utils.provideFavBookModelFactory(getActivity());
        mFavoriteViewModel = ViewModelProviders.of(this,favModelFactory).get(FavoriteViewModel.class);

         mBookId = getArguments().getInt(ARG_BOOK);
         Log.d(TAG, "Getting single Book Details "+mBookId);
        mBookDetailViewModel.fetchSingleBookDetail(mBookId);

      }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book,container,false);
        mUnbinder = ButterKnife.bind(this,view);

        setupUI();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onNavigateUp();
            }
        });

        setFavButton();
        mFab.setOnClickListener(this);
        mShareIcon.setOnClickListener(this);
        return view;
    }


    private void setFavButton() {
        mFavoriteViewModel.checkIfFav(mBookId).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    mFab.setTag(FAV_TAG);
                    mFab.setImageResource(R.drawable.ic_favorite);
                }else{
                    mFab.setTag(FAV_NOT_TAG);
                    mFab.setImageResource(R.drawable.ic_not_favorite);
                }
            }
        });
    }

    private void setupUI() {

        mBookDetailViewModel.getSingleBookDetails().observe(this, new Observer<ResultDisplay<BookDetail>>() {
            @Override
            public void onChanged(@Nullable ResultDisplay<BookDetail> bookDetailResultDisplay) {

                switch(bookDetailResultDisplay.state){
                    case ResultDisplay.STATE_SUCCESS:
                        mProgressBar.setVisibility(View.GONE);
                        mErrorMsg.setVisibility(View.GONE);
                        mContentLayout.setVisibility(View.VISIBLE);
                        mFab.setVisibility(View.VISIBLE);
                        mBookDetail = bookDetailResultDisplay.data;
                        Log.d(TAG, "Getting Book results "+ mBookDetail.toString());

                        if(mBookDetail.getAuthor()==null){
                            mBookAuthor.setText(R.string.no_author);
                        }else {
                            mBookAuthor.setText(mBookDetail.getAuthor());
                        }

                        if(TextUtils.isEmpty(mBookDetail.getTitle())){
                            mBookTitle.setText(R.string.no_title);
                        }else {
                            mBookTitle.setText(mBookDetail.getTitle());
                        }

                        if(mBookDetail.getISBN()==null){
                            mBookISBN.setText(R.string.no_isbn);
                        }else {
                            mBookISBN.setText(String.valueOf(mBookDetail.getISBN()));
                        }

                        if(mBookDetail.getDescription()==null){
                            mBookDescription.setText(R.string.description_unavailable);
                        }else{
                            mBookDescription.setText(mBookDetail.getDescription());
                        }
                        if(mBookDetail.getPrice()==null){

                            mBookPrice.setText(R.string.price_unavailable);
                        }else{
                            String price = String.format(mBookCost,mBookDetail.getPrice(),mBookDetail.getCurrencyCode());
                            mBookPrice.setText(price);
                        }
                        int imgId;
                        switch(mBookDetail.getId()){

                            case 100:
                                imgId = R.drawable.mike_riley;
                                break;
                            case 200:
                                imgId = R.drawable.andrew_david;
                                break;
                            case 300:
                                imgId = R.drawable.sean_morris;
                                break;
                            case 400:
                                imgId = R.drawable.kevin_mitnick;
                                break;
                            case 500:
                                imgId = R.drawable.charles_ash;
                                break;
                            case 600:
                                imgId = R.drawable.donn_felker;
                                break;
                            default:
                                imgId =R.drawable.image_not_found;
                        }
                        mBookImage.setImageResource(imgId);

                       break;
                    case ResultDisplay.STATE_LOADING:
                        mContentLayout.setVisibility(View.GONE);
                        mErrorMsg.setVisibility(View.GONE);
                        mFab.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);

                        break;
                    case ResultDisplay.STATE_ERROR:
                        mContentLayout.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        mFab.setVisibility(View.GONE);
                        mErrorMsg.setVisibility(View.VISIBLE);
                        mErrorMsg.setText(bookDetailResultDisplay.errorMessage);
                        break;
                        default:
                            mContentLayout.setVisibility(View.GONE);
                            mErrorMsg.setVisibility(View.GONE);
                            mProgressBar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.share_icon:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,mBookDetail.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT,mBookPrice.getText());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,R.string.fav_books);
                shareIntent = Intent.createChooser(shareIntent, getString(R.string.share_details_title));
                startActivity(shareIntent);

                break;
            case R.id.fab:
                int tag = (int) view.getTag();
                if(tag==FAV_TAG){
                    mFavoriteViewModel.deleteById(mBookId);
                    mFab.setTag(FAV_NOT_TAG);
                    mFab.setImageResource(R.drawable.ic_not_favorite);
                    Snackbar.make(coordinatorLayout, R.string.book_removed, Snackbar.LENGTH_LONG).show();
                }else{
                   insertFavBook();
                    mFab.setTag(FAV_TAG);
                    mFab.setImageResource(R.drawable.ic_favorite);
                    Snackbar.make(coordinatorLayout, R.string.book_added, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void insertFavBook() {
        FavBooks favBook = new FavBooks(mBookId, mBookDetail.getTitle(),mBookDetail.getISBN(),
                mBookDetail.getPrice(),mBookDetail.getCurrencyCode(),mBookDetail.getAuthor());
        mFavoriteViewModel.insertBook(favBook);
    }
}
