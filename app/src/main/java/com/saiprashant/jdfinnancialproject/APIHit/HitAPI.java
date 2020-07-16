package com.saiprashant.jdfinnancialproject.APIHit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.saiprashant.jdfinnancialproject.Utility.Utility;
import org.json.JSONObject;

public class HitAPI extends AsyncTask<String, String, Message> {

    Context context;
    Handler callback;
    JSONObject parameter;
    String url;
    ProgressDialog progressDialog;
    boolean showDialog = true;
    String request_method = "POST";

    public HitAPI(Context context, Handler callback, JSONObject parameter, String url) {
        this.context = context;
        this.callback = callback;
        this.parameter = parameter;
        this.url = url;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            progressDialog.show();
        }

    }

    @Override
    protected void onPostExecute(Message s) {
        super.onPostExecute(s);
        if (showDialog) {
            progressDialog.dismiss();
        }
        callback.handleMessage(s);

    }

    @Override
    protected Message doInBackground(String... params) {
        try {
            String res;
            Utility.printMessage("URL : " + url);
            Utility.printMessage("PARAMETER : " + parameter);
            res = APIHit.HitAPI(url, parameter, request_method/*,from_html*/);
            Utility.printMessage("Response : " + res);
            if (res.contains("u836640827_prashant")) {
                return Parseresponce(res.replace("u836640827_prashant", "").trim());
            } else {
                return Parseresponce(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bundle dataBundle = new Bundle();
            dataBundle.putString(Utility.msg, "Could not decode data from server");
            dataBundle.putInt(Utility.code, Utility.decodeerror);
            dataBundle.putString(Utility.res, "");
            Message msg = Message.obtain();
            msg.setData(dataBundle);
            return msg;
        }
    }

    private Message Parseresponce(String res) {
        Message msg = Message.obtain();
        if (res != null) {
            try {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(Utility.code, Utility.success);
                dataBundle.putString(Utility.res, res);
                msg.setData(dataBundle);
            } catch (Exception e) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString(Utility.msg, e.getMessage());
                dataBundle.putInt(Utility.code, Utility.exception);
                dataBundle.putString(Utility.res, res);
                msg.setData(dataBundle);
            }
        } else {
            Bundle dataBundle = new Bundle();
            dataBundle
                    .putString(Utility.msg,
                            "Could not fetch data as the server has stopped responding.");

            dataBundle.putInt(Utility.code, Utility.noresponsefromserver);
            dataBundle.putString(Utility.res, res);

            msg.setData(dataBundle);

        }
        return msg;

    }
}