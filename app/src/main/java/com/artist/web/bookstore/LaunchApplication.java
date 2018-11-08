package com.artist.web.bookstore;

import android.app.Application;

import com.artist.web.bookstore.network.ApiManager;

public class LaunchApplication extends Application {

    public static ApiManager sApiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sApiManager = ApiManager.getInstance();
    }
}
