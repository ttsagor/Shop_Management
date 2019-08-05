package com.rezaandreza.shop.Activities.Sale.SaleReport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.rezaandreza.shop.Activities.Sale.POS.IteamModel;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.System.Service.BaseDataService.dataToModel;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;

public class SalesDetials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detials);
        SalesInvoice salesInvoice =(SalesInvoice) MyIntent.getIntentObject(getIntent(),new SalesInvoice());
        ArrayList<Object> iteamList = dataToModel(salesInvoice.iteam_list, new IteamModel());
        setView(salesInvoice);
        ScrollListView.loadListViewUpdateHeight(Season.applicationContext, (ListView) findViewById(R.id.iteam_list), R.layout.sale_pos_popup_iteam_list, iteamList, "productListShow", 0, iteamList.size(),true);
    }
    public void productListShow(final ViewData data)
    {
        setView(data.object,data.view);
    }
}
