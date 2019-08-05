package com.rezaandreza.shop.Activities.Dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rezaandreza.shop.Activities.Sale.POS.POS;
import com.rezaandreza.shop.Activities.Sale.SaleReport.SalesList;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;

import static com.rezaandreza.shop.System.UI.ViewModifier.getView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button sale = getView("sale");
        Button salelist = getView("salelist");
        sale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(POS.class);
            }
        });
        salelist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(SalesList.class);
            }
        });
    }
}
