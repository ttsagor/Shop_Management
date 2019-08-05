package com.rezaandreza.shop.System.Helper;

import android.graphics.Bitmap;

public class TypeCasting {
    public static String dbDataTypeSelection(String type)
    {
        type = type.toUpperCase();
        if("Boolean".toUpperCase().equals(type)){ return "TEXT";}
        if("byte".toUpperCase().equals(type)){ return "INTEGER";}
        if("short".toUpperCase().equals(type)){ return "INTEGER";}
        if("int".toUpperCase().equals(type)){ return "INTEGER";}
        if("long".toUpperCase().equals(type)){ return "INTEGER";}
        if("float".toUpperCase().equals(type)){ return "REAL";}
        if("double".toUpperCase().equals(type)){ return "REAL";}
        return "TEXT";
    }
    public static String dbDataTypeCondition(String type)
    {
        type = type.toUpperCase();
        if("Boolean".toUpperCase().equals(type)){ return "'";}
        if("byte".toUpperCase().equals(type)){ return "";}
        if("short".toUpperCase().equals(type)){ return "";}
        if("int".toUpperCase().equals(type)){ return "";}
        if("long".toUpperCase().equals(type)){ return "";}
        if("float".toUpperCase().equals(type)){ return "";}
        if("double".toUpperCase().equals(type)){ return "";}
        return "'";
    }
    public static Object toObject( String clazz, String value ) {
        clazz = clazz.toUpperCase();
        if("Boolean".toUpperCase().equals(clazz)){ return Boolean.parseBoolean(value);}
        if("byte".toUpperCase().equals(clazz)){ return Byte.parseByte(value);}
        if("short".toUpperCase().equals(clazz)){ return Short.parseShort(value);}
        if("int".toUpperCase().equals(clazz)){ return Integer.parseInt(value);}
        if("long".toUpperCase().equals(clazz)){ return Long.parseLong(value);}
        if("float".toUpperCase().equals(clazz)){ return Float.parseFloat(value);}
        if("double".toUpperCase().equals(clazz)){ return Double.parseDouble(value);}
        return value;
    }
    public static Object toObject( Class clazz, String value ) {

        if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz ) return Byte.parseByte( value );
        if( Short.class == clazz ) return Short.parseShort( value );
        if( Integer.class == clazz ) return Integer.parseInt( value );
        if( Long.class == clazz ) return Long.parseLong( value );
        if( Float.class == clazz ) return Float.parseFloat( value );
        if( Double.class == clazz ) return Double.parseDouble( value );
        return value;
    }
    public static Object toObjectFromObject( String clazz, Object value ) {
        clazz = clazz.toUpperCase();
        if("Boolean".toUpperCase().equals(clazz)){ return Boolean.parseBoolean(value.toString());}
        if("byte".toUpperCase().equals(clazz)){ return Byte.parseByte(value.toString());}
        if("short".toUpperCase().equals(clazz)){ return Short.parseShort(value.toString());}
        if("int".toUpperCase().equals(clazz)){ return Integer.parseInt(value.toString());}
        if("long".toUpperCase().equals(clazz)){ return Long.parseLong(value.toString());}
        if("float".toUpperCase().equals(clazz)){ return Float.parseFloat(value.toString());}
        if("double".toUpperCase().equals(clazz)){ return Double.parseDouble(value.toString());}
        if("Bitmap".toUpperCase().equals(clazz)){
            return (Bitmap) value;
        }
        return value;
    }

    public static double parseDouble(Object value)
    {
        double output = 0.0;
        try{
            output = Double.parseDouble(value.toString());
        }catch (Exception e){}
        return output;
    }
    public static int parseInt(Object value)
    {
        int output = 0;
        try{
            output = Integer.parseInt(value.toString());
        }catch (Exception e){}
        return output;
    }
    public static String round2Dec(Double value)
    {
        return String.format("%.2f", value);
    }
    public static String round1Dec(Double value)
    {
        return String.format("%.1f", value);
    }
}
