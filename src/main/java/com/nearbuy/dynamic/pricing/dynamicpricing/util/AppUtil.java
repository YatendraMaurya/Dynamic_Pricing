package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class AppUtil {

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

    public static <T> T getFromJson(String json, Class<T> class1) {//returns the json file back in form of that class object
        return new Gson().fromJson(json, class1);
    }

}
