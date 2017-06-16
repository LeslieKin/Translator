package com.cn.kin.translator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kin on 17.6.9.
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){

        if (context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null){
                return true;
            }
        }
        return false;
    }

}
