package com.nearbuy.dynamic.pricing.dynamicpricing.factory;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;

public class AppRequestFactory {

    private static final String POST = "post";
    private static final String GET = "get";

    public static <T, P> AppRestRequest<T, P> getPostRequest(String url, T body, Class<P> resposeType){
      return new AppRestRequest<T ,P>(POST, url, null,  body, resposeType);
    }

    public static <T> AppRestRequest<Void, T> get(String url, Class<T> resposeType) {
        return new AppRestRequest(GET, url, null, null, resposeType);
    }
}
