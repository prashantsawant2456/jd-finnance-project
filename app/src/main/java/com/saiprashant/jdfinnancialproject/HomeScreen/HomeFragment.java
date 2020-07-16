package com.saiprashant.jdfinnancialproject.HomeScreen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.R;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import java.util.Calendar;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.REPLACE;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.list_screen;

public class HomeFragment extends Fragment implements HomeFrag_Interface {

    TextInputLayout ti_investment_name, ti_date_of_purchase, ti_num_of_unit, ti_purchase_price;
    EditText et_investment_name, et_date_of_purchase, et_num_of_unit, et_purchase_price;
    Button btn_savedata, btn_go_to_list;
    Switch switch_btn;
    MainActivity mainActivity;
    Context context;
    int recuring_flag = 0;
    Calendar date_of_purchase;
    HomeFrag_Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        context = getContext();
        presenter = new HomeFrag_Presenter(this, mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindView(view);
    }


    @Override
    public void onBindView(View view) {
        ti_investment_name = view.findViewById(R.id.ti_investment_name);
        ti_date_of_purchase = view.findViewById(R.id.ti_date_of_purchase);
        ti_num_of_unit = view.findViewById(R.id.ti_num_of_unit);
        ti_purchase_price = view.findViewById(R.id.ti_purchase_price);

        et_investment_name = view.findViewById(R.id.et_investment_name);
        et_date_of_purchase = view.findViewById(R.id.et_date_of_purchase);
        et_num_of_unit = view.findViewById(R.id.et_num_of_unit);
        et_purchase_price = view.findViewById(R.id.et_purchase_price);

        btn_savedata = view.findViewById(R.id.btn_savedata);
        btn_go_to_list = view.findViewById(R.id.btn_go_to_list);
        switch_btn = view.findViewById(R.id.switch_btn);
        setClickAction();
    }

    @Override
    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (date_of_purchase != null) {
            calendar.set(Calendar.YEAR, date_of_purchase.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, date_of_purchase.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, date_of_purchase.get(Calendar.DAY_OF_MONTH));
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date_of_purchase = Calendar.getInstance();
                date_of_purchase.set(i, i1, i2);
                DateFormat dateFormat = new DateFormat();
                et_date_of_purchase.setText(dateFormat.format("yyyy-MM-dd", date_of_purchase));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }


    private void setClickAction() {
        switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_btn.isChecked()) {
                    recuring_flag = 1;
                } else {
                    recuring_flag = 0;
                }

            }
        });
        et_date_of_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        btn_savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    presenter.saveData(et_investment_name.getText().toString().trim(), recuring_flag, et_date_of_purchase.getText().toString().trim(), et_num_of_unit.getText().toString().trim(), et_purchase_price.getText().toString().trim());

                }
            }
        });
        btn_go_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.FragmentManagement(list_screen, REPLACE, true, null);
            }
        });
    }


    public boolean validation() {
        boolean validate = true;

        if (et_investment_name.getText().length() == 0) {
            et_investment_name.setError(getString(R.string.error_investment_name));
            validate = false;
        } else if (et_date_of_purchase.getText().toString().length() == 0) {
            et_date_of_purchase.setError(getString(R.string.error_purchase_date));
            validate = false;
        } else if (et_num_of_unit.getText().toString().length() == 0) {
            et_num_of_unit.setError(getString(R.string.error_number_unit));
            validate = false;
        } else if (et_purchase_price.getText().toString().length() == 0) {
            et_purchase_price.setError(getString(R.string.error_purchase_price));
            validate = false;
        }
        return validate;
    }

    @Override
    public void successResponse(String response_msg) {
        Utility.ShowToast(context, response_msg);
        et_investment_name.setText("");
        et_date_of_purchase.setText("");
        et_purchase_price.setText("");
        et_num_of_unit.setText("");
        switch_btn.setChecked(false);
    }

}
