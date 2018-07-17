package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.DiscoveryPostRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDiscoveryResponse;
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
    public static final Logger logger=LoggerFactory.getLogger(DiscoveryService.class);

    public MerchantDiscoveryResponse getDiscoveryDetail(DiscoveryPostRequest dpr) {
        this.discoveryPostRequest = dpr;
        initRequest();
        String url="http://unified-discovery.nearbuytoolsstag.in/nile-discovery-V2/merchants/listing";
        ResponseEntity<String> resp=restClient.firePostJsonWithCaching(url,null,this.postBody);
        if (resp.getStatusCode().is2xxSuccessful()) {

            return AppUtil.getFromJson(resp.getBody(), MerchantDiscoveryResponse.class);
        } else {

            logger.error("Error in getting Discovery Details for bookingId {}");
            return null;
        }
    }

    private void initRequest(){
        this.postBody = "{\"offersOnly\":true,\"offset\":0,\"count\":30,\"vertical\":\"LOCAL\"," +
                "\"categoryIds\":[\"FNB\"],\"sortOrder\":\"SCORE_NEAR_BY\",\"sortPattern\":\"ASC\",\"customerId\":"+this.discoveryPostRequest.getCUSTOMER_ID()+
                ",\"workflowTypes\":[\"BOOKING_TYPE2\"],\"source\":\"MOBILE\",\"location\":{\"coordinates\":" +
                "{\"lat\":"+this.discoveryPostRequest.getLAT()+",\"lng\":"+this.discoveryPostRequest.getLNG()+",\"radius\":"+this.discoveryPostRequest.getRADIUS()+"}" +
                ",\"dates\":{\"startDate\":"+this.discoveryPostRequest.getSTART_DATE()+",\"endDate\":"+this.discoveryPostRequest.getEND_DATE()+"}," +
                "\"context\":{\"categoryId\":\"FNB\"}}}";
    }
}
