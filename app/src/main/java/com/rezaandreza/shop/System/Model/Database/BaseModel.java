package com.rezaandreza.shop.System.Model.Database;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.System.Database.DBHandler;

import static com.rezaandreza.shop.System.Helper.TypeCasting.dbDataTypeCondition;
import static com.rezaandreza.shop.System.Helper.TypeCasting.toObject;

/**
 * Created by sagor on 7/9/2018.
 */

public class BaseModel {

    public static ArrayList<String> __PRIMARYKEY = new ArrayList<String>();
    public static ArrayList<String> __AUTOINCREMENT = new ArrayList<String>();
    public static ArrayList<String> __UNIQUE = new ArrayList<String>();
    public static ArrayList<String> __CONDITION = new ArrayList<String>();
    public static ArrayList<String> __NONDBDATA = new ArrayList<String>(
                                                        Arrays.asList("__PRIMARYKEY",
                                                            "__AUTOINCREMENT",
                                                            "__UNIQUE",
                                                            "__CONDITION",
                                                            "__NONDBDATA"
                                                    ));

    ContentValues getContectValue()
    {
        ContentValues insertValues = new ContentValues();
        for (Field f : getClass().getDeclaredFields())
        {
            try
            {
                ArrayList<String> nd = new ArrayList<String>();
                ArrayList<String> ai = new ArrayList<String>();
                try {
                    nd = (ArrayList<String>) getClass().getDeclaredField("__NONDBDATA").get(this);
                }
                catch (Exception e)
                {
                    nd = __NONDBDATA;
                }

                try {
                    ai = (ArrayList<String>) getClass().getDeclaredField("__AUTOINCREMENT").get(this);
                }
                catch (Exception e)
                {
                    ai = __AUTOINCREMENT;
                }


                if(nd.contains(f.getName()))
                {
                    continue;
                }

                if(f.getGenericType().toString().toUpperCase().equals("int".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (int) getClass().getDeclaredField(f.getName()).get(this)==0))
                    {
                        insertValues.put(f.getName(),(int) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("double".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (double) getClass().getDeclaredField(f.getName()).get(this)==0.0))
                    {
                        insertValues.put(f.getName(),(double) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("float".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (double) getClass().getDeclaredField(f.getName()).get(this)==0.0))
                    {
                        insertValues.put(f.getName(),(double) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("long".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (long) getClass().getDeclaredField(f.getName()).get(this)!=0))
                    {
                        insertValues.put(f.getName(),(long) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("byte".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (byte) getClass().getDeclaredField(f.getName()).get(this)!=0))
                    {
                        insertValues.put(f.getName(),(byte) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("short".toUpperCase()))
                {
                    if(!(ai.contains(f.getName()) && (short) getClass().getDeclaredField(f.getName()).get(this)!=0))
                    {
                        insertValues.put(f.getName(),(short) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else if(f.getGenericType().toString().toUpperCase().equals("boolean".toUpperCase()))
                {
                    if(!(ai.contains(f.getName())))
                    {
                        insertValues.put(f.getName(),(boolean) getClass().getDeclaredField(f.getName()).get(this));
                    }
                }
                else
                {
                    if(!(ai.contains(f.getName()) && getClass().getDeclaredField(f.getName()).get(this).equals("")))
                    {
                        insertValues.put(f.getName(),getClass().getDeclaredField(f.getName()).get(this).toString());
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  insertValues;
    }
    String conditionGenerator()
    {
        String con="";
        try
        {
            ArrayList<String> cons = new ArrayList<String>();
            try {
                cons = (ArrayList<String>) getClass().getDeclaredField("__CONDITION").get(this);
            }catch (Exception e)
            {
                cons = __CONDITION;
            }

            if(cons.size()==0)
            {
                cons = (ArrayList<String>) getClass().getDeclaredField("__PRIMARYKEY").get(this);
            }
            for(String c : cons)
            {
                Field f = getClass().getDeclaredField(c);
                String col = dbDataTypeCondition( f.getGenericType().toString());
                con+=" "+f.getName()+" = "+col+getClass().getDeclaredField(f.getName()).get(this)+col+" AND ";
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return con.substring(0,con.length()-4);
    }
    public ArrayList<?> selectAll()
    {
        return selectCon("1=1");
    }
    public ArrayList<?> selectCon(String con)
    {
        DBHandler db = Season.DBInstance;
        Cursor c = db.getData("*",getClass().getSimpleName(),con,"");
        ArrayList<Object> baseModels = new ArrayList<>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                try {
                     Object baseModel = getClass().newInstance();
                     for (int i = 0; i < c.getColumnNames().length; i++) {
                        String columnName = c.getColumnName(i);
                        try {
                            Field f = getClass().getDeclaredField(columnName);
                            f.set(baseModel, toObject(f.getGenericType().toString(),c.getString(c.getColumnIndex(columnName))));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    baseModels.add(baseModel);
                }catch (Exception e){System.out.println(e.getStackTrace());}
            } while (c.moveToNext());
        }
        return baseModels;
    }
    public void select()
    {
        DBHandler db = Season.DBInstance;
        ArrayList<?> data = selectCon(conditionGenerator());
        if(data.size()==0)
        {
            return;
        }
        Object cObject = data.get(0);
        for (Field f : getClass().getDeclaredFields())
        {
            try
            {
                ArrayList<String> nd = new ArrayList<String>();
                try {
                    nd = (ArrayList<String>) getClass().getDeclaredField("__NONDBDATA").get(this);
                }catch (Exception e){
                    nd = __NONDBDATA;
                }

                if(nd.contains(f.getName()))
                {
                    continue;
                }
                f.set(this, toObject(f.getGenericType().toString(),cObject.getClass().getDeclaredField(f.getName()).get(cObject).toString()));

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void insert()
    {
        Season.DBInstance.insertData(getContectValue(),getClass().getSimpleName().toString(),conditionGenerator());
    }
    public void update(String data,String con)
    {
        Season.DBInstance.updateData(data,con, getClass().getSimpleName());
    }
    public void update()
    {
        Season.DBInstance.updateData(getContectValue(),getClass().getSimpleName(),conditionGenerator());
    }
    public void delete()
    {
        Season.DBInstance.deleteData(getClass().getSimpleName().toString() ,conditionGenerator());
    }
    public int count(String con)
    {
        return Season.DBInstance. getDataCount(getClass().getSimpleName().toString(),con);
    }

    public int sum(String sel, String con)
    {
        return Season.DBInstance.sumData(sel, getClass().getSimpleName().toString(), con);
    }
    public void delete(String con)
    {
        Season.DBInstance.deleteData(getClass().getSimpleName().toString() ,con);
    }
    public int max(String sel, String con)
    {
        return Season.DBInstance.getMax(getClass().getSimpleName().toString(),sel, con);
    }
}
