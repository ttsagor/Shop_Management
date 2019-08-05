package com.rezaandreza.shop.Model.Database;

import java.util.ArrayList;
import java.util.Arrays;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

public class AreaCode extends BaseModel {

    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("area_id"));
    public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>(Arrays.asList("area_id"));

    public  int area_id;
    public  int parent_id;
    public  String area_name_eng;
    public  String area_name_bng;
    public int active_stat;
    public String last_update;
}
