package com.rezaandreza.shop.System.Database;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;
import com.rezaandreza.shop.Configuration.Database;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.System.Model.Database.BaseModel;

import static com.rezaandreza.shop.System.Helper.TypeCasting.dbDataTypeSelection;

public class Migration {

    public static ArrayList<Table> tables = new ArrayList<Table>();
    public static ArrayList<String> tableCreateSQL = new ArrayList<String>();
    public Migration()
    {
        if(Database.DBCreate) {
            String[] classes = getClassesOfPackage(Database.DBPackagePath);
            for(String c: classes)
            {
                try {
                    if(!Database.DBExcludedTables.contains(getClass().forName(Database.DBPackagePath+"."+c))) {
                        Database.models.add(getClass().forName(Database.DBPackagePath + "." + c));
                    }
                }catch (Exception e){System.out.println(e.getMessage());}
            }
            generateTable();
        }
    }

    public String dataTypeSelection(String type)
    {
        return dbDataTypeSelection(type);
    }

    public void generateSyntex(ArrayList<Table> tables)
    {
        tableCreateSQL = new ArrayList<String>();
        for(Table table : tables)
        {
            String sql="CREATE TABLE " +  table.name+" ( ";
            for(Column column : table.columns)
            {
                String pk ="";
                String ai="";
                String uq="";
                if(column.isPrimaryKey())
                {
                    pk=" PRIMARY KEY ";
                }
                if(column.isAutoIncrement())
                {
                    ai=" AUTOINCREMENT ";
                }
                if(column.isUnique())
                {
                    uq=" UNIQUE ";
                }
                sql += column.getName()+" "+column.getType()+pk+ai+uq+" ,";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql +=");";
            tableCreateSQL.add(sql);
        }

    }
    public void generateTable()
    {
        tables = new ArrayList<Table>();
        for(Class cl : Database.models)
        {
            try {
                ArrayList<String> _PRIMARYKEY =  new ArrayList<String>();
                try {
                    _PRIMARYKEY = (ArrayList<String>) cl.getDeclaredField("__PRIMARYKEY").get(cl.newInstance());
                }catch (Exception e)
                {
                    _PRIMARYKEY = BaseModel.__PRIMARYKEY;
                }

                ArrayList<String> __AUTOINCREMENT = new ArrayList<String>();
                try {
                    __AUTOINCREMENT = (ArrayList<String>) cl.getDeclaredField("__AUTOINCREMENT").get(cl.newInstance());
                }catch (Exception e)
                {
                    __AUTOINCREMENT = BaseModel.__AUTOINCREMENT;
                }
                ArrayList<String> __UNIQUE = new ArrayList<String>();;
                try {
                    __UNIQUE = (ArrayList<String>) cl.getDeclaredField("__UNIQUE").get(cl.newInstance());
                }catch (Exception e)
                {
                    __UNIQUE = BaseModel.__UNIQUE;
                }

                ArrayList<String> __NONDBDATA = new ArrayList<String>();
                try {
                    __NONDBDATA = (ArrayList<String>) cl.getDeclaredField("__NONDBDATA").get(cl.newInstance());
                }catch (Exception e)
                {
                    __NONDBDATA = BaseModel.__NONDBDATA;
                }

                Table table = new Table();
                table.columns = new ArrayList<Column>();
                table.setName(cl.getSimpleName());
                for (Field f : cl.getDeclaredFields())
                {
                    if(__NONDBDATA.contains(f.getName()))
                    {
                        continue;
                    }
                    Column column = new Column();
                    column.setName(f.getName());
                    column.setType(dataTypeSelection(f.getGenericType().toString()));
                    column.setPrimaryKey(_PRIMARYKEY.contains(f.getName()));
                    column.setUnique(__UNIQUE.contains(f.getName()));
                    column.setAutoIncrement(__AUTOINCREMENT.contains(f.getName()));
                    table.columns.add(column);
                }
                tables.add(table);
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        generateSyntex(tables);
    }

    private String[] getClassesOfPackage(String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            String packageCodePath = Season.applicationContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String className = iter.nextElement();
                if (className.contains(packageName)) {
                    classes.add(className.substring(className.lastIndexOf(".") + 1, className.length()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes.toArray(new String[classes.size()]);
    }

}
