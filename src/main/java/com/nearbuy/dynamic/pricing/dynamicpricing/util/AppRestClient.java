package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * all services communications should happen through this class
 */
@Component
public class AppRestClient {

    private static final Logger logger = LoggerFactory.getLogger(AppRestClient.class);

    private RestTemplate restTemplate;

    public <T> AppRestResponse<T> firePost(AppRestRequest<String, String> req){
        this.restTemplate=new RestTemplate();
        logger.info(String.valueOf(req.getResponseType().getClass() == null));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(req.getBody(), httpHeaders);
        ResponseEntity response = restTemplate.exchange(req.getUrl(), HttpMethod.POST, httpEntity, req.getResponseType());
        AppRestResponse<T> originalResponse = new AppRestResponse<>();
        originalResponse.setBody((T)response.getBody());
        return originalResponse;
    }

    public ResponseEntity<String> firePost(){
        return null;
    }

    public ResponseEntity<String> fireGet(String uri, Map<String, String> headers,Map<String, Object> uriVariables) {
        HttpEntity<String> request = null;
        uriVariables = uriVariables == null ? new HashMap<String, Object>() : uriVariables;

        if(headers != null && headers.size() > 0) {
            HttpHeaders headerObj = new HttpHeaders();
            for(Map.Entry<String, String> entrySet : headers.entrySet()) {
                headerObj.set(entrySet.getKey(), entrySet.getValue());
            }
            request = new HttpEntity<String>(headerObj);
        }
        return fire(uri,HttpMethod.GET,request,String.class,uriVariables,null);
    }

    private <T> ResponseEntity<T> fire(String uri,HttpMethod method,HttpEntity entity,Class<T> responseType,Map<String,Object> uriVariables, ResponseExtractor<ResponseEntity<T>> responseExtractor) {
        ResponseEntity<T> response = null;
        this.restTemplate=new RestTemplate();
        if(responseExtractor != null) {
            if (uriVariables == null) {
                response = restTemplate.execute(uri, method, null, responseExtractor);
            }
            else {
                response =  restTemplate.execute(uri, method, null, responseExtractor, uriVariables);
            }
        }
        else {
            if (uriVariables == null)
                response = restTemplate.exchange(uri, method, entity, responseType);
            else
                response = restTemplate.exchange(uri, method, entity, responseType, uriVariables);
        }
        return response;
    }
}

