package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.Config.CacheManager;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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

    @Autowired
    private RestTemplate restTemplate;

//    public <T> AppRestResponse<T> firePost(AppRestRequest<String, String> req){
//        this.restTemplate=new RestTemplate();
//        logger.info(String.valueOf(req.getResponseType().getClass() == null));
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<?> httpEntity = new HttpEntity<Object>(req.getBody(), httpHeaders);
//        ResponseEntity response = restTemplate.exchange(req.getUrl(), HttpMethod.POST, httpEntity, req.getResponseType());
//        AppRestResponse<T> originalResponse = new AppRestResponse<>();
//        originalResponse.setBody((T)response.getBody());
//        return originalResponse;
//    }


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
        long startTime = AppUtil.currentTime();
        ResponseEntity<T> response = null;
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
        Object resBody;
        if(!response.getStatusCode().is2xxSuccessful()){
            resBody =response.getBody();
        }else{
            resBody = "not logging on STAG";
        }
        long responseTime = AppUtil.currentTime() - startTime;
        String headers = (entity == null  || entity.getHeaders() == null ) ? null : entity.getHeaders().toString();
        String body = (entity == null  || entity.getBody() == null ) ? null : entity.getBody().toString();
        logger.info("\n " +
                        "url : {} \n " +
                        "type : {} \n " +
                        "uriVariables : {} \n " +
                        "headers : {} \n " +
                        "request : {} \n " +
                        "response : {} \n " +
                        "status : {} \n " +
                        "Time Taken : {}"
                ,uri, method.name(),
                uriVariables,
                headers,
                body,
                resBody,
                response.getStatusCode(),responseTime);
        return response;
    }

    public <T> ResponseEntity<String> firePostJson(String uri,Map<String, Object> uriVariables, String body) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;
        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        HttpEntity<String> request = new HttpEntity<>((body),headers);
        return fire(uri,HttpMethod.POST,request,String.class,uriVariables,null);
    }

    public ResponseEntity<String> fireGetWithCaching(String uri, Map<String, String> headers,Map<String, Object> uriVariables) {
        Object response = CacheManager.get(uri);
        ResponseEntity<String> responseEntity ;
        if(response == null){
            responseEntity = fireGet(uri,headers,uriVariables);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                CacheManager.add(responseEntity,uri);
            }
            return responseEntity;
        }
        return (ResponseEntity<String>)response;
    }

    public ResponseEntity<String> firePostJsonWithCaching(String uri,Map<String, Object> uriVariables, String body) {
        Object response = CacheManager.get(uri+body);
        ResponseEntity<String> responseEntity ;
        if(response == null){
            responseEntity = firePostJson(uri,uriVariables,body);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                CacheManager.add(responseEntity,uri+body);
            }
            return responseEntity;
        }
        return (ResponseEntity<String>)response;
    }
}

