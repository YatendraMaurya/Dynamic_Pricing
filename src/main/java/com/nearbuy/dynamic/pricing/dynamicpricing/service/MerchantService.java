package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nearbuy.dynamic.pricing.dynamicpricing.factory.AppRequestFactory;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.APIResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDetail;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    private static final Logger logger=LoggerFactory.getLogger(MerchantService.class);
    private static final String IsPublished ="true" ;
    private static String MERCHANT_SERVICE="http://merchant-platform.iwanto.in/api/v2/merchant/";

    @Autowired
    private AppRestClient client;
    //FIXME:CREATE MERCHANTRESPONSE CLASS FOR GETTING RESPONSE IN POJO
    public MerchantDetail getMerchant(Long hostId)
    {
        ResponseEntity<String> resp=client.fireGetWithCaching(MERCHANT_SERVICE + hostId+"?publishedState="+IsPublished,null,null);
        if(resp.getStatusCode().is2xxSuccessful()) {
            return AppUtil.getFromJson(resp.getBody(), MerchantDetail.class);
        }else{
            logger.warn("Invalid response from merchant service for hotspot : {}", hostId);
            return null;
        }
    }

    }

