package com.rezaandreza.shop.Activities.Sale.POS_Drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.nmaltais.calcdialog.CalcDialog;
import com.rezaandreza.shop.Activities.Sale.POSPriceWiseDrawer.POSPriceWiseDrawer;
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
import static com.rezaandreza.shop.System.Helper.TypeCasting.round1Dec;
import static com.rezaandreza.shop.System.Softkeyboard.SoftkeyboardHelper.hideKeyboard;
import static com.rezaandreza.shop.System.UI.LayoutDataBind.setView;
import static com.rezaandreza.shop.System.UI.LayoutDataRecevier.getViewData;
import static com.rezaandreza.shop.System.UI.ViewColor.setColorView;
import static com.rezaandreza.shop.System.UI.ViewModifier.getView;
import static com.rezaandreza.shop.System.UI.ViewModifier.setFontFromView;

public class POS_Drawer_Ac extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

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
    TextView amount_list;
    SalesInvoice finalSaleInvoice = new SalesInvoice();
    ListView iteam_list;
    Boolean weight_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Season.__InitiatSeasonAction(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos__drawer_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFontFromView(getView("root_view"));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        menu_setup(navigationView);

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


        iteam_list = (ListView) findViewById(R.id.iteam_list);
        final TextView date = getView("date",NumberEngToBng(DateTimeCalculation.getCurrentDate()));
        //final TextView iteam_list = getView("iteam_list");
        setFontFromView(getView("root_view"));
        //iteam_list.setMovementMethod(new ScrollingMovementMethod());
        amount_list = getView("amount_list");
        amount_list.setMovementMethod(new ScrollingMovementMethod());
        ((TextView) getView("total_amount")).setMovementMethod(new ScrollingMovementMethod());
        ((TextView) getView("total_quatity")).setMovementMethod(new ScrollingMovementMethod());
        final Button btn_dot= getView("btn_dot");
        final Button btn_0 = getView("btn_0");
        final Button btn_00 = getView("btn_00");
        final Button btn_1 = getView("btn_1");
        final Button btn_2 = getView("btn_2");
        final Button btn_3 = getView("btn_3");
        final Button btn_4 = getView("btn_4");
        final Button btn_5 = getView("btn_5");
        final Button btn_6 = getView("btn_6");
        final Button btn_7 = getView("btn_7");
        final Button btn_8 = getView("btn_8");
        final Button btn_9 = getView("btn_9");
        final Button btn_equal = getView("btn_equal");
        final Button btn_add = getView("btn_add");
        final Button btn_multi = getView("btn_multi");
        final Button btn_c = getView("btn_c");

        final LinearLayout btn_iteam_list = getView("btn_iteam_list");
        final LinearLayout btn_cash = getView("btn_cash");

        final LinearLayout price_wise_btn = getView("price_wise_btn");

        final Button go_to_price_wise = getView("go_to_price_wise");
        // final Button btn_due = getView("btn_due");
        final ImageView btn_cal = getView("btn_cal");

        price_wise_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyIntent.start(POSPriceWiseDrawer.class);
                finish();
            }
        });

        amount_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
                vf.setDisplayedChild(0);
            }
        });
        btn_cal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyPopupView.showPopupViewFull(R.layout.sale_popup_calculator,"calPop");
            }
        });

        btn_cash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createInvoice("Cash");
            }
        });
       /* btn_due.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createInvoice("Due");
            }
        });*/
        btn_dot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount(".");
            }
        });
        btn_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("0");
            }
        });
        btn_00.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("00");
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("1");
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("2");
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("3");
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("4");
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("5");
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("6");
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("7");
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("8");
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("9");
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("+");
            }
        });
        btn_multi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("x");
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("c");
            }
        });


        btn_c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                amount_list.setText("");
                calculateQuantityAmount("c");
                return true;
            }
        });
        btn_iteam_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
                vf.setDisplayedChild(1);
                //setFontFromView(getView("root_view",data.popupView));

                ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true);

                final ArrayList<Iteam> newIteam = new ArrayList<>();
                final Button close_tab = (Button) findViewById(R.id.btn_submit);

                final ListView iteam_list = (ListView) findViewById(R.id.iteam_list);
                final ListView iteam_added = (ListView) findViewById(R.id.iteam_added);

                final EditText iteam_name = (EditText) findViewById(R.id.iteam_name);
                final LinearLayout price_amount_holder = findViewById(R.id.price_amount_holder);
                price_amount_holder.setVisibility(View.GONE);
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
                            ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_pop_iteam_list_price_wise, newIteam, "productListShow", 0, AlliteamList.size(),true);
                        }
                        else
                        {
                            newIteam.clear();
                            iteam_list.getLayoutParams().height = 0;
                            ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_pop_iteam_list_price_wise, newIteam, "productListShow", 0, AlliteamList.size(),true);
                        }
                    }
                });

                //calculateUpdateQuantityPrice((View) Season.applicationContext));
                ScrollListView.loadListView(Season.applicationContext, iteam_added, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true);
                iteam_name.requestFocus();
                iteam_name.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager keyboard = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.showSoftInput(iteam_name, 0);
                    }
                },200);

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

                // MyPopupView.showPopupView(R.layout.sale_pos_iteam_popup,"getPopupdataIteam");
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
    public void calculateCheckout(String amount,View data)
    {
        final EditText note_given = getView("note_given",data);
        final EditText customer_name = getView("customer_name",data);
        final TextView note_receive = getView("note_receive",data);


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
    PopupWindow customer_list_popupWindow;
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

                //((ListView) getView("customer_list",data.parentView)).setVisibility(View.GONE);
                calculateCheckoutViewUpdate(checkoutView);
                customer_list_popupWindow.dismiss();
            }
        });
    }

    public void getPopupdataIteamQuantity(final PopupViewData data)
    {
        setFontFromView(getView("root_view",data.popupView));
        final ImageView close_tab = (ImageView) data.popupView.findViewById(R.id.close_tab);
        final ListView iteam_list = (ListView) data.popupView.findViewById(R.id.iteam_list);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.popupWindow.dismiss();
            }
        });
        ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.popupView);
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

        getView("iteam_price",NumberEngToBng(i.iteam_price),data.view);
        ImageView remove_btn = getView("remove_btn",data.view);
        final EditText iteam_quantity = getView("iteam_quantity",data.view);

        if(weight_flag)
        {
            getView("iteam_quantity",NumberEngToBng(i.iteam_quantity),data.view);
        }
        else
        {
            getView("iteam_quantity","-",data.view);
        }
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iteamList.remove(data.position);
                ListView iteam_list = getView("iteam_added",data.parentView);
                ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);
                ScrollListView.loadListView(Season.applicationContext, (ListView) getView("iteam_list",data.parentView), R.layout.sale_pos_pop_iteam_list_price_wise, new ArrayList<>(), "productListShow", 0, AlliteamList.size(),true,data.parentView);
                ListView l = (ListView) getView("iteam_list",data.parentView);
                l.getLayoutParams().height=0;
                getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())),data.parentView);
            }
        });
    }
    public void getPopupdataIteam(final PopupViewData data)
    {
        final ArrayList<Iteam> newIteam = new ArrayList<>();
        final Button close_tab = (Button) data.popupView.findViewById(R.id.btn_submit);

        final ListView iteam_list = (ListView) data.popupView.findViewById(R.id.iteam_list);
        final ListView iteam_added = (ListView) data.popupView.findViewById(R.id.iteam_added);

        final EditText iteam_name = (EditText) data.popupView.findViewById(R.id.iteam_name);
        final LinearLayout price_amount_holder = (LinearLayout) data.popupView.findViewById(R.id.price_amount_holder);
        price_amount_holder.setVisibility(View.GONE);
        setFontFromView(getView("root_view",data.popupView));


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
                    ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_pop_iteam_list_price_wise, newIteam, "productListShow", 0, AlliteamList.size(),true,data.popupView);
                }
                else
                {
                    newIteam.clear();
                    iteam_list.getLayoutParams().height = 0;
                    ScrollListView.loadListView(Season.applicationContext, iteam_list, R.layout.sale_pos_pop_iteam_list_price_wise, newIteam, "productListShow", 0, AlliteamList.size(),true,data.popupView);
                }
            }
        });
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String out ="";
                for(Iteam i : iteamList)
                {
                    out+=i.iteam_name_bng+",";

                }
                getView("iteam_list",out,data.parentView);
                data.popupWindow.dismiss();
                hideKeyboard();
            }
        });
        getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())),data.popupView);
        ScrollListView.loadListView(Season.applicationContext, iteam_added, R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.popupView);
        iteam_name.requestFocus();
        iteam_name.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(iteam_name, 0);
            }
        },200);
    }
    public void calculateUpdateQuantityPrice(View v)
    {
        double totalPrice =0.0;
        for(Iteam i : iteamList)
        {
            totalPrice+=TypeCasting.parseDouble(i.iteam_price);
        }

        getView("total_amount",NumberEngToBng(String.valueOf(totalPrice)),v);
        getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())),v);
    }
    Iteam selectedIteamPriceQuantity = new Iteam();
    public void priceQuantityPopup(final PopupViewData data)
    {
        Button submit_btn = getView("submit_btn",data.popupView);

        LinearLayout ll = getView("price_holder",data.popupView);
        ll.setVisibility(View.GONE);

        ll = getView("quantity_holder",data.popupView);
        ll.setVisibility(View.VISIBLE);

        setView(selectedIteamPriceQuantity,data.popupView);
        setFontFromView(getView("root_view",data.popupView));
        final ImageView close_tab = (ImageView) data.popupView.findViewById(R.id.close_tab);
        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.popupWindow.dismiss();
            }
        });
        final EditText yourEditText= (EditText) data.popupView.findViewById(R.id.iteam_quantity);
        EdittextHelper.setFocus((EditText) getView("iteam_quantity",data.popupView));
        yourEditText.requestFocus();
        submit_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                Iteam n =(Iteam) getViewData(new Iteam(),data.popupView);
                Iteam i = selectedIteamPriceQuantity;
                selectedIteamPriceQuantity.iteam_price =n.iteam_price;
                selectedIteamPriceQuantity.iteam_quantity=n.iteam_quantity;

                if(!(n.iteam_quantity.equals("") || n.iteam_quantity.equals("0")))
                {
                    if(iteamList.contains(i))
                    {
                        int p = iteamList.indexOf(i);
                        iteamList.set(p,selectedIteamPriceQuantity);
                    }
                    else
                    {
                        iteamList.add(selectedIteamPriceQuantity);
                    }
                }

                data.popupWindow.dismiss();
                ListView l = (ListView) getView("iteam_list",data.parentView);
                ((EditText) getView("iteam_name",data.parentView)).setText("");
                l.getLayoutParams().height=0;
                ScrollListView.loadListView(Season.applicationContext, (ListView) getView("iteam_added",data.parentView), R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);
                getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())),data.parentView);

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
                if(!weight_flag)
                {
                    if(iteamList.contains(i))
                    {
                        int p = iteamList.indexOf(i);
                        i.iteam_quantity = "0";
                        iteamList.set(p,i);

                    }
                    else
                    {
                        i.iteam_quantity = "0";
                        iteamList.add(i);
                    }
                    getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())), (data.parentView));
                    ((EditText) getView("iteam_name",data.parentView)).setText("");
                    ScrollListView.loadListView(Season.applicationContext,(ListView) getView("iteam_added",data.parentView), R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);

                }
                else
                {
                    selectedIteamPriceQuantity = i;
                    selectedIteamPriceQuantity.iteam_quantity="";
                    MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);
                }

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
            btn1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(iteamList.contains(i))
                    {
                        /*if(c.isChecked())
                        {
                            selectedIteamPriceQuantity = i;
                            selectedIteamPriceQuantity.iteam_quantity = quantity;
                            MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);
                        }
                        else*/
                        {
                            int p = iteamList.indexOf(i);
                            i.iteam_quantity = quantity;
                            iteamList.set(p,i);
                        }

                    }
                    else
                    {
                        /*if(c.isChecked())
                        {
                            selectedIteamPriceQuantity = i;
                            selectedIteamPriceQuantity.iteam_quantity = quantity;
                            MyPopupView.showPopupView(R.layout.sale_pos_popup_popup_price_quantity,"priceQuantityPopup",data.parentView);
                        }
                        else*/
                        {
                            i.iteam_quantity = quantity;
                            iteamList.add(i);
                        }
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
                    getView("total_quatity",NumberEngToBng(String.valueOf(iteamList.size())),data.parentView);
                    ((EditText) getView("iteam_name",data.parentView)).setText("");
                    ScrollListView.loadListView(Season.applicationContext,(ListView) getView("iteam_added",data.parentView), R.layout.sale_pos_popup_iteam_quantity_list_price_wise, iteamList, "productListQuantityShow", 0, AlliteamList.size(),true,data.parentView);
                }
            });
        }
        setFontFromView(getView("quantity_container",data.view));
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




    public void calculateQuantityAmount(String txt)
    {
        String fullEquation = amount_list.getText().toString();

        if(txt.equals("c"))
        {
            if( !fullEquation.trim().equals(""))
            {
                fullEquation = fullEquation.substring(0, fullEquation.length() - 1);
            }
        }
        else
        {
            fullEquation = fullEquation + txt;
        }

        fullEquation = NumberBngToEng(fullEquation);

        while (fullEquation.contains("++") || fullEquation.contains("xx") || fullEquation.contains(".."))
        {
            fullEquation = fullEquation.replace("++", "+");
            fullEquation = fullEquation.replace("xx", "x");
            fullEquation = fullEquation.replace("..", ".");
        }
        String[] numbers = fullEquation.split("\\+");
        double output = 0.0;
        for (String eq : numbers)
        {
            if (eq.trim().equals("")) {
                continue;
            }

            if (eq.contains("x")) {
                double multi = 1;
                for (String mul : eq.split("x")) {
                    multi = multi * TypeCasting.parseDouble(mul);
                }
                output += multi;
            } else {
                output += TypeCasting.parseDouble(eq);
            }
        }
        if(fullEquation.equals(""))
        {
            TextView total_quatity = getView("total_quatity", NumberEngToBng(String.valueOf(0)));
            TextView total_amount = getView("total_amount", NumberEngToBng(round1Dec(output)));
            amount_list.setText(NumberEngToBng(fullEquation));
        }
        else {
            TextView total_quatity = getView("total_quatity", NumberEngToBng(String.valueOf(numbers.length)));
            TextView total_amount = getView("total_amount", NumberEngToBng(round1Dec(output)));
            amount_list.setText(NumberEngToBng(fullEquation));
        }
    }

    public void calPop(final PopupViewData data)
    {
        setFontFromView(getView("root_view",data.popupView));
        LinearLayout close_calculation = getView("close_calculation",data.popupView);
        close_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.popupWindow.dismiss();
            }
        });

        LinearLayout add_calculation = getView("add_calculation",data.popupView);
        add_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView pop_cal_text =  getView("pop_cal_text",data.popupView);
                amount_list.setText(String.valueOf(pop_cal_text.getText()));
                data.popupWindow.dismiss();
            }
        });

        final Button btn_dot= getView("btn_dot",data.popupView);
        final Button btn_0 = getView("btn_0",data.popupView);
        final Button btn_00 = getView("btn_00",data.popupView);
        final Button btn_1 = getView("btn_1",data.popupView);
        final Button btn_2 = getView("btn_2",data.popupView);
        final Button btn_3 = getView("btn_3",data.popupView);
        final Button btn_4 = getView("btn_4",data.popupView);
        final Button btn_5 = getView("btn_5",data.popupView);
        final Button btn_6 = getView("btn_6",data.popupView);
        final Button btn_7 = getView("btn_7",data.popupView);
        final Button btn_8 = getView("btn_8",data.popupView);
        final Button btn_9 = getView("btn_9",data.popupView);
        final Button btn_equal = getView("btn_equal",data.popupView);
        final Button btn_add = getView("btn_add",data.popupView);
        final Button btn_multi = getView("btn_multi",data.popupView);
        final Button btn_c = getView("btn_c",data.popupView);
        final Button btn_minus = getView("btn_minus",data.popupView);
        final Button btn_div = getView("btn_div",data.popupView);
        final Button btn_cc = getView("btn_cc",data.popupView);
        final Button btn_per = getView("btn_per",data.popupView);

        btn_cc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("cc",data.popupView);
            }
        });
        btn_per.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("%",data.popupView);
            }
        });
        btn_dot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount(".",data.popupView);
            }
        });
        btn_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("0",data.popupView);
            }
        });
        btn_00.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("00",data.popupView);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("1",data.popupView);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("2",data.popupView);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("3",data.popupView);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("4",data.popupView);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("5",data.popupView);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("6",data.popupView);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("7",data.popupView);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("8",data.popupView);
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("9",data.popupView);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("+",data.popupView);
            }
        });
        btn_multi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("x",data.popupView);
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("c",data.popupView);
            }
        });

        btn_c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //amount_list.setText("");
                calculateQuantityAmount("c",data.popupView);
                return true;
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("-",data.popupView);
            }
        });
        btn_div.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculateQuantityAmount("÷",data.popupView);
            }
        });
        btn_equal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                calculateQuantityAmount("=",data.popupView);



            }
        });
    }

    public void calculateQuantityAmount(String txt, View v)
    {
        if(txt.equals("cc"))
        {
            getView("pop_cal_text", " ", v);
            return;
        }

        TextView pop_cal_text = getView("pop_cal_text",v);
        String fullEquation = pop_cal_text.getText().toString().trim();
        double output = 0.0;

        if(txt.equals("c"))
        {
            if( !fullEquation.trim().equals(""))
            {
                fullEquation = fullEquation.substring(0, fullEquation.length() - 1);
            }
        }
        else if (txt.trim().equals("=")) {

        }
        else
        {
            fullEquation = fullEquation + txt;
        }




        if(fullEquation.equals(""))
        {
            pop_cal_text.setText(NumberEngToBng(fullEquation));
        }
        else {
            pop_cal_text.setText(NumberEngToBng(fullEquation));
        }
        if (txt.contains("=")) {
            output = finalCal(NumberBngToEng("0"+fullEquation));
            getView("pop_cal_text", NumberEngToBng(String.valueOf(round1Dec(output))), v);
        }
    }
    public double finalCal(String num)
    {
        System.out.println(num);
        double output = 0.0;
        if(num.contains("+"))
        {
            String[] numbers = num.split("\\+");
            for (String eq : numbers)
            {
                output += finalCal(eq);
            }
            return output;
        }
        else if(num.contains("-"))
        {
            String[] numbers = num.split("-");
            output=finalCal(numbers[0])*2;
            for (String eq : numbers)
            {
                output -= finalCal(eq);
            }
            return output;
        }
        else if(num.toLowerCase().contains("x"))
        {
            output = 1;
            String[] numbers = num.split("x");
            for (String eq : numbers)
            {
                output *= finalCal(eq);
            }

            return output;
        }
        else if(num.contains("÷"))
        {
            String[] numbers = num.split("÷");
            output=finalCal(numbers[0])*finalCal(numbers[0]);
            for (String eq : numbers)
            {
                output /= finalCal(eq);
            }
            return output;
        }
        else if(num.contains("%"))
        {
            String[] numbers = num.split("%");

            output=(finalCal(numbers[1])*finalCal(numbers[0]))/100;
            return output;
        }
        return TypeCasting.parseDouble(num);
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
       // getMenuInflater().inflate(R.menu.pos__drawer_, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
