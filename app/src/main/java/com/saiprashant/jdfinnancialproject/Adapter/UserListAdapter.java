package com.saiprashant.jdfinnancialproject.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saiprashant.jdfinnancialproject.APIHit.HitAPI;
import com.saiprashant.jdfinnancialproject.Activity.MainActivity;
import com.saiprashant.jdfinnancialproject.Model.UserDetails;
import com.saiprashant.jdfinnancialproject.R;
import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.REPLACE;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.Update_Screen;
import static com.saiprashant.jdfinnancialproject.Urls.Urls.deleteApi;

public class UserListAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    Context context;
    ArrayList<UserDetails> userList;

    public UserListAdapter(MainActivity mainActivity, ArrayList<UserDetails> notificationList) {
        this.mainActivity = mainActivity;
        context = mainActivity;
        userList = notificationList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childview = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserListRowViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        UserListRowViewHolder notificaitonRowViewHolder = (UserListRowViewHolder) holder;

        if (userList != null) {
            notificaitonRowViewHolder.tv_purchase_price.setText("Purchase Price -: "+userList.get(position).getPurchase_price());
            notificaitonRowViewHolder.tv_investment_name.setText("Name -: "+userList.get(position).getInvestment_name());
            notificaitonRowViewHolder.tv_purchase_date.setText("Purchase Date -: "+userList.get(position).getDate_of_purchase());
            notificaitonRowViewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteData(userList.get(position).getId());
                }
            });
            notificaitonRowViewHolder.rl_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("update_data", convertToJson(userList.get(position)).toString());
                    mainActivity.FragmentManagement(Update_Screen, REPLACE, true, bundle);
                }
            });
        }
    }

    private JSONObject convertToJson(UserDetails userDetails) {
        JSONObject jo_convert = new JSONObject();
        try {
            jo_convert.put("id", userDetails.getId());
            jo_convert.put("investment_name", userDetails.getInvestment_name());
            jo_convert.put("recurring_flag", userDetails.getRecurring());
            jo_convert.put("num_of_unit", userDetails.getNum_of_unit());
            jo_convert.put("purchase_date", userDetails.getDate_of_purchase());
            jo_convert.put("purchase_price", userDetails.getPurchase_price());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo_convert;
    }


    private void deleteData(int id) {
        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id", id);
            HitAPI hitAPI = new HitAPI(context, deleteHandler, parameter, deleteApi);
            if (Utility.isNetworkConnected(context)) {
                hitAPI.execute();
            } else {
                Utility.ShowToast(context, "kindly Check you internet connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler deleteHandler = new Handler() {
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
                            Utility.ShowToast(context, response_message);
                            mainActivity.list_fragment.presenter.getData();
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


    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    class UserListRowViewHolder extends RecyclerView.ViewHolder {
        TextView tv_investment_name, tv_purchase_price,tv_purchase_date;
        ImageView iv_delete;
        RelativeLayout rl_update;
        UserListRowViewHolder(View itemView) {
            super(itemView);
            tv_investment_name = itemView.findViewById(R.id.tv_investment_name);
            tv_purchase_price = itemView.findViewById(R.id.tv_purchase_price);
            tv_purchase_date = itemView.findViewById(R.id.tv_purchase_date);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            rl_update = itemView.findViewById(R.id.rl_update);
        }
    }


}
