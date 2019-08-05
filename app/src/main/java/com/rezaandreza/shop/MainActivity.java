package com.rezaandreza.shop;
//this isasdasdasdasd
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import com.rezaandreza.shop.Activities.Customer.AddCustomer;
import com.rezaandreza.shop.Activities.Customer.CustomerList;
import com.rezaandreza.shop.Activities.Dashboard.Dashboard;
import com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer;
import com.rezaandreza.shop.Activities.Registration.Registration;
import com.rezaandreza.shop.Activities.Registration.Signup.Signup;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.Iteam;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.Service.BaseDataService;
import com.rezaandreza.shop.System.UI.LayoutDataRecevier;

import static com.rezaandreza.shop.Configuration.APIPaths.login_api;
import static com.rezaandreza.shop.Helper.Debug.print;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        Season.__InitiatSeason(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeason(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User();

        if(user.selectAll().size()>0)
        {
            user = (User) user.selectAll().get(0);
            if(user.active_status ==3) {
                MyIntent.start(DashboardDrawer.class);
                //MyIntent.start(CustomerList.class);
                finish();
            }else {
                MyIntent.start(Registration.class);
                finish();
            }
        }

        Button btn_login = getView("btn_login");
        Button btn_register = getView("btn_register");

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User user = (User) LayoutDataRecevier.getViewData(new User());
                BaseDataService m = new BaseDataService();
                m.url = login_api;
                m.outputToModel = new User();
                m.parameterdataClass = user;
                m.getDataFromURLModel(Season.applicationContext,"downloadData");
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MyIntent.start(Signup.class);
            }
        });
       /* BaseDataService m = new BaseDataService();
        m.url = registration_api;
        m.outputToModel = new AreaCode();
        m.parameter.put("parent_id","0");
        //m.parameter.put("t","batman");
        m.getDataFromURLModel(this,"downloadData");*/
        //Intent intent = new Intent(MainActivity.this, Signup.class);
       // intent.putExtra("keyName","value");
       // startActivity(intent);
        //finish();
    }

    public void downloadData(ArrayList<Object> data)
    {
        Iteam it1 = new Iteam();
        Iteam it2 = new Iteam();
        Iteam it3 = new Iteam();
        Iteam it4 = new Iteam();
        Iteam it5 = new Iteam();
        Iteam it6 = new Iteam();
        Iteam it7 = new Iteam();
        Iteam it8 = new Iteam();
        Iteam it9 = new Iteam();
        Iteam it10 = new Iteam();
        Iteam it11 = new Iteam();
        Iteam it12 = new Iteam();
        Iteam it13 = new Iteam();
        Iteam it14 = new Iteam();
        Iteam it15 = new Iteam();

        it1.iteam_name_bng="চাল";
        it2.iteam_name_bng="পোলার চাল";
        it3.iteam_name_bng="ডাল";
        it4.iteam_name_bng="মুগ ডাল";
        it5.iteam_name_bng="বুটের ডাল";
        it6.iteam_name_bng="সোলা ডাল";
        it7.iteam_name_bng="লবণ (প্যাকেট)";
        it8.iteam_name_bng="লবণ (খোলা)";
        it9.iteam_name_bng="তেল	tel";
        it10.iteam_name_bng="সরিষা তেল";
        it11.iteam_name_bng="নারিকেল তেল";
        it12.iteam_name_bng="আটা";
        it13.iteam_name_bng="ময়দা";
        it14.iteam_name_bng="সুজি";
        it15.iteam_name_bng="কয়েল";

        it1.iteam_name_eng="chal";
        it2.iteam_name_eng="polar chal";
        it3.iteam_name_eng="dal";
        it4.iteam_name_eng="mogdal";
        it5.iteam_name_eng="boter dal";
        it6.iteam_name_eng="sola dal";
        it7.iteam_name_eng="lobon";
        it8.iteam_name_eng="lobon";
        it9.iteam_name_eng="tel";
        it10.iteam_name_eng="sorisha tel";
        it11.iteam_name_eng="narikel tel";
        it12.iteam_name_eng="ata";
        it13.iteam_name_eng="moyda";
        it14.iteam_name_eng="soji";
        it15.iteam_name_eng="koyel";

        it1.iteam_unit="কেজি";
        it2.iteam_unit="কেজি";
        it3.iteam_unit="কেজি";
        it4.iteam_unit="কেজি";
        it5.iteam_unit="কেজি";
        it6.iteam_unit="কেজি";
        it7.iteam_unit="কেজি";
        it8.iteam_unit="কেজি";
        it9.iteam_unit="কেজি";
        it10.iteam_unit="কেজি";
        it11.iteam_unit="কেজি";
        it12.iteam_unit="কেজি";
        it13.iteam_unit="কেজি";
        it14.iteam_unit="কেজি";
        it15.iteam_unit="কেজি";

        it1.iteam_popular_quantity="1,5,10,20";
        it2.iteam_popular_quantity="1,5,10,20";
        it3.iteam_popular_quantity="1,5,10,20";
        it4.iteam_popular_quantity="1,5,10,20";
        it5.iteam_popular_quantity="1,5,10,20";
        it6.iteam_popular_quantity="1,5,10,20";
        it7.iteam_popular_quantity="1,5,10,20";
        it8.iteam_popular_quantity="1,5,10,20";
        it9.iteam_popular_quantity="1,5,10,20";
        it10.iteam_popular_quantity="0.1,0.5,0.8";
        it11.iteam_popular_quantity="0.1,0.5,0.8";
        it12.iteam_popular_quantity="0.1,0.5,0.8";
        it13.iteam_popular_quantity="0.1,0.5,0.8";
        it14.iteam_popular_quantity="0.1,0.5,0.8";
        it15.iteam_popular_quantity="0.1,0.5,0.8";


        it1.insert();
        it2.insert();
        it3.insert();
        it4.insert();
        it5.insert();
        it6.insert();
        it7.insert();
        it8.insert();
        it9.insert();
        it10.insert();
        it11.insert();
        it12.insert();
        it13.insert();
        it14.insert();
        it15.insert();

        String[] names =   {"crestedponens","aversionsecurity","bosundogwatch","allianzrequest","enjoindelete","predictstellar","jerseywomens","toolthermal","poppysmicduh","deaffreeway","neuroticritzy","tootiedisk","hiataljulia","knabstruphombre","collisionelidir","shiningrefurbish","returnboller","greaseproofeve","tastetasha","underfedvigilante","blundererpaddling","snickerswonga","parksilk","oblonglow","semicolonlunchroom","rustyluke","wazzedoutshine","flirtkissing","mumbogazump","whistoncelibate","mammothlinguists","duiskpacha","scandiumnosebag","slapstickpeggotty","shambrichness","mugwumpgorgeous","sudokufoundation","cimarroncork","gravyointment","approveentwine","redoasis","egfordstride","pullmallowford","babediploma","flinchsingle","choiceprissy","gyrusmajestic","shutsizzle","purchasequerulous","raftgit"};
        for(String name : names)
        {
            Customer c = new Customer();
            c.customer_name = name;
            c.customer_phone = "011111";
            c.insert();
        }

        try {
            User user = (User) data.get(0);
            if(user.active_status==0)
            {
                Toast.makeText(Season.applicationContext, "Username or Password not Exists", Toast.LENGTH_SHORT).show();
            }
            else if(user.active_status < 3)
            {
                Debug.print(user);
                Toast.makeText(Season.applicationContext, "Login Sucessful", Toast.LENGTH_SHORT).show();
                user.insert();
                MyIntent.start(Registration.class);
                finish();
            }
            else if(user.active_status==3)
            {
                Debug.print(user);
                Toast.makeText(Season.applicationContext, "Login Sucessful", Toast.LENGTH_SHORT).show();
                user.insert();
                MyIntent.start(DashboardDrawer.class);
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

        //Instructions.instructionCount=-1;


        //MyIntent.start(Signup.class);

        //finish();
        // AsyncTask mMyTask = new BaseImageDownloader(this,"downloadBG",logo_imgs).execute();
    }
}
