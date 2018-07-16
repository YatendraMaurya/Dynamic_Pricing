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
