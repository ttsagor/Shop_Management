package com.rezaandreza.shop.Activities.Registration.PinGenerate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rezaandreza.shop.Activities.Registration.Registration;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.MainActivity;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.Service.BaseDataService;

import java.util.ArrayList;

import static com.rezaandreza.shop.Configuration.APIPaths.pin_generate_submit_api;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class PinGenerate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_generate);
        User cuser = new User();
        if(cuser.selectAll().size()>0) {
            final User user = (User) cuser.selectAll().get(0);
            if(user.active_status ==2)
            {
                setFontFromView(getView("root_view"));
                setView(user);
                final Button btn = getView("btn_submit");

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        EditText user_password = getView("user_password");
                        EditText re_user_password = getView("re_user_password");
                        if(user_password.getText().toString().length()==4 && user_password.getText().toString().equals(re_user_password.getText().toString()))
                        {
                            user.user_password = user_password.getText().toString();
                            BaseDataService m = new BaseDataService();
                            m.url = pin_generate_submit_api;
                            m.outputToModel = new User();
                            m.parameterdataClass = user;
                            m.getDataFromURLModel(Season.applicationContext,"dataDownload");
                            btn.setEnabled(false);
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
                finish();
            }
        }
        else
        {
            MyIntent.start(Registration.class);
            finish();
        }
    }

    public void dataDownload(ArrayList<Object> data)
    {
        try {
            final User user = (User) data.get(0);
            if(user.active_status==0)
            {
                Toast.makeText(Season.applicationContext, "User not found", Toast.LENGTH_SHORT).show();
            }
            else if(user.active_status==3)
            {
                user.update();
                MyIntent.start(MainActivity.class);
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
