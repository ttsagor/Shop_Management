package com.rezaandreza.shop.Activities.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rezaandreza.shop.Activities.Customer.AddCustomer;
import com.rezaandreza.shop.Activities.Customer.CustomerList;
import com.rezaandreza.shop.Activities.Sale.POS.POS;
import com.rezaandreza.shop.Activities.Sale.POSPriceWise.POSPriceWise;
import com.rezaandreza.shop.Activities.Sale.POS_Drawer.POS_Drawer_Ac;
import com.rezaandreza.shop.Activities.Sale.SaleReport.SalesList;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Application.NavMenu;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.Intent.MyIntent;

import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class DashboardDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeasonNoActionBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        User user = new User();
        user = (User) user.selectAll().get(0);
        setView(user);

        setFontFromView(getView("root_view"));

        Button sale = getView("sale");
        //Button sale_price_wise = getView("sale_price_wise");
        //Button salelist = getView("salelist");
        sale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(POS_Drawer_Ac.class);
            }
        });


        menu_setup(navigationView);



       /* salelist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(SalesList.class);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        menu_action(item,Season.applicationContext);
        return true;
    }

    public static void menu_action(MenuItem item,Context context)
    {
        int id = item.getItemId();

        if (id == R.id.home) {
            MyIntent.start(DashboardDrawer.class);
        }else if (id == R.id.sale_list) {
            MyIntent.start(SalesList.class);
        } else if (id == R.id.customer_list) {
            MyIntent.start(CustomerList.class);
        } else if (id == R.id.customer_add) {
            MyIntent.start(AddCustomer.class);
        } /*else if (id == R.id.product_list) {

        } else if (id == R.id.product_add) {

        } else if (id == R.id.save_list) {

        }
        else if (id == R.id.settings) {

        }*/
        try {
            DrawerLayout drawer = (DrawerLayout) ((Activity) context).findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }catch (Exception e){}
    }

    public static void menu_setup(NavigationView navigationView)
    {
        setFontFromView(navigationView.getHeaderView(0));
        SalesInvoice si = new SalesInvoice();
        double total_amount = si.sum("total_amount","1=1");
        double due_amount = si.sum("due_amount","1=1");

        NavMenu nm = new NavMenu(total_amount,total_amount - due_amount,due_amount);

        setView(nm,navigationView.getHeaderView(0));
    }
}
