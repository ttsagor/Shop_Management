package com.rezaandreza.shop.Model.Database;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;

public class User  extends BaseModel {
    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("id"));
    //public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>(Arrays.asList("id"));

    public int id;
    public String user_name;
    public String area_sl;
    public String user_password;
    public String shop_name;
    public String shop_category;
    public String shop_owner;
    public String owner_nid;
    public String shop_address_loc;
    public String owner_phone;
    public String shop_address;
    public String shop_location;
    public String distributer_phone;
    public String distributer_id;
    public int active_status;
    public String registrated_datetime;
    public String registration_otp;
}
