package com.rezaandreza.shop.Activities.Dashboard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.rezaandreza.shop.Activities.Sale.POS.POS;
import com.rezaandreza.shop.Activities.Sale.POSPriceWise.POSPriceWise;
import com.rezaandreza.shop.Activities.Sale.POS_Drawer.POS_Drawer_Ac;
import com.rezaandreza.shop.Activities.Sale.SaleReport.SalesList;
import com.rezaandreza.shop.Configuration.Season;
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
        setView(user,navigationView.getHeaderView(0));
        setFontFromView(getView("root_view"));

        Button sale = getView("sale");
        //Button sale_price_wise = getView("sale_price_wise");
        //Button salelist = getView("salelist");
        sale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(POS_Drawer_Ac.class);
            }
        });

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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
