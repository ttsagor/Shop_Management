package com.rezaandreza.shop.Configuration;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rezaandreza.shop.MainActivity;
import com.rezaandreza.shop.System.Database.DBHandler;

import static com.rezaandreza.shop.System.Display.Display.SetDisplay;

public class Season {
    public static Context applicationContext;
    public static DBHandler DBInstance;
    public static String FCMToken;

    public static void __InitiatSeason(Context context)
    {
        //--- setting global context
        applicationContext = context;

        //---Initiating setting in first activity
        if(!Initialization.__InitializationSucess){
            Initialization.__StartInitialization(context);
        }

        //----- Setting global Database Instance
        DBInstance = new DBHandler(context,null,null,1);


        ///----- setting Display
        SetDisplay(context,Display.orientation,Display.diplayActionBar);

    }

    public static void __InitiatSeasonNoActionBar(Context context)
    {
        //--- setting global context
        applicationContext = context;

        //---Initiating setting in first activity
        if(!Initialization.__InitializationSucess){
            Initialization.__StartInitialization(context);
        }

        //----- Setting global Database Instance
        DBInstance = new DBHandler(context,null,null,1);

    }
}
