package com.rezaandreza.shop.System.FormHelper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rezaandreza.shop.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FormHelper {

    public static ArrayList<View> validateDataList(LinkedHashMap<View, String> linkedHashMap, Context context, Boolean setBorder, Boolean showToast)
    {
        ArrayList<View> list = new ArrayList<>();
        String finalMsg="";
        for( LinkedHashMap.Entry<View,String> entry : linkedHashMap.entrySet())
        {
            View view = entry.getKey();
            String msg = entry.getValue();
            if (view instanceof EditText)
            {
                EditText editText = (EditText) view;
                if(editText.getText().toString().equals(""))
                {
                    if(setBorder)
                    {
                        editText.setBackground(getDrawable(context, R.drawable.border_red));
                    }
                    finalMsg+=msg+ "\n";
                    list.add(editText);
                }
                else
                {
                    if(setBorder)
                    {
                        editText.setBackground(getDrawable(context,R.drawable.border));
                    }
                }
            }
            else if (view instanceof Spinner)
            {
                Spinner spinner = (Spinner) view;
                if(spinner.getSelectedItemPosition()==0)
                {
                    if(setBorder)
                    {
                        spinner.setBackground(getDrawable(context,R.drawable.border_red));
                    }
                    finalMsg+=msg+ "\n";
                    list.add(spinner);
                }
                else
                {
                    if(setBorder)
                    {
                        spinner.setBackground(getDrawable(context,R.drawable.border));
                    }
                }
            }
        }
        if(!finalMsg.equals("") && showToast)
        {
          //  Toasty.error(context, finalMsg.trim(), Toast.LENGTH_SHORT, true).show();
        }
        return list;
    }
    public static Boolean validateData(LinkedHashMap<View, String> linkedHashMap, Context context,Boolean setBorder)
    {
        String finalMsg="";
        for( LinkedHashMap.Entry<View,String> entry : linkedHashMap.entrySet())
        {
            View view = entry.getKey();
            String msg = entry.getValue();
            if (view instanceof EditText)
            {
                EditText editText = (EditText) view;
                if(editText.getText().toString().equals(""))
                {
                    if(setBorder)
                    {
                        editText.setBackground(getDrawable(context,R.drawable.border_red));
                    }
                    finalMsg+=msg+ "\n";
                }
            }
            else if (view instanceof Spinner)
            {
                Spinner spinner = (Spinner) view;
                if(spinner.getSelectedItemPosition()==0)
                {
                    if(setBorder)
                    {
                        spinner.setBackground(getDrawable(context,R.drawable.border_red));
                    }
                    finalMsg+=msg+ "\n";
                }
            }
        }
        if(!finalMsg.equals(""))
        {

           // Toasty.error(context, finalMsg.trim(), Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }
    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return ResourcesCompat.getDrawable(context.getResources(), id, null);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
    public static String getData(View view)
    {
        if (view instanceof EditText)
        {
            EditText editText = (EditText) view;
            return editText.getText().toString();

        }
        else if (view instanceof Spinner)
        {
            Spinner spinner = (Spinner) view;
            if(spinner.getSelectedItemPosition()!=0)
            {
                return spinner.getSelectedItem().toString();
            }
        }
        return "";
    }
}
