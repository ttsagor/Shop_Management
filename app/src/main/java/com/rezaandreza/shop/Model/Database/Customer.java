package com.rezaandreza.shop.Model.Database;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;

public class Customer extends BaseModel {
    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("customer_id"));
    public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>(Arrays.asList("customer_id"));
    public ArrayList<String> __UNIQUE = new ArrayList<String>(Arrays.asList("customer_name"));

    public int customer_id;
    public String customer_name;
    public String customer_phone;
    public String customer_category;
    public String customer_company;
    public String customer_fathers_name;
    public String customer_address;
    public String customer_reference;
    public String customer_nid;
    public String customer_mail;
    public double total_due_limit;
    public double total_due;
    public double initial_due;
    public double total_advance;
    public double current_balance;
    public int active_status;
    public String customer_shop;
    public String registared_date;
}
