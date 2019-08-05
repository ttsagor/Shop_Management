package com.rezaandreza.shop.Configuration;

import java.util.ArrayList;

public class Database {
    public static String DBName = "shop";
    public static String DBPackagePath = "com.rezaandreza.shop.Model.Database";
    public static int DBVersion = 1;
    public static boolean DBCreate = true;

    public static ArrayList<Class> DBExcludedTables = new ArrayList<Class>();
    public static ArrayList<Class> models = new ArrayList<Class>();
}
