package com.rezaandreza.shop.Activities.Registration.Signup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rezaandreza.shop.Activities.Dashboard.Dashboard;
import com.rezaandreza.shop.Activities.Registration.NumberVerification.NumberVerification;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.AreaCode;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.FormHelper.FormHelper;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.System.Service.BaseDataService;
import com.rezaandreza.shop.System.SpinnerHelper.SpinnerHelper;
import com.rezaandreza.shop.System.UI.LayoutDataRecevier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.rezaandreza.shop.Configuration.APIPaths.registration_api;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class Signup extends AppCompatActivity {


    ArrayList<AreaCode> divisions = new ArrayList<>();
    ArrayList<AreaCode> districts = new ArrayList<>();
    ArrayList<AreaCode> upazillas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setFontFromView(getView("root_view"));

        BaseDataService m = new BaseDataService();
        m.url = "http://365ehub.com/shop-management/address-api.php";
        AreaCode mv = new AreaCode();
        mv.area_id = 622;
        m.outputToModel = mv;
        m.parameterdataClass = mv;
        m.getDataFromURLModel(this,"areaDownloadDivision");

        Button submit = getView("btn_submit");

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final User user =(User) LayoutDataRecevier.getViewData(new User());
                LinkedHashMap<View, String> linkedHashMap = new LinkedHashMap<View, String>();
                linkedHashMap.put(getView("shop_name"),"Please Select shop_name");
                linkedHashMap.put(getView("shop_category"),"Please Select shop_category");
                linkedHashMap.put(getView("shop_owner"),"Please Select shop_owner");
                linkedHashMap.put(getView("owner_nid"),"Please Select owner_nid");
                linkedHashMap.put(getView("shop_address_loc"),"Please Select shop_address_loc");
                linkedHashMap.put(getView("owner_phone"),"Please Select owner_phone");
                linkedHashMap.put(getView("shop_address"),"Please Select shop_address");
                linkedHashMap.put(getView("shop_location"),"Please Select Retail shop_location");
                linkedHashMap.put(getView("distributer_phone"),"Please Select Retail distributer_phone");
                linkedHashMap.put(getView("distributer_id"),"Please Select Retail distributer_id");
                ArrayList<View> validateData = FormHelper.validateDataList(linkedHashMap,Season.applicationContext,true,true);
                if(validateData.size()==0 && user.owner_phone.length()==11 && user.distributer_phone.length()==11 && (user.owner_nid.length()==10 || user.owner_nid.length()==13 || user.owner_nid.length()==17))
                {
                    Spinner shop_location = getView("shop_location");
                    user.shop_location = String.valueOf((upazillas.get(shop_location.getSelectedItemPosition()-1)).area_id);
                    BaseDataService m = new BaseDataService();
                    m.url = registration_api;
                    m.outputToModel = new User();
                    m.parameterdataClass = user;
                    m.getDataFromURLModel(Season.applicationContext,"downloadData");
                }
                else
                {
                    Toast.makeText(Season.applicationContext, "Please fill up form", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Spinner division_id = getView("division_id");
        Spinner district_id = getView("district_id");


        division_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0)
                {return;}
                BaseDataService m = new BaseDataService();
                m.url = "http://365ehub.com/shop-management/address-api.php";
                AreaCode mv = divisions.get(position-1);
                m.outputToModel = mv;
                m.parameterdataClass = mv;
                m.getDataFromURLModel(Season.applicationContext,"areaDownloadDistrict");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        district_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0)
                {return;}
                BaseDataService m = new BaseDataService();
                m.url = "http://365ehub.com/shop-management/address-api.php";
                AreaCode mv = districts.get(position-1);
                m.outputToModel = mv;
                m.parameterdataClass = mv;
                m.getDataFromURLModel(Season.applicationContext,"areaDownloadUpazilla");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void downloadData(ArrayList<Object> data)
    {
        try {
            User user = (User) data.get(0);
            if(user.active_status==0)
            {
                Toast.makeText(Season.applicationContext, "Phone Number Already Exists", Toast.LENGTH_SHORT).show();
            }
            else if(user.active_status==1)
            {
                Toast.makeText(Season.applicationContext, "Signup Sucessful", Toast.LENGTH_SHORT).show();
                user.insert();
                MyIntent.start(NumberVerification.class);
                finish();
            }
            else
            {
                Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
        }

    }
    public void areaDownloadDivision(ArrayList<Object> data)
    {
        ArrayList<String> d = new ArrayList<>();
        divisions = new ArrayList<>();
        d.add("সিলেক্ট");
        for(Object ob : data)
        {
            divisions.add(((AreaCode) ob));
            d.add(((AreaCode) ob).area_name_bng);
        }
        Spinner spinner = getView("division_id");
        SpinnerHelper.setSpinnerData(spinner, d);
    }

    public void areaDownloadDistrict(ArrayList<Object> data)
    {
        Debug.print(data);
        ArrayList<String> d = new ArrayList<>();
        districts = new ArrayList<>();
        d.add("সিলেক্ট");
        for(Object ob : data)
        {
            districts.add(((AreaCode) ob));
            d.add(((AreaCode) ob).area_name_bng);
        }
        Spinner spinner = getView("district_id");
        SpinnerHelper.setSpinnerData(spinner, d);
    }
    public void areaDownloadUpazilla(ArrayList<Object> data)
    {
        Debug.print(data);
        ArrayList<String> d = new ArrayList<>();
        upazillas = new ArrayList<>();
        d.add("সিলেক্ট");
        for(Object ob : data)
        {
            upazillas.add(((AreaCode) ob));
            d.add(((AreaCode) ob).area_name_bng);
        }
        Spinner spinner = getView("shop_location");
        SpinnerHelper.setSpinnerData(spinner, d);
    }

}
