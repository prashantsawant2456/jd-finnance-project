package com.saiprashant.jdfinnancialproject.Model;

import org.json.JSONObject;

public class UserDetails {

    String investment_name = "", date_of_purchase = "", num_of_unit = "", purchase_price = "";
    int recurring = 0, id = 0;

    public String getInvestment_name() {
        return investment_name;
    }

    public void setInvestment_name(String investment_name) {
        this.investment_name = investment_name;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public String getNum_of_unit() {
        return num_of_unit;
    }

    public void setNum_of_unit(String num_of_unit) {
        this.num_of_unit = num_of_unit;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getRecurring() {
        return recurring;
    }

    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


  public   UserDetails(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                id = Integer.parseInt(jsonObject.getString("id"));
            }
            if (jsonObject.has("investment_name")) {
                investment_name = jsonObject.getString("investment_name");
            }
            if (jsonObject.has("recurring_flag")) {
                recurring = Integer.parseInt(jsonObject.getString("recurring_flag"));
            }
            if (jsonObject.has("num_of_unit")) {
                num_of_unit = jsonObject.getString("num_of_unit");
            }
            if (jsonObject.has("purchase_date")) {
                date_of_purchase = jsonObject.getString("purchase_date");

            }
            if (jsonObject.has("purchase_price")) {
                purchase_price = jsonObject.getString("purchase_price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
