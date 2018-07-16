package com.nearbuy.dynamic.pricing.dynamicpricing.service;

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
    @Autowired
    AppRestClient restClient;
    public static final Logger logger=LoggerFactory.getLogger(DiscoveryService.class);

    public MerchantDiscoveryResponse getDiscoveryDetail(String body) {
        String url="http://unified-discovery.nearbuytoolsstag.in/nile-discovery-V2/merchants/listing";
        ResponseEntity<String> resp=restClient.firePostJson(url,null,body);
        if (resp.getStatusCode().is2xxSuccessful()) {

            return AppUtil.getFromJson(resp.getBody(), MerchantDiscoveryResponse.class);
        } else {

            logger.error("Error in getting Discovery Details for bookingId {}");
            return null;
        }
    }
}
