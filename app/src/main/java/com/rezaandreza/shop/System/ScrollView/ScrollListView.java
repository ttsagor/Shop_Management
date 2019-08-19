package com.rezaandreza.shop.System.ScrollView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.frosquivel.scrollinfinite.ScrollInfiniteAdapter;
import com.frosquivel.scrollinfinite.ScrollInfiniteListener;
import com.rezaandreza.shop.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.rezaandreza.shop.System.ScrollView.ViewData.getViewData;

public class ScrollListView {
    public static void loadListView(final Context context, final ListView lv, final int layout, final ArrayList<?> ojects, final String rowMethod, final int firstCountToShow, final int setByStep, Boolean showFooter, Boolean updateHeight, Boolean spacial,final View parentView)
    {
        if(ojects.size()==0)
        {
            lv.setAdapter(null);
            return;
        }
        final View footer = ((Activity) context).getLayoutInflater().inflate(R.layout.progessbar, null);
        final ProgressBar progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);

        if(showFooter) {
            lv.addFooterView(footer);
        }

        final ArrayList<Object> obs= new ArrayList<Object>();
        for(Object o : ojects)
        {
            obs.add((Object) o);
        }

        final ScrollInfiniteAdapter adapter = new ScrollInfiniteAdapter((Activity) context, obs, layout, firstCountToShow, setByStep) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                Object obj = getItem(position);
                if (convertView == null) {
                    view = LayoutInflater.from(getContext()).inflate(layout, null);
                } else {
                    view = convertView;
                }
                try {
                    Method m = context.getClass().getMethod(rowMethod,new Class[]{ViewData.class});
                    m.invoke(context,getViewData(context,view,parentView,obj,position,obs));
                }catch (Exception e){e.printStackTrace();}

                return view;
            }
        };

        lv.setAdapter(adapter);
        lv.setOnScrollListener(new ScrollInfiniteListener(adapter, progressBar));
        if(updateHeight)
        {
            int hfCOunt=0;
            if(lv.getHeaderViewsCount()>0)
            {
                hfCOunt++;
            }
            if(lv.getFooterViewsCount()>0)
            {
                hfCOunt++;
            }
            if(spacial)
            {
                // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                {
                    // updateListViewHeightManual(context, lv, layout,(int) Math.floor(obs.size() + hfCOunt+1));
                }
                // else
                {
                    updateListViewHeightManual(context, lv, layout,(int) Math.floor(obs.size() + hfCOunt/2));
                }

            }
            else {
                updateListViewHeightManual(context, lv, layout, obs.size() + hfCOunt);
            }
        }
    }
    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,false,false,null);
    }
    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter,View parentView)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,false,false,parentView);
    }
    public static void loadListView(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,String rowMethod)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,false,false,null);
    }
    public static void loadListViewNoFooter(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,String rowMethod)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),false,false,false,null);
    }
    public static void loadListViewUpdateHeight(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,false,null);
    }
    public static void loadListViewUpdateHeight(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter,View view)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,false,view);
    }
    public static void loadListViewUpdateHeightSpecial(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,true,null);
    }
    public static void loadListViewUpdateHeightSpecial(final Context context,final  ListView lv,final int layout,final ArrayList<?> ojects,final String rowMethod,final int firstCountToShow,final int setByStep,Boolean showFooter,View view)
    {
        loadListView(context,lv,layout,ojects,rowMethod,0,ojects.size(),true,true,true,view);
    }
    public static void updateListViewHeightManual(Context context,ListView listView,int id, int iteamCount) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = inflater.inflate(id, null);

            float px = 500 * (listView.getResources().getDisplayMetrics().density);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));



            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            {
                iteamCount = item.getMeasuredHeight()*iteamCount;
                params.height = iteamCount/2 - ((iteamCount/2)/7);
            }
            else
            {
                iteamCount = item.getMeasuredHeight()*iteamCount;
                params.height = iteamCount;
            }

            listView.setLayoutParams(params);
            listView.requestLayout();
            // return true;

        } else {
            //return false;
        }

    }
}
