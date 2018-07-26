package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.DiscoveryPostRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDiscoveryResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryService {
    DiscoveryPostRequest discoveryPostRequest;
    String postBody;

    @Autowired
    AppRestClient restClient;

    @Autowired
    AppProperties env;

    public static final Logger logger=LoggerFactory.getLogger(DiscoveryService.class);

    public MerchantDiscoveryResponse getDiscoveryDetail(DiscoveryPostRequest dpr) {
        this.discoveryPostRequest = dpr;
        initRequest();
        String url=env.getProperty("discovery.base") + "/nile-discovery-V2/merchants/listing";
        ResponseEntity<String> resp=restClient.firePostJsonWithCaching(url,null,this.postBody);
        if (resp.getStatusCode().is2xxSuccessful()) {

            return AppUtil.getFromJson(resp.getBody(), MerchantDiscoveryResponse.class);
        } else {

            logger.error("Error in getting Discovery Details for bookingId {}");
            return null;
        }
    }

    private void initRequest(){
        this.postBody = "{  \n" +
                " \"offersOnly\":true,\n" +
                " \"offset\":0,\n" +
                " \"count\":50,\n" +
                " \"vertical\":\"LOCAL\",\n" +
                " \"categoryIds\":[  \n" +
                "    \"FNB\"\n" +
                " ],\n" +
                " \"sortOrder\":\"SCORE_NEAR_BY\",\n" +
                " \"sortPattern\":\"ASC\",\n" +
                " \"customerId\":"+this.discoveryPostRequest.getCUSTOMER_ID()+",\n" +
                " \"workflowTypes\":[  \n" +
                "    \"BOOKING_TYPE2\"\n" +
                " ],\n" +
                " \"source\":\"MOBILE\",\n" +
                " \"location\":{  \n" +
                "    \"coordinates\":{  \n" +
                "       \"lat\":"+this.discoveryPostRequest.getLAT()+",\n" +
                "       \"lng\":"+this.discoveryPostRequest.getLNG()+",\n" +
                "       \"radius\":"+this.discoveryPostRequest.getRADIUS()+"\n" +
                "    }\n" +
                "},\n" +
                "\"dates\":{  \n" +
                "       \"startDate\":"+this.discoveryPostRequest.getSTART_DATE()+",\n" +
                "       \"endDate\":"+this.discoveryPostRequest.getEND_DATE()+"\n" +
                "    },\n" +
                "    \"context\":{  \n" +
                "       \"categoryId\":\"FNB\"\n" +
                "    },\n" +
                " \"slotId\" : "+this.discoveryPostRequest.getSLOT_ID()+"\n" +
                "}";
    }
}
