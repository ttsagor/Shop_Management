package com.rezaandreza.shop.Activities.Registration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rezaandreza.shop.Activities.Dashboard.Dashboard;
import com.rezaandreza.shop.Activities.Registration.NumberVerification.NumberVerification;
import com.rezaandreza.shop.Activities.Registration.PinGenerate.PinGenerate;
import com.rezaandreza.shop.Activities.Registration.Signup.Signup;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_resigtration);

        User user = new User();
        if(user.selectAll().size()>0)
        {
            user = (User) user.selectAll().get(0);
            if(user.active_status ==0)
            {
                MyIntent.start(Signup.class);
                finish();
            }
            else if(user.active_status ==1)
            {
                MyIntent.start(NumberVerification.class);
                finish();
            }
            else if(user.active_status ==2)
            {
                MyIntent.start(PinGenerate.class);
                finish();
            }
        }
    }
}
