package com.rezaandreza.shop.Activities.Registration.NumberVerification;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rezaandreza.shop.Activities.Registration.Registration;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.Service.BaseDataService;

import java.util.ArrayList;

import static com.rezaandreza.shop.Configuration.APIPaths.number_verify_api;
import static com.rezaandreza.shop.Configuration.APIPaths.number_verify_submit_api;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class NumberVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification);

        User cuser = new User();
        if(cuser.selectAll().size()>0) {
            final User user = (User) cuser.selectAll().get(0);
            if(user.active_status ==1)
            {
                setFontFromView(getView("root_view"));
                setView(user);
                final Button btn = getView("btn_verify");

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        EditText owner_phone = getView("owner_phone");
                        if(owner_phone.getText().toString().length()==11)
                        {
                            user.owner_phone = owner_phone.getText().toString();
                            BaseDataService m = new BaseDataService();
                            m.url = number_verify_api;
                            m.outputToModel = new User();
                            m.parameterdataClass = user;
                            m.getDataFromURLModel(Season.applicationContext,"downloadData");
                            btn.setVisibility(View.GONE);
                            owner_phone.setEnabled(false);
                        }
                        else
                        {
                            Toast.makeText(Season.applicationContext, "Please fill up form", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else
            {
                MyIntent.start(Registration.class);
            }
        }
        else
        {
            MyIntent.start(Registration.class);
        }
    }

    public void downloadData(ArrayList<Object> data)
    {
        try {
            final User user = (User) data.get(0);
            if(user.active_status==0)
            {
                Toast.makeText(Season.applicationContext, "User not found", Toast.LENGTH_SHORT).show();
            }
            else if(user.active_status==1)
            {
                final LinearLayout otp_holder = getView("otp_holder");
                final Button btn_submit = getView("btn_submit");
                otp_holder.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        EditText owner_phone = getView("owner_phone");
                        EditText registration_otp = getView("registration_otp");
                        if(owner_phone.getText().toString().length()==11 && registration_otp.getText().toString().length()==4)
                        {
                            user.owner_phone = owner_phone.getText().toString();
                            user.registration_otp = registration_otp.getText().toString();
                            BaseDataService m = new BaseDataService();
                            m.url = number_verify_submit_api;
                            m.outputToModel = new User();
                            m.parameterdataClass = user;
                            m.getDataFromURLModel(Season.applicationContext,"finaldataDownload");
                        }
                        else
                        {
                            Toast.makeText(Season.applicationContext, "Please fill up form", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
        }

    }

    public void finaldataDownload(ArrayList<Object> data)
    {
        try {
            final User user = (User) data.get(0);
            if(user.active_status==0)
            {
                Toast.makeText(Season.applicationContext, "User not found", Toast.LENGTH_SHORT).show();
            }
            else if(user.active_status==2)
            {
                user.update();
                MyIntent.start(Registration.class);
                finish();
            }
            else
            {
                Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(Season.applicationContext, "Some thing went wrong. please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
