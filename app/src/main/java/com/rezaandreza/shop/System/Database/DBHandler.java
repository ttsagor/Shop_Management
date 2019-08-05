package com.rezaandreza.shop.System.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import com.rezaandreza.shop.Configuration.Database;

public class DBHandler  extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = Database.DBVersion;
    public static final String DATABASE_NAME= Database.DBName;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for(String query : Migration.tableCreateSQL)
        {
            try {
                db.execSQL(query);
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for(Table table : Migration.tables)
        {
            db.execSQL("DROP TABLE IF EXISTS " + table.name);
        }
        onCreate(db);
        //db.close();
    }

    public void  dropDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        for(Table table : Migration.tables)
        {
            db.execSQL("DROP TABLE IF EXISTS " + table.name);
        }
    }

    public Cursor getData(String sel,String table,String where,String groupby)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        if(groupby.length()>0)
        {
            query = "SELECT "+sel+" FROM " + table + " WHERE " + where+" GROUP BY "+groupby;
        }
        else
        {
            query = "SELECT "+sel+" FROM " + table + " WHERE " + where;
        }
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        return c;
    }

    public void insertData(ContentValues insertValues, String table_name,String con) {

        int size = getDataCount(table_name, con);
        SQLiteDatabase db = getWritableDatabase();
        if(size>0)
        {
            try
            {
                db.update(table_name, insertValues, con, null);
            }
            catch (Exception e) {
            }

        }
        else
        {
            try
            {
                db.insert(table_name, null, insertValues);
            }
            catch (Exception ex) {
            }
        }
        db.close();
    }

    public void updateData(ContentValues insertValues, String table_name,String con) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.update(table_name, insertValues, con, null);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        db.close();
    }
    public void updateData(String data, String con, String TableName) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String qu = "UPDATE " + TableName + " SET " + data + " WHERE " + con + ";";
            db.execSQL(qu);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        db.close();
    }

    public void deleteData( String TableName , String con) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String qu = "DELETE FROM " + TableName + " WHERE " + con + ";";
            db.execSQL(qu);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
    }
    public int getDataCount(String TABLE_NAME, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
        return c.getCount();
    }

    public int sumData(String data, String TABLE_NAME, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM("+data+") FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    return c.getInt(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
        return 0;
    }

    public int getMax(String TABLE_NAME,String col, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(con.equals(""))
        {
            con = "1=1";
        }
        String query = "SELECT MAX("+col+") FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                return c.getInt(0);
            } while (c.moveToNext());
        }
        return 0;
    }
}
