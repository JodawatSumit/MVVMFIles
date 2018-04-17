package com.mvvm.demo;

import com.support.base.CoreApp;

public class App extends CoreApp {

    public static final String TAG = App.class.getSimpleName();
    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}