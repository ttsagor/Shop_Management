package com.rezaandreza.shop.System.PopupWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class PopupViewData {
    public Context context;
    public View popupView;
    public View parentView;
    public PopupWindow popupWindow;
    PopupViewData(Context context,View popupView,PopupWindow popupWindow,View parentView)
    {
        this.context = context;
        this.popupView = popupView;
        this.popupWindow = popupWindow;
        this.parentView = parentView;
    }
    public static PopupViewData getViewData(Context context,View popupView,PopupWindow popupWindow,View parentView)
    {
        return new PopupViewData(context,popupView,popupWindow,parentView);
    }
}
