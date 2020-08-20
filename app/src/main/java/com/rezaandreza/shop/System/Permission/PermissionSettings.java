package com.rezaandreza.shop.System.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.nabinbhandari.android.permissions.*;
import com.rezaandreza.shop.Configuration.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class PermissionSettings {
    public static void setPermission()
    {
        for(String per : Permission.permissionList)
        {
            Permissions.check(Season.applicationContext, per, null, new PermissionHandler() {
                @Override
                public void onGranted() {
                    // do your task.
                }
                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                    // permission denied, block the feature.
                }
            });
        }
    }

    public static void askForPermission(String permission)
    {
        Permissions.check(Season.applicationContext, permission, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
            }
        });
    }

    public static boolean ifPermissionSet(String permissionName)
    {
        int checkPermission = ContextCompat.checkSelfPermission(Season.applicationContext, permissionName);
        if (checkPermission != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static void flowAfterPermission(String permissoinName,String onGrant,String onDeny)
    {
        flowAfterPermission(permissoinName,onGrant,onDeny,Season.applicationContext,Season.applicationContext);
    }

    public static void flowAfterPermission(final String permissoinName,final String onGrant,final String onDeny,final Object onGOb,final Object onDOb)
    {
        Permissions.check(Season.applicationContext, permissoinName, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                try {
                    Method m = onGOb.getClass().getMethod(onGrant,new Class[]{String.class});
                    m.invoke(onGOb,"s");
                }catch (Exception e){System.out.println(e.getMessage());}
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                try {
                    Method m = onDOb.getClass().getMethod(onDeny,new Class[]{String.class});
                    m.invoke(onDOb,"s");
                }catch (Exception e){System.out.println(e.getMessage());}
            }
        });
    }
}
