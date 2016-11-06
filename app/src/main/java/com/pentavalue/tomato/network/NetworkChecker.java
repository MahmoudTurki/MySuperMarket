package com.pentavalue.tomato.network;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Check the availability of network ad if it connected or not.
 *
 * @param context The context of which the method is called in.
 * @author Mahmoud Turki
 */
public class NetworkChecker {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}