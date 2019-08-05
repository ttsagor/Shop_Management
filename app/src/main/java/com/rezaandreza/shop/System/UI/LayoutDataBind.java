package com.rezaandreza.shop.System.UI;

import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;

public class LayoutDataBind {
    public static void setView(Object obj,HashMap<String,String> map,View v)
    {
        for (Field f : obj.getClass().getDeclaredFields())
        {
            if (map.get(f.getName()) != null) {
                try {
                    getView(obj,map.get(f.getName()), f.get(obj).toString(),v);
                } catch (Exception e) {
                }
            }
            else
            {
                try {
                    getView(obj,f.getName(), f.get(obj).toString(),v);
                } catch (Exception e) {
                }
            }
        }
    }
    public static void setView(Object obj,HashMap<String,String> map)
    {
        setView(obj,map,null);
    }
    public static void setView(Object obj)
    {
        setView(obj,new HashMap<String,String>(),null);
    }
    public static void setView(Object obj,View v)
    {
        setView(obj,new HashMap<String,String>(),v);
    }
}
