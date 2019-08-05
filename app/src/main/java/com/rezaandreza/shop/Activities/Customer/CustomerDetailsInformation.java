package com.rezaandreza.shop.Activities.Customer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Intent.MyIntent;

import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.System.Intent.MyIntent.getIntentObject;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class CustomerDetailsInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_information);
        setFontFromView(getView("root_view"));
        Footer.footerMenu(getView("root_view"),this);
        getView("registared_date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));

        final Customer c = (Customer) getIntentObject(getIntent(),new Customer());
        setView(c);


        Button send_sms_btn = getView("send_sms_btn");
        Button details_transaction_btn = getView("details_transaction_btn");

        send_sms_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", c.customer_phone);
                startActivity(smsIntent);
            }
        });

        details_transaction_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(CustomerTransactionHistory.class,c);
            }
        });
    }
}
