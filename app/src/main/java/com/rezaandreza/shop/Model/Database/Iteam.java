package com.rezaandreza.shop.Model.Database;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;

public class Iteam extends BaseModel {
    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("iteam_name_bng"));

    public String iteam_name_bng="";
    public String iteam_name_eng="";
    public String iteam_unit="";
    public String iteam_popular_quantity="0";
    public String iteam_quantity="0";
    public String iteam_price="0";
}
