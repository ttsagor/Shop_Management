package com.rezaandreza.shop.Configuration;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jaredrummler.android.device.DeviceName;
import com.rezaandreza.shop.System.Database.Migration;
import com.rezaandreza.shop.System.Helper.DeviceInformation;
import com.rezaandreza.shop.System.Permission.PermissionSettings;

import static com.rezaandreza.shop.Configuration.Season.applicationContext;

public class Initialization extends Application {
    public static boolean __InitializationSucess = false;
    public Initialization(Context context)
    {
        __StartInitialization(context);
    }

    public static void __StartInitialization(Context context)
    {
       //Permission Settings
       PermissionSettings.setPermission(new Permission());

       // Database Ini
       Migration migration = new Migration();

       //FMC Token for Firebase
       FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( (Activity) applicationContext,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Season.FCMToken = instanceIdResult.getToken();

            }
        });

       //Device Information
        DeviceName.with(Season.applicationContext).request(new DeviceName.Callback() {

            @Override public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                DeviceInformation.manufacturer = info.manufacturer;  // "Samsung"
                DeviceInformation.name = info.marketName;            // "Galaxy S8+"
                DeviceInformation.model = info.model;                // "SM-G955W"
                DeviceInformation.codename = info.codename;          // "dream2qltecan"
                DeviceInformation.deviceName = info.getName();       // "Galaxy S8+"
            }
        });



       __InitializationSucess = true;
    }
}
