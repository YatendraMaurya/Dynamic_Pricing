package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * all services communications should happen through this class
 */
@Component
public class AppRestClient {

    private static final Logger logger = LoggerFactory.getLogger(AppRestClient.class);

    public <T> AppRestResponse<T> firePost(AppRestRequest<String, String> req){
        RestTemplate restTemplate=new RestTemplate();
        logger.info(String.valueOf(req.getResponseType().getClass() == null));
//        ResponseEntity response = restTemplate.postForEntity(req.getUrl(), req.getBody(), req.getResponseType());
//        Map<String, String> map = new HashMap<>();
//        map.put("Content-type", "application/json");
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        ArrayList<String> temp = new ArrayList<String>();
//        temp.add("application/json");
//        map.put("Content-type", temp);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(req.getBody(), httpHeaders);
        ResponseEntity response = restTemplate.exchange(req.getUrl(), HttpMethod.POST, httpEntity, req.getResponseType());
        logger.info(String.valueOf(response == null));
        AppRestResponse<T> originalResponse = new AppRestResponse<>();
        originalResponse.setBody((T)response.getBody());
        return originalResponse;
    }

    public <T> AppRestResponse<T> fireGet(AppRestRequest restRequest){return null;}

    public ResponseEntity<String> firePost(){
        return null;
    }

    public ResponseEntity<String> fireGet(){
        return null;
    }

}
