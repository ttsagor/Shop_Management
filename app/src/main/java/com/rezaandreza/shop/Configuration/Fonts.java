package com.rezaandreza.shop.Configuration;

import android.graphics.Typeface;

public class Fonts {
    public static String fontFolder = "fonts";
    public static String PrimaryFont = "SolaimanLipi.ttf";
    public static Typeface PrimaryFontTF = Typeface.createFromAsset(Season.applicationContext.getAssets(), fontFolder+"/"+PrimaryFont);

    public static Typeface getFont()
    {
        return PrimaryFontTF;
    }
}
