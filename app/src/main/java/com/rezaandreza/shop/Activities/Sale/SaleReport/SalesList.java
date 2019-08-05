package com.rezaandreza.shop.Activities.Sale.SaleReport;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;

public class SalesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ListView iteam_list = (ListView) findViewById(R.id.iteam_list);
        System.out.println("here: "+(new SalesInvoice()).selectAll().size());
        ScrollListView.loadListView(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.sale_salereport_saleslist_iteam, (new SalesInvoice()).selectAll(), "productListShow", 0, (new SalesInvoice()).selectAll().size(),true);

    }

    public void productListShow(final ViewData data)
    {
        setView(data.object,data.view);
        LinearLayout layout_holder = (LinearLayout) data.view.findViewById(R.id.layout_holder);
        layout_holder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(SalesDetials.class,data.object);
            }
        });
    }
}
