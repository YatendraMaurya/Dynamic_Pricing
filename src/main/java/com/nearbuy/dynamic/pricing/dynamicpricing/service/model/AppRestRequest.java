package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.Map;

public class AppRestRequest<T> {

    String type;
    String url;
    Map<String, Object> queryParams;
    T body;
//    Add req header

    public AppRestRequest(String type, String url, Map<String, Object> queryParams, T body) {
        this.type = type;
        this.url = url;
        this.queryParams = queryParams;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
