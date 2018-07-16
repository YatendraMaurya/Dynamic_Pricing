package com.nearbuy.dynamic.pricing.dynamicpricing.dao.model;

import java.util.ArrayList;
import java.util.List;

public class PalBooking {
    private long orderid;
    private long merchantid;
    private String status;
    private float latitude;
    private float longitude;
    private long time;
    private ArrayList<Double> cahsback;

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(long merchantid) {
        this.merchantid = merchantid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<Double> getCahsback() {
        return cahsback;
    }

    public void setCahsback(ArrayList<Double> cahsback) {
        this.cahsback = cahsback;
    }

    @Override
    public String toString() {
        return "PalBooking{" +
                "orderid=" + orderid +
                ", merchantid=" + merchantid +
                ", status='" + status + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", time=" + time +
                ", cahsback=" + cahsback +
                '}';
    }
}
