package com.rezaandreza.shop.Configuration;

import android.Manifest;

import java.util.ArrayList;

public class Permission {
    public static ArrayList<String> permissionList = new ArrayList<>();

    Permission()
    {
        permissionList.add(Manifest.permission.INTERNET);
        /*permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.CALL_PHONE);
        permissionList.add(Manifest.permission.SEND_SMS);*/
    }
}
