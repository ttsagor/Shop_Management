package com.rezaandreza.shop.Configuration;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Service {

    public static int __ServiceCallType = Request.Method.GET;
    public static HashMap<String, String> __APIKEY = new HashMap<String, String>() {{
               // put("app_name","icapp");
            }};

    public static ArrayList<Object> __PARAMETERMODEL = new ArrayList<Object>(
            Arrays.asList(
                   new Object()
            ));
}
