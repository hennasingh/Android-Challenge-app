package com.artist.web.bookstore.favorite;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.artist.web.bookstore.SingleFragmentActivity;

public class FavoriteActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new FavoriteFragment();
    }

    public static Intent newIntent(Context context){
        return new Intent(context,FavoriteActivity.class);

    }

}
