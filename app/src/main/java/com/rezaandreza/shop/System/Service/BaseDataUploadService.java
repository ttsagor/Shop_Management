package com.rezaandreza.shop.System.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.rezaandreza.shop.Helper.Debug.print;

public class BaseDataUploadService extends BaseDataService {

    public int uploadCounter=0;
    public Object callBackClassEach;
    public String callBackMethodEach;
    public Object callBackClassFinal;
    public String callBackMethodFinal;
    public ArrayList<Object> uploadingData;
    public ArrayList<String> uploadedDataList;
    public void uploadData(ArrayList<Object> data,final Object callBackClassEach,final String callBackMethodEach,final Object callBackClassFinal,final String callBackMethodFinal)
    {
        this.callBackClassEach=callBackClassEach;
        this.callBackMethodEach=callBackMethodEach;
        this.callBackClassFinal=callBackClassFinal;
        this.callBackMethodFinal=callBackMethodFinal;
        this.uploadingData=data;
        this.uploadedDataList = new ArrayList<>();
        this.parameterdataClass = this.uploadingData.get(uploadCounter);
        this.getDataFromURLString(this,"uploadEachClassBack");

    }
    public void uploadEachClassBack(String data) {
        try {
            this.uploadedDataList.add(data);
            try {
                if(!this.callBackMethodEach.equals("")) {
                    Method m = this.callBackClassEach.getClass().getMethod(this.callBackMethodEach, Integer.TYPE, String.class);
                    m.invoke(this.callBackClassEach, this.uploadCounter, data);
                }
            } catch (Exception e) {
                System.out.println("errorE"+e.getMessage());
            }
            if (this.uploadCounter >= this.uploadingData.size()-1) {
                try {
                    if(!this.callBackMethodFinal.equals("")) {
                        Method m = this.callBackClassFinal.getClass().getMethod(this.callBackMethodFinal, new Class[]{ArrayList.class});
                        m.invoke(this.callBackClassFinal, this.uploadedDataList);
                    }
                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
            } else {
                this.uploadCounter++;
                this.parameterdataClass = this.uploadingData.get(uploadCounter);
                this.getDataFromURLString(this, "uploadEachClassBack");
            }
        }catch (Exception e){System.out.println("Error: "+e.getMessage());}
    }
}
