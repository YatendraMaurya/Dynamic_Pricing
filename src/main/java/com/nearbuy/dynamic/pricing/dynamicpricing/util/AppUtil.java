package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AppUtil {

    private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);

    public static String IntToStringSlot(int slot) {
        if(slot==0)
            return "BRE";
        else if(slot==1)
            return "LUN";
        else if(slot==2)
            return "EVE";
        else if(slot==3)
            return "DIN";
        else return "ALL";
    }

    public static int StringToIntSlot(String slot){
        if(slot.equalsIgnoreCase("BRE"))
            return 0;
        else if(slot.equalsIgnoreCase("LUN"))
            return 1;
        else if(slot.equalsIgnoreCase("EVE"))
            return 2;
        else if(slot.equalsIgnoreCase("DIN"))
            return 3;
        else return 4;
    }

    public String getJson(Object object){
        return null;
    }

    public static long currentTime(){
        return System.currentTimeMillis();
    }

    public static <T> T parseJson(String json,Type type)
    {
        return new Gson().fromJson(json,type);
    }

    public static <T> T getFromJson(String json, Class<T> class1) {
        //returns the json file back in form of that class object
       // logger.info(new Gson().fromJson(json, class1).toString());
        return new Gson().fromJson(json, class1);
    }

    public static Long getTimeFromDate(String pattern, String value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        Date d = sdf.parse(value);

        Calendar date = Calendar.getInstance();
        date.setTime(d);
        return date.getTimeInMillis();
    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

}
