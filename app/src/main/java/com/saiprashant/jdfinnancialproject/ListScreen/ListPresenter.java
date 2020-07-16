package com.saiprashant.jdfinnancialproject.ListScreen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.saiprashant.jdfinnancialproject.APIHit.HitAPI;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.Adapter.UserListAdapter;
import com.saiprashant.jdfinnancialproject.Model.UserDetails;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.getApi;

public class ListPresenter {
    ListInterface listInterface;
    MainActivity mainActivity;
    ArrayList<UserDetails> detailsList;
    Context context;
    UserDetails userDetails;

    public ListPresenter(com.saiprashant.jdfinnancialproject.ListScreen.ListInterface listInterface, MainActivity mainActivity) {
        this.listInterface = listInterface;
        this.mainActivity = mainActivity;
        this.context = mainActivity;
    }

    public void getData() {
        try {
            HitAPI hitAPI = new HitAPI(context, getHandler, null, getApi);
            if (Utility.isNetworkConnected(context)) {
                hitAPI.execute();
            } else {
                Utility.ShowToast(context, "kindly Check you internet connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler getHandler = new Handler() {
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
                        if (object.has(Utility.response_data))
                            response_message = object.getString(Utility.response_data);
                        if (response_status && response_validate) {
                            Utility.printMessage(" Api reponse- : " + response_message);
                            addData(response_message);
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


    private void addData(String response_msg) {
        try {
            JSONArray jsonArray = new JSONArray(response_msg);
            detailsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userDetails = new UserDetails(jsonObject);
                detailsList.add(userDetails);
            }
            listInterface.getList(detailsList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
