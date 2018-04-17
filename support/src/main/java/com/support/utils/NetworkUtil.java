package com.support.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;


public class NetworkUtil {
    private final Activity activity;
    private final String TAG = NetworkUtil.class.getName();
    private InternetListener listener;

    private NetworkUtil(@NonNull Activity activity) {
        this.activity = activity;
    }

    public static NetworkUtil getInstance(@NonNull Activity activity) {

        NetworkUtil networkUtil = new NetworkUtil(activity);

        return networkUtil;
    }

    public boolean isConnectedToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnected();
    }

}
