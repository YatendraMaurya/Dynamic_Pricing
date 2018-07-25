package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.DealServiceResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealService {

    public static final Logger logger = LoggerFactory.getLogger(DealService.class);

    @Autowired
    AppRestClient appRestClient;

    @Autowired
    AppProperties env;

    public DealServiceResponse getDealDetail(Long merchantId){
        String url = env.getProperty("deal.service")+merchantId+"?isPal=true";
        logger.info(url);
        ResponseEntity<String> resp = appRestClient.fireGetWithCaching(url,null,null);
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(resp.getBody(), DealServiceResponse.class).toString());
            return AppUtil.getFromJson(resp.getBody(), DealServiceResponse.class);
        } else {
            logger.error("Error in getting booking Details for bookingId {}",merchantId);
            return null;
        }
    }

    public List<Long> getOptionIdforGivenSlot(int slot,Long mid){
        List<Long> options = new ArrayList<>();
        DealServiceResponse dealServiceResponse = getDealDetail(mid);
        for(DealServiceResponse.Result res :dealServiceResponse.getResult()){
            for(DealServiceResponse.Offers offers : res.getOffers()){
                if(offers.getOfferValidity().getValidityTimings()[0].getTags()[0].equalsIgnoreCase(AppUtil.IntToStringSlot(slot))){
                        options.add(offers.getKey());
                }
            }
        }
        return options;
    }

}
