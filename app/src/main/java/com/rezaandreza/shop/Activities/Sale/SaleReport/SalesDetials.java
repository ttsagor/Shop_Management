package com.rezaandreza.shop.Activities.Sale.SaleReport;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Activities.Sale.POS.IteamModel;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.Iteam;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Helper.TypeCasting;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.Service.BaseDataService.dataToModel;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class SalesDetials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detials);
        setFontFromView(getView("root_view"));
        Footer.footerMenu(getView("root_view"),this);
        SalesInvoice salesInvoice =(SalesInvoice) MyIntent.getIntentObject(getIntent(),new SalesInvoice());
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));

        ArrayList<Object> iteamList = dataToModel(salesInvoice.iteam_list, new Iteam());
        setView(salesInvoice);

        getView("total_amount", NumberEngToBng(salesInvoice.total_amount));
        getView("total_quatity", NumberEngToBng(String.format("%.0f",salesInvoice.total_quatity)));

        if(salesInvoice.sale_category.equals("due"))
        {
            getView("sale_category","বাকি");
        }
        else
        {
            getView("sale_category","নগদ");
        }
        TextView transaction_date = getView("transaction_date",NumberEngToBng(salesInvoice.sale_datetime.split(" ")[0]));
        TextView transaction_time = getView("transaction_time",NumberEngToBng(salesInvoice.sale_datetime.split(" ")[1]));
        //Debug.print(iteamList);
        ScrollListView.loadListView(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.customer_transaction_history_details_row, iteamList, "productListShow", 0, iteamList.size(),true);
    }
    public void productListShow(final ViewData data)
    {
        LinearLayout root_view = getView("root_view",data.view);
        Iteam i = (Iteam) data.object;
        setView(data.object,data.view);
        getView("iteam_quantity",NumberEngToBng(i.iteam_quantity),data.view);
        getView("iteam_price",NumberEngToBng(i.iteam_price),data.view);


        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

    }
}
