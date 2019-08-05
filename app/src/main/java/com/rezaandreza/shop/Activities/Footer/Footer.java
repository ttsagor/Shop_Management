package com.rezaandreza.shop.Activities.Footer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.rezaandreza.shop.Activities.Customer.CustomerList;
import com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer;
import com.rezaandreza.shop.Activities.Sale.SaleReport.SalesList;
import com.rezaandreza.shop.System.Intent.MyIntent;

import static com.rezaandreza.shop.System.UI.ViewModifier.getView;

public class Footer {
    public static void footerMenu(final View v, final Context c)
    {
        Button footer_home = getView("footer_home",v);
        Button footer_product_list = getView("footer_product_list",v);
        Button footer_customer_list = getView("footer_customer_list",v);
        Button footer_accounting = getView("footer_accounting",v);

        footer_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(DashboardDrawer.class);
                ((Activity) c ).finish();
            }
        });

        footer_product_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(DashboardDrawer.class);
                ((Activity) c ).finish();
            }
        });

        footer_customer_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(CustomerList.class);
                ((Activity) c ).finish();
            }
        });

        footer_accounting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(SalesList.class);
                ((Activity) c ).finish();
            }
        });
    }
}
