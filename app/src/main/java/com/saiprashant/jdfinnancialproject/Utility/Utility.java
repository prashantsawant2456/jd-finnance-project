package com.saiprashant.jdfinnancialproject.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public final static String code = "code", msg = "msg", res = "res", value = "", response_status_s = "Response-Status", response_validate_s = "Response-Validate",response_message = "Response-Message",response_data="Response-Data";
    public final static int success = 100, decodeerror = 101, exception = 102, noresponsefromserver = 500;

    public static void printMessage(String s) {
        Log.d("@@@Test @@@", s);
    }

    public static void ShowToast(Context context, String toast_msg) {
        Toast.makeText(context, toast_msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
