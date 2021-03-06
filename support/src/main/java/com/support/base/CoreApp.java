package com.support.base;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.support.utils.AppForegroundChecker;

public class CoreApp extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static CoreApp mInstance;

    AppForegroundChecker.Listener listener = new AppForegroundChecker.Listener() {
        public void onBecameForeground() {
//            Toaster.shortToast("foreground");
        }

        public void onBecameBackground() {
//            Toaster.shortToast("background");
        }
    };

    public static synchronized CoreApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppForegroundChecker.get(mInstance).addListener(listener);
    }

    @Override
    public void onTerminate() {
        AppForegroundChecker.get(getInstance()).removeListener(listener);
        super.onTerminate();
    }
}
