package com.artist.web.bookstore.favorite;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bookstore.R;
import com.artist.web.bookstore.database.FavBooks;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends PagedListAdapter<FavBooks, FavoriteAdapter.FavViewHolder> {

    private static DiffUtil.ItemCallback<FavBooks> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavBooks>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavBooks oldItem, @NonNull FavBooks newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavBooks oldItem, @NonNull FavBooks newItem) {
            return oldItem.equals(newItem);
        }
    };

    public FavoriteAdapter(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.favorite_book_item,viewGroup,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder favViewHolder, int position) {
        FavBooks book = getItem(position);
        if(book!=null){
            favViewHolder.bindTo(book);
        }

    }

     class FavViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_title)
        TextView mTitle;

        @BindView(R.id.book_author)
        TextView mAuthor;

        @BindView(R.id.book_price)
        TextView mPrice;

        @BindString(R.string.book_price)
        String mBookCost;

         FavViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

         void bindTo(FavBooks book){
             mTitle.setText(book.getTitle());
             mAuthor.setText(book.getAuthor());
             String price = String.format(mBookCost,book.getPrice(),book.getCurrencyCode());
             mPrice.setText(price);

        }
    }

}
