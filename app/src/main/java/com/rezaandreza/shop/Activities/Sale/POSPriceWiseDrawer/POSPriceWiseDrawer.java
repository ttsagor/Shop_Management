package com.rezaandreza.shop.Activities.Sale.POSPriceWiseDrawer;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rezaandreza.shop.Activities.Footer.Footer;
import com.rezaandreza.shop.Activities.Sale.POS.POS;
import com.rezaandreza.shop.Activities.Sale.POS_Drawer.POS_Drawer_Ac;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.Helper.Debug;
import com.rezaandreza.shop.Model.Database.Customer;
import com.rezaandreza.shop.Model.Database.Iteam;
import com.rezaandreza.shop.Model.Database.SalesInvoice;
import com.rezaandreza.shop.Model.Database.User;
import com.rezaandreza.shop.R;
import com.rezaandreza.shop.System.DateTime.DateTimeCalculation;
import com.rezaandreza.shop.System.Helper.TypeCasting;
import com.rezaandreza.shop.System.Intent.MyIntent;
import com.rezaandreza.shop.System.PopupWindow.MyPopupView;
import com.rezaandreza.shop.System.PopupWindow.PopupViewData;
import com.rezaandreza.shop.System.ScrollView.ScrollListView;
import com.rezaandreza.shop.System.ScrollView.ViewData;
import com.rezaandreza.shop.System.UIComponents.EdittextHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import static com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer.menu_action;
import static com.rezaandreza.shop.Activities.Dashboard.DashboardDrawer.menu_setup;
import static com.rezaandreza.shop.Helper.NumberConverter.NumberBngToEng;
import static com.rezaandreza.shop.Helper.NumberConverter.NumberEngToBng;
import static com.rezaandreza.shop.Model.Database.CustomerActivity.invoiceToActivity;
import static com.rezaandreza.shop.System.Softkeyboard.SoftkeyboardHelper.hideKeyboard;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.LayoutDataRecevier.getViewData;
import static com.rezaandreza.shop.System.UI.ViewColor.setColorView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class POSPriceWiseDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Iteam> iteamList = new ArrayList<>();
    ArrayList<Iteam> AlliteamList = new ArrayList<>();
    ArrayList<Customer> AllCustomer = new ArrayList<>();
    private @Nullable
    BigDecimal value;
    Iteam cIteam = null;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Iteam selectedIteamPriceQuantity = new Iteam();
    SalesInvoice finalSaleInvoice = new SalesInvoice();
    Boolean weight_flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeasonAction(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posprice_wise_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        menu_setup(navigationView);

        setFontFromView(getView("root_view"));


        AlliteamList = new ArrayList<>();
        AllCustomer = new ArrayList<>();
        for(Object o : (new Iteam()).selectAll())
        {
            AlliteamList.add((Iteam) o);
        }

        for(Object o : (new Customer()).selectAll())
        {
            AllCustomer.add((Customer) o);
        }

        final LinearLayout btn_submit = getView("btn_cash");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createInvoice("cash");
            }
        });

        final TextView date = getView("date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        final ArrayList<Iteam> newIteam = new ArrayList<>();
        final Button close_tab = (Button) findViewById(R.id.btn_submit);
        final LinearLayout price_wise_sale = (LinearLayout) findViewById(R.id.price_wise_sale);
        //final ImageView add_iteam = (ImageView) data.popupView.findViewById(R.id.add_iteam);

        //final Button submit_btn = (Button) data.popupView.findViewById(R.id.submit_btn);
        //final Button submit_remove = (Button) data.popupView.findViewById(R.id.submit_remove);

        final ListView iteam_list = (ListView) findViewById(R.id.iteam_list);
        final ListView iteam_added = (ListView) findViewById(R.id.iteam_added);

        final EditText iteam_name = (EditText) findViewById(R.id.iteam_name);
        //final EditText iteam_unit = (EditText) data.popupView.findViewById(R.id.iteam_unit);

        //final TextView iteam_list_name = (TextView) data.popupView.findViewById(R.id.iteam_list_name);

        setFontFromView(getView("root_view"));
        iteam_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    iteam_list.getLayoutParams().height = 0;
                }
            }
        });

        iteam_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {

                try {
                    iteam_list.invalidateViews();
                    iteam_list.removeAllViews();
                }catch (Exception e){}
                if(s.length() != 0)
                {
                    cIteam = null;
                    newIteam.clear();
                    for(Iteam i : AlliteamList)
                    {
                        if((i.iteam_name_bng!=null && i.iteam_name_bng.contains(s)) || (i.iteam_name_eng!=null && i.iteam_name_eng.contains(s)))
                        {
                            newIteam.add(i);
                        }
                    }
                    iteam_list.getLayoutParams().height = 1000;
                    ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_list, newIteam, "productListShow", 0, AlliteamList.size(),false);
                }
                else
                {
                    iteam_list.getLayoutParams().height = 0;
                    //ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_list, AlliteamList, "productListShow", 0, AlliteamList.size(),false);
                }
            }
        });

        ScrollListView.loadListView(Season.applicationContext, iteam_added, R.layout.sale_pos_popup_iteam_quantity_list, iteamList, "productListQuantityShow", 0, AlliteamList.size(),false);

        price_wise_sale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(POS_Drawer_Ac.class);
                finish();
            }
        });


        final ImageView product_weight = (ImageView) findViewById(R.id.product_weight);


        product_weight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(weight_flag)
                {
                    weight_flag = false;
                    product_weight.setBackgroundResource(R.drawable.weight_inactive);
                }
                else
                {
                    weight_flag = true;
                    product_weight.setBackgroundResource(R.drawable.weight_active);
                }

            }
        });


    }


    public void productListShow(final ViewData data)
    {
        setView(data.object,data.view);
        final Iteam i = (Iteam) data.object;

        String[] quantities = i.iteam_popular_quantity.split(",");
        final LinearLayout ll = data.view.findViewById(R.id.quantity_container);
        final LinearLayout root_view_iteam = data.view.findViewById(R.id.root_view_iteam);
        final LinearLayout txt_layout = data.view.findViewById(R.id.txt_layout);
        ll.removeAllViews();
        if(data.position%2==0)
        {
            root_view_iteam.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view_iteam.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        if(iteamList.contains(i))
        {
            i.iteam_quantity = iteamList.get(iteamList.indexOf(i)).iteam_quantity;
        }
        else
        {
            i.iteam_quantity ="0";
        }
        txt_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                selectedIteamPriceQuantity = i;
                selectedIteamPriceQuantity.iteam_quantity="";
                MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);

            }
        });

        for (final String quantity :quantities)
        {
            final Button btn1 = new Button(data.context);
            btn1.setText(NumberEngToBng(quantity+" "+i.iteam_unit));
            //btn1.setLayoutParams()new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0); //left, top, right, bottom
            btn1.setLayoutParams(params);
            if(i.iteam_quantity.equals(quantity))
            {
                btn1.setBackgroundResource(R.drawable.pos_quantity_btn_selected);
                btn1.setTextColor(Color.parseColor("#2982a3"));
            }
            else
            {
                btn1.setBackgroundResource(R.drawable.pos_quantity_btn);
                btn1.setTextColor(Color.parseColor("#6e6e6e"));
            }
            ll.addView(btn1);
            final CheckBox c = getView("with_price",data.parentView);
            btn1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(iteamList.contains(i))
                    {
                        //if(c.isChecked())
                        {
                            selectedIteamPriceQuantity = i;
                            selectedIteamPriceQuantity.iteam_quantity = quantity;
                            MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);
                        }
                        /*else
                        {
                            int p = iteamList.indexOf(i);
                            i.iteam_quantity = quantity;
                            iteamList.set(p,i);
                        }*/

                    }
                    else
                    {
                        // if(c.isChecked())
                        {
                            selectedIteamPriceQuantity = i;
                            selectedIteamPriceQuantity.iteam_quantity = quantity;
                            MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);
                        }
                        /*else
                        {
                           // i.iteam_quantity = quantity;
                           // iteamList.add(i);
                        }*/
                    }
                    for(int index = 0; index<((ViewGroup)ll).getChildCount(); ++index) {
                        System.out.println(i);
                        View nextChild = ((ViewGroup)ll).getChildAt(index);
                        if(nextChild instanceof Button)
                        {
                            Button b = (Button) nextChild;
                            b.setBackgroundResource(R.drawable.pos_quantity_btn);
                            b.setTextColor(Color.parseColor("#6e6e6e"));
                        }
                    }
                    btn1.setBackgroundResource(R.drawable.pos_quantity_btn_selected);
                    btn1.setTextColor(Color.parseColor("#2982a3"));
                    //btn.setText("ইনপুট");
                    calculateUpdateQuantityPrice(data.parentView);
                    ((EditText) getView("iteam_name",data.parentView)).setText("");
                    ScrollListView.loadListView(Season.applicationContext,(ListView) getView("iteam_added",data.parentView), R.layout.sale_pos_popup_iteam_quantity_list, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);
                }
            });
        }
        setFontFromView(getView("quantity_container",data.view));
        /*final CheckBox c = getView("with_price",data.parentView);*/
        final LinearLayout quantity_container = getView("quantity_container",data.view);
        if(!weight_flag)
        {
            quantity_container.setVisibility(View.GONE);
        }
        else
        {
            quantity_container.setVisibility(View.VISIBLE);
        }

    }
    public void priceQuantityPopup(final PopupViewData data)
    {
        try {
            final Button submit_btn = getView("submit_btn",data.popupView);

            final LinearLayout quantity_holder = getView("quantity_holder",data.popupView);
            final LinearLayout iteam_quantity_holder = getView("iteam_quantity_holder",data.popupView);
            final LinearLayout price_holder = getView("price_holder",data.popupView);
            final EditText iteam_quantity = getView("iteam_quantity",data.popupView);
            final EditText iteam_price = getView("iteam_price",data.popupView);

            setView(selectedIteamPriceQuantity,data.popupView);
            setFontFromView(getView("root_view",data.popupView));

            if(weight_flag)
            {
                if(selectedIteamPriceQuantity.iteam_price.equals(""))
                {
                    iteam_price.setText("");
                }
                else
                {
                    iteam_price.setSelection(iteam_price.getText().length());
                }
                iteam_quantity.setEnabled(false);
                iteam_quantity_holder.setBackground(null);
                price_holder.setVisibility(View.VISIBLE);
                EdittextHelper.setFocus((EditText) getView("iteam_price",data.popupView));
                iteam_price.setSelectAllOnFocus(true);
                iteam_price.requestFocus();
            }
            else
            {
                //quantity_holder.setVisibility(View.VISIBLE);
                price_holder.setVisibility(View.GONE);
                EdittextHelper.setFocus((EditText) getView("iteam_quantity",data.popupView));
                iteam_quantity.setSelectAllOnFocus(true);
                iteam_quantity.requestFocus();
            }

            final ImageView close_tab = (ImageView) data.popupView.findViewById(R.id.close_tab);
            close_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.popupWindow.dismiss();
                }
            });

            submit_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(price_holder.getVisibility()==View.GONE)
                    {
                        price_holder.setVisibility(View.VISIBLE);
                        quantity_holder.setVisibility(View.GONE);
                        iteam_price.setSelectAllOnFocus(true);
                        iteam_price.requestFocus();
                    }
                    else {
                        hideKeyboard();
                        Iteam n = (Iteam) getViewData(new Iteam(), data.popupView);
                        Iteam i = selectedIteamPriceQuantity;
                        selectedIteamPriceQuantity.iteam_price = n.iteam_price;
                        selectedIteamPriceQuantity.iteam_quantity = n.iteam_quantity;

                        if (!(n.iteam_quantity.equals("") || n.iteam_quantity.equals("0"))) {
                            if (iteamList.contains(i)) {
                                int p = iteamList.indexOf(i);
                                iteamList.set(p, selectedIteamPriceQuantity);
                            } else {
                                iteamList.add(selectedIteamPriceQuantity);
                            }
                        } else {
                            selectedIteamPriceQuantity.iteam_quantity = "0";
                            if (iteamList.contains(i)) {
                                int p = iteamList.indexOf(i);
                                iteamList.set(p, selectedIteamPriceQuantity);
                            } else {
                                iteamList.add(selectedIteamPriceQuantity);
                            }
                        }
                        ListView l = (ListView) getView("iteam_list", data.parentView);
                        l.getLayoutParams().height = 0;
                        ((EditText) getView("iteam_name", data.parentView)).setText("");
                        data.popupWindow.dismiss();
                        ScrollListView.loadListView(Season.applicationContext, (ListView) getView("iteam_added", data.parentView), R.layout.sale_pos_popup_iteam_quantity_list, iteamList, "productListQuantityShow", 0, AlliteamList.size(), true, data.parentView);
                        calculateUpdateQuantityPrice(data.parentView);
                    }
                }
            });
        }
        catch (Exception e){System.out.println(e.getMessage());}
    }
    public void calculateUpdateQuantityPrice(View v)
    {
        double totalPrice =0.0;
        for(Iteam i : iteamList)
        {
            totalPrice+= TypeCasting.parseDouble(i.iteam_price);
        }
        System.out.println("price"+String.valueOf(totalPrice));
        System.out.println("price"+String.valueOf(iteamList.size()));
        getView("total_amount",NumberEngToBng(String.valueOf(totalPrice)));
        getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())));
    }

    public void productListQuantityShow(final ViewData data)
    {
        LinearLayout root_view = data.view.findViewById(R.id.root_view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        Iteam i = (Iteam) data.object;
        setView(data.object,data.view);
        if(i.iteam_quantity.equals("0"))
        {
            getView("iteam_quantity","-",data.view);
        }
        else
        {
            getView("iteam_quantity",NumberEngToBng(i.iteam_quantity),data.view);
        }
        getView("iteam_price",NumberEngToBng(i.iteam_price),data.view);
        ImageView remove_btn = getView("remove_btn",data.view);
        final EditText iteam_quantity = getView("iteam_quantity",data.view);

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iteamList.remove(data.position);
                ListView iteam_list = getView("iteam_added",data.parentView);
                ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_quantity_list, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);
                ScrollListView.loadListView(Season.applicationContext, (ListView) getView("iteam_list",data.parentView), R.layout.sale_pos_popup_iteam_list, AlliteamList, "productListShow", 0, AlliteamList.size(),false,data.parentView);
                ListView l = (ListView) getView("iteam_list",data.parentView);
                l.getLayoutParams().height=0;
                calculateUpdateQuantityPrice(data.parentView);
            }
        });
    }

    public void createInvoice(String type)
    {
        try {
            final TextView date = getView("date");
            finalSaleInvoice = (SalesInvoice) getViewData(new SalesInvoice());
            finalSaleInvoice.total_amount = TypeCasting.parseDouble(NumberBngToEng(((TextView) getView("total_amount")).getText().toString()));
            finalSaleInvoice.total_quatity = TypeCasting.parseDouble(NumberBngToEng(((TextView) getView("total_quatity")).getText().toString()));
            Gson g = new Gson();
            finalSaleInvoice.iteam_list = g.toJson(iteamList);
            finalSaleInvoice.sale_category = type;
            finalSaleInvoice.sale_datetime = DateTimeCalculation.getCurrentDateTime();
            /*if(NumberBngToEng(date.getText().toString()).equals(DateTimeCalculation.getCurrentDate()))
            {
                finalSaleInvoice.sale_datetime = DateTimeCalculation.getCurrentDateTime();
            }
            else
            {
                finalSaleInvoice.sale_datetime = NumberBngToEng(date.getText().toString()+" 00:00:01");
            }*/
            User u = new User();
            finalSaleInvoice.sale_by = ((User) u.selectAll().get(0)).user_name;
            finalSaleInvoice.status=1;
            if(finalSaleInvoice.total_amount>0.0) {
                MyPopupView.showPopupView(R.layout.sale_pos_popup_checkout, "getPopupdataCustomer");
            }
            else
            {
                Toast.makeText(Season.applicationContext, "Please complete entry", Toast.LENGTH_SHORT).show();
            }
            // i.insert();
            //Toast.makeText(Season.applicationContext, "Sale Successful", Toast.LENGTH_SHORT).show();
            //finish();
        }catch (Exception e)
        {
            Toast.makeText(Season.applicationContext, "Something wen wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void calculateCheckout(String amount,View data)
    {
        final EditText note_given = getView("note_given",data);
        final EditText customer_name = getView("customer_name",data);

        String amountOld = NumberBngToEng(note_given.getText().toString());
        if(!amount.equals(""))
        {
            if(amount.equals("0"))
            {
                getView("note_given", "০",data);
                finalSaleInvoice.note_given = "0";
            }
            else if(!amount.equals("custom"))
            {
                getView("note_given", NumberEngToBng(String.valueOf(TypeCasting.parseDouble(amountOld)+TypeCasting.parseDouble(amount))),data);
                finalSaleInvoice.note_given = String.valueOf(TypeCasting.parseDouble(amountOld)+TypeCasting.parseDouble(amount));
            }
            else if(amount.equals("custom"))
            {
                getView("note_given", NumberEngToBng(String.valueOf(finalSaleInvoice.total_amount)),data);
                finalSaleInvoice.note_given = String.valueOf(finalSaleInvoice.total_amount);
            }
        }
        calculateCheckoutViewUpdate(data);
    }
    public void finalSubmitView(final PopupViewData data)
    {
        final ImageView close_tab = (ImageView) data.popupView.findViewById(R.id.close_tab);
        setFontFromView(getView("root_view",data.popupView));
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.popupWindow.dismiss();
            }
        });

        setView(finalSaleInvoice,data.popupView);
        if(finalSaleInvoice.customer_name.equals(""))
        {
            TextView customer_name_txt = getView("customer_name_txt",data.popupView);
            customer_name_txt.setVisibility(View.GONE);
        }
        ListView iteam_added = (ListView) getView("iteam_added", data.popupView);
        if(iteamList.size()>0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iteam_added.getLayoutParams();
            lp.height = 100*iteamList.size();
            iteam_added.setLayoutParams(lp);
            ScrollListView.loadListView(Season.applicationContext, iteam_added, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShowFinal", 0, AlliteamList.size(), true, data.popupView);
        }
        else
        {
            LinearLayout list_holder = (LinearLayout) getView("list_holder", data.popupView);
            list_holder.setVisibility(View.GONE);
        }
        Button submit_btn = getView("submit_btn",data.popupView);

        if(finalSaleInvoice.advance_amount.equals("0"))
        {
            getView("advance_amount","-",data.popupView);
        }
        getView("total_quatity",NumberEngToBng(finalSaleInvoice.total_quatity),data.popupView);
        getView("total_amount",NumberEngToBng(finalSaleInvoice.total_amount),data.popupView);
        getView("note_given",NumberEngToBng(finalSaleInvoice.note_given),data.popupView);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalSaleInvoice.insert();
                finalSaleInvoice.invoice_id = finalSaleInvoice.max("invoice_id","");
                invoiceToActivity(finalSaleInvoice);
                data.popupWindow.dismiss();
                Toast.makeText(Season.applicationContext, "Sale Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void productListQuantityShowFinal(final ViewData data)
    {
        LinearLayout root_view = data.view.findViewById(R.id.root_view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        Iteam i = (Iteam) data.object;
        setView(data.object,data.view);

        if(i.iteam_quantity.equals("0")){
            getView("iteam_quantity","-",data.view);
        }
        else {
            getView("iteam_quantity",NumberEngToBng(i.iteam_quantity)+" "+i.iteam_unit,data.view);
        }

        if(i.iteam_price.equals("0")){
            getView("iteam_price","-",data.view);
        }
        else {
            getView("iteam_price",NumberEngToBng(i.iteam_price)+" টাকা",data.view);
        }

        ImageView remove_btn = getView("remove_btn",data.view);
        final EditText iteam_quantity = getView("iteam_quantity",data.view);

        remove_btn.setVisibility(View.GONE);
    }
    View checkoutView;
    public void getPopupdataCustomer(final PopupViewData data)
    {
        checkoutView = data.popupView;
        final ArrayList<Customer> newIteam = new ArrayList<>();
        final ImageView close_tab = (ImageView) data.popupView.findViewById(R.id.close_tab);
        final ListView customer_list = (ListView) data.popupView.findViewById(R.id.customer_list);
        final LinearLayout price_amount_holder = (LinearLayout) data.popupView.findViewById(R.id.price_amount_holder);
        setFontFromView(getView("root_view",data.popupView));
        Button submit_btn = getView("submit_btn",data.popupView);
        Button submit_btn_due = getView("submit_btn_due",data.popupView);
        Button customer_button = getView("customer_button",data.popupView);
        final EditText customer_name = getView("customer_name",data.popupView);
        final EditText note_given = getView("note_given",data.popupView);
        final TextView total_amount = getView("total_amount",NumberEngToBng(String.valueOf(finalSaleInvoice.total_amount)),data.popupView);

        //Button btn_0 = getView("btn_0",data.popupView);
        Button btn_custom = getView("btn_custom",NumberEngToBng(String.valueOf(finalSaleInvoice.total_amount)),data.popupView);
        Button btn_1000 = getView("btn_1000",data.popupView);
        Button btn_500 = getView("btn_500",data.popupView);
        Button btn_100 = getView("btn_100",data.popupView);
        Button btn_50 = getView("btn_50",data.popupView);
        Button btn_20 = getView("btn_20",data.popupView);
        //Button btn_10 = getView("btn_10",data.popupView);



        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("custom",data.popupView);
            }
        });
        btn_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("1000",data.popupView);
            }
        });
        btn_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("500",data.popupView);
            }
        });
        btn_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("100",data.popupView);
            }
        });
        btn_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("50",data.popupView);
            }
        });
        btn_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCheckout("20",data.popupView);
            }
        });


        note_given.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                finalSaleInvoice.note_given = s.toString();
                calculateCheckoutViewUpdate(data.popupView);
            }
        });

        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.popupWindow.dismiss();
            }
        });

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(submit_btn);
        buttons.add(submit_btn_due);

        for(Button btn : buttons)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double ta = TypeCasting.parseDouble(NumberBngToEng(total_amount.getText().toString()));
                    double ng = TypeCasting.parseDouble(NumberBngToEng(note_given.getText().toString()));
                    if(note_given.getText().toString().equals(""))
                    {
                        Toast.makeText(Season.applicationContext, "গ্রহন সিলেক্ট করুন", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        if(ta==ng || (ng > ta && customer_name.getText().toString().equals("")))
                        {
                            finalSaleInvoice.total_amount = ta;
                            finalSaleInvoice.note_given = String.valueOf(ng);
                            finalSaleInvoice.sale_category = "cash";
                            finalSaleInvoice.customer_name = customer_name.getText().toString().toUpperCase();
                            finalSaleInvoice.due_amount = "0";
                            finalSaleInvoice.advance_amount="0";
                            finalSaleInvoice.sale_type="product_wise";
                            MyPopupView.showPopupView(R.layout.final_submit_view, "finalSubmitView",data.parentView);
                        }
                        else
                        {
                            finalSaleInvoice.total_amount = ta;
                            finalSaleInvoice.note_given = String.valueOf(ng);
                            String customer = customer_name.getText().toString().toUpperCase();
                            finalSaleInvoice.sale_type="product_wise";
                            boolean cuFlag = false;
                            for(Customer c : AllCustomer)
                            {
                                if(c.customer_name.toUpperCase().equals(customer))
                                {
                                    cuFlag = true;
                                    break;
                                }
                            }
                            if(cuFlag)
                            {
                                finalSaleInvoice.customer_name = customer;
                                if(ta > ng)
                                {
                                    finalSaleInvoice.due_amount = String.valueOf ((ta - ng));
                                    finalSaleInvoice.sale_category = "due";
                                }
                                else
                                {
                                    finalSaleInvoice.due_amount = String.valueOf ((ng - ta));
                                    finalSaleInvoice.sale_category = "advance";
                                }
                                MyPopupView.showPopupView(R.layout.final_submit_view, "finalSubmitView");
                            }
                            else
                            {
                                Toast.makeText(Season.applicationContext, "Please Select Customer", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
        ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, AllCustomer, "customerList", 0, AllCustomer.size(),true,data.popupView);

        customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPopupView.showPopupView(R.layout.sale_pos_popup_checkout_customer_name,"popupCustomerSelect",data.popupView);
            }
        });

        /*customer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {

                if(s.length() != 0)
                {
                    customer_list.setVisibility(View.VISIBLE);
                    newIteam.clear();
                    for(Customer i : AllCustomer)
                    {
                        if((i.customer_name!=null && i.customer_name.contains(s)) || (i.customer_phone!=null && i.customer_phone.contains(s)))
                        {
                            newIteam.add(i);
                        }
                    }
                    ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, newIteam, "customerList", 0, newIteam.size(),true,data.popupView);
                }
                else
                {
                    customer_list.setVisibility(View.GONE);
                    calculateCheckoutViewUpdate(data.popupView);
                    //ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, AllCustomer, "customerList", 0, AllCustomer.size(),true,data.popupView);
                }
            }
        });*/
    }

    PopupWindow customer_list_popupWindow;
    public void  popupCustomerSelect(final PopupViewData data)
    {
        customer_list_popupWindow = data.popupWindow;
        LinearLayout root_view = data.popupView.findViewById(R.id.root_view);
        final ListView customer_list = data.popupView.findViewById(R.id.customer_list);
        EditText customer_name = data.popupView.findViewById(R.id.customer_name);
        setFontFromView(getView("root_view",data.popupView));
        final ArrayList<Customer> newIteam = new ArrayList<>();

        customer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {

                if(s.length() != 0)
                {
                    newIteam.clear();
                    for(Customer i : AllCustomer)
                    {
                        if((i.customer_name!=null && i.customer_name.contains(s)) || (i.customer_phone!=null && i.customer_phone.contains(s)))
                        {
                            newIteam.add(i);
                        }
                    }
                    ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, newIteam, "customerList", 0, newIteam.size(),true,data.popupView);
                }
                else
                {
                    calculateCheckoutViewUpdate(data.popupView);
                    //ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, AllCustomer, "customerList", 0, AllCustomer.size(),true,data.popupView);
                }
            }
        });
        ScrollListView.loadListView(Season.applicationContext, customer_list, R.layout.sale_pos_popup_customer_list, AllCustomer, "customerList", 0, AllCustomer.size(),true,data.popupView);
    }
    public void setDueCashButton(Button cashButton, Button dueButton, Boolean cashBool, Boolean dueBool)
    {
        cashButton.setEnabled(cashBool);
        dueButton.setEnabled(dueBool);
        if(cashBool)
        {
            cashButton.setBackgroundColor(Color.parseColor("#12584D"));
            cashButton.setVisibility(View.VISIBLE);

        }
        else
        {
            cashButton.setBackgroundColor(Color.parseColor("#6ab344"));
            cashButton.setVisibility(View.GONE);
        }

        if(dueBool)
        {
            dueButton.setBackgroundColor(Color.parseColor("#D32F2F"));
            dueButton.setVisibility(View.VISIBLE);
        }
        else
        {
            dueButton.setBackgroundColor(Color.parseColor("#F57C00"));
            dueButton.setVisibility(View.GONE);
        }


        cashButton.setText("নগদ");
    }
    public void calculateCheckoutViewUpdate(View data)
    {
        final EditText customer_name = getView("customer_name",data);
        final Button cashButton = getView("submit_btn",  data);
        final Button dueButton = getView("submit_btn_due",  data);
        final Button customer_button = getView("customer_button",  data);
        final EditText note_given = getView("note_given",  data);
        final TextView note_receive = getView("note_receive",  data);
        note_receive.setText(NumberEngToBng(note_given.getText().toString()));

        if(customer_name.getText().toString().equals(""))
        {
            customer_button.setVisibility(View.GONE);
            if (finalSaleInvoice.total_amount == TypeCasting.parseDouble(finalSaleInvoice.note_given))
            {
                //due.setVisibility(View.VISIBLE);
                getView("due_advance", NumberEngToBng("0"), data);
                getView("due_advance_txt", "ফেরত", data);
                setColorView(data, "due_advance_txt", "#12584D");
                setColorView(data, "due_advance", "#12584D");
                /*due = getView("submit_btn", "নগদ", data);
                due.setBackgroundColor(Color.parseColor("#12584D"));
                submit_btn_due.setEnabled(false);
                due.setEnabled(true);*/
                setDueCashButton(cashButton, dueButton, true,false);
            }
            else if (finalSaleInvoice.total_amount > TypeCasting.parseDouble(finalSaleInvoice.note_given))
            {
                TextView tt = getView("due_advance", data);
                tt.setSingleLine(false);
                tt.setText("ক্রেতার নাম\nসিলেক্ট করুন");
                getView("due_advance_txt", "ফেরত", data);
                setColorView(data, "due_advance_txt", "#FF0000");
                setColorView(data, "due_advance", "#FF0000");
                setDueCashButton(cashButton, dueButton, false,false);
                customer_button.setVisibility(View.VISIBLE);
            }
            else if (finalSaleInvoice.total_amount < TypeCasting.parseDouble(finalSaleInvoice.note_given))
            {
                //due.setVisibility(View.VISIBLE);
                getView("due_advance", NumberEngToBng(String.valueOf((TypeCasting.parseDouble(finalSaleInvoice.note_given) - finalSaleInvoice.total_amount))), data);
                getView("due_advance_txt", "ফেরত", data);
                setColorView(data, "due_advance_txt", "#12584D");
                setColorView(data, "due_advance", "#12584D");
                /*due = getView("submit_btn", "নগদ", data);
                due.setBackgroundColor(Color.parseColor("#12584D"));
                submit_btn_due.setEnabled(false);
                due.setEnabled(true);*/
                setDueCashButton(cashButton, dueButton, true,false);
            }
        }
        else
        {
            customer_button.setVisibility(View.GONE);
            Button due = getView("submit_btn",  data);
            Button submit_btn_due = getView("submit_btn_due",  data);
            due.setVisibility(View.VISIBLE);
            if (finalSaleInvoice.total_amount == TypeCasting.parseDouble(finalSaleInvoice.note_given))
            {
                getView("due_advance", NumberEngToBng("0"), data);
                getView("due_advance_txt", "ফেরত", data);
                setColorView(data, "due_advance_txt", "#12584D");
                setColorView(data, "due_advance", "#12584D");
                //due = getView("submit_btn", "নগদ", data);
                //due.setBackgroundColor(Color.parseColor("#12584D"));
                finalSaleInvoice.due_amount ="";
                finalSaleInvoice.advance_amount ="";
                setDueCashButton(cashButton, dueButton, true,false);
            }
            else if (finalSaleInvoice.total_amount > TypeCasting.parseDouble(finalSaleInvoice.note_given)) {
                getView("due_advance", NumberEngToBng(String.valueOf((finalSaleInvoice.total_amount - TypeCasting.parseDouble(finalSaleInvoice.note_given)))), data);
                getView("due_advance_txt", "বাকি", data);
                setColorView(data, "due_advance_txt", "#FF0000");
                setColorView(data, "due_advance", "#FF0000");
                // due = getView("submit_btn", "বাকি", data);
                // due.setBackgroundColor(Color.RED);
                finalSaleInvoice.advance_amount ="";
                finalSaleInvoice.due_amount = String.valueOf((finalSaleInvoice.total_amount - TypeCasting.parseDouble(finalSaleInvoice.note_given)));
                //submit_btn_due.setEnabled(true);
                //due.setEnabled(false);
                setDueCashButton(cashButton, dueButton, false,true);
            }
            else if (finalSaleInvoice.total_amount < TypeCasting.parseDouble(finalSaleInvoice.note_given)) {
                /*getView("due_advance", NumberEngToBng(String.valueOf((TypeCasting.parseDouble(finalSaleInvoice.note_given) - finalSaleInvoice.total_amount))), data);
                getView("due_advance_txt", "আগ্রিম", data);
                setColorView(data, "due_advance_txt", "#2982a3");
                setColorView(data, "due_advance", "#2982a3");
                finalSaleInvoice.due_amount ="";
                finalSaleInvoice.advance_amount = String.valueOf((TypeCasting.parseDouble(finalSaleInvoice.note_given) - finalSaleInvoice.total_amount));
                setDueCashButton(cashButton, dueButton, true,false);
                due = getView("submit_btn", "আগ্রিম", data);
                due.setBackgroundColor(Color.parseColor("#2982a3"));*/

                getView("due_advance", NumberEngToBng(String.valueOf((TypeCasting.parseDouble(finalSaleInvoice.note_given) - finalSaleInvoice.total_amount))), data);
                getView("due_advance_txt", "ফেরত", data);
                setColorView(data, "due_advance_txt", "#12584D");
                setColorView(data, "due_advance", "#12584D");
                setDueCashButton(cashButton, dueButton, true,false);
            }
        }
    }
    public void customerList(final ViewData data)
    {
        setFontFromView(getView("root_view",data.view));
        setView(data.object,data.view);
        LinearLayout root_view = getView("root_view",data.view);
        if(data.position%2==0)
        {
            root_view.setBackgroundColor(Color.parseColor("#e6e6e6"));
        }
        else
        {
            root_view.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
        root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText customer_name = getView("customer_name",checkoutView);
                EditText note_given = getView("note_given",checkoutView);
                note_given.requestFocus();
                customer_name.setText(((Customer) data.object).customer_name);

                calculateCheckoutViewUpdate(checkoutView);
                customer_list_popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posprice_wise_drawer, menu);
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
}
