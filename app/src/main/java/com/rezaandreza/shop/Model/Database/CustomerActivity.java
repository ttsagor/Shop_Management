package com.rezaandreza.shop.Model.Database;

import com.rezaandreza.shop.System.Helper.TypeCasting;
import com.rezaandreza.shop.System.Model.Database.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerActivity  extends BaseModel {
    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("activity_id"));
    public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>(Arrays.asList("activity_id"));

    public int activity_id;
    public String invoice_id;
    public String customer_name;
    public String note;
    public String entry_by;
    public double due_amount;
    public double advance_amount;
    public double current_balance;
    public int active_status;


    public static void invoiceToActivity(SalesInvoice invoice)
    {
        if(TypeCasting.parseDouble(invoice.due_amount)>0.00 || TypeCasting.parseDouble(invoice.advance_amount)>0.00) {
            CustomerActivity ca = new CustomerActivity();
            ca.due_amount = TypeCasting.parseDouble(invoice.due_amount);
            ca.advance_amount = TypeCasting.parseDouble(invoice.advance_amount);
            ca.customer_name = invoice.customer_name;
            ca.invoice_id = String.valueOf(invoice.invoice_id);
            ca.note = invoice.note;

            Customer customer = new Customer();
            customer = (Customer) customer.selectCon("customer_name='"+ca.customer_name+"'").get(0);
            customer.current_balance = customer.current_balance - ca.due_amount + ca.advance_amount;
            customer.insert();
            ca.insert();
        }
    }
}
