package com.saiprashant.jdfinnancialproject.UpdateData;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.saiprashant.jdfinnancialproject.APIHit.HitAPI;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONObject;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.REPLACE;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.list_screen;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.updateApi;

public class Update_Presenter {

    MainActivity mainActivity;
    Update_Interface update_interface;
    Context context;

    public Update_Presenter(MainActivity mainActivity, Update_Interface update_interface) {
        this.mainActivity = mainActivity;
        this.update_interface = update_interface;
        this.context = mainActivity;
    }

    public void UpdateData(int id, String investment_name, int recurring_flag, String date_of_purchase,
                            String num_of_unit, String purchase_price) {
        JSONObject object = new JSONObject();
        try {

            object.put("id", id);
            object.put("investment_name", investment_name);
            object.put("recurring_flag", recurring_flag);
            object.put("purchase_date", date_of_purchase);
            object.put("num_of_unit", num_of_unit);
            object.put("purchase_price", purchase_price);
            HitAPI hitAPI = new HitAPI(context, updateHandler, object, updateApi);
            if (Utility.isNetworkConnected(context)) {
                hitAPI.execute();
            } else {
                Utility.ShowToast(context, "kindly Check you internet connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int code = bundle.getInt(Utility.code);
            String message;
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
                         update_interface.successResponse(response_message);
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
