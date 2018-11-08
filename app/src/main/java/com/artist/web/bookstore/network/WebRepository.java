package com.artist.web.bookstore.network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.artist.web.bookstore.LaunchApplication;
import com.artist.web.bookstore.ResultDisplay;
import com.artist.web.bookstore.model.BookDetail;
import com.artist.web.bookstore.model.BookList;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebRepository {

    private static final String TAG = WebRepository.class.getSimpleName();
    private static volatile WebRepository sWebRepository;

    private MutableLiveData<ResultDisplay<List<BookList>>> mBookListObservable = new MutableLiveData<>();
    private MutableLiveData<List<BookList>> mBookListData = new MutableLiveData<>();
    private MutableLiveData<ResultDisplay<BookDetail>> mSingleBookLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMsg = new MutableLiveData<>();


    private WebRepository(){

    }
    public static WebRepository getInstance(){
        if(sWebRepository==null){
            sWebRepository = new WebRepository();
        }
        return sWebRepository;
    }

    public void getSingleBookDetails(int id){
        Log.d(TAG, "calling SingleBookDetails from repo");
        mSingleBookLiveData.setValue(ResultDisplay.loading((BookDetail)null));

        LaunchApplication.sApiManager.getSingleBook(id, new Callback<BookDetail>() {
            @Override
            public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {

                if(response.isSuccessful()){

                    mSingleBookLiveData.setValue(ResultDisplay.success(response.body()));
                                       ;
                }else{
                    String error = "Results not found- ";
                    error = error.concat(response.message());

                    mErrorMsg.setValue(error);
                    mSingleBookLiveData.setValue(ResultDisplay.error(error,response.body()));
                }
            }

            @Override
            public void onFailure(Call<BookDetail> call, Throwable t) {

                String error = "The Http call failed with error -";
                error=error.concat(t.getMessage());
                Log.d(TAG, "Calling failing"+ error);

                mErrorMsg.setValue(error);
            }
        });

    }

    public void getBookListResult(){
        mBookListObservable.setValue(ResultDisplay.loading(Collections.<BookList>emptyList()));

        LaunchApplication.sApiManager.getBookList(new Callback<List<BookList>>() {
            @Override
            public void onResponse(Call<List<BookList>> call, Response<List<BookList>> response) {

                if(response.isSuccessful()){

                    if(response.body()!=null){
                    mBookListObservable.setValue(ResultDisplay.success(response.body()));
                    mBookListData.setValue(response.body());
                    }else{
                        String error = "Results not found "+response.message();
                        //error = error.concat(response.message());

                        mBookListObservable.setValue(ResultDisplay.error(error, Collections.<BookList>emptyList()));
                    }
                }else{
                    String error = "There is error retrieving results - "+response.message();
                    //error = error.concat(response.message());

                    mBookListObservable.setValue(ResultDisplay.error(error, Collections.<BookList>emptyList()));
                }

            }

            @Override
            public void onFailure(Call<List<BookList>> call, Throwable t) {
                String error = "The Http call failed with error -"+t;
                //error=error.concat(t.getLocalizedMessage());

                mBookListObservable.setValue(ResultDisplay.error(error,Collections.<BookList>emptyList()));

            }
        });
    }

    public MutableLiveData<ResultDisplay<List<BookList>>> getBookListObservable() {
        return mBookListObservable;
    }

    public MutableLiveData<List<BookList>> getBookListData(){
        return mBookListData;
    }

    public MutableLiveData<ResultDisplay<BookDetail>> getSingleBookDetail(){
          return mSingleBookLiveData;
    }

    public MutableLiveData<String> getErrorMsg(){
        return mErrorMsg;
    }

}
