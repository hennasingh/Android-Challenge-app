package com.artist.web.bookstore.booklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.bookstore.R;
import com.artist.web.bookstore.model.BookList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<BookList> mBookList;
    private BookListFragment.onBookSelectCallback mSelectCallback;

    public BookAdapter(List<BookList> bookList, BookListFragment.onBookSelectCallback callback){
        mBookList = bookList;
        mSelectCallback = callback;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_book_item,
                viewGroup,false);
        ButterKnife.bind(this,view);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        BookList book = mBookList.get(position);
        bookViewHolder.bindTo(book);

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public void setBooks(List<BookList> data) {
        mBookList = data;
    }

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         @BindView(R.id.book_title)
         TextView mBookTitle;

         @BindView(R.id.book_author)
         TextView mBookAuthor;

         @BindView(R.id.book_image)
         ImageView mBookIcon;

         BookList mBook;


         BookViewHolder(@NonNull View itemView) {
             super(itemView);
             ButterKnife.bind(this, itemView);
             itemView.setOnClickListener(this);
         }

         void bindTo(BookList book) {
             mBook = book;
             if(!TextUtils.isEmpty(book.getTitle())) {
                 mBookTitle.setText(book.getTitle());
             }else{
                 mBookTitle.setText(R.string.no_title);
             }
             if(!TextUtils.isEmpty(book.getAuthor())) {
                 mBookAuthor.setText(book.getAuthor());
             }else{
                 mBookAuthor.setText(R.string.no_author);
             }

             int imgId;

             switch (book.getId()) {
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
             mBookIcon.setImageResource(imgId);
         }

        @Override
        public void onClick(View view) {

             mSelectCallback.onBookSelected(mBook.getId());
        }
    }
}
