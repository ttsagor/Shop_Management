package com.rezaandreza.shop.System.Intent;

import android.content.Context;
import android.content.Intent;

import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.MainActivity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.rezaandreza.shop.System.Helper.TypeCasting.toObject;

public class MyIntent {

    public static void start(Context source, Class target, HashMap<String,String> data,boolean clear)
    {
        Intent intent = new Intent(source, target);
        if(clear)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        for(Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            intent.putExtra(key,value);
        }
        source.startActivity(intent);
    }
    public static void start(Class target, HashMap<String,String> data)
    {
        start(Season.applicationContext, target, data,false);
    }
    public static void startClearStack(Class target)
    {
        start(Season.applicationContext, target,new HashMap<String, String>(),false);
    }
    public static void start(Class target)
    {
        start(Season.applicationContext, target, new HashMap<String, String>(),false);
    }
    public static void start(Class target, Object data)
    {
        HashMap<String, String> entry = new HashMap<String, String>();
        for (Field f : data.getClass().getDeclaredFields())
        {
            try
            {
                entry.put(f.getName(), data.getClass().getDeclaredField(f.getName()).get(data).toString());
            }
            catch (Exception e){}
        }
        start(Season.applicationContext, target, entry,false);
    }

    public static Object getIntentObject(Intent ii, Object data)
    {
        for (Field f : data.getClass().getDeclaredFields())
        {
            try
            {
                f.set(data,toObject(f.getGenericType().toString(),ii.getStringExtra(f.getName())));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return data;
    }
}
