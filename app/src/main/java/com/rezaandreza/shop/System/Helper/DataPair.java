package com.rezaandreza.shop.System.Helper;

import java.io.ObjectStreamException;
import java.util.ArrayList;

public class DataPair {
    public Object value1;
    public Object value2;

    public DataPair(Object value1,Object value2)
    {
        this.value1 = value1;
        this.value2 = value2;
    }

    public static boolean isPairPresent(ArrayList<DataPair> dataPair,Object value1,Object value2)
    {
        for(DataPair cDataPair : dataPair)
        {
            if(value1.equals(cDataPair.value1) && value1.equals(cDataPair.value1))
            {
                return true;
            }
        }
        return false;
    }

    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    public Object getValue1() {
        return value1;
    }

    public Object getValue2() {
        return value2;
    }
}
