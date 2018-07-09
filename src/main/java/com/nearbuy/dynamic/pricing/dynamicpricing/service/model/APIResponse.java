package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

public class APIResponse<T> {
    private Integer st;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    private T result;

    public T getRs() {
        return rs;
    }

    public void setRs(T rs) {
        this.rs = rs;
    }

    private T rs;

    public Integer getSt() {
        return st;
    }

    public void setSt(Integer st) {
        this.st = st;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
