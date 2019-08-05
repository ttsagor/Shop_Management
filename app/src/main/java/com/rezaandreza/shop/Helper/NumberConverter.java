package com.rezaandreza.shop.Helper;

public class NumberConverter {
    public static String NumberEngToBng(double engNumber) {
        return NumberEngToBng(String.valueOf(engNumber));
    }
    public static String NumberEngToBng(String engNumber)
    {
        String bngNumber="";
        if(engNumber==null)
        {
            return "";
        }
        for(char c : engNumber.toCharArray())
        {
            if(c=='0')
            {
                bngNumber+='০';
            }
            else if(c=='1')
            {
                bngNumber+='১';
            }
            else if(c=='2')
            {
                bngNumber+='২';
            }
            else if(c=='3')
            {
                bngNumber+='৩';
            }
            else if(c=='4')
            {
                bngNumber+='৪';
            }
            else if(c=='5')
            {
                bngNumber+='৫';
            }
            else if(c=='6')
            {
                bngNumber+='৬';
            }
            else if(c=='7')
            {
                bngNumber+='৭';
            }
            else if(c=='8')
            {
                bngNumber+='৮';
            }
            else if(c=='9')
            {
                bngNumber+='৯';
            }
            else
            {
                bngNumber+=c;
            }
        }
        return bngNumber;
    }
    public static String NumberBngToEng(String engNumber)
    {
        String bngNumber="";
        for(char c : engNumber.toCharArray())
        {
            if(c=='০')
            {
                bngNumber+='0';
            }
            else if(c=='১')
            {
                bngNumber+='1';
            }
            else if(c=='২')
            {
                bngNumber+='2';
            }
            else if(c=='৩')
            {
                bngNumber+='3';
            }
            else if(c=='৪')
            {
                bngNumber+='4';
            }
            else if(c=='৫')
            {
                bngNumber+='5';
            }
            else if(c=='৬')
            {
                bngNumber+='6';
            }
            else if(c=='৭')
            {
                bngNumber+='7';
            }
            else if(c=='৮')
            {
                bngNumber+='8';
            }
            else if(c=='৯')
            {
                bngNumber+='9';
            }
            else
            {
                bngNumber+=c;
            }
        }
        return bngNumber;
    }
}
