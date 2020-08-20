package com.rezaandreza.shop.Activities.Sale.SaleReport;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.NumberConverter;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Helper.TypeCasting;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class SalesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        setFontFromView(getView("root_view"));
        ListView iteam_list = (ListView) findViewById(R.id.iteam_list);
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        ScrollListView.loadListView(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.sale_salereport_saleslist_iteam, (new SalesInvoice()).selectAll(), "productListShow", 0, (new SalesInvoice()).selectAll().size(),true);
        Footer.footerMenu(getView("root_view"),this);

        final EditText search_para = getView("search_para");

        final ArrayList<SalesInvoice> AllSalesInvoice = new ArrayList<>();

        for(Object o : (new SalesInvoice()).selectAll())
        {
            AllSalesInvoice.add((SalesInvoice) o);
        }

        search_para.addTextChangedListener(new TextWatcher() {
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
                    final ArrayList<SalesInvoice> newIteam = new ArrayList<>();
                    for(SalesInvoice i : AllSalesInvoice)
                    {
                        if((i.customer_name!=null && i.customer_name.contains(s)) || (i.sale_datetime!=null && i.sale_category.contains(s)))
                        {
                            newIteam.add(i);
                        }
                    }
                    ScrollListView.loadListView(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.sale_salereport_saleslist_iteam, newIteam, "productListShow", 0, (new SalesInvoice()).selectAll().size(),true);

                }
                else
                {
                    ScrollListView.loadListView(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.sale_salereport_saleslist_iteam, (new SalesInvoice()).selectAll(), "productListShow", 0, (new SalesInvoice()).selectAll().size(),true);
                }
            }
        });
    }

    public void productListShow(final ViewData data)
    {
        SalesInvoice salesInvoice = (SalesInvoice) data.object;
        setView(data.object,data.view);
        LinearLayout root_view = getView("root_view",data.view);
        TextView total_quatity = getView("total_quatity",data.view);
        TextView total_amount = getView("total_amount",data.view);
        TextView customer_name = getView("customer_name",data.view);
        TextView sale_datetime = getView("sale_datetime",data.view);

        sale_datetime.setText(NumberConverter.NumberEngToBng(salesInvoice.sale_datetime));
        if(salesInvoice.customer_name.trim().equals(""))
        {
            customer_name.setText("নগদ");
        }
        total_amount.setText(NumberConverter.NumberEngToBng(salesInvoice.total_amount)+" টাকা/");

        total_quatity.setText(  NumberConverter.NumberEngToBng(String.format("%.0f",salesInvoice.total_quatity))+" পদ");




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
                MyIntent.start(SalesDetials.class,data.object);
            }
        });
    }
}
