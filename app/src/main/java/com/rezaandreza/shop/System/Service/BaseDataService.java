package com.rezaandreza.shop.System.Service;

import android.net.Uri;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Configuration.Service;

import static com.rezaandreza.shop.Configuration.Service.__ServiceCallType;
import static com.rezaandreza.shop.System.Helper.TypeCasting.toObject;

public class BaseDataService {
    public String url;
    public Map<String, String> parameter = new HashMap<String, String>();
    public Object parameterdataClass = new Object();
    public ArrayList<String> parameterExclueded = new ArrayList<String>();
    public Map<String, String> parametervariableMapping = new HashMap<String, String>();
    public int callType = __ServiceCallType;
    public Object outputToModel = new Object();
    public Map<String, String> outputModelVariableMap = new HashMap<String, String>();
    public ArrayList<String> parameterfixedExcluded = new ArrayList<String>(
            Arrays.asList("__PRIMARYKEY",
                    "__AUTOINCREMENT",
                    "__UNIQUE",
                    "__CONDITION",
                    "__NONDBDATA"
            ));

    public String generateURL() {
        this.url += "?";
        for (Map.Entry<String, String> entry : Service.__APIKEY.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                this.url += key + "=" + URLEncoder.encode(value, "utf-8") + "&";
            }catch (Exception e){}
        }

        for (Object currentObject : Service.__PARAMETERMODEL) {
            for (Field f : currentObject.getClass().getDeclaredFields()) {
                try {
                    ArrayList<String> exList = (ArrayList<String>) currentObject.getClass().getDeclaredField("parameterExclueded").get(currentObject);
                    if (!exList.contains(f.getName())) {
                        try {
                            Map<String, String> varMap = (Map<String, String>) currentObject.getClass().getDeclaredField("variableMapping").get(currentObject);
                            if (varMap.get(f.getName()) != null) {
                                this.parameter.put(varMap.get(f.getName()), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            } else {
                                this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            }
                        } catch (Exception ex) {
                            this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                        }
                    }
                } catch (Exception e) {
                    try {
                        try {
                            Map<String, String> varMap = (Map<String, String>) currentObject.getClass().getDeclaredField("variableMapping").get(currentObject);
                            if (varMap.get(f.getName()) != null) {
                                this.parameter.put(varMap.get(f.getName()), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            } else {
                                this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                            }
                        } catch (Exception ex) {
                            this.parameter.put(f.getName(), currentObject.getClass().getDeclaredField(f.getName()).get(currentObject).toString());
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }

        try {
            parameterfixedExcluded = this.parameterfixedExcluded;
        } catch (Exception e) {
            parameterfixedExcluded = parameterfixedExcluded;
        }
        for (Field f : parameterdataClass.getClass().getDeclaredFields()) {
            if (parameterfixedExcluded.contains(f.getName())) {
                continue;
            }
            try {
                if (!this.parameterExclueded.contains(f.getName())) {
                    try {
                        if (this.parametervariableMapping.get(f.getName()) != null) {
                            this.parameter.put(this.parametervariableMapping.get(f.getName()), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        } else {
                            this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        }
                    } catch (Exception e) {
                        this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                    }
                }
            } catch (Exception e) {
                try {
                    try {
                        if (this.parametervariableMapping.get(f.getName()) != null) {
                            this.parameter.put(this.parametervariableMapping.get(f.getName()), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        } else {
                            this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                        }
                    } catch (Exception ex) {
                        this.parameter.put(f.getName(), parameterdataClass.getClass().getDeclaredField(f.getName()).get(parameterdataClass).toString());
                    }
                } catch (Exception ex) {
                }
            }
        }
        try {
            for (Map.Entry<String, String> entry : this.parameter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                this.url += key + "=" + URLEncoder.encode(value, "utf-8") + "&";
            }
        } catch (Exception e) {
        }
        System.out.println(this.url);
        return this.url;
    }

    public void getDataFromURLModel(final Object callBackClass, final String callBackMethod) {
        getDataFromURL(callBackClass, callBackMethod, "model");
    }

    public void getDataFromURLString(final Object callBackClass, final String callBackMethod) {
        getDataFromURL(callBackClass, callBackMethod, "string");
    }

    public void getDataFromURL(final Object callBackClass, final String callBackMethod, final String output) {
        generateURL();
        System.out.println(this.url);
        RequestQueue queue = Volley.newRequestQueue(Season.applicationContext);
        StringRequest stringRequest = new StringRequest(callType, this.url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        if (output.toUpperCase().equals("string".toUpperCase())) {
                            try {
                                Method m = callBackClass.getClass().getMethod(callBackMethod, new Class[]{String.class});
                                m.invoke(callBackClass, response);
                            } catch (Exception e) {
                                System.out.println("error: " + e.getMessage());
                            }
                        } else if (output.toUpperCase().equals("model".toUpperCase())) {
                            dataToModel(response, callBackClass, callBackMethod);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                Toast.makeText(Season.applicationContext, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public void dataToModel(String data, final Object callBackClass, final String callBackMethod) {
        ArrayList<Object> models = new ArrayList<>();
        try {
            JSONArray jr = new JSONArray("[" + data.toString() + "]");
            if (data.toString().charAt(0) == '[') {
                jr = new JSONArray(data.toString());
            }
            for (int i = 0; i < jr.length(); i++) {
                Object baseModel = this.outputToModel.getClass().newInstance();
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                for (Field f : this.outputToModel.getClass().getDeclaredFields()) {
                    try {
                        if (this.outputModelVariableMap.get(f.getName()) != null) {
                            if (!this.outputModelVariableMap.get(f.getName()).equals("")) {
                                f.set(baseModel, toObject(f.getGenericType().toString(), jb.get(this.outputModelVariableMap.get(f.getName())).toString().trim()));
                            }
                        } else {
                            f.set(baseModel, toObject(f.getGenericType().toString(), jb.get(f.getName()).toString().trim()));
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + f.getName() + "-" + e.getMessage());
                    }
                }
                models.add(baseModel);
            }
            try {
                Method m = callBackClass.getClass().getMethod(callBackMethod, new Class[]{ArrayList.class});
                m.invoke(callBackClass, models);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static ArrayList<Object> dataToModel(String data, final Object callBackClass) {
        ArrayList<Object> models = new ArrayList<>();
        try {
            JSONArray jr = new JSONArray("[" + data.toString() + "]");
            if (data.toString().charAt(0) == '[') {
                jr = new JSONArray(data.toString());
            }
            for (int i = 0; i < jr.length(); i++) {
                Object baseModel = callBackClass.getClass().newInstance();
                JSONObject jb = (JSONObject) jr.getJSONObject(i);
                for (Field f : callBackClass.getClass().getDeclaredFields()) {
                    try {
                          f.set(baseModel, toObject(f.getGenericType().toString(), jb.get(f.getName()).toString().trim()));
                    } catch (Exception e) {
                        System.out.println("Error: " + f.getName() + "-" + e.getMessage());
                    }
                }
                models.add(baseModel);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return models;
    }
}
