package com.rezaandreza.shop.System.UI;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.rezaandreza.shop.Configuration.Fonts;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class ViewColor {
    public static void setColor(Object obj, HashMap<String,String> map,View v)
    {
        for(HashMap.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                Field f  = obj.getClass().getDeclaredField(value);
                setColorView(v,key,f.get(obj).toString());
            }catch (Exception e){
                setColorView(v,key,value);
            }
        }
    }

    public static void setColorView(View v,String field,String color)
    {
        try {
            View view;
            if (v == null) {
                Resources res = Season.applicationContext.getResources();
                int id = res.getIdentifier(field, "id", Season.applicationContext.getPackageName());
                Activity a = (Activity) Season.applicationContext;
                view = a.findViewById(id);
            } else {
                Resources res = Season.applicationContext.getResources();
                int id = res.getIdentifier(field, "id", Season.applicationContext.getPackageName());
                view = v.findViewById(id);
            }

            if (view instanceof TextView) {
                if (!color.equals("")) {
                    TextView t = (TextView) view;
                    t.setTextColor(Color.parseColor(color));
                }
            } else if (view instanceof TextView) {
                if (!color.equals("")) {
                    EditText t = (EditText) view;
                    t.setTextColor(Color.parseColor(color));
                }
            } else if (view instanceof LinearLayout) {
                if (!color.equals("")) {
                    LinearLayout t = (LinearLayout) view;
                    t.setBackgroundColor(Color.parseColor(color));
                }
            }
        }catch (Exception e){}
    }

    public static void setBorderColor(View view,int width,String bgColor,String borderColor,int borderRadius)
    {
        GradientDrawable border = new GradientDrawable();
        border.setStroke(width, Color.parseColor(borderColor));
        border.setColor(Color.parseColor(bgColor));
        border.setCornerRadius(borderRadius);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(border);
        } else {
            view.setBackground(border);
        }
    }

    public static void setBorderColor(View view,int width,String bgColor,String borderColor)
    {
        setBorderColor(view,width,bgColor,borderColor,0);
    }

    public static void setColor(Object obj, HashMap<String,String> map) {
        setColor(obj, map,null);
    }
}
