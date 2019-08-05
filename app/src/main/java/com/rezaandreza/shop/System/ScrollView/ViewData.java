package com.rezaandreza.shop.System.ScrollView;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public class ViewData {
    public Context context;
    public View view;
    public View parentView;
    public Object object;
    public int position;
    public ArrayList<Object> objects;
    ViewData(Context context,View view,View parentView,Object object,int position,ArrayList<Object> objects)
    {
        this.context = context;
        this.view = view;
        this.parentView = parentView;
        this.object = object;
        this.objects = objects;
        this.position = position;
    }
    public static ViewData getViewData(Context context,View view,View parentView,Object object,int position,ArrayList<Object> objects)
    {
        return new ViewData(context,view,parentView,object,position,objects);
    }
}
