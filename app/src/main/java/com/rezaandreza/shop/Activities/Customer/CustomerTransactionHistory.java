package com.rezaandreza.shop.Activities.Customer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.Intent.MyIntent.getIntentObject;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class CustomerTransactionHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction_history);
        setFontFromView(getView("root_view"));
        Footer.footerMenu(getView("root_view"),this);
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        final Customer c = (Customer) getIntentObject(getIntent(),new Customer());
        setView(c);

        EditText date_end = getView("date_end",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        EditText date_start = getView("date_start",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        EditText search_edit = getView("search_edit");
        ListView transaction_history = getView("transaction_history");
        final ArrayList<SalesInvoice> AllCustomer = new ArrayList<>();

        for(Object o : (new SalesInvoice()).selectCon("upper(customer_name)='"+c.customer_name.trim().toUpperCase()+"'"))
        {
            AllCustomer.add((SalesInvoice) o);
        }
        ScrollListView.loadListView(Season.applicationContext, transaction_history, R.layout.customer_transaction_history_row, AllCustomer, "transation_list", 0, AllCustomer.size(),true);


    }
    public void transation_list(final ViewData data)
    {
        setFontFromView(getView("root_view",data.view));
        setView(data.object,data.view);
        LinearLayout root_view = getView("root_view",data.view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#ECECEC"));
        }

        root_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(CustomerTransactionHistoryDetails.class,data.object);
            }
        });
    }
}
