package com.rezaandreza.shop.Model.Database;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;

public class SalesInvoice extends BaseModel {
    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("invoice_id"));
    public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>(Arrays.asList("invoice_id"));

    public int invoice_id;
    public String customer_name;
    public double total_amount;
    public double total_quatity;
    public String iteam_list;
    public String amount_list;
    public String sale_by;
    public String sale_datetime;
    public String sale_category;
    public String sale_type;
    public String note_given;
    public String due_amount;
    public String advance_amount;
    public String note;
    public int status;
    public int if_uploaded;

}
