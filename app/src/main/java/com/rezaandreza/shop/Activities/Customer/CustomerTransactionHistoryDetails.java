package com.rezaandreza.shop.Activities.Customer;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Activities.Sale.POS.IteamModel;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.Iteam;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;

import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.Intent.MyIntent.getIntentObject;
import static com.rezaandreza.shop.System.Service.BaseDataService.dataToModel;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class CustomerTransactionHistoryDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction_history_details);
        setFontFromView(getView("root_view"));
        Footer.footerMenu(getView("root_view"),this);
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));

        final SalesInvoice c = (SalesInvoice) getIntentObject(getIntent(),new SalesInvoice());
        setView(c);

        TextView transaction_date = getView("transaction_date",NumberEngToBng(c.sale_datetime.split(" ")[0]));
        TextView transaction_time = getView("transaction_time",NumberEngToBng(c.sale_datetime.split(" ")[1]));
        getView("total_amount",NumberEngToBng(c.total_amount));
        getView("total_quatity",NumberEngToBng(c.total_quatity));
        ListView iteam_added = getView("iteam_added");
        ArrayList<Object> iteamList = dataToModel(c.iteam_list, new Iteam());

        ScrollListView.loadListView(Season.applicationContext, iteam_added, R.layout.customer_transaction_history_details_row, iteamList, "productListShow", 0, iteamList.size(),true);


    }
    public void productListShow(final ViewData data)
    {
        LinearLayout root_view = getView("root_view",data.view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        setView(data.object,data.view);
    }
}
