package com.rezaandreza.shop.System.Display;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class Display {

    public static void SetDisplay(Context context, int orientation, boolean diplayActionBar)
    {
        Activity a = (Activity) context;
        AppCompatActivity ap = (AppCompatActivity) context;
        a.setRequestedOrientation(orientation);
        if(!diplayActionBar)
        {
            ActionBar actionBar = ap.getSupportActionBar();
            actionBar.hide();

        }
    }


}
