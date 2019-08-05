package com.rezaandreza.shop.System.Service;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rezaandreza.shop.Configuration.Season;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BaseImageDownloader extends AsyncTask<URL,Integer,ArrayList<Bitmap>> {
    public Object callBackClass;
    public String callBackMethod;
    public ArrayList<String> urls = new ArrayList<>();
    // Before the tasks execution

    public BaseImageDownloader(Object callBackClass,String callBackMethod,ArrayList<String> urls)
    {
        this.callBackClass = callBackClass;
        this.callBackMethod = callBackMethod;
        this.urls = urls;
    }
    protected void onPreExecute(){
        //System.out.println("pre");
        // Display the progress dialog on async task start
       // mProgressDialog.show();
       // mProgressDialog.setProgress(0);
    }

    // Do the task in background/non UI thread
    protected ArrayList<Bitmap> doInBackground(URL...u){
       // System.out.println("cur");
        int count = urls.size();
        //URL url = urls[0];
        HttpURLConnection connection = null;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        // Loop through the urls
        for(int i=0;i<count;i++){
            URL currentURL = stringToURL(urls.get(i));
            // So download the image from this url
            try{
                // Initialize a new http url connection
                connection = (HttpURLConnection) currentURL.openConnection();

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                // Add the bitmap to list
                bitmaps.add(bmp);

                // Publish the async task progress
                // Added 1, because index start from 0
                publishProgress((int) (((i+1) / (float) count) * 100));
                if(isCancelled()){
                    break;
                }

            }catch(IOException e){
                e.printStackTrace();
            }finally{
                // Disconnect the http url connection
                connection.disconnect();
            }
        }
        // Return bitmap list
        return bitmaps;
    }

    // On progress update
    protected void onProgressUpdate(Integer... progress){
        // Update the progress bar
       // mProgressDialog.setProgress(progress[0]);
    }

    // On AsyncTask cancelled
    protected void onCancelled(){
        //Snackbar.make(mCLayout,"Task Cancelled.",Snackbar.LENGTH_LONG).show();
    }

    // When all async task done
    protected void onPostExecute(ArrayList<Bitmap> result){
        // Hide the progress dialog
        // mProgressDialog.dismiss();
        //System.out.println("post");
        // Remove all views from linear layout
       // mLLayout.removeAllViews();
        try {
            if(!this.callBackMethod.equals("")) {
                Method m = this.callBackClass.getClass().getMethod(this.callBackMethod, new Class[]{ArrayList.class});
                m.invoke(this.callBackClass, result);
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }

    }
    // Custom method to convert string to url
    public static URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Custom method to save a bitmap into internal storage
    protected Uri saveImageToInternalStorage(Bitmap bitmap, int index){
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(Season.applicationContext);

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images",MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName"+ index+".jpg");

        try{
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        }catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }

    void ImageDownloadComplete(ArrayList<?> bitmaps)
    {
        for(int i=0; i< bitmaps.size();i++)
        {
            System.out.println(i);
        }
    }
}
