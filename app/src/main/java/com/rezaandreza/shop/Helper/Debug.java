package com.rezaandreza.shop.Helper;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Debug {

    public static String print(ArrayList<?> obs,String col,String comma)
    {
        String output="";
        for(Object ob : obs)
        {
            output+=print(ob,":",",")+"\n";
        }
        return output;
    }

    public static String print(ArrayList<?> obs)
    {
        String output="";
        for(Object ob : obs)
        {
            output+=print(ob)+"\n";
        }
        return output;
    }
    public static String print(Object ob)
    {
        return print(ob,":",",");
    }
    public static String print(Object ob,String col,String comma)
    {
        String output="";
        for (Field f : ob.getClass().getDeclaredFields()) {
            try {
                output+= f.getName()+col+ ob.getClass().getDeclaredField(f.getName()).get(ob)+comma;
            }catch (Exception e){}

        }
        System.out.println(output);
        return output;
    }
    public static String printArray(ArrayList<String> data)
    {
        String output="";
        for(String s:data)
        {
            output+=s+";";
        }
        System.out.println(output);
        return output;
    }
}
