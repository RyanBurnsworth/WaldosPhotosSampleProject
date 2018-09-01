package com.ryanburnsworth.waldosphotossample.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionUtil {
    private Context context;

    public NetworkConnectionUtil(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
