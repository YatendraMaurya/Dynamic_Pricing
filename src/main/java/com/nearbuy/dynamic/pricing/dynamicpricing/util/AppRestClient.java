package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;

/**
 * all services communications should happen through this class
 */
public class AppRestClient {

    public <T> AppRestResponse<T> fireRequest(AppRestRequest req){
        return null;
    }

}
