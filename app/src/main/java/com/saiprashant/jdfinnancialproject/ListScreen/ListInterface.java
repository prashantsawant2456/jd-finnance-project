package com.saiprashant.jdfinnancialproject.ListScreen;

import android.view.View;

import com.saiprashant.jdfinnancialproject.Model.UserDetails;

import java.util.ArrayList;

public interface ListInterface {
    void onBindView(View view);
    void getList(ArrayList<UserDetails> detailsList);

}
