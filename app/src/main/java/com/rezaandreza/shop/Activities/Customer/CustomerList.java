package com.rezaandreza.shop.Activities.Customer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer;
import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Activities.Registration.Signup.Signup;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberBngToEng;
import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class CustomerList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        setFontFromView(getView("root_view"));
        Footer.footerMenu(getView("root_view"),this);
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        final ArrayList<Customer> AllCustomer = new ArrayList<>();

        for(Object o : (new Customer()).selectAll())
        {
            AllCustomer.add((Customer) o);
        }

        final EditText customer_name = getView("customer_name");
        final ListView customer_list = getView("customer_list");

        final ImageView add_customer = getView("add_customer");

        add_customer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    MyIntent.start(AddCustomer.class);
            }
        });

        customer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() != 0)
                {
                    final ArrayList<Customer> newIteam = new ArrayList<>();
                    for(Customer i : AllCustomer)
                    {
                        if((i.customer_name!=null && i.customer_name.contains(s)) || (i.customer_phone!=null && i.customer_phone.contains(s)))
                        {
                            newIteam.add(i);
                        }
                    }
                    ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.customer_list_data_row, newIteam, "customerList", 0, newIteam.size(),true);
                }
                else
                {
                    ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.customer_list_data_row, AllCustomer, "customerList", 0, AllCustomer.size(),true);
                }
            }
        });

        ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.customer_list_data_row, AllCustomer, "customerList", 0, AllCustomer.size(),true);
    }
    public void customerList(final ViewData data)
    {
        setFontFromView(getView("root_view",data.view));
        Customer a = (Customer) data.object;
        setView(data.object,data.view);
        getView("current_balance",NumberEngToBng(String.valueOf(a.current_balance)),data.view);

        if(a.current_balance < 0.00)
        {

           TextView tx = getView("current_balance",data.view);
           tx.setTextColor(Color.RED);
           tx.setText("বাকিঃ "+NumberEngToBng(String.valueOf(a.current_balance*-1)));
        }
        else if(a.current_balance > 0.00)
        {
            TextView tx = getView("current_balance",data.view);
            tx.setTextColor(Color.parseColor("#12584D"));
            tx.setText("অগ্রিমঃ "+NumberEngToBng(String.valueOf(a.current_balance)));
        }
        else
        {
            TextView tx = getView("current_balance",data.view);
            tx.setTextColor(Color.BLACK);
        }

        LinearLayout root_view = getView("root_view",data.view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

        root_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(CustomerDetailsInformation.class,data.object);
            }
        });
    }
}
