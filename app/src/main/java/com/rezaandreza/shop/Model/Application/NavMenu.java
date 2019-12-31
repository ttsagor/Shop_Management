package com.rezaandreza.shop.Model.Application;

import com.rezaandreza.shop.System.Helper.TypeCasting;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;

public class NavMenu {
    public String running_sale;
    public String menu_due;
    public String menu_cash;

    public NavMenu(double running_sale, double menu_cash,double menu_due)
    {
        this.running_sale = NumberEngToBng(String.valueOf(running_sale));
        this.menu_due = NumberEngToBng(String.valueOf(menu_due));
        this.menu_cash = NumberEngToBng(String.valueOf(menu_cash));
    }
}
