package com.saiprashant.jdfinnancialproject.UpdateData;

import android.view.View;

import com.saiprashant.jdfinnancialproject.Model.UserDetails;

public interface Update_Interface {
    void onBindView(View view);
    void setData(UserDetails userDetails);
    void openDatePicker();
    boolean validation();
    void successResponse(String response_msg);
}
