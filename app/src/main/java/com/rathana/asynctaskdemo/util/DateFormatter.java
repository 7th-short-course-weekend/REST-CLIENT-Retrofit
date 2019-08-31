package com.rathana.asynctaskdemo.util;

public class DateFormatter {

    public static String format(String date){
        if(date==null)
            return "";
        String year=date.substring(0,4);
        String month=date.substring(4,6);
        String day=date.substring(6,8);

        return day+"/"+month+"/"+year;
    }
}
