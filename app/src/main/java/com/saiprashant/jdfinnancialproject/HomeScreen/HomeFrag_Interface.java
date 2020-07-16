package com.saiprashant.jdfinnancialproject.HomeScreen;

import android.view.View;

public interface HomeFrag_Interface {
    void onBindView(View view);
    void openDatePicker();
    boolean validation();
    void successResponse(String response_msg);
}
