package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * all services communications should happen through this class
 */
@Component
public class AppRestClient {

    

    public <T> AppRestResponse<T> fireRequest(AppRestRequest req){
        return null;
    }

}
