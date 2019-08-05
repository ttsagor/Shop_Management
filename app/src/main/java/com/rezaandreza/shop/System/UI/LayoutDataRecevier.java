package com.rezaandreza.shop.System.UI;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.rezaandreza.shop.Configuration.Fonts;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import static com.rezaandreza.shop.System.Helper.TypeCasting.toObject;
import static com.rezaandreza.shop.System.Helper.TypeCasting.toObjectFromObject;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;

public class LayoutDataRecevier {
    public static Object getViewData(Object obj, HashMap<String,String> map, View v)
    {
        for (Field f : obj.getClass().getDeclaredFields())
        {
            if (map.get(f.getName()) != null) {
                try {
                    Object o = getValueFromView(v,f,map.get(f.getName()));
                    f.set(obj,toObjectFromObject(f.getGenericType().toString(),o));
                } catch (Exception e) {
                    System.out.println(f.getName()+"--"+f.getGenericType()+"--"+e.getMessage());
                }
            }
            else
            {
                try {
                    Object o = getValueFromView(v,f,f.getName());
                    f.set(obj,toObject(f.getGenericType().toString(),o.toString()));
                } catch (Exception e) {
                    System.out.println(f.getName()+"--"+f.getGenericType()+"--"+e.getMessage());
                }
            }
        }
        return obj;
    }

    public static Object getValueFromView(View view, Field f,String Viewid)
    {

        if(view == null)
        {
            Resources res = Season.applicationContext.getResources();
            int id = res.getIdentifier(Viewid, "id", Season.applicationContext.getPackageName());
            Activity a = (Activity) Season.applicationContext;
            view = a.findViewById(id);
        }
        else
        {
            Resources res = Season.applicationContext.getResources();
            int id = res.getIdentifier(Viewid, "id", Season.applicationContext.getPackageName());
            view = view.findViewById(id);
        }
        if (view instanceof TextView)
        {
            return ((TextView) view).getText().toString();
        }
        else if (view instanceof EditText) {
            return ((EditText) view).getText().toString();
        }
        else if (view instanceof ImageView) {
            try {
                f = f.getClass().getDeclaredField(f.getName());
                if(f.getGenericType().toString().toUpperCase().equals("CLASS ANDROID.GRAPHICS.BITMAP"))
                {
                    ImageView nv = (ImageView) view;
                    BitmapDrawable drawable = (BitmapDrawable) nv.getDrawable();
                    //return drawable.getBitmap();
                }
                else
                {
                    ImageView nv = (ImageView) view;
                }
            }catch (Exception e){System.out.println(e.getMessage());}
        }
        else if (view instanceof Spinner) {

            return ((Spinner) view).getSelectedItem().toString();
        }
        return "";
    }
    public static Object getViewData(Object obj)
    {
        return getViewData(obj,new HashMap<String,String>(),null);
    }
    public static Object getViewData(Object obj,View view)
    {
        return getViewData(obj,new HashMap<String,String>(),view);
    }
}
