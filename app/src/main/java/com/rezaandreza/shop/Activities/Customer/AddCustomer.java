package com.rezaandreza.shop.Activities.Customer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nmaltais.calcdialog.CalcDialog;
import com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer;
import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.FormHelper.FormHelper;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.Service.BaseDataService;
import com.rezaandreza.shop.System.UI.LayoutDataRecevier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.rezaandreza.shop.Configuration.APIPaths.registration_api;
import static com.rezaandreza.shop.Helper.NumberConverter.NumberBngToEng;
import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class AddCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        setFontFromView(getView("root_view"));
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        final Button submit_btn = getView("submit_btn");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Customer user =(Customer) LayoutDataRecevier.getViewData(new Customer());
                LinkedHashMap<View, String> linkedHashMap = new LinkedHashMap<View, String>();
                linkedHashMap.put(getView("customer_name"),"Please Select Customer's name");
                linkedHashMap.put(getView("customer_phone"),"Please Select Customer's phone number");
                linkedHashMap.put(getView("customer_nid"),"Please Select Customer's NID number");
                ArrayList<View> validateData = FormHelper.validateDataList(linkedHashMap,Season.applicationContext,true,true);

                if(validateData.size()==0 && user.customer_phone.length()==11 && (user.customer_nid.length()==10 || user.customer_nid.length()==13 || user.customer_nid.length()==17))
                {
                    if(user.total_due>0.0)
                    {
                        user.current_balance=user.total_due*-1;
                        user.initial_due=user.total_due*-1;
                    }
                    user.registared_date = NumberBngToEng(user.registared_date);
                    user.active_status=1;
                    user.insert();
                    Toast.makeText(Season.applicationContext, "Customer Successfully added", Toast.LENGTH_SHORT).show();
                    MyIntent.start(DashboardDrawer.class);
                    finish();
                }
                else
                {
                    Toast.makeText(Season.applicationContext, "Please fill up form.Valid NID & Phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Footer.footerMenu(getView("root_view"),this);
    }
}
