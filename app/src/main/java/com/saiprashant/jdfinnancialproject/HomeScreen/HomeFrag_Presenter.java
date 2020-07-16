package com.saiprashant.jdfinnancialproject.HomeScreen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.saiprashant.jdfinnancialproject.APIHit.HitAPI;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.R;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONObject;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.insertApi;

public class HomeFrag_Presenter {
    HomeFrag_Interface homeFrag_interface;
    MainActivity mainActivity;
    Context context;


    public HomeFrag_Presenter(HomeFrag_Interface homeFrag_interface, MainActivity mainActivity) {
        this.homeFrag_interface = homeFrag_interface;
        this.mainActivity = mainActivity;
        this.context =  mainActivity;
    }

    public void saveData(String investment_name,int recurring_flag,String purchase_date,String num_of_unit,String purchase_price) {
        JSONObject object = new JSONObject();
        try {
            object.put("investment_name", investment_name);
            object.put("recurring_flag", recurring_flag);
            object.put("purchase_date", purchase_date);
            object.put("num_of_unit", num_of_unit);
            object.put("purchase_price",purchase_price);
            HitAPI hitAPI = new HitAPI(context, saveHandler, object, insertApi);
            if (Utility.isNetworkConnected(context)) {
                hitAPI.execute();
            } else {
                Utility.ShowToast(context, mainActivity.getString(R.string.internet_warring));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler saveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int code = bundle.getInt(Utility.code);
            switch (code) {
                case Utility.success:
                    try {
                        JSONObject object = new JSONObject(bundle.getString(Utility.res));
                        boolean response_status = false, response_validate = false;
                        String response_message = "";
                        if (object.has(Utility.response_status_s))
                            response_status = object.getBoolean(Utility.response_status_s);
                        if (object.has(Utility.response_validate_s))
                            response_validate = object.getBoolean(Utility.response_validate_s);
                        if (object.has(Utility.response_message))
                            response_message = object.getString(Utility.response_message);
                        if (response_status && response_validate) {
                            Utility.printMessage(" Api reponse- : " + response_message);
                        homeFrag_interface.successResponse(response_message);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Utility.exception:
                    Utility.printMessage(bundle.getString(Utility.res));
                    break;
                case Utility.decodeerror:
                    Utility.printMessage(bundle.getString(Utility.res));
                    break;
                case Utility.noresponsefromserver:
                    Utility.printMessage(bundle.getString(Utility.res));
                    break;
            }

        }
    };


}
