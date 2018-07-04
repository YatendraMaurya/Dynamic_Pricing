package com.nearbuy.dynamic.pricing.dynamicpricing.factory;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;

public class AppRequestFactory {

    private static final String POST = "post";

    public static <T> AppRestRequest<T> getPostRequest(String url, T body){
      return new AppRestRequest<>(POST, url, null, body);
    }
}
