package com.rezaandreza.shop.System.PopupWindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.rezaandreza.shop.Configuration.Season;

import java.lang.reflect.Method;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyPopupView {
    public static void showPopupView(int view,String callBackFunction,View parentView)
    {
        LayoutInflater layoutInflater = (LayoutInflater) Season.applicationContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(view, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            Method m = Season.applicationContext.getClass().getMethod(callBackFunction, new Class[]{PopupViewData.class});
            m.invoke(Season.applicationContext, PopupViewData.getViewData(Season.applicationContext, popupView, popupWindow,parentView));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        popupWindow.showAtLocation(((Activity)Season.applicationContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //popupWindow.showAsDropDown(pos_payment, 0, 0);
    }
    public static void showPopupView(int view,String callBackFunction)
    {
        LayoutInflater layoutInflater = (LayoutInflater) Season.applicationContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(view, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            Method m = Season.applicationContext.getClass().getMethod(callBackFunction, new Class[]{PopupViewData.class});
            m.invoke(Season.applicationContext, PopupViewData.getViewData(Season.applicationContext, popupView, popupWindow,null));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        popupWindow.showAtLocation(((Activity)Season.applicationContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //popupWindow.showAsDropDown(pos_payment, 0, 0);
    }
    public static void showPopupViewFull(int view,String callBackFunction)
    {
        showPopupViewFull(view,callBackFunction,null);

    }
    public static void showPopupViewFull(int view,String callBackFunction,View parentView)
    {
        LayoutInflater layoutInflater = (LayoutInflater) Season.applicationContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(view, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // Closes the popup window when touch outside.
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // Removes default background.
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            Method m = Season.applicationContext.getClass().getMethod(callBackFunction, new Class[]{PopupViewData.class});
            m.invoke(Season.applicationContext, PopupViewData.getViewData(Season.applicationContext, popupView, popupWindow,parentView));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        popupWindow.showAtLocation(((Activity)Season.applicationContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //popupWindow.showAsDropDown(pos_payment, 0, 0);
    }
}
