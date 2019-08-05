package com.rezaandreza.shop.System.SpinnerHelper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rezaandreza.shop.Configuration.Season;

import java.util.ArrayList;

public class SpinnerHelper {

    public static Spinner setSpinnerData(Spinner spinner, ArrayList<String> data)
    {
        return setSpinnerData(spinner,data, Season.applicationContext);
    }

    public static Spinner setSpinnerData(Spinner spinner, ArrayList<String> data, Context context)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        return spinner;
    }
}
