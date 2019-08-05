package com.rezaandreza.shop.System.AsyncTask;

import android.app.ProgressDialog;

import java.lang.reflect.Method;

import com.rezaandreza.shop.Helper.Debug;

public class BaseAsyncTask extends android.os.AsyncTask<String, String, String>  {
    public ProgressDialog progressDialog;
    public Object callBackPerExecuteCbject;
    public String callBackPerExecuteMethod;
    public Object callBackExecuteCbject;
    public String callBackExecuteMethod;
    public Object callBackPosExecuteCbject;
    public String callBackPosExecuteMethod;
    public Object callBackPublishExecuteCbject;
    public String callBackPublishExecuteMethod;

    public void setObjects(Object ob)
    {
        this.callBackPerExecuteCbject=ob;
        this.callBackExecuteCbject=ob;
        this.callBackPosExecuteCbject=ob;
        this.callBackPublishExecuteCbject=ob;
    }
    @Override
    public String doInBackground(String... params) {
        try {
            Method m = this.callBackExecuteCbject.getClass().getMethod(this.callBackExecuteMethod,new Class[]{String.class});
            m.invoke(this.callBackExecuteCbject,"s");
        }catch (Exception e){System.out.println(e.getMessage());}
        return "";
    }


    @Override
    public void onPostExecute(String result) {
        try {
            Method m = this.callBackPosExecuteCbject.getClass().getMethod(this.callBackPosExecuteMethod,new Class[]{String.class});
            m.invoke(this.callBackPosExecuteCbject,result);
        }catch (Exception e){System.out.println(e.getMessage());}
    }


    @Override
    public void onPreExecute() {
        try {
            Method m = this.callBackPerExecuteCbject.getClass().getMethod(this.callBackPerExecuteMethod,new Class[]{String.class});
            m.invoke(this.callBackPerExecuteCbject,"");
        }catch (Exception e){System.out.println(e.getMessage());}
    }


    @Override
    public void onProgressUpdate(String... text) {
        try {
            Method m = this.callBackPublishExecuteCbject.getClass().getMethod(this.callBackPublishExecuteMethod,new Class[]{String.class});
            m.invoke(this.callBackPublishExecuteCbject,text[0]);
        }catch (Exception e){System.out.println(e.getMessage());}
    }

    public void updateView(String data)
    {
        publishProgress(data);
    }
}
