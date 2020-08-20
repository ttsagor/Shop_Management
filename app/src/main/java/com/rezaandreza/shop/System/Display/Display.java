package com.rezaandreza.shop.System.Display;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Display {

    public static void SetDisplay(Context context, int orientation, boolean diplayActionBar)
    {
        Activity a = (Activity) context;
        AppCompatActivity ap = (AppCompatActivity) context;
        a.setRequestedOrientation(orientation);
        if(!diplayActionBar)
        {
            try {
                ActionBar actionBar = ap.getSupportActionBar();
                actionBar.hide();
            } catch (Exception e) {

            }
        }
    }


}
