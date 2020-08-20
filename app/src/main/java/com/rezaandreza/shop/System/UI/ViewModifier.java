package com.rezaandreza.shop.System.UI;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.rezaandreza.shop.Configuration.Fonts;
import com.rezaandreza.shop.Configuration.Season;
import com.rezaandreza.shop.R;

import me.grantland.widget.AutofitHelper;

public class ViewModifier {

    public static <T extends View> T getView(String stringID,String txt,View v)
    {
        return getView(new Object(),stringID,txt,v);
    }
    public static <T extends View> T getView(Object obj,String stringID,String txt,View v)
    {
        try {
            View view;
            if(v == null)
            {
                Resources res = Season.applicationContext.getResources();
                int id = res.getIdentifier(stringID, "id", Season.applicationContext.getPackageName());
                Activity a = (Activity) Season.applicationContext;
                view = a.findViewById(id);
            }
            else
            {
                Resources res = Season.applicationContext.getResources();
                int id = res.getIdentifier(stringID, "id", Season.applicationContext.getPackageName());
                view = v.findViewById(id);
            }

            if (view instanceof TextView) {

                ((TextView) view).setTypeface(Fonts.getFont());
                if(!txt.equals("")) {
                    ((TextView) view).setText(txt);
                    TextView nv = (TextView) view;

                }
                return (T) view;
            }
            if (view instanceof Spinner) {
                if(!txt.equals("")) {
                    Spinner nv = (Spinner) view;
                }
                return (T) view;
            }
            else if (view instanceof EditText) {
                ((EditText) view).setTypeface(Fonts.getFont());
                if(!txt.equals("")) {
                    ((EditText) view).setText(txt);
                    EditText nv = (EditText) view;
                }
                return (T) view;
            }
            else if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(Fonts.getFont());
                if(txt.toLowerCase().equals("true")) {
                    CheckBox c = (CheckBox) view;
                    c.setSelected(true);
                }
                else if(txt.toLowerCase().equals("false")) {
                    CheckBox c = (CheckBox) view;
                    c.setSelected(false);
                }
                return (T) view;
            }
            else if (view instanceof LinearLayout) {
                try {
                    Field f = obj.getClass().getDeclaredField(stringID);
                    if(f.getGenericType().toString().toUpperCase().equals("CLASS ANDROID.GRAPHICS.BITMAP"))
                    {
                        LinearLayout nv = (LinearLayout) view;
                        Bitmap bm = (Bitmap) f.get(obj);
                        ((LinearLayout) view).setBackgroundDrawable(new BitmapDrawable(bm));
                        nv.setBackgroundResource(0);

                    }
                    else
                    {
                        if(!txt.equals("")) {
                            LinearLayout nv = (LinearLayout) view;
                            nv.setBackgroundResource(0);
                            Glide.with(Season.applicationContext)
                                    .load(txt)
                                    .thumbnail(Glide.with(Season.applicationContext).load(R.drawable.avatar_background))
                                    .fitCenter();
                            nv.setBackgroundResource(0);
                        }
                    }return (T) view;
                }catch (Exception e){System.out.println(e.getMessage());return (T) view;}
            }
            else if (view instanceof ImageView) {
                try {
                    Field f = obj.getClass().getDeclaredField(stringID);
                    if(f.getGenericType().toString().toUpperCase().equals("CLASS ANDROID.GRAPHICS.BITMAP"))
                    {
                        ImageView nv = (ImageView) view;
                        Bitmap bm = (Bitmap) f.get(obj);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        Glide.with(Season.applicationContext)
                                .load(stream.toByteArray())
                                .into((ImageView) view);
                        nv.setBackgroundResource(0);

                    }
                    else
                    {
                        if(!txt.equals("")) {
                            ImageView nv = (ImageView) view;
                            nv.setBackgroundResource(0);
                            Glide.with(Season.applicationContext)
                                    .load(txt)
                                    .placeholder(R.drawable.waiting)
                                    .fitCenter()
                                    .into((ImageView) view);
                            nv.setBackgroundResource(0);
                        }
                    }
                }catch (Exception e){System.out.println(e.getMessage());}
                return (T) view;
            }

            return (T) view;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return (T) new View(Season.applicationContext);
        }
    }
    public static <T extends View> T getView(String stringID,View v)
    {
        return getView(stringID,"",v);
    }
    public static <T extends View> T getView(String stringID,String txt)
    {
        return getView(stringID,txt,null);
    }
    public static <T extends View> T getView(String stringID)
    {
        return getView(stringID,"",null);
    }

    public static int getLayout(String name)
    {
        int resID = -1;
        resID = Season.applicationContext.getResources().getIdentifier(name, "layout", Season.applicationContext.getPackageName());
        return resID;
    }

    public static void setFontFromView(View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    // recursively call this method
                    setFontFromView(child);
                }
            }
            else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Fonts.getFont());
            }
            else if (v instanceof EditText) {
                ((EditText) v).setTypeface(Fonts.getFont());
            }
            else if (v instanceof Button) {
                ((Button) v).setTypeface(Fonts.getFont());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
