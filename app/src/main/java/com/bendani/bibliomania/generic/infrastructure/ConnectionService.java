package com.bendani.bibliomania.generic.infrastructure;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionService {

    private Context context;

    public ConnectionService(Context context) {
        this.context = context;
    }

    public boolean isDeviceConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
